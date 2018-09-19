package net.inconnection.charge.extend.chargeDevice.deviceManage.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.Device;
import net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.Alarm;
import net.inconnection.charge.extend.chargeDevice.jms.ActiveMQ;
import net.inconnection.charge.extend.chargeDevice.jms.JmsSender;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.message.facet.GatewayFacet;
import net.inconnection.charge.extend.chargeDevice.protocol.message.RequestMsg;
import net.inconnection.charge.extend.chargeDevice.protocol.message.facet.RequestSocketFacet;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmConfigManager;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmInfo;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmInfoConfig;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import net.inconnection.charge.extend.model.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.*;

import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.CONTINUE;
import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.END;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class ChargeSocketComponent implements Device {
    public static final String DEVICEUSED = "1";

    private static final Integer CHARGE_AUTO_DONE = 0;
    private static final Integer CHARGE_ING = 1;
    private static final Integer OVER_FLOW_PROTECT = 2;
    private static final Integer CHARGE_TIME_DONE = 3;

    private static Logger log = LoggerFactory.getLogger(ChargeSocketComponent.class);

    private Long chargeSocketId;
    private Long chargePileId;

    private boolean used;
    private Long startPower;//初始充电功率，单位W
    private Long chargeIntensity = 0L;//电流，单位mA
    private Long chargeTime;//充电时长，单位s
    private Integer chargeState;//充电状态， 1：充电中  0：充电截止 2：过流保护
    private Integer lastChargeState = 0;//上次的充电状态
    private Date lastUpdateTime;
    private Long chargeVoltage = 0L;
    private Long chargePower = 0L;//充电功率，单位 W



    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存该网关的状态数据

    public ChargeSocketComponent(Long chargePileId, Long chargeSocketId){
        this.chargePileId = chargePileId;
        this.chargeSocketId = chargeSocketId;
    }

    public void updateDataToDb(){
        ChargeSocket chargeSocketDo = new ChargeSocket();
        Long socketKey = Long.parseLong(chargePileId.toString() + chargeSocketId.toString());
        chargeSocketDo.setId(socketKey).setIsUsed(used).setStartPower(startPower).setChargePower(chargePower)
                .setChargeIntensity(chargeIntensity).setChargeTime(chargeTime).setChargeState(chargeState).setUpdateTime(lastUpdateTime).update();

        ChargeSocketHistory chargeSocketHistory = new ChargeSocketHistory();
        chargeSocketHistory.setChargeSocketId(socketKey).setChargePileId(chargePileId).setStartPower(startPower).setChargePower(chargePower)
                .setChargeIntensity(chargeIntensity).setChargeState(chargeState).setUpdateTime(lastUpdateTime).save();
    }


    @Override
    public void setGWId(Long gwId) {
        chargePileId = gwId;
    }

    @Override
    public Long getGWId() {
        return chargePileId;
    }

    @Override
    public void setID(Long id) {
        chargeSocketId = id;
    }

    @Override
    public Long getID() {
        return chargeSocketId;
    }

    @Override
    public String toString() {
        return "ChargeSocketComponent{" +
                "chargeSocketId=" + chargeSocketId +
                ", chargePileId=" + chargePileId +
                ", used=" + used +
                ", startPower=" + startPower +
                ", chargeIntensity=" + chargeIntensity +
                ", chargeTime=" + chargeTime +
                ", chargeState=" + chargeState +
                ", lastChargeState=" + lastChargeState +
                ", lastUpdateTime=" + lastUpdateTime +
                ", chargeVoltage=" + chargeVoltage +
                ", chargePower=" + chargePower +
                ", alarmMap=" + alarmMap +
                '}';
    }

    @Override
    public boolean updateData(Date updateTime, JSONObject deviceObj) {

        if (deviceObj.containsKey(MSG_INUSE)){
            lastUpdateTime = updateTime;
            if (DEVICEUSED.equals(deviceObj.getString(MSG_INUSE))) {
                used = true;
            }else {
                //设备没有被占用，
                used = false;
            }

                if (deviceObj.containsKey(MSG_STARTPOWER)){
                    startPower = Long.parseLong(deviceObj.getString(MSG_STARTPOWER));
                } else {
                    log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_STARTPOWER);
                }

                if (deviceObj.containsKey(MSG_CHARGEINTENSITY)){
                    chargeIntensity = Long.parseLong(deviceObj.getString(MSG_CHARGEINTENSITY));
                } else {
                    log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGEINTENSITY);
                }

                if (deviceObj.containsKey(MSG_CHARGETIME)){
                    chargeTime = Long.parseLong(deviceObj.getString(MSG_CHARGETIME));
                } else {
                    log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGETIME);
                }

                if (deviceObj.containsKey(MSG_CHARGESTATE)){
                    chargeState = Integer.parseInt(deviceObj.getString(MSG_CHARGESTATE));
                    //TODO 如果是过流保护，应该添加报警处理
                } else {
                    log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGESTATE);
                }

            chargePower = chargeIntensity * chargeVoltage/1000;

            updateDataToDb();
//            log.info("pile id = " + chargePileId + ", socket Id  =  " + chargeSocketId);
            if (lastChargeState.equals(CHARGE_ING) && (!chargeState.equals(CHARGE_ING)) && chargeTime>0){
                //充电结束,结算费用

                //向设备发送关闭消息
                shutDownChargeSocket();

                calculateFeeAndUpdata(chargePileId, chargeSocketId);
            }

            lastChargeState = chargeState;

        }else {
            log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_INUSE);
        }
        return true;
    }

    private void calculateFeeAndUpdata(Long chargePileId, Long chargeSocketId){

        ChargeHistory chargeHistory = ChargeHistory.dao.findFirst("select * from yc_charge_history where deviceId = ? and socketSn = ? order by operStartTime desc ",new Object[]{chargePileId, chargeSocketId});

        Integer autoChargeUnitPrice = 0;
        Integer autoChargeMoney = 0;

        Integer chargerMoney = 0;
        Integer walletAccountForWeixinPush = 0;

        if (chargeHistory != null){

            Date startTime = chargeHistory.getStartTime();
            Date nowTime = new Date();

            Long recordChargeTime = nowTime.getTime() - startTime.getTime();//单位ms
            Long diffTime = recordChargeTime - chargeTime*1000;
            if ( diffTime > 10*60*1000 || diffTime< -10*60*1000){
                log.error("diviceId = " + chargePileId + " socketId = " + chargeSocketId + "没有找到正确的充电记录，请检查充电记录和充电开始调用结果");
                return;
            }


            chargeHistory.setFeeStatus("S");
            chargeHistory.setRealChargeTime(chargeTime.intValue()/60);
            chargeHistory.setEndTime(new Date());

            Tuser tuser = Tuser.dao.findFirst("select * from tuser where openId = ? ", new Object[]{chargeHistory.getOpenId()});

            if (chargeHistory.getChargeType().equals("auto")){

                autoChargeUnitPrice = chargeHistory.getAutoUnitPrice();

                if (chargeTime < 10*60){
                    //10分钟内不收费
                    autoChargeMoney = 0;
                }else {

                    Double chargeHours = chargeTime/3600.0D;
                    //向上取整计算费用
                    autoChargeMoney = new Double(autoChargeUnitPrice * Math.ceil(chargeHours)).intValue();
                }

                chargerMoney = autoChargeMoney;

                Double realRate = chargeHistory.getRealRate();
                int realMoney = new Double((double)autoChargeMoney * realRate).intValue();
                int giftMoney = autoChargeMoney - realMoney;

                chargeHistory.setChargeMoney(autoChargeMoney);
                chargeHistory.setRealMoney(realMoney);
                chargeHistory.setGiftMoney(giftMoney);

                if (tuser != null) {

                    int walletAccount = tuser.getWalletAccount() - autoChargeMoney;
                    int walletRaelMoney;
                    int walletGiftMony;
                    if (tuser.get("wallet_real_money") != null) {
                        walletRaelMoney = tuser.getWalletRealMoney() - realMoney;
                        if (tuser.get("wallet_gift_money") != null) {
                            walletGiftMony = tuser.getWalletGiftMoney() - giftMoney;
                        } else {
                            walletGiftMony = 0;
                        }
                    } else {
                        walletRaelMoney = tuser.getWalletAccount() - realMoney;
                        walletGiftMony = 0;
                    }

                    double raelGiftRate = walletRaelMoney / (double) walletAccount;
                    tuser.setWalletAccount(walletAccount);
                    tuser.setWalletRealMoney(walletRaelMoney);
                    tuser.setWalletGiftMoney(walletGiftMony);
                    tuser.setRealGitRate(raelGiftRate);

                    tuser.update();

                    walletAccountForWeixinPush = walletAccount;
                }

            }else {
                chargerMoney = chargeHistory.getChargeMoney();
                if (tuser != null){
                    walletAccountForWeixinPush = tuser.getWalletAccount();
                }
            }

            chargeHistory.update();

            String message = "充电完成";
            if (chargeState.equals(CHARGE_AUTO_DONE)){
                message = "监测电池充满断电";
            }else if (chargeState.equals(CHARGE_TIME_DONE)){
                message = "预设时间充电完成";
            }else if (chargeState.equals(OVER_FLOW_PROTECT)){
                message = "监测功率超限断电";
            }

            String title;

            if (chargeHistory.getOperType().equals("M")){
                //临时充电
                title = "您选择临时充电，充电时间" + chargeHistory.getChargeTime() + "分钟，实际充电时间" + chargeTime/60 + "分钟";
            }else {
                if (chargeHistory.getChargeType().equals("auto")){
                    //智能充电
                    title = "您选择会员充满自停，实际充电时间" + chargeTime/60 + "分钟";

                }else {
                    //会员定时充电
                    title = "您选择会员充电，充电时间" + chargeHistory.getChargeTime() + "分钟，实际充电时间" + chargeTime/60 + "分钟";
                }
            }

            sendActiveMqStartCharge(chargeHistory.getOpenId(), chargePileId, chargeSocketId, chargerMoney, walletAccountForWeixinPush, title, message);

        }


        ChargeBatteryInfo chargeBatteryInfo = ChargeBatteryInfo.dao.findFirst("select * from charge_battery_info where deviceId = ? and devicePort = ? order by operStartTime desc ", new Object[]{chargePileId, chargeSocketId});
        if (chargeBatteryInfo != null){

            chargeBatteryInfo.setFeeStatus("S");
            chargeBatteryInfo.setStatus("S");
            if (!autoChargeMoney.equals(0)){
                chargeBatteryInfo.setCharge(autoChargeMoney.toString());
            }
            chargeBatteryInfo.setRealChargeTime(chargeTime.toString());
            chargeBatteryInfo.setEndTime(new Date());

            chargeBatteryInfo.update();
        }
    }

    private void sendActiveMqStartCharge(String openId, Long deviceId, Long devicePort,Integer money, Integer walletAccount, String title, String message){
        JmsSender jmsSender = ActiveMQ.getSender("sendtoWeixinPush");

        ChargePile chargePile = ChargePile.dao.findFirst("select * from yc_charge_pile where id = ? ",new Object[]{deviceId});

        String area;

        if (chargePile != null){
            String province = chargePile.get("province");
            String city = chargePile.get("city");
            String detail_location = chargePile.get("detail_location");
            String name = chargePile.get("name");

            area =  province + city + detail_location + name;

        }else {
            log.error("查询不到充电设备");

            area =  "设备ID" + deviceId + "端口" + devicePort;
        }


        WeiXin weixin = new WeiXin();
        weixin.setArea(area);
        weixin.setChannelNum(devicePort.toString());
        weixin.setDeviceId(deviceId.toString());
        weixin.setMessage(message);
        weixin.setOpenId(openId);
        weixin.setTitle(title );
        weixin.setType("NC");
        weixin.setMoney(money.toString());//单位 分
        weixin.setWalletAccount(walletAccount);//单位 分
        weixin.setChargeTime("");
        weixin.setOperStartTime(new Date());
        String str = JSON.toJSONString(weixin);
        TextMessage msg = null;
        try {
            msg = jmsSender.getSession().createTextMessage(str);
            jmsSender.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    //请求关闭插座
    private void shutDownChargeSocket(){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        GatewayFacet gatewayFacet = new GatewayFacet(sequenceNum, utc, String.format("%012d",chargePileId));
        requestMsg.setGatewayFacet(gatewayFacet);
        requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_SHUTDOWNSOCKET, chargeSocketId.toString()));

        String requestMsgStr = requestMsg.toString();
        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);

    }

    //通过mqtt向硬件推送数据
    private void sendMqttMsg(String topicType, String msg) {
        try {
            //以后改成response
            GeneralTopic stationInfoSend = new GeneralTopic(MQTT_TOPIC_INDUSTRY_CHARGE, String.format("%012d",chargePileId), topicType);//发送主题的前部分和接受到的主题相同
            String msgReplace = msg.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
            MqttMsgSender.getInstance().Send(stationInfoSend.getTopic(), msgReplace,1);
        } catch (MqttException e) {
            log.error("向mqtt推送消息错误!",e.getMessage());
        }
    }

    @Override
    public boolean updateStatus(Date updateTime, JSONObject deviceObj) {

        List<Integer> alarmTagList = new ArrayList<>();
        if (deviceObj.containsKey(MSG_ALARM) ){
            if(!deviceObj.getString(MSG_ALARM).equals("")) {
                JSONArray alarmTagJsonArray = deviceObj.getJSONArray(MSG_ALARM);
                deviceObj.remove(MSG_ALARM);

                for (int index=0; index<alarmTagJsonArray.size(); index++) {
                    Integer deviceAlarmTag = Integer.parseInt(alarmTagJsonArray.getString(index));
                    alarmTagList.add(deviceAlarmTag);
                }
            }
        }

        updateAllAlarm(alarmTagList, updateTime);
        return true;
    }

    private void updateAllAlarm(List<Integer> alarmTagList, Date updateTime){
        //删除所有end的消息
        Iterator<Map.Entry<Integer, Alarm>> it = alarmMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Alarm> entry = it.next();
            if (entry.getValue().getStatus() == END) {
                it.remove();
            }
        }

        //在更新全部报警之前，将所有的报警设置为结束
        for (Alarm alarm : alarmMap.values()){
            alarm.setStatus(END);
        }

        //更新所有的报警
        for (Integer alarmTag : alarmTagList){
            updateAlarm(alarmTag, updateTime);
        }
    }
    private void updateAlarm(Integer alarmTag, Date updateTime) {
        if (alarmMap.containsKey(alarmTag)){
            //已存在的报警就更新时间，状态改为 CONTINUE
            Alarm alarm = alarmMap.get(alarmTag);
            alarm.setEndTime(updateTime);
            alarm.setStatus(CONTINUE);
        }else {
            //不存在的报警就新增，状态为 START
            AlarmInfoConfig alarmConfig = AlarmConfigManager.getInstance().getAlarmConfig("CHARGE_SOCKET");

            if (alarmConfig.containsTag(alarmTag.toString())){

                AlarmInfo alarmInfo = alarmConfig.getAlarmInfo(alarmTag.toString());

                String alarmMessage = alarmInfo.getAlarmMessage();
                Alarm alarm = new Alarm(alarmTag, alarmMessage, updateTime, updateTime);

                alarmMap.put(alarmTag, alarm);

            }else {
                log.error("PVInverter.updateAlarm(): error, cannot find alarmInfo of " + alarmTag + ", the divice type is " + "CHARGE_SOCKET");
            }
        }
    }

    public boolean isUsed() {
        return used;
    }

    public Long getStartPower() {
        return startPower;
    }

    public Long getChargeIntensity() {
        return chargeIntensity;
    }

    public Long getChargeTime() {
        return chargeTime;
    }

    public Integer getChargeState() {
        return chargeState;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setChargeVoltage(Long chargeVoltage) {
        this.chargeVoltage = chargeVoltage;
    }
}

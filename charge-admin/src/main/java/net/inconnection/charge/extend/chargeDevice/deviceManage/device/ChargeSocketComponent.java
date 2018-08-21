package net.inconnection.charge.extend.chargeDevice.deviceManage.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.Device;
import net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.Alarm;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.message.GatewayFacet;
import net.inconnection.charge.extend.chargeDevice.protocol.message.RequestMsg;
import net.inconnection.charge.extend.chargeDevice.protocol.message.RequestSocketFacet;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmConfigManager;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmInfo;
import net.inconnection.charge.extend.chargeDevice.utils.AlarmInfoConfig;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import net.inconnection.charge.extend.model.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.CONTINUE;
import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.END;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class ChargeSocketComponent implements Device {
    public static final String DEVICEUSED = "1";

    private static final Integer CHARGE_DONE = 0;
    private static final Integer CHARGE_ING = 1;
    private static final Integer OVER_FLOW_PROTECT = 2;

    private static Logger _log = LoggerFactory.getLogger(ChargeSocketComponent.class);

    private Long chargeSocketId;
    private Long chargePileId;

    private boolean used;
    private Long startPower;//初始充电功率，单位mW
    private Long chargeIntensity;//电流，单位mA
    private Long chargeTime;//充电时长，单位s
    private Integer chargeState;//充电状态， 1：充电中  0：充电截止 2：过流保护
    private Integer lastChargeState = 0;//上次的充电状态
    private Date lastUpdateTime;



    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存该网关的状态数据

    public ChargeSocketComponent(Long chargePileId, Long chargeSocketId){
        this.chargePileId = chargePileId;
        this.chargeSocketId = chargeSocketId;
    }

    public void updateDataToDb(){
        ChargeSocket chargeSocketDo = new ChargeSocket();
        Long socketKey = Long.parseLong(chargePileId.toString() + chargeSocketId.toString());
        chargeSocketDo.setId(socketKey).setIsUsed(used).setStartPower(startPower)
                .setChargeIntensity(chargeIntensity).setChargeTime(chargeTime).setChargeState(chargeState).setUpdateTime(lastUpdateTime).update();

        ChargeSocketHistory chargeSocketHistory = new ChargeSocketHistory();
        chargeSocketHistory.setChargeSocketId(socketKey).setStartPower(startPower)
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
                ", lastUpdateTime=" + lastUpdateTime +
                ", alarmMap=" + alarmMap +
                '}';
    }

    @Override
    public boolean updateData(Date updateTime, JSONObject deviceObj) {

        if (deviceObj.containsKey(MSG_INUSE)){
            lastUpdateTime = updateTime;
            if (DEVICEUSED.equals(deviceObj.getString(MSG_INUSE))){
                used = true;

                if (deviceObj.containsKey(MSG_STARTPOWER)){
                    startPower = Long.parseLong(deviceObj.getString(MSG_STARTPOWER));
                } else {
                    _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_STARTPOWER);
                }

                if (deviceObj.containsKey(MSG_CHARGEINTENSITY)){
                    chargeIntensity = Long.parseLong(deviceObj.getString(MSG_CHARGEINTENSITY));
                } else {
                    _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGEINTENSITY);
                }

                if (deviceObj.containsKey(MSG_CHARGETIME)){
                    chargeTime = Long.parseLong(deviceObj.getString(MSG_CHARGETIME));
                } else {
                    _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGETIME);
                }

                if (deviceObj.containsKey(MSG_CHARGESTATE)){
                    chargeState = Integer.parseInt(deviceObj.getString(MSG_CHARGESTATE));
                } else {
                    _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_CHARGESTATE);
                }

            }else {

                //设备没有被占用，
                used = false;
                startPower = 0L;
                chargeIntensity = 0L;
                chargeTime = 0L;
                chargeState = 0;
            }
            updateDataToDb();
//            _log.info("pile id = " + chargePileId + ", socket Id  =  " + chargeSocketId);
            if (lastChargeState.equals(CHARGE_ING) && chargeState.equals(CHARGE_DONE) && chargeTime>0){
                //充电完成,结算费用

                //向设备发送关闭消息
                shutDownChargeSocket();

                calculateFeeAndUpdata(chargePileId, chargeSocketId);
            }

            lastChargeState = chargeState;

        }else {
            _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_INUSE);
        }
        return true;
    }

    private void calculateFeeAndUpdata(Long chargePileId, Long chargeSocketId){

        ChargeHistory chargeHistory = ChargeHistory.dao.findFirst("select * from yc_charge_history where deviceId = ? and socketSn = ? order by operStartTime desc ",new Object[]{chargePileId, chargeSocketId});

        Integer autoChargeUnitPrice = 0;
        Integer autoChargeMoney = 0;
        if (chargeHistory != null){
            chargeHistory.setFeeStatus("S");
            chargeHistory.setRealChargeTime(chargeTime.intValue());
            chargeHistory.setEndTime(new Date());
            if (chargeHistory.getChargeType().equals("auto")){

                autoChargeUnitPrice = chargeHistory.getAutoUnitPrice();
                autoChargeMoney = new Double(autoChargeUnitPrice * (double)chargeTime/60).intValue();
                chargeHistory.setChargeMoney(autoChargeMoney);

                Double realRate = chargeHistory.getRealRate();
                int realMoney = new Double(autoChargeMoney * realRate).byteValue();
                int giftMoney = autoChargeMoney - realMoney;

                chargeHistory.setChargeMoney(autoChargeMoney);
                chargeHistory.setRealMoney(realMoney);
                chargeHistory.setGiftMoney(giftMoney);

                Tuser tuser = Tuser.dao.findFirst("select * from tuser where openId = ? ", new Object[]{chargeHistory.getOpenId()});

                int walletAccount = tuser.getWalletAccount() - autoChargeMoney;
                int walletRaelMoney = tuser.getWalletRealMoney() - realMoney;
                int walletGiftMony = tuser.getWalletGiftMoney() - giftMoney;
                double raelGiftRate = walletGiftMony/(double)walletAccount;
                tuser.setWalletAccount(walletAccount);
                tuser.setWalletRealMoney(walletRaelMoney);
                tuser.setWalletGiftMoney(walletGiftMony);
                tuser.setRealGitRate(raelGiftRate);

                tuser.update();

            }

            chargeHistory.update();

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
            _log.error("向mqtt推送消息错误!",e.getMessage());
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
                _log.error("PVInverter.updateAlarm(): error, cannot find alarmInfo of " + alarmTag + ", the divice type is " + "CHARGE_SOCKET");
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
}

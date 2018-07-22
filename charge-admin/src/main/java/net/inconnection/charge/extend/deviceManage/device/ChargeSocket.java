package net.inconnection.charge.extend.deviceManage.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.deviceInterface.Device;
import net.inconnection.charge.extend.deviceManage.alarm.Alarm;
import net.inconnection.charge.extend.utils.AlarmConfigManager;
import net.inconnection.charge.extend.utils.AlarmInfo;
import net.inconnection.charge.extend.utils.AlarmInfoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.inconnection.charge.extend.deviceManage.alarm.AlarmStatus.CONTINUE;
import static net.inconnection.charge.extend.deviceManage.alarm.AlarmStatus.END;
import static net.inconnection.charge.extend.protocol.ProtocolConstant.*;


public class ChargeSocket implements Device {
    public static final String DEVICEUSED = "1";

    private static Logger _log = LoggerFactory.getLogger(ChargeSocket.class);

    private Long chargeSocketId;
    private Long chargePileId;

    private boolean used;
    private Long startPower;//初始充电功率，单位mW
    private Long chargeIntensity;//电流，单位mA
    private Long chargeTime;//充电时长，单位s
    private Integer chargeState;//充电状态， 1：充电中  0：充电截止

    private Date lastUpdateTime;



    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存该网关的状态数据

    public ChargeSocket(Long chargePileId, Long chargeSocketId){
        this.chargePileId = chargePileId;
        this.chargeSocketId = chargeSocketId;
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
        }else {
            _log.error("device message is : " + deviceObj.toJSONString() + ", no " + MSG_INUSE);
        }
        return true;
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

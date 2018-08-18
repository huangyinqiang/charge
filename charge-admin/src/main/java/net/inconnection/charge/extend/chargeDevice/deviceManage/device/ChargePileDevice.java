package net.inconnection.charge.extend.chargeDevice.deviceManage.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.Device;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.GateWay;
import net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.Alarm;
import net.inconnection.charge.extend.chargeDevice.jms.DeviceUpdateMQServer;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.MsgConvertUtil;
import net.inconnection.charge.extend.chargeDevice.protocol.message.*;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.protocol.update.UpdateMsgHandle;
import net.inconnection.charge.extend.chargeDevice.utils.*;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.ChargePileHistory;
import net.inconnection.charge.extend.model.ChargeSocket;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.CONTINUE;
import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.END;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class ChargePileDevice implements GateWay {
    private Long chargePileId;//充电桩Id
    private String name;//充电桩名称

    private Long voltage;//充电电压
    private Long power;//充电功率

    private Integer batVol;//电池电压
    private Integer controllerVol;//控制器供电电压
    private Date lastUpdataTime = null;



    private boolean isOnline;

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存的状态数据

    private Map<Long, Device> chargeSocketMap = new HashMap<>();//该充电桩下的所有插座

    private static Logger _log = LoggerFactory.getLogger(ChargePileDevice.class);
    private Map<Integer, String> requestSNAndCallBackQueueNameMap = new ConcurrentHashMap();

    public ChargePileDevice(Long chargePileId){
        this.chargePileId = chargePileId;
        isOnline = false;//仅仅是初始化，未上线
    }

    void updateOnLineToDb(){
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setId(chargePileId).setIsOnline(isOnline).update();
    }

    void updateDataToDb(){
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setId(chargePileId).setIsOnline(isOnline).setTotalVoltage(voltage).setBatVol(batVol).setControllerVol(controllerVol).setPowerTotal(power).setUpdateTime(lastUpdataTime).update();

        ChargePileHistory chargePileHistory = new ChargePileHistory();
        chargePileHistory.setPileId(chargePileId).setTotalVoltage(voltage).setBatVol(batVol).setControllerVol(controllerVol).setPowerTotal(power).setUpdateTime(lastUpdataTime).save();


    }

    void addNewDeviceToDb(Long chargePileId, Long socketSn){
        ChargeSocket chargeSocket = new ChargeSocket();
        Long socketKey = Long.parseLong(chargePileId.toString() + socketSn.toString());
        chargeSocket.setId(socketKey).setChargePileId(chargePileId).setChargeSocketSn(Integer.parseInt(socketSn.toString())).save();
    }

    public void setVoltage(Long voltage) {
        this.voltage = voltage;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public void setBatVol(Integer batVol) {
        this.batVol = batVol;
    }

    public void setControllerVol(Integer controllerVol) {
        this.controllerVol = controllerVol;
    }

    public void setLastUpdataTime(Date lastUpdataTime) {
        this.lastUpdataTime = lastUpdataTime;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setChargeSocketMap(Map<Long, Device> chargeSocketMap) {
        this.chargeSocketMap = chargeSocketMap;
    }

    @Override
    public void setID(Long id) {
        chargePileId = id;
    }

    @Override
    public Long getID() {
        return chargePileId;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public Device getDevice(Long deviceId) {
        return null;
    }

    @Override
    public Vector<Device> getDevices() {
        return null;
    }

    public void dataMsgHandle(String message) {
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        long startTime=System.currentTimeMillis();//记录开始时间

        String messageIn = getPreMsgStr(message);
        JSONArray messageJsonArr = MsgConvertUtil.msg2Json(messageIn);
        updateDataAndAlarm(messageJsonArr);

        long endTime=System.currentTimeMillis();//记录结束时间
        float excTime=(float)(endTime-startTime)/1000;
        _log.info("PVStation dataMsgHandle convertDataValue2StandardUnit 执行时间："+excTime+"s");
    }

    private void updateDataAndAlarm(JSONArray msgJArray){

        System.out.println("updateDataAndAlarm jarray: " + msgJArray);

        JSONObject gwFacetObj = msgJArray.getJSONObject(0);

        if (chargePileId != Long.parseLong(gwFacetObj.getString(MSG_GWID))){
            _log.error("the id in message is not match the id in topic!!!");
            return;
        }

        Date dataTime= DateUtil.yyyyMMddHHmmssStrToDate(gwFacetObj.getString(MSG_TIME));

        if (null != lastUpdataTime && lastUpdataTime.getTime() > dataTime.getTime()){
            return;
        }

        lastUpdataTime = dataTime;

        updateAlarm(gwFacetObj, dataTime);

        if (gwFacetObj.containsKey(MSG_CHARGEVOLTAGE)){
            voltage = Long.parseLong(gwFacetObj.getString(MSG_CHARGEVOLTAGE));
        }

        if (gwFacetObj.containsKey(MSG_CHARGEPOWER)){
            power = Long.parseLong(gwFacetObj.getString(MSG_CHARGEPOWER));
        }

        if (gwFacetObj.containsKey(MSG_BAT_VOL)){
            batVol = Integer.parseInt(gwFacetObj.getString(MSG_BAT_VOL));
        }

        if (gwFacetObj.containsKey(MSG_CONTROLLER_VOL)){
            controllerVol = Integer.parseInt(gwFacetObj.getString(MSG_CONTROLLER_VOL));
        }

        updateDataToDb();

        for (int i=1; i<msgJArray.size(); i++){
            JSONObject chargeSocketObj = msgJArray.getJSONObject(i);

            Long socketSn = Long.parseLong(gwFacetObj.getString(MSG_GWID) + chargeSocketObj.getString(MSG_DEVICESN));

            if (!chargeSocketMap.containsKey(socketSn)){
                ChargeSocketComponent chargeSocketComponent = new ChargeSocketComponent(chargePileId, socketSn);
                chargeSocketMap.put(socketSn, chargeSocketComponent);
                addNewDeviceToDb(chargePileId, socketSn);
            }

            Device chargeSocket = chargeSocketMap.get(socketSn);

            chargeSocket.updateData(dataTime, chargeSocketObj);

            chargeSocket.updateStatus(dataTime, chargeSocketObj);

        }

        displayAll();
    }

    private void displayAll(){

        System.out.println("---------id: " + chargePileId + "   display-------------");
        System.out.println("name: " + name + " ; voltage: " + voltage + "; power: " + power + "; online: " + isOnline + "; last update time:" + lastUpdataTime.toString());
        System.out.println("status: " + alarmMap);
        System.out.println("sockets: " + chargeSocketMap);

    }

    private void updateAlarm(JSONObject gwFacetObj, Date dataTime) {
        List<Integer> gatewayStatusTagList = new ArrayList<>();
        if (gwFacetObj.containsKey(MSG_GW_STATUS) ){
            if(!gwFacetObj.getString(MSG_GW_STATUS).equals("")) {
                JSONArray gatewayStatusTagJsonArray = gwFacetObj.getJSONArray(MSG_GW_STATUS);
                for (int index=0; index<gatewayStatusTagJsonArray.size(); index++) {
                    Integer gatewayStatusTag = Integer.parseInt(gatewayStatusTagJsonArray.getString(index));
                    gatewayStatusTagList.add(gatewayStatusTag);
                }
            }
        }
        updateAllAlarm(gatewayStatusTagList, dataTime);//这里不在if中处理的原因：每次有新的数据都应该携带状态数据，如果没有携带，认为是状态改变
    }

    public void updateAllAlarm(List<Integer> alarmTagList, Date updateTime){
        //删除所有end的消息,删除一定要用迭代器
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
            updateSingleAlarm(alarmTag, updateTime);
        }

    }
    private void updateSingleAlarm(Integer alarmTag, Date updateTime) {
        if (alarmMap.containsKey(alarmTag)){
            //已存在的报警就更新时间，状态改为 CONTINUE
            Alarm alarm = alarmMap.get(alarmTag);
            alarm.setEndTime(updateTime);
            alarm.setStatus(CONTINUE);
        }else {
            //不存在的报警就新增，状态为 START
            AlarmInfoConfig alarmConfig = AlarmConfigManager.getInstance().getAlarmConfig("CHARGE_PILE");

            if (alarmConfig.containsTag(alarmTag.toString())){
                AlarmInfo alarmInfo = alarmConfig.getAlarmInfo(alarmTag.toString());
                String alarmMessage = alarmInfo.getAlarmMessage();
                Alarm alarm = new Alarm(alarmTag, alarmMessage, updateTime, updateTime);
                alarmMap.put(alarmTag, alarm);
            }else {
                _log.error("error, cannot find alarmInfo of " + alarmTag);
            }
        }
    }


    public void imageMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        //不需要转换


    }
    public void requestMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }
    }

    public void responseMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        long startTime=System.currentTimeMillis();//记录开始时间

        String messageIn = getPreMsgStr(message);
        JSONArray messageJsonArr = MsgConvertUtil.msg2Json(messageIn);

        JSONObject gwFacetObj = messageJsonArr.getJSONObject(0);

        String gwIdStr = gwFacetObj.getString(MSG_GWID);
        if (!chargePileId.equals(Long.parseLong(gwIdStr))){
            _log.error("gwid in message: " + gwIdStr + " is not equal to gwid in topic: " +  chargePileId.toString());
            return;
        }

        Integer responseSequenceNum = Integer.parseInt(gwFacetObj.getString(MSG_SERIALNUMBER));

        if (!requestSNAndCallBackQueueNameMap.containsKey(responseSequenceNum)){
            _log.error("response SequenceNum dose not exist in requestSNAndCallBackQueueNameMap:" + responseSequenceNum.toString());
            return;
        }

        JSONObject responseObj = messageJsonArr.getJSONObject(1);
        if (!responseObj.containsKey(MSG_RESPONSE)){
            _log.error("invalid response message : " + responseObj.toString());
            return;
        }

        String callBackQueueName = requestSNAndCallBackQueueNameMap.get(responseSequenceNum);

        String responseType = responseObj.getString(MSG_RESPONSE);

        switch (responseType){
            case MSG_RESPONCE_CODE_PERMISSIONONLINE:
                if (responseObj.getString(MSG_RESPONCE_RESULT).equals("1")){
                    isOnline = true;
                }
                updateOnLineToDb();
                ActiveMqSender.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);
                break;
            case MSG_RESPONCE_CODE_SHUTDOWNALLSOCKETS:

            case MSG_RESPONCE_CODE_SHUTDOWNSOCKET:

            case MSG_RESPONCE_CODE_TESTSOCKETPOWER:

            case MSG_RESPONCE_CODE_SOCKETSTARTCHARGE:
                ActiveMqSender.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);
                break;
            default:
                break;

        }


    }

    private String getPreMsgStr(String message) {
        String messageOut = message.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
        return messageOut.replaceAll(MSG_FACET_SEPARATOR, MSG_FACET_SEPARATOR_INSIDE);
    }

    public void updateMsgHandle(String message){

        String messageJson = MsgConvertUtil.msg2Json(message).toString();
        //判断消息是否为空
        if (messageJson.equals("[]")){
            return;
        }

        DeviceUpdateMQServer server = DeviceUpdateMQServer.getInstance();
        UpdateMsgHandle.processUpdateResponseMsg(server,messageJson);
    }

    //通过mqtt向硬件推送数据
    private void sendMqttMsg(String topicType, String msg) {
        try {
            //以后改成response
            GeneralTopic stationInfoSend = new GeneralTopic(MQTT_TOPIC_INDUSTRY_CHARGE, getGatewayIdStr(), topicType);//发送主题的前部分和接受到的主题相同
            String msgReplace = msg.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
            MqttMsgSender.getInstance().Send(stationInfoSend.getTopic(), msgReplace,0);
        } catch (MqttException e) {
            _log.error("向mqtt推送消息错误!",e.getMessage());
        }
    }


    //请求允许上线
    public void permissionOnLine(String callBackQueueName){

        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        GatewayFacet gatewayFacet = new GatewayFacet(sequenceNum, utc, getGatewayIdStr());
        requestMsg.setGatewayFacet(gatewayFacet);
        RequestChargePlieFacet requestFacet = new RequestChargePlieFacet(MSG_REQUEST_CODE_PERMISSIONONLINE);
        requestMsg.addRequestFacet(requestFacet);

        String requestMsgStr = requestMsg.toString();

        System.out.println("permissionOnLine: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(gatewayFacet.getSequenceNum(), callBackQueueName);


    }

    private String getGatewayIdStr() {
        return String.format("%012d",chargePileId);
    }

    //请求关闭所有插座
    public void shutDownAllSockets(String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        requestMsg.addRequestFacet(new RequestChargePlieFacet(MSG_REQUEST_CODE_SHUTDOWNALLSOCKETS));

        String requestMsgStr = requestMsg.toString();

        System.out.println("shutDownAllSockets: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求关闭插座
    public void shutDownChargeSocket(Vector<Long> socketIds, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        GatewayFacet gatewayFacet = new GatewayFacet(sequenceNum, utc, getGatewayIdStr());
        requestMsg.setGatewayFacet(gatewayFacet);
        for (Long socketId : socketIds){
            requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_SHUTDOWNSOCKET, socketId.toString()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("shutDownChargeSocket: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(gatewayFacet.getSequenceNum(), callBackQueueName);

    }

    //请求测试充电功率
    public void requestTestPower(Vector<Long> socketIds, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        for (Long socketId : socketIds){
            requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_TESTSOCKETPOWER, socketId.toString()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("requestTestPower: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求插座开始充电
    public void startCharge(Map<Long, Integer> socketIdChargeTimeMap, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        for (Map.Entry<Long, Integer>  entry: socketIdChargeTimeMap.entrySet()){
            requestMsg.addRequestFacet(new RequsetSocketStartChargeFacet(MSG_REQUEST_CODE_SOCKETSTARTCHARGE, entry.getKey().toString(), entry.getValue()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("startCharge: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }




    public static void main(String[] args){


        ChargePileDevice chargePileDevice = new ChargePileDevice(10100000001L);

        String testQueue = "testqueue";

//        chargePileDevice.permissionOnLine(testQueue);


//
//        chargePileDevice.shutDownAllSockets(testQueue);
//
        Vector<Long> sockets = new Vector<>();
        sockets.add(1L);
//        sockets.add(2L);

//        chargePileDevice.shutDownChargeSocket(sockets, testQueue);

        chargePileDevice.requestTestPower(sockets, testQueue);


//        Map<Long, Integer> socketIdChargeTimeMap = new ConcurrentHashMap<>();
//        socketIdChargeTimeMap.put(1L, 60);
//        socketIdChargeTimeMap.put(2L, 60);
//
//        chargePileDevice.startCharge(socketIdChargeTimeMap, testQueue);



    }



}

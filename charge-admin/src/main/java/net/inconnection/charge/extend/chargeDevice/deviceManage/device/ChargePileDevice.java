package net.inconnection.charge.extend.chargeDevice.deviceManage.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.Device;
import net.inconnection.charge.extend.chargeDevice.deviceInterface.GateWay;
import net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.Alarm;
import net.inconnection.charge.extend.chargeDevice.jms.DeviceUpdateMQServer;
import net.inconnection.charge.extend.chargeDevice.jms.ImageTransMQServer;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.MsgConvertUtil;
import net.inconnection.charge.extend.chargeDevice.protocol.image.ImageMsgHandle;
import net.inconnection.charge.extend.chargeDevice.protocol.message.*;
import net.inconnection.charge.extend.chargeDevice.protocol.message.facet.*;
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

    private static final Integer STATUS_OK = 1;
    private static final Integer STATUS_OFFLINE = 2;
    private static final Integer STATUS_ALARM = 3;

    private Long chargePileId;//充电桩Id
    private String name;//充电桩名称

    private Long voltage;//充电电压
    private Long intensity;//充电电流
    private Long power;//充电功率

    private Integer batVol;//电池电压
    private Integer controllerVol;//控制器供电电压
    private Date lastUpdataTime = null;

    private Integer status;



    private boolean isOnline;

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存的状态数据

    private Map<Long, ChargeSocketComponent> chargeSocketMap = new HashMap<>();//该充电桩下的所有插座

    private static Logger _log = LoggerFactory.getLogger(ChargePileDevice.class);
    private Map<Integer, String> requestSNAndCallBackQueueNameMap = new ConcurrentHashMap();

    public ChargePileDevice(Long chargePileId){
        this.chargePileId = chargePileId;
        isOnline = false;//仅仅是初始化，未上线
    }

    void updateOnLineToDb(){
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setId(chargePileId).setIsOnline(isOnline).setOnlineDate(new Date()).update();
    }

    void updateDataToDb(){
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setId(chargePileId).setIsOnline(isOnline).setStatus(status).setTotalVoltage(voltage).setTotalIntensity(intensity).
                setBatVol(batVol).setControllerVol(controllerVol).setPowerTotal(power).setUpdateTime(lastUpdataTime).update();

        ChargePileHistory chargePileHistory = new ChargePileHistory();
        chargePileHistory.setPileId(chargePileId).setTotalVoltage(voltage).setTotalIntensity(intensity).
                setBatVol(batVol).setControllerVol(controllerVol).setPowerTotal(power).setUpdateTime(lastUpdataTime).save();

    }

    void updateStatusToDb(Integer status){
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setId(chargePileId).setStatus(status).setUpdateTime(lastUpdataTime).update();

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

    public void setChargeSocketMap(Map<Long, ChargeSocketComponent> chargeSocketMap) {
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
        _log.info("dataMsgHandle convertDataValue2StandardUnit 执行时间："+excTime+"s");
    }

    private void updateDataAndAlarm(JSONArray msgJArray){

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


        intensity = 0L;
        if (msgJArray.size() > 1) {
            status = STATUS_OK;
            for (int i = 1; i < msgJArray.size(); i++) {
                JSONObject chargeSocketObj = msgJArray.getJSONObject(i);

                Long socketSn = Long.parseLong(chargeSocketObj.getString(MSG_DEVICESN));

                if (!chargeSocketMap.containsKey(socketSn)) {
                    ChargeSocketComponent chargeSocketComponent = new ChargeSocketComponent(chargePileId, socketSn);
                    chargeSocketMap.put(socketSn, chargeSocketComponent);
                    addNewDeviceToDb(chargePileId, socketSn);
                }

                ChargeSocketComponent chargeSocket = chargeSocketMap.get(socketSn);

                chargeSocket.setChargeVoltage(voltage);

                chargeSocket.updateData(dataTime, chargeSocketObj);

                chargeSocket.updateStatus(dataTime, chargeSocketObj);

                intensity += chargeSocket.getChargeIntensity();
            }
        }else {
            //只有gateway，没有强电板数据
            _log.error("充电桩没有携带强电板数据，请检查，充电设备id为： " + chargePileId);
            status = STATUS_ALARM;
        }

        updateDataToDb();

//        displayAll();
    }

    private void displayAll(){

        _log.info("---------id: " + chargePileId + "   display-------------");
        _log.info("name: " + name + " ; voltage: " + voltage + "; power: " + power + "; online: " + isOnline + "; last update time:" + lastUpdataTime.toString());
        _log.info("status: " + alarmMap);
        _log.info("sockets: " + chargeSocketMap);

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
        String messageJson = MsgConvertUtil.msg2Json(message).toString();
        //判断消息是否为空
        if (messageJson.equals("[]")){
            return;
        }

        ImageTransMQServer server = ImageTransMQServer.getInstance();
        ImageMsgHandle.processImageResponseMsg(server,messageJson);

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
                _log.info("callBackQueueName : " + callBackQueueName);
                _log.info("message: " + messageJsonArr);
                ActiveMqSender.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);
                break;
            case MSG_RESPONCE_CODE_SHUTDOWNALLSOCKETS:

            case MSG_RESPONCE_CODE_SHUTDOWNSOCKET:

            case MSG_RESPONCE_CODE_TESTSOCKETPOWER:

            case MSG_RESPONCE_CODE_SOCKETSTARTCHARGE:

            case MSG_RESPONCE_CODE_DELETEIMAGE:

            case MSG_RESPONCE_CODE_SHOWIMAGE:

                ActiveMqSender.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);

                _log.info("callBackQueueName : " + callBackQueueName);
                _log.info("message: " + messageJsonArr);
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

    public void willMsgHandle(String message){

        //判断消息是否为空
        if (message.isEmpty() || message.equals("")){
            return;
        }

        status = STATUS_OFFLINE;
        updateStatusToDb(status);

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

        _log.info("permissionOnLine: \n\r" + requestMsgStr);

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

        _log.info("shutDownAllSockets: \n\r" + requestMsgStr);

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

        _log.info("shutDownChargeSocket: \n\r" + requestMsgStr);

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

        _log.info("requestTestPower: \n\r" + requestMsgStr);

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

        _log.info("startCharge: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求删除某广告图片
    public void deleteImage(String imageName, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        requestMsg.addRequestFacet(new RequestDeleteImageFacet(MSG_REQUEST_CODE_DELETEIMAGE, imageName));

        String requestMsgStr = requestMsg.toString();

        _log.info("deleteImage: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求设置某广告图片显示效果
    public void showImage(String imageName, Integer timeLast, Integer xPoint, Integer yPoint, String startTime, String endTime, Integer dayLast, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        requestMsg.addRequestFacet(new RequestShowImageFacet(MSG_REQUEST_CODE_SHOWIMAGE, imageName,timeLast, xPoint, yPoint, startTime, endTime, dayLast));

        String requestMsgStr = requestMsg.toString();

        _log.info("set show image: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }



    public static void main(String[] args){


        ChargePileDevice chargePileDevice = new ChargePileDevice(201808000020L);

        String testQueue = "testqueue";

        chargePileDevice.permissionOnLine(testQueue);


//
//        chargePileDevice.shutDownAllSockets(testQueue);
//
//        Vector<Long> sockets = new Vector<>();
//        sockets.add(1L);
////        sockets.add(2L);
//
////        chargePileDevice.shutDownChargeSocket(sockets, testQueue);
//
//        chargePileDevice.requestTestPower(sockets, testQueue);


//        Map<Long, Integer> socketIdChargeTimeMap = new ConcurrentHashMap<>();
//        socketIdChargeTimeMap.put(1L, 60);
//        socketIdChargeTimeMap.put(2L, 60);
//
//        chargePileDevice.startCharge(socketIdChargeTimeMap, testQueue);
//
//        chargePileDevice.deleteImage("123", testQueue);
//
//        chargePileDevice.showImage("123",60,null,null,null,null,null , testQueue);
//


    }



}

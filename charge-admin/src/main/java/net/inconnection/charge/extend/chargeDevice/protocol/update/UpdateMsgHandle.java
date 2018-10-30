package net.inconnection.charge.extend.chargeDevice.protocol.update;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import net.inconnection.charge.extend.chargeDevice.jms.DeviceUpdateMQServer;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.message.DeviceUpdateMsg;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;
import net.inconnection.charge.extend.model.ChargePileHardwareVersion;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_CUR_VERSION;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_INDUSTRY_CHARGE;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.TOPIC_UPDATE;


public class UpdateMsgHandle {

    private static Log _log = Log.getLog(UpdateMsgHandle.class);

    public static void sendFirstFirmwareDataFrame(DeviceUpdateMQServer server, String key, JSONObject jsonObject){
        _log.info("key:"+key);
        _log.info("正在发送升级数据");
        String industry = jsonObject.getString("industry");
        String protocolVersion = jsonObject.getString("protocolVersion");
        BigInteger gwid = new BigInteger(jsonObject.getString("gwid"));
        String hardWareVersion = jsonObject.getString("version");
        String seq = jsonObject.getString("seq");
        String timeStr = jsonObject.getString("timeStr");

        byte[] firmwareFileName = RedisUtil.get(gwid.toString().getBytes());

        //获取升级的buff
        byte[] buff = RedisUtil.get(firmwareFileName);

        if(buff == null || buff.length == 0){
            response2Client(server,key,13);
            return;
        }


        //生成主题
        GeneralTopic generalTopic = new GeneralTopic();
        String topic = generalTopic.generateTopic(industry, protocolVersion, String.format("%012d",gwid) , TOPIC_UPDATE);

        //update数据处理类,可进行编码解码等操作
        DeviceUpdateMsg deviceUpdateMsg = new DeviceUpdateMsg(seq, timeStr, gwid, hardWareVersion, 0, 0, buff.length, 0);
        byte[] bytes = deviceUpdateMsg.updateEncode();

        //向mqtt发送数据
        sendMqttMsg(topic,bytes);

        //等待反馈的方法,若网关无响应则等待10分钟删除对应信息
        removeDataIfTimeout(server,key);
    }

    //mqtt接收消息后处理update类型消息的方法
    public static void processUpdateResponseMsg(DeviceUpdateMQServer server, String updateResMsgJson) {
        //update消息解析类获取message的各个属性
        DeviceUpdateMsg deviceUpdateMsgRecive = new DeviceUpdateMsg();
        deviceUpdateMsgRecive.updateDecode(updateResMsgJson);
        String seq = deviceUpdateMsgRecive.getSequenceNum();
        String timeStr = deviceUpdateMsgRecive.getTimeStr();
        BigInteger gwid = deviceUpdateMsgRecive.getGwid();

        int offset = deviceUpdateMsgRecive.getOffset();
        int status = deviceUpdateMsgRecive.getStatus();

        //socketMap管理socket的唯一key
        String gwIdStr = gwid.toString();

        String hardWareVersion = server.getData(gwIdStr).getString("version");

        //生成主题
        GeneralTopic generalTopic = new GeneralTopic();
        String topic = generalTopic.generateTopic(MQTT_TOPIC_INDUSTRY_CHARGE, MQTT_TOPIC_CUR_VERSION, String.format("%012d",gwid) , TOPIC_UPDATE);

        byte[] firmwareFileName = RedisUtil.get(gwid.toString().getBytes());
        byte[] firmwareDataBytes = RedisUtil.get(firmwareFileName);
        byte[] updateMsgToSend = null;
        if(firmwareDataBytes.length != offset){
            UpdateDevice updateDevice = new UpdateDevice(firmwareDataBytes);
            updateDevice.analysisFile(offset);

            DeviceUpdateMsg deviceUpdateMsgSend = new DeviceUpdateMsg(seq, timeStr, gwid, hardWareVersion, offset, updateDevice.getLength(), updateDevice.getLengthAll(), updateDevice.getCrc());
            byte[] updateMsgPrefix = deviceUpdateMsgSend.updateEncode();//获取到crc校验之前的所有消息数据
            byte[] firmwareBinDataFrame = updateDevice.getBytes();//获取到当前需要发送的二进制的固件文件

            updateMsgToSend = new byte[updateMsgPrefix.length + firmwareBinDataFrame.length];
            //下面进行消息断的拼接
            System.arraycopy(updateMsgPrefix, 0, updateMsgToSend, 0, updateMsgPrefix.length);
            System.arraycopy(firmwareBinDataFrame, 0, updateMsgToSend, updateMsgPrefix.length, firmwareBinDataFrame.length);
        }

        _log.info("**********************");
        _log.info("网关已发来数据应答!");
        _log.info("status=" + status);
        _log.info("**********************");

        switch (status) {
            case 0:
                _log.info("校验有误,正在重发!");
                sendMqttMsg(topic, updateMsgToSend);
                break;
            case 1:
                if(firmwareDataBytes.length == offset){
                    response2Client(server,gwIdStr , status);
                    _log.info("数据发送成功,网关正在重启请稍后!");
                }else {
                    _log.info("收到回复,正在发送下一数据包!");
                    sendMqttMsg(topic, updateMsgToSend);
                }
                break;
            case 3:
                _log.info("子站网关处于电池供电,退出更新!");
                response2Client(server,gwIdStr , status);
                break;
            case 4:
                _log.info("内部flash损坏,不可升级!");
                response2Client(server,gwIdStr, status);
                break;
            case 5:
                _log.info("版本已最新,无需升级!");
                response2Client(server,gwIdStr, status);
                break;
            case 10:
                _log.info("升级成功,正在返回页面!");
                //记录硬件设备版本
                ChargePileHardwareVersion chargePileHardwareVersion = new ChargePileHardwareVersion();
                chargePileHardwareVersion.setId(gwid.longValue());
                chargePileHardwareVersion.setVersion(hardWareVersion);
                chargePileHardwareVersion.setUpdateTime(new Date());
                List<ChargePileHardwareVersion> chargePileHardwareVersionList = ChargePileHardwareVersion.dao.find("select * from yc_charge_pile_hardware_version where id=" + gwid);
                if (chargePileHardwareVersionList == null || chargePileHardwareVersionList.isEmpty()){
                    //不存在则创建
                    chargePileHardwareVersion.save();
                }else {
                    //已存在则更新
                    chargePileHardwareVersion.update();
                }
                response2Client(server,gwIdStr, status);
                break;
            case 11:
                _log.info("升级失败,正在返回页面!");
                response2Client(server,gwIdStr, status);
                break;
            case 12:
                _log.info("其他用户正在升级，请勿操作!");
                response2Client(server,gwIdStr, status);
                break;
            default:
                _log.info("其他错误,正在返回页面!");
                response2Client(server,gwIdStr, status);
                break;
        }
    }

    //向mqtt发送数据的方法
    private static void sendMqttMsg(String topic, byte[] bytes) {
        try {
            _log.info("向mqtt服务器发送数据,消息主题为:"+topic);
            MqttMsgSender.getInstance().Send(topic, bytes, 1);
        } catch (Exception e) {
            _log.error("ImageMsgHandle >> sendMqttMsg Exception",e);
        }
    }

    private static void response2Client(DeviceUpdateMQServer server, String key, int status) {

        server.response2Client(key,status+"");
    }

    private static void removeDataIfTimeout(DeviceUpdateMQServer server, String key){
        Thread deleteDataThread = new Thread(() -> server.removeData(key));
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            _log.error("休眠出错!",e);
        }
        deleteDataThread.start();
    }
}

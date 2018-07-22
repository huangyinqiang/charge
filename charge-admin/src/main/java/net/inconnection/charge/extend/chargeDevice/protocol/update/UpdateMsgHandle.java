package net.inconnection.charge.extend.chargeDevice.protocol.update;

import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.listener.ActiveMQMsgServer;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.TOPIC_UPDATE;


public class UpdateMsgHandle {

    private static Logger _log = LoggerFactory.getLogger(UpdateMsgHandle.class);

    //处理update的queue,若加入其他业务的处理则在下方加入新的queue
    private static final String UPDATE_QUEUE = "update_queue";

    public static void firstSend(ActiveMQMsgServer server, String key, JSONObject jsonObject){
        _log.info("key:"+key);
        _log.info("正在发送升级数据");
        String industry = jsonObject.getString("industry");
        String protocolVersion = jsonObject.getString("protocolVersion");
        BigInteger gwid = new BigInteger(jsonObject.getString("gwid"));
        String hardWareVersion = jsonObject.getString("version");
        String seq = jsonObject.getString("seq");
        String timeStr = jsonObject.getString("timeStr");

        //String buffKey = province + "/" + city + "/" + powerstationName;
        //String key = buffKey + "/" + gwid + "/" + version;

        //获取升级的buff
        byte[] buff = RedisUtil.get((industry + "/" + protocolVersion + "/" + gwid.toString()).getBytes());

        if(buff == null || buff.length == 0){
            response(server,key,13);
            return;
        }

        /*if(server.isUpdating(key)){
            response(server,key,12);
            return;
        }*/

        //生成主题
        GeneralTopic generalTopic = new GeneralTopic();
        String topic = generalTopic.generateTopic(industry, protocolVersion, gwid.toString() , TOPIC_UPDATE);

        //update数据处理类,可进行编码解码等操作
        UpdateMsgProcesser ump = new UpdateMsgProcesser(seq, timeStr, gwid, hardWareVersion, 0, 0, buff.length, 0);
        byte[] bytes = ump.updateEncode();

        //向mqtt发送数据
        send(topic,bytes);

        //等待反馈的方法,若网关无响应则等待10分钟删除对应信息
        removeData(server,key);
    }

    //mqtt接收消息后处理update类型消息的方法
    public static void handle(ActiveMQMsgServer server,String industryAndVersion,String message) {
        //update消息解析类获取message的各个属性
        UpdateMsgProcesser ump = new UpdateMsgProcesser();
        ump.updateDecode(message);
        String seq = ump.getSequenceNum();
        String timeStr = ump.getTimeStr();
        BigInteger gwid = ump.getGwid();

        int offset = ump.getOffset();
        int status = ump.getStatus();

        //socketMap管理socket的唯一key
        String key = industryAndVersion+"/"+gwid;

        String hardWareVersion = server.getData(key).getString("version");

        //获取主题
        //生成主题
        GeneralTopic generalTopic = new GeneralTopic();
        String topic = generalTopic.generateTopic(industryAndVersion.split("/")[0], industryAndVersion.split("/")[1], gwid.toString() , TOPIC_UPDATE);

        byte[] keyBytes = (industryAndVersion + "/" + gwid.toString()).getBytes();
        byte[] buff = RedisUtil.get(keyBytes);
        byte[] bytes = null;
        if(!(buff.length == offset)){
            UpdateDevice ud = new UpdateDevice(buff);
            ud.analysisFile(offset);

            UpdateMsgProcesser updateMsgProcesser = new UpdateMsgProcesser(seq, timeStr, gwid, hardWareVersion, offset, ud.getLength(), ud.getLengthAll(), ud.getCrc());
            byte[] bytes1 = updateMsgProcesser.updateEncode();
            byte[] bytes2 = ud.getBytes();

            bytes = new byte[bytes1.length + bytes2.length];
            System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
            System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);
        }

        _log.info("**********************");
        _log.info("网关已发来数据应答!");
        _log.info("status=" + status);
        _log.info("**********************");

        switch (status) {
            case 0:
                _log.info("校验有误,正在重发!");
                send(topic, bytes);
                break;
            case 1:
                if(buff.length == offset){
                    _log.info("数据发送成功,网关正在重启请稍后!");
                }else {
                    _log.info("收到回复,正在发送下一数据包!");
                    send(topic, bytes);
                }
                break;
            case 3:
                _log.info("子站网关处于电池供电,退出更新!");
                response(server,key , status);
                break;
            case 4:
                _log.info("内部flash损坏,不可升级!");
                response(server,key, status);
                break;
            case 5:
                _log.info("版本已最新,无需升级!");
                response(server,key, status);
                break;
            case 10:
                _log.info("升级成功,正在返回页面!");
                response(server,key, status);
                break;
            case 11:
                _log.info("升级失败,正在返回页面!");
                response(server,key, status);
                break;
            case 12:
                _log.info("其他用户正在升级，请勿操作!");
                response(server,key, status);
                break;
            default:
                _log.info("其他错误,正在返回页面!");
                response(server,key, status);
                break;
        }
    }

    //向mqtt发送数据的方法
    private static void send(String topic, byte[] bytes) {
        try {
            _log.info("向mqtt服务器发送数据,消息主题为:"+topic);
            MqttMsgSender.getInstance().Send(topic, bytes, 1);
        } catch (Exception e) {
            _log.error("UpdateMsgHandle >> send Exception",e);
        }
    }

    private static void response(ActiveMQMsgServer server,String key,int status) {
        /*TextMessage tempMessage = server.getTextMessage(key);
        if(tempMessage == null){
            _log.warn("无key:"+key+"对应消息!");
            return;
        }
        try {
            TextMessage response = server.getSession().createTextMessage();
            response.setText(status+"");
            response.setJMSCorrelationID(key);
            server.getProducer().send(tempMessage.getJMSReplyTo(),response);
        } catch (JMSException e) {
            _log.error("新建消息错误!",e);
        }finally {
            server.removeData(key);
        }*/
        server.response(key,status+"");
    }

    private static void removeData(ActiveMQMsgServer server, String key){
        Thread deleteDataThread = new Thread(() -> server.removeData(key));
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            _log.error("休眠出错!",e);
        }
        deleteDataThread.start();
    }
}

package net.inconnection.charge.extend.chargeDevice.protocol.image;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import net.inconnection.charge.extend.chargeDevice.jms.ImageTransMQServer;
import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.message.ImageTransMsg;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;

import java.math.BigInteger;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class ImageMsgHandle {

    private static Log _log = Log.getLog(ImageMsgHandle.class);

    public static void sendFirstFirmwareDataFrame(ImageTransMQServer server, String key, JSONObject jsonObject){
        _log.info("key:"+key);
        _log.info("正在发送图像数据");
        String industry = jsonObject.getString("industry");
        String protocolVersion = jsonObject.getString("protocolVersion");
        BigInteger gwid = new BigInteger(jsonObject.getString("gwid"));
        String imageName = jsonObject.getString("imageName");
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
        ImageTransMsg imageTransMsg = new ImageTransMsg(seq, timeStr, gwid, imageName, 0, 0, buff.length, 0);
        byte[] bytes = imageTransMsg.imageTransMsgEncode();

        //向mqtt发送数据
        sendMqttMsg(topic,bytes);

        //等待反馈的方法,若网关无响应则等待10分钟删除对应信息
        removeDataIfTimeout(server,key);
    }

    //mqtt接收消息后处理update类型消息的方法
    public static void processImageResponseMsg(ImageTransMQServer server, String updateResMsgJson) {
        //update消息解析类获取message的各个属性
        ImageTransMsg imageTransMsg = new ImageTransMsg();
        imageTransMsg.imageTransMsgDecode(updateResMsgJson);
        String seq = imageTransMsg.getSequenceNum();
        String timeStr = imageTransMsg.getTimeStr();
        BigInteger gwid = imageTransMsg.getGwid();

        int offset = imageTransMsg.getOffset();
        int status = imageTransMsg.getStatus();

        //socketMap管理socket的唯一key
        String gwIdStr = gwid.toString();

        String imageName = server.getData(gwIdStr).getString("imageName");

        //生成主题
        GeneralTopic generalTopic = new GeneralTopic();
        String topic = generalTopic.generateTopic(MQTT_TOPIC_INDUSTRY_CHARGE, MQTT_TOPIC_CUR_VERSION, String.format("%012d",gwid) , TOPIC_UPDATE);

        byte[] imageFileName = RedisUtil.get(gwid.toString().getBytes());
        byte[] imageDataBytes = RedisUtil.get(imageFileName);
        byte[] imageTransMsgToSend = null;
        if(imageDataBytes.length != offset){
            ImageTransAnaly imageTransAnaly = new ImageTransAnaly(imageDataBytes);
            imageTransAnaly.analysisFile(offset);

            ImageTransMsg imageTransMsgSend = new ImageTransMsg(seq, timeStr, gwid, imageName, offset, imageTransAnaly.getLength(), imageTransAnaly.getLengthAll(), imageTransAnaly.getCrc());
            byte[] imageTransMsgPrefix = imageTransMsgSend.imageTransMsgEncode();//获取到crc校验之前的所有消息数据
            byte[] imageBinDataFrame = imageTransAnaly.getBytes();//获取到当前需要发送的图片的二进制数据

            imageTransMsgToSend = new byte[imageTransMsgPrefix.length + imageBinDataFrame.length];
            //下面进行消息断的拼接
            System.arraycopy(imageTransMsgPrefix, 0, imageTransMsgToSend, 0, imageTransMsgPrefix.length);
            System.arraycopy(imageBinDataFrame, 0, imageTransMsgToSend, imageTransMsgPrefix.length, imageBinDataFrame.length);
        }

        _log.info("**********************");
        _log.info("网关已发来图片数据应答!");
        _log.info("status=" + status);
        _log.info("**********************");

        switch (status) {
            case 0:
                _log.info("校验有误,正在重发!");
                sendMqttMsg(topic, imageTransMsgToSend);
                break;
            case 1:
                if(imageDataBytes.length == offset){
                    _log.info("数据发送成功,网关正在重启请稍后!");
                }else {
                    _log.info("收到回复,正在发送下一数据包!");
                    sendMqttMsg(topic, imageTransMsgToSend);
                }
                break;
            case 2:
                _log.info("存储空间不够!");
                response2Client(server,gwIdStr , status);
                break;
            case 3:
                _log.info("图片索引已满");
                response2Client(server,gwIdStr, status);
                break;
            case 4:
                _log.info("图片大小超限");
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

    private static void response2Client(ImageTransMQServer server, String key, int status) {

        server.response2Client(key,status+"");
    }

    private static void removeDataIfTimeout(ImageTransMQServer server, String key){
        Thread deleteDataThread = new Thread(() -> server.removeData(key));
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            _log.error("休眠出错!",e);
        }
        deleteDataThread.start();
    }
}

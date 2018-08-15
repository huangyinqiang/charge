package net.inconnection.charge.extend.chargeDevice.protocol;


import com.jfinal.log.Log;
import net.inconnection.charge.extend.chargeDevice.utils.MqttReconnectUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_BROKER_IP;


public class MqttMsgSender {
    private static Log _log = Log.getLog(MqttMsgSender.class);
    public static final String HOST = MQTT_BROKER_IP; //设置端口
    private static final String clientid = "MqttMsgSender";

    private MqttClient client;

    static MqttMsgSender mqttMsgSenderInstance = new MqttMsgSender();

    public static MqttMsgSender getInstance(){
        if (mqttMsgSenderInstance == null){
            mqttMsgSenderInstance = new MqttMsgSender();
        }

        return mqttMsgSenderInstance;
    }


    private MqttMsgSender(){
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        connect();
    }

    private void connect() {
        try {
            String clientid = "MqttMsgSender_" + UUID.randomUUID();
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
        }catch (MqttException e){
            _log.error(e.getMessage());
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);

        // 设置超时时间
        options.setConnectionTimeout(300);
        // 设置会话心跳时间
        options.setKeepAliveInterval(200);
        try {
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    _log.error("MqttMsgSender.connectionLost():掉线");
                    long start = System.currentTimeMillis();
                    String result = MqttReconnectUtil.reconnect(client,options);
                    long end = System.currentTimeMillis();
                    double reconnectTime = (end-start)/1000;
                    _log.info("MqttMsgSender重连成功!断线时长为:"+reconnectTime+"秒");
                    if(result.split("/")[0].equals("1")){
                        connect();
                    }
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            client.connect(options);
        } catch (Exception e) {
            _log.error("MqttMsgSender.connect().Exception:"+e);
            e.printStackTrace();
        }
    }

    //MqttMessage message
    private void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException,//推送方法
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        _log.info("message is published completely! "
                + token.isComplete());
    }

    //实施方法
    public void Send(String topicStr, String msg) throws MqttException {

        MqttTopic topic = client.getTopic(topicStr);
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(false);
        message.setPayload(msg.getBytes());//获取推送的消息
        publish(topic, message);

        _log.info("MqttMsgSender.Send():"+topicStr);
        _log.info("MqttMsgSender.Send():ratained状态: " + message.isRetained());

    }

    public void Send(String topicStr, String msg ,int qos) throws MqttException {

        MqttTopic topic = client.getTopic(topicStr);
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(false);
        message.setPayload(msg.getBytes());//获取推送的消息
        publish(topic, message);

        _log.info("MqttMsgSender.Send():"+topicStr);
        _log.info("MqttMsgSender.Send():ratained状态: " + message.isRetained());
        _log.info("MqttMsgSender.Send():发送的消息为:"+msg);

    }

    public void Send(String topicStr, byte[] bytes ,int qos) throws MqttException, UnsupportedEncodingException {

        MqttTopic topic = client.getTopic(topicStr);
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(false);
        message.setPayload(bytes);//获取推送的消息
        publish(topic, message);
        _log.info("MqttMsgSender.Send():"+topicStr);
        _log.info("MqttMsgSender.Send():"+message);
        _log.info(message.isRetained() + "------ratained状态");

    }


}

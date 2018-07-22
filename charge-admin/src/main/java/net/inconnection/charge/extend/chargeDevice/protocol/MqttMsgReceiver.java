package net.inconnection.charge.extend.chargeDevice.protocol;

import net.inconnection.charge.extend.chargeDevice.jms.ActiveMQConstant;
import net.inconnection.charge.extend.chargeDevice.jms.ActiveMQSender;
import net.inconnection.charge.extend.chargeDevice.utils.CommunicateBySocket;
import net.inconnection.charge.extend.chargeDevice.utils.MqttReconnectUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class MqttMsgReceiver {

    private MqttClient client;
    private ActiveMQSender activeMQSendertoMqttProcessers = new ActiveMQSender(ActiveMQConstant.MQTT_TO_MqttMsgProcesser);
    private static Logger _log = LoggerFactory.getLogger(MqttMsgReceiver.class);

    //单例模式获取唯一对象
    private volatile static MqttMsgReceiver mqttMsgReceiver;

    private MqttMsgReceiver(){}

    public static MqttMsgReceiver getInstance() {
        if (mqttMsgReceiver == null) {
            synchronized (MqttMsgReceiver.class) {
                if (mqttMsgReceiver == null) {
                    mqttMsgReceiver = new MqttMsgReceiver();
                    Thread chSubscribeThread = new Thread(() -> CommunicateBySocket.getMsg(mqttMsgReceiver));
                    chSubscribeThread.start();
                }
            }
        }
        return mqttMsgReceiver;
    }

    public void start() {

        System.out.println("ddddddddddddddddd");

        try {
            String clientid = "MqttMsgReceiver_" + UUID.randomUUID();
            client = new MqttClient(MQTT_BROKER_IP, clientid, new MemoryPersistence());
        } catch (MqttException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }

        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //options.setUserName("userName");
        //options.setPassword("iioss99!".toCharArray());
        // 设置回调
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                //TODO 这里必须添加连接断掉之后的逻辑
                _log.error("MqttMsgReceiver.connectionLost():掉线");
                long start = System.currentTimeMillis();
                String[] result = MqttReconnectUtil.reconnect(client,options).split("/");
                long end = System.currentTimeMillis();
                double reconnectTime = (end-start)/1000;
                _log.info("重连完成,断线时长为:"+reconnectTime+"秒");
                if(result[0].equals("1")){
                    start();
                    return;
                }
                if(result[1].equals("1")){
                    subscribe();
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                _log.info("MqttMsgReceiver接收消息主题 : " + topic + ", 消息Qos : " + message.getQos());

                String messageStr = new String(message.getPayload(),"ISO-8859-1");
                String messageIn = messageStr;
                String[] type = topic.split("/");
                if((type[type.length-1]).equals("data")){
                    _log.info("data类型消息");
                }else {
                    _log.info("其他类型消息");
                    String messageOut = messageStr.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
                    messageIn = messageOut.replaceAll(MSG_FACET_SEPARATOR, MSG_FACET_SEPARATOR_INSIDE);
                }
                _log.info("MqttMsgReceiver接收消息内容为 : \n" + messageIn);

                TopicAndMsgStruct topicAndMsgStruct = new TopicAndMsgStruct(topic, messageIn);

                String msg4MQ = topicAndMsgStruct.toString();//MQTT主题和MQTT消息拼接为一个消息

                activeMQSendertoMqttProcessers.sendMessage(msg4MQ);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                _log.info("deliveryComplete---------" + iMqttDeliveryToken.isComplete());

            }
        }
        );
        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
        // options.setWill(topic, "[{},{}]".getBytes(), 1, true);
        try {
            client.connect(options);
        } catch (MqttException e) {
            _log.error("错误!mqtt客户端无法新建链接!"+e);
            if(e.getReasonCode() == 32103){
                _log.error("网络无连接,或"+ MQTT_BROKER_IP +"不可达,请检查网络");
            }
            e.printStackTrace();
        }
        subscribe();
    }

    private void subscribe() {

        String[] topic1 = new String[7];
        int count = 0;
            topic1[count] = "C/CHARGE/+/+/data";
            count++;
            topic1[count] = "C/CHARGE/+/+/request";
            count++;
            topic1[count] = "C/CHARGE/+/+/response";
            count++;
            topic1[count] = "C/CHARGE/+/+/image";
            count++;
            topic1[count] = "C/CHARGE/+/+/update";
            count++;

        topic1[count]="C/GW_INIT/+/+/notify";
        count++;
        topic1[count]="C/GW_INIT/+/+/confirm";
        try {
            if(topic1.length>0){
                client.subscribe(topic1);
            }
            _log.info("订阅已启动，订阅区域：");

        } catch (MqttException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    //重启订阅流程
    public void reSubscribe(String title , String topic) {
        _log.info("title:"+title+"   "+"topic:"+topic);
        if(title.equals("del")){
            String[] delTopics = new String[5];
            delTopics[0] = "C/"+topic + "/+/data";
            delTopics[1] = "C/"+topic + "/+/request";
            delTopics[2] = "C/"+topic+ "/+/response";
            delTopics[3] = "C/"+topic+ "/+/image";
            delTopics[4] = "C/"+topic+ "/+/update";
            try {
                mqttMsgReceiver.client.unsubscribe(delTopics);
                _log.info("订阅删除完成:"+topic);
            } catch (MqttException e) {
                _log.error("mqtt取消订阅失败!"+e);
                e.printStackTrace();
            }
        }
        if(title.equals("add")){
            String[] addTopics = new String[5];
            addTopics[0] = "C/"+topic + "/+/data";
            addTopics[1] = "C/"+topic + "/+/request";
            addTopics[2] = "C/"+topic+ "/+/response";
            addTopics[3] = "C/"+topic+ "/+/image";
            addTopics[4] = "C/"+topic+ "/+/update";
            try {
                mqttMsgReceiver.client.subscribe(addTopics);
                _log.info("订阅新增完成:"+topic);
            } catch (MqttException e) {
                _log.error("添加订阅失败!"+e);
                e.printStackTrace();
            }
        }
    }
}

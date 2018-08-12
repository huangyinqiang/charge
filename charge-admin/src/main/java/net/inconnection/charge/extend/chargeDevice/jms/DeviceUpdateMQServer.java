package net.inconnection.charge.extend.chargeDevice.jms;

import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.protocol.update.UpdateMsgHandle;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.*;
import java.util.Hashtable;
import java.util.Map;

public class DeviceUpdateMQServer implements MessageListener{

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static Logger _log = LoggerFactory.getLogger(DeviceUpdateMQServer.class);

    //private BrokerService broker;
    //private final String brokerUrl = TCP_LOCALHOST_ACTIVEMQ;
    private Session session;

    //处理update的queue,若加入其他业务的处理则在下方加入新的queue
    private static final String UPDATE_QUEUE = "update_queue";

    //存放textMessage的map,主要为了按照收到的message原路返回一个响应
    private Map<String,Destination> requestMap = new Hashtable<>();

    //存放textMessage的map,主要为了按照收到的message原路返回一个响应
    private Map<String, JSONObject> dataMap = new Hashtable<>();

    //存放topic的topic的map
    //private Map<String, String> topicMap = new Hashtable<>();

    //存放producer的map,键为queue的名称,即一个队列对应一个producer
    //private Map<String,MessageProducer> producerMap = new Hashtable<>();

    //存放consumer的map,键为queue的名称,即一个队列对应一个consumer
    //private Map<String,MessageConsumer> consumerMap = new Hashtable<>();

    private MessageProducer producer;
    private MessageConsumer consumer;
    private Connection connection;

    //单例模式
    private static final DeviceUpdateMQServer instance = new DeviceUpdateMQServer();



    //私有化构造方法
    private DeviceUpdateMQServer(){
        start();
    }

    //单例方法
    public static DeviceUpdateMQServer getInstance(){
        return instance;
    }

    //单利方法
    /*public static DeviceUpdateMQServer getInstance() {
        if (instance == null) {
            synchronized (DeviceUpdateMQServer.class) {
                if (instance == null) {
                    instance = new DeviceUpdateMQServer();
                }
            }
        }
        return instance;
    }*/

    //预处理,若没有queue则创建queue
    //public void prepare(){
    //    if(consumerMap.get(queueName) == null){
    //       try {
    //            setupConsumer(queueName);
    //        } catch (JMSException e) {
    //            _log.error("创建队列失败!",e);
    //        }
    //    }
    //}

    //初始session的方法
    public void start() {


        //createBroker();
        try {
            setupConsumer();
        } catch (JMSException e) {
            _log.error("创建brokerl错误，请检查端口是否可用、用户名密码是否正确或网络是否可达！",e);
        }
        _log.info("初始化ActiveMQ完成,正在等待接收消息!");
        //设置线程池的初始化
        /*ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //往后若需要加入新的queue,则调用响应queue的setupConsumer方法
        setupConsumer(UPDATE_QUEUE);*/
    }

    //初始化代理的方法
    /*private void createBroker() throws Exception {
        broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.addConnector(brokerUrl);
        broker.start();
    }*/

    //设置consumer和producer并放入相应的容器中,待使用时通过相应的key获取
    /*private void setupConsumer(String queueName) throws JMSException {
        Destination adminQueue = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        MessageConsumer consumer = session.createConsumer(adminQueue);

        //将一个实现的messageListenner放入该consumer中,实现消息的自动接收
        consumer.setMessageListener(this);
        producerMap.put(queueName,producer);
        consumerMap.put(queueName,consumer);
    }*/

    private void setupConsumer() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination adminQueue = session.createQueue(UPDATE_QUEUE);
        producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        consumer = session.createConsumer(adminQueue);
        consumer.setMessageListener(this);
        //producerMap.put(UPDATE_QUEUE,producer);
        //consumerMap.put(UPDATE_QUEUE,consumer);
    }

    //关闭资源(因需要长时间运转,故暂时用不到)
    public void stop() throws Exception {
        producer.close();
        consumer.close();
        session.close();
        connection.stop();
        //broker.stop();
    }

    //获取producer
    /*public MessageProducer getProducer(){
        return producer;
    }*/

    //获取consumer
    /*public MessageConsumer getConsumer(String queueName){
        return consumerMap.get(queueName);
    }*/

    //获取textMessage
    public JSONObject getData(String key){
        return dataMap.get(key);
    }

    //获取数据
    /*public boolean isUpdating(String key){
        return dataMap.containsKey(key);
    }*/

    //获取session
    /*public Session getSession(){
        return session;
    }*/

    //移除response
    public void removeData(String key){
        if(requestMap.containsKey(key)){
            requestMap.remove(key);
            _log.info("删除Destination完成!");
        }
        if(dataMap.containsKey(key)){
            dataMap.remove(key);
            _log.info("删除Data完成!");
        }
    }

    public void response(String key,String message){
        Destination request = requestMap.get(key);
        if(request == null){
            System.out.println("无该消息");
            return;
        }
        try {
            TextMessage response = session.createTextMessage();
            response.setText(message);
            response.setJMSCorrelationID(key);
            producer.send(request,response);
        } catch (JMSException e) {
            _log.error("发送消息出错,请检查消息生产者配置是否正确!");
        }finally {
            removeData(key);
        }
    }

    @Override
    public void onMessage(Message message) {
        // 使用线程池多线程处理
        threadPoolTaskExecutor.execute(() -> {
            try {
                if (message instanceof TextMessage) {
                    TextMessage txtMsg = (TextMessage) message;
                    String messageText = txtMsg.getText();
                    _log.info(">>>>>>>>>>>>>>>收到请求消息:"+txtMsg.getJMSCorrelationID());
                    _log.info(">>>>>>>>>>>>>>>消息内容为:"+messageText);
                    JSONObject jsonObject = JSONObject.parseObject(messageText);
                    //dataMap.put(txtMsg.getJMSCorrelationID(),jsonObject);
                    requestMap.put(txtMsg.getJMSCorrelationID(),txtMsg.getJMSReplyTo());
                    dataMap.put(txtMsg.getJMSCorrelationID(),jsonObject);
                    UpdateMsgHandle.firstSend(instance,txtMsg.getJMSCorrelationID(),jsonObject);
                }
            } catch (JMSException e) {
                _log.error("接收消息出错,请检查配置是否正确!",e);
            }
        });

    }

    public static void main(String[] args) {
        DeviceUpdateMQServer.getInstance();
    }
}
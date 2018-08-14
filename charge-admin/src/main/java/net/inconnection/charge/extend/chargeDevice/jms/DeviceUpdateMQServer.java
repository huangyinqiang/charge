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
import java.util.concurrent.ThreadPoolExecutor;

public class DeviceUpdateMQServer implements MessageListener{

   // @Autowired   不再依赖spring
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static Logger _log = LoggerFactory.getLogger(DeviceUpdateMQServer.class);

    private Session session;

    //处理update的queue,若加入其他业务的处理则在下方加入新的queue
    private static final String UPDATE_QUEUE = "update_queue";

    //存放textMessage的map,主要为了按照收到的message原路返回一个响应
    private Map<String,Destination> requestMap = new Hashtable<>();

    //存放textMessage的map,主要为了按照收到的message原路返回一个响应
    private Map<String, JSONObject> dataMap = new Hashtable<>();


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

        //取消配置文件注入方式
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(50);
        threadPool.setMaxPoolSize(1000);
        threadPool.setQueueCapacity(20000);
        threadPool.setKeepAliveSeconds(3000);
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.initialize();
        instance.threadPoolTaskExecutor=threadPool;

        return instance;
    }


    //初始session的方法
    public void start() {

        try {
            setupConsumer();
        } catch (JMSException e) {
            _log.error("创建brokerl错误，请检查端口是否可用、用户名密码是否正确或网络是否可达！",e);
        }
        _log.info("初始化ActiveMQ完成,正在等待接收消息!");

    }

    private void setupConsumer() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination deviceUpdateQueue = session.createQueue(UPDATE_QUEUE);
        producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        consumer = session.createConsumer(deviceUpdateQueue);
        consumer.setMessageListener(this);
    }

    //关闭资源(因需要长时间运转,故暂时用不到)
    public void stop() throws Exception {
        producer.close();
        consumer.close();
        session.close();
        connection.stop();
        //broker.stop();
    }


    //获取textMessage
    public JSONObject getData(String key){
        return dataMap.get(key);
    }

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

    public void response2Client(String key,String message){
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
                    requestMap.put(txtMsg.getJMSCorrelationID(),txtMsg.getJMSReplyTo());
                    dataMap.put(txtMsg.getJMSCorrelationID(),jsonObject);
                    UpdateMsgHandle.sendFirstFirmwareDataFrame(instance,txtMsg.getJMSCorrelationID(),jsonObject);
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
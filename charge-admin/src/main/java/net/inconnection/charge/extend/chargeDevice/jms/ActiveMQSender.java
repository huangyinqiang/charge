package net.inconnection.charge.extend.chargeDevice.jms;

/**
 * Created by wukai on 17-7-21.
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ActiveMQSender {
    private static Logger _log = LoggerFactory.getLogger(ActiveMQSender.class);
    private String queue;
    private Session session;// Session： 一个发送或接收消息的线程
    private static Connection connection;
    private static ConnectionFactory connectionFactory;


    private MessageProducer producer;// MessageProducer：消息发送者

    public ActiveMQSender(String queue) {
        this.queue=queue;
        connection = null;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
        try {
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值，不使用事务处理，提高速度
            destination = session.createQueue(queue);
            // 得到消息生成者
            producer = session.createProducer(destination);
            // 设置不持久化.发送时间会大大提高
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        }catch (Exception e) {
            _log.error("ActiveMQSender.ActiveMQSender().Exception:"+e);
            e.printStackTrace();
        }


    }

    public  void sendMessage(String msg) throws Exception {


        try {
            long startTime=System.currentTimeMillis();//记录开始时间

            TextMessage message = session.createTextMessage(msg);
            // 发送消息到目的地方
            producer.send(message);

            long endTime=System.currentTimeMillis();//记录结束时间
            float excTime=(float)(endTime-startTime)/1000;
            _log.info("ActiveMQSender sendMessage() 执行时间："+excTime+"s");
            _log.info("ActiveMQSender.sendMessage():  queue:" + queue + "---");

        } catch (Exception e) {
            _log.error("ActiveMQSender.sendMessage().Exception:"+e);
            e.printStackTrace();

            connection.close();

            connection = connectionFactory.createConnection();
            // 启动
            connection.start();

            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session.createTextMessage(msg);

            producer.send(message);

            _log.info("ActiveMQSender.ResendMessage():  queue:" + queue + "---");
        }

    }
}

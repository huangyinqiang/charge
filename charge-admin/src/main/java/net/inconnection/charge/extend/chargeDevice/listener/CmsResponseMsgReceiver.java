package net.inconnection.charge.extend.chargeDevice.listener;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static net.inconnection.charge.extend.chargeDevice.jms.ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ;


public class CmsResponseMsgReceiver{

    private static Logger _log = LoggerFactory.getLogger(CmsResponseMsgReceiver.class);

    public String getProcessedResponseMsg(String queue,long timeout) {
        // 消费者，消息接收者
        MessageConsumer consumer = null;
        //连接工厂方法
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session se = null;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, TCP_LOCALHOST_ACTIVEMQ);

        try{
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            se = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = se.createQueue(queue);
            consumer = se.createConsumer(destination);
            TextMessage msgJson = (TextMessage) consumer.receive(timeout);
            if(msgJson != null) {
                String jsonMsg = msgJson.getText();
                return jsonMsg;
            }
        }catch(JMSException e){
            _log.error("监听硬件发送消息错误",e);
            return null;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if(consumer != null){
                    consumer.close();
                }
                if(se != null){
                    se.close();
                }
            } catch (JMSException e) {
                _log.error("activeMQ关闭失败",e);
            }
        }
        return null;
    }
}


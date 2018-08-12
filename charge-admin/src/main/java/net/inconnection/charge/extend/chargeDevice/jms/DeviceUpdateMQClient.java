package net.inconnection.charge.extend.chargeDevice.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static net.inconnection.charge.extend.chargeDevice.jms.ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ;


public class DeviceUpdateMQClient {
    private static Logger _log = LoggerFactory.getLogger(DeviceUpdateMQClient.class);
    //private BrokerService broker;
    private final String brokerUrl = TCP_LOCALHOST_ACTIVEMQ;
    private Connection connection;
    private Session session;
    private Queue tempDest;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private static final String UPDATE_QUEUE = "update_queue";

    public void start() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination adminQueue = session.createQueue(UPDATE_QUEUE);
        producer = session.createProducer(adminQueue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        tempDest = session.createTemporaryQueue();
        consumer = session.createConsumer(tempDest);
        //consumer.setMessageListener(this);
    }

    public void stop() throws Exception {
        producer.close();
        consumer.close();
        session.close();
        connection.close();
        //broker.stop();
    }

    public void requestStartUpdateDevice(String id,String request) throws JMSException {
        _log.info("Requesting: " + request);
        TextMessage txtMessage = session.createTextMessage();
        txtMessage.setText(request);
        txtMessage.setJMSReplyTo(tempDest);
        //String correlationId = UUID.randomUUID().toString();
        txtMessage.setJMSCorrelationID(id);
        this.producer.send(txtMessage);
    }

    public String getUpdateResult(int timeOut) {
        try {
            Message message = consumer.receive(timeOut);
            if(message == null){
                return null;
            }
            TextMessage textMessage = (TextMessage)message;
            System.out.println("Received response for: " + textMessage.getText());
            return textMessage.getText();
        } catch (JMSException e) {
            _log.error("activeMQ接收消息错误!",e);
        }
        return null;
    }
}
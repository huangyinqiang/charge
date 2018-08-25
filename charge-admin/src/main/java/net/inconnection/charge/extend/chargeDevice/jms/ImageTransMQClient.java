package net.inconnection.charge.extend.chargeDevice.jms;

import com.jfinal.log.Log;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static net.inconnection.charge.extend.chargeDevice.jms.ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ;


public class ImageTransMQClient {
    private static Log _log = Log.getLog(ImageTransMQClient.class);
    //private BrokerService broker;
    private final String brokerUrl = TCP_LOCALHOST_ACTIVEMQ;
    private Connection connection;
    private Session session;
    private Queue tempDest;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private static final String IMAGE_QUEUE = "image_queue";

    public void start() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination adminQueue = session.createQueue(IMAGE_QUEUE);
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

    public void requestStartTransImage(String id, String request) throws JMSException {
        _log.info("requestStartTransImage: " + request);
        TextMessage txtMessage = session.createTextMessage();
        txtMessage.setText(request);
        txtMessage.setJMSReplyTo(tempDest);
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
            System.out.println("getUpdateResult: " + textMessage.getText());
            return textMessage.getText();
        } catch (JMSException e) {
            _log.error("ImageTransMQClient接收消息错误!",e);
        }
        return null;
    }
}
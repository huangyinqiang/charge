package net.inconnection.charge.extend.chargeDevice.jms;

import org.apache.activemq.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class MQTTListener extends JmsReceiver {


    private static Logger _log = LoggerFactory.getLogger(MQTTListener.class);

    public MQTTListener(String name,
                       PooledConnection connection,
                       DestinationType type,
                       String subject) throws JMSException {
        super(name, connection, type, subject);

    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                TextMessage msg = (TextMessage) message;
                System.out.println("MQTTListener" + msg.getText());

            } catch (Exception e){
                _log.error("消息处理异常:",e);
            }
        }

    }
}

package net.inconnection.charge.extend.chargeDevice.jms;

import org.apache.activemq.pool.PooledConnection;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.Enumeration;


public class JmsReceiver implements MessageListener {
    private String name;
    private Session session;
    private MessageConsumer consumer;
    public JmsReceiver(String name,
                       PooledConnection connection,
                       DestinationType type,
                       String subject) throws JMSException {
        this.name = name;
        // 事务性会话，自动确认消息
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 消息的目的地（Queue/Topic）
        if (type.equals(DestinationType.Topic)) {
            Topic destination = session.createTopic(subject);
            consumer = session.createConsumer(destination);
        } else {
            Queue destination = session.createQueue(subject);
            consumer = session.createConsumer(destination);
        }
        consumer.setMessageListener(this);
    }
    public String getName() {
        return name;
    }
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                System.out.println(msg.getText());
            } else if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                Enumeration enumer = msg.getMapNames();
                while (enumer.hasMoreElements()) {
                    Object obj = enumer.nextElement();
                    System.out.println(msg.getObject(obj.toString()));
                }
            } else if (message instanceof StreamMessage) {
                StreamMessage msg = (StreamMessage) message;
                System.out.println(msg.readString());
                System.out.println(msg.readBoolean());
                System.out.println(msg.readLong());
            } else if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                System.out.println(msg);
            } else if (message instanceof BytesMessage) {
                BytesMessage msg = (BytesMessage) message;
                byte[] byteContent = new byte[1024];
                int length = -1;
                StringBuffer content = new StringBuffer();
                while ((length = msg.readBytes(byteContent)) != -1) {
                    content.append(new String(byteContent, 0, length));
                }
                System.out.println(content.toString());
            } else {
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

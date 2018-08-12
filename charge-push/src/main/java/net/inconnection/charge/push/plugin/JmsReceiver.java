package net.inconnection.charge.push.plugin;

import com.alibaba.fastjson.JSON;
import net.inconnection.charge.push.model.WeiXin;
import net.inconnection.charge.push.weixin.WeiXinSender;
import org.apache.activemq.pool.PooledConnection;

import javax.jms.*;

public class JmsReceiver implements MessageListener {
    private String name;
    private Session session;
    private MessageConsumer consumer;

    public JmsReceiver(String name, PooledConnection connection, Destination type, String subject) throws JMSException {
        this.name = name;
        this.session = connection.createSession(false, 1);
        if (type.equals(Destination.Topic)) {
            Topic destination = this.session.createTopic(subject);
            this.consumer = this.session.createConsumer(destination);
        } else {
            Queue destination = this.session.createQueue(subject);
            this.consumer = this.session.createConsumer(destination);
        }

        this.consumer.setMessageListener(this);
    }

    public String getName() {
        return this.name;
    }

    public void close() {
        try {
            this.consumer.close();
        } catch (JMSException var2) {
            System.out.println(var2.getMessage());
        }

    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage)message;

                try {
                    WeiXin weixin = (WeiXin)JSON.parseObject(msg.getText(), WeiXin.class);
                    System.out.println("TextMessage " + msg.getText());
                    WeiXinSender.SendToWeiXin(weixin);
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            } else {
                System.out.println(message);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}


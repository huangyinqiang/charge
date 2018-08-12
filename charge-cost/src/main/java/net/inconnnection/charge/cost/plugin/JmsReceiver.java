package net.inconnnection.charge.cost.plugin;

import com.alibaba.fastjson.JSON;
import net.inconnnection.charge.cost.bean.ChargeBatteryInfoBean;
import net.inconnnection.charge.cost.model.Result;
import net.inconnnection.charge.cost.weixin.Cost;
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
                    ChargeBatteryInfoBean cbInfo = (ChargeBatteryInfoBean)JSON.parseObject(msg.getText(), ChargeBatteryInfoBean.class);
                    System.out.println("TextMessage " + msg.getText());
                    System.out.println("--------扣费信息:" + cbInfo.toString() + "--------");
                    new Result();
                    Result result;
                    if ("auto".equalsIgnoreCase(cbInfo.getType())) {
                        result = Cost.updateAutoCharge(cbInfo);
                    } else {
                        result = Cost.updatePowerCharge(cbInfo);
                    }

                    System.out.println(result);
                } catch (Exception var5) {
                    System.out.println(var5.getMessage());
                }
            } else {
                System.out.println(message);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}


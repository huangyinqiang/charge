package net.inconnnection.charge.cost.test;

import net.inconnnection.charge.cost.plugin.ActiveMQ;
import net.inconnnection.charge.cost.plugin.ActiveMQPlugin;
import net.inconnnection.charge.cost.plugin.Destination;
import net.inconnnection.charge.cost.plugin.JmsReceiver;

import javax.jms.JMSException;

public class Test {


    public static void main(String[] args) {
        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://localhost:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "CostMessage";

        try {
            ActiveMQ.addReceiver(new JmsReceiver("testReceiver1", ActiveMQ.getConnection(), Destination.Queue, subject));
        } catch (JMSException var4) {
            var4.printStackTrace();
        }

    }

}

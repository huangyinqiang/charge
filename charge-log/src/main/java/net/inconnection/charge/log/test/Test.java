package net.inconnection.charge.log.test;

import net.inconnection.charge.log.plugin.ActiveMQ;
import net.inconnection.charge.log.plugin.ActiveMQPlugin;
import net.inconnection.charge.log.plugin.Destination;
import net.inconnection.charge.log.plugin.JmsReceiver;

import javax.jms.JMSException;

public class Test {
    public Test() {
    }

    public static void main(String[] args) {
        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://cloud.henankejue.com:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "CostMessage";

        try {
            ActiveMQ.addReceiver(new JmsReceiver("testReceiver1", ActiveMQ.getConnection(), Destination.Queue, subject));
        } catch (JMSException var4) {
            var4.printStackTrace();
        }

    }
}
package net.inconnnection.charge.cost.test;

import com.alibaba.fastjson.JSON;
import net.inconnnection.charge.cost.bean.ChargeBatteryInfoBean;
import net.inconnnection.charge.cost.plugin.ActiveMQ;
import net.inconnnection.charge.cost.plugin.ActiveMQPlugin;
import net.inconnnection.charge.cost.plugin.Destination;
import net.inconnnection.charge.cost.plugin.JmsSender;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class TestSender {
    public TestSender() {
    }

    public static void main(String[] args) throws Exception {
        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://localhost:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "CostMessage";

        try {
            ActiveMQ.addSender(new JmsSender("testSender1", ActiveMQ.getConnection(), Destination.Queue, subject));
            JmsSender sq1 = ActiveMQ.getSender("testSender1");

            for(int i = 0; i < 1; ++i) {
                ChargeBatteryInfoBean bean = new ChargeBatteryInfoBean();
                bean.setDeviceId("356566071100200");
                bean.setType("auto");
                bean.setStatus("C");
                bean.setMoney("10");
                bean.setRealChargeTime("80");
                bean.setDevicePort("8");
                bean.setOpenId("omPt_1CLfrL1r-Lia_tI6UC8ZrXs");
                String str = JSON.toJSONString(bean);
                System.out.println(str);
                TextMessage msg = sq1.getSession().createTextMessage(str);
                sq1.sendMessage(msg);
            }

            System.out.println("wancheng");
            sq1.close();
            p.stop();
        } catch (JMSException var8) {
            var8.printStackTrace();
        }

    }
}
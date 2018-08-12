package net.inconnection.charge.push.test;

import com.alibaba.fastjson.JSON;
import net.inconnection.charge.push.model.WeiXin;
import net.inconnection.charge.push.plugin.ActiveMQ;
import net.inconnection.charge.push.plugin.ActiveMQPlugin;
import net.inconnection.charge.push.plugin.Destination;
import net.inconnection.charge.push.plugin.JmsSender;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

public class TestSender {
    public TestSender() {
    }

    public static void main(String[] args) throws Exception {
        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://cloud.henankejue.com:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "WeixinMessage";

        try {
            ActiveMQ.addSender(new JmsSender("testSender1", ActiveMQ.getConnection(), Destination.Queue, subject));
            JmsSender sq1 = ActiveMQ.getSender("testSender1");

            for(int i = 0; i < 10; ++i) {
                WeiXin weixin = new WeiXin();
                weixin.setArea("aaa");
                weixin.setChannelNum("10");
                weixin.setDeviceId("");
                weixin.setMessage("测试");
                weixin.setOpenId("omPt_1CLfrL1r-Lia_tI6UC8ZrXs");
                weixin.setTitle("在哪？" + i);
                weixin.setType("F");
                weixin.setMoney("100");
                weixin.setWalletAccount(500);
                weixin.setChargeTime("200");
                weixin.setOperStartTime(new Date());
                String str = JSON.toJSONString(weixin);
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


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
        ActiveMQPlugin p = new ActiveMQPlugin("failover://(tcp://charge.inconnection.net:61616)?initialReconnectDelay=1000");
        p.start();
        String subject = "WeixinMessage_xa";

        try {
            ActiveMQ.addSender(new JmsSender("testSender1", ActiveMQ.getConnection(), Destination.Queue, subject));
            JmsSender sq1 = ActiveMQ.getSender("testSender1");

            for(int i = 0; i < 1; ++i) {
                WeiXin weixin = new WeiXin();
                weixin.setArea("陕西西安会展三号设备ID 1233456，端口号 17");
                weixin.setChannelNum("10");
                weixin.setDeviceId("陕西西安会展三号设备ID 1233456");
                weixin.setMessage("开始充电");
                weixin.setOpenId("oNFzS1JvIjMozdyk7r7ZUlD0Mu0s");
                weixin.setTitle("您选择临时充电，充电时间***分钟，充电费用**元；" );
                weixin.setType("NC");
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


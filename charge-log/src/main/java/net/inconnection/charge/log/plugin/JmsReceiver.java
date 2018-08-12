package net.inconnection.charge.log.plugin;

import com.alibaba.fastjson.JSON;
import net.inconnection.charge.log.bean.CardLog;
import net.inconnection.charge.log.bean.Customer;
import net.inconnection.charge.log.bean.DeviceLog;
import net.inconnection.charge.log.bean.QrDevice;
import org.apache.activemq.pool.PooledConnection;

import javax.jms.*;
import java.util.Date;

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
                    DeviceLog deviceLog = (DeviceLog)JSON.parseObject(msg.getText(), DeviceLog.class);
                    String content = deviceLog.getContent();
                    if (content.indexOf("?2") > -1) {
                        CardLog cardLog = new CardLog();
                        String[] strs = content.split(";");
                        String deviceId = strs[0].substring(0, strs[0].length() - 2);
                        String channelNum = strs[1].substring(1, strs[1].length());
                        String phyicalId = strs[2].substring(1, strs[2].length());
                        String cardCode = strs[3].substring(1, strs[3].length());
                        String balance = strs[4].substring(1, strs[4].length());
                        Customer customer = (Customer)Customer.me.findFirst("select * from customer where cardnum=?", new Object[]{cardCode});
                        QrDevice qrDevice = (QrDevice)QrDevice.me.findFirst("select * from qr_match_device where match_num=?", new Object[]{deviceId});
                        int gid = (Integer)qrDevice.get("gid");
                        String area = deviceLog.getQrDevice().getArea();
                        String name = "";
                        String phone = "";
                        if (customer != null) {
                            name = (String)customer.get("name");
                            phone = (String)customer.get("phone");
                        } else {
                            name = "未绑定用户";
                            phone = "卡号";
                        }

                        String gname = (String)qrDevice.get("gname");
                        cardLog.set("deviceId", deviceId);
                        cardLog.set("phyicalId", phyicalId);
                        cardLog.set("cardCode", cardCode);
                        cardLog.set("balance", balance);
                        cardLog.set("joinDate", new Date());
                        cardLog.set("name", name);
                        cardLog.set("gname", gname);
                        cardLog.set("area", area);
                        cardLog.set("phone", phone);
                        cardLog.set("channelNum", channelNum);
                        cardLog.set("gid", gid);
                        cardLog.save();
                    } else {
                        deviceLog.set("type", deviceLog.getType());
                        deviceLog.set("openid", deviceLog.getOpenid());
                        deviceLog.set("deviceid", deviceLog.getDeviceid());
                        deviceLog.set("ip", deviceLog.getIp());
                        deviceLog.set("content", deviceLog.getContent());
                        deviceLog.set("jointime", deviceLog.getJointime());
                        deviceLog.save();
                    }
                } catch (Exception var19) {
                    var19.printStackTrace();
                }
            } else {
                System.out.println(message);
            }
        } catch (Exception var20) {
            var20.printStackTrace();
        }

    }
}


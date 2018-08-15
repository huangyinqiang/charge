package net.inconnection.charge.extend.chargeDevice;


import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

/**
 * Created by root on 17-11-10.
 */
public class PerformenceTester {
    public static void main(String[] args) {

            //while (true)
            {
                for (int index=0; index<1; index++) {
                    final String industry = "CHARGE";
                    final String version = "1";
                    final String gwId = "010100000001";


                    String notifyTopic = "C/" + "CHARGE" + "/" + version +"/" + gwId + "/" + TOPIC_NOTIFY;

                    String timeStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
                    String seq = String.valueOf(SEQGeneration.getInstance().getSEQ());//sequenceNum生成

                    String notifyMsg = "GWID:" + gwId + ";SEQ:" + seq + ";TIME:" + timeStr + MSG_FACET_SEPARATOR_INSIDE +
                            "NOTIFY:1";


                    sendMqttMsg(notifyTopic, notifyMsg);


                    String dataTopic = "C/" + industry + "/" + version +"/" + gwId + "/" + TOPIC_DATA;


                    timeStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
                    seq = String.valueOf(SEQGeneration.getInstance().getSEQ());//sequenceNum生成

                    String dataMsg = "GWID:" + gwId + ";SEQ:" + seq + ";TIME:" + timeStr + ";VOLTAGE:220;POWER:12000" + ";STATUS:1" + MSG_FACET_SEPARATOR_INSIDE +
                            "DEVICESN:01;MAINTYPE:1;USE:1;SP:100;CI:234;CT:332;CS:1;WARN:1,2" + MSG_FACET_SEPARATOR_INSIDE +
                            "DEVICESN:02;MAINTYPE:1;USE:1;SP:100;CI:234;CT:332;CS:1";

                    sendMqttMsg(dataTopic, dataMsg);
                }

                try {

                    Thread.sleep(29000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }



    }

    private static void sendMqttMsg(String dataTopic, String dataMsg) {
        try {
            MqttMsgSender.getInstance().Send(dataTopic,dataMsg);
            System.out.println("PerformenceTester.msg: " + dataMsg);

        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package net.inconnection.charge.extend.utils;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

import static net.inconnection.charge.extend.protocol.ProtocolConstant.MQTT_BROKER_IP;


public class MqttReconnectUtil {

    private static Logger _log = LoggerFactory.getLogger(MqttReconnectUtil.class);

    public static String reconnect(MqttClient client,MqttConnectOptions options){
        int count = 1;
        int conResult = 0;
        int subResult = 0;
        while (!client.isConnected()) {
            if(isReachable(MQTT_BROKER_IP)){
                try {
                    _log.info("mqtt正在进行重连,请稍候...");
                    IMqttToken iMqttToken = client.connectWithResult(options);
                    _log.info("重连是否成功:"+iMqttToken.isComplete());
                } catch (MqttException e) {
                    _log.error("MqttException的错误码:"+e);
                    int reasonCode = e.getReasonCode();
                    if (reasonCode == 32100) {
                        _log.info("mqtt状态为:已经连接的,无需重连!");
                    } else if (reasonCode == 32110) {
                        if(count == 1)_log.info("mqtt状态为:正在连接中,请等待...");
                    } else if (reasonCode == 32102 || reasonCode == 32111) {
                        _log.info("mqtt状态为:已经断开或关闭的链接,正在建立新连接...");
                        try {
                            client.close();
                        } catch (MqttException e1) {
                            _log.error("MqttException的错误码2:"+e1);
                            if (e1.getReasonCode() == 32100) {
                                _log.info("mqtt状态为:已经连接的,无需重连!");
                            } else if (e1.getReasonCode() == 32110) {
                                _log.info("mqtt状态为:正在连接的,无需重连!");
                            }
                        }
                        _log.info("正在建立mqtt新连接...");
                        conResult=1;
                        break;
                    } else {
                        _log.error("其他异常!");
                    }
                }
            }else {
                if(count == 1) _log.error("网络无连接,或"+ MQTT_BROKER_IP +"不可达,请检查网络!");
                count++;
            }
            if(!client.isConnected()){
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    _log.error("Mqtt线程错误!"+e);
                }
            }else {
                subResult=1;
                break;
            }
        }
        String result = conResult+"/"+subResult;
        return result;
    }


    private static boolean isReachable(String remoteInetAddr) {
        if(remoteInetAddr.indexOf("//")>0){
            remoteInetAddr = remoteInetAddr.substring(remoteInetAddr.indexOf("//")+2);
        }
        if(remoteInetAddr.indexOf(":")>0){
            remoteInetAddr = remoteInetAddr.substring(0,remoteInetAddr.indexOf(":"));
        }
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(remoteInetAddr);
            reachable = address.isReachable(5000);
        } catch (Exception e) {
            _log.error("MqttMsgReceiver.isReachable()方法错误,请检查"+ MQTT_BROKER_IP +"是否可达!"+e);
        }
        return reachable;
    }
}

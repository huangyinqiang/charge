package net.inconnection.charge.extend.utils;

import net.inconnection.charge.extend.protocol.MqttMsgReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/*
*
* 进程通信用工具
* 2017-10-9
* */

public class CommunicateBySocket {
    private static Logger _log = LoggerFactory.getLogger(CommunicateBySocket.class);

    public static void getMsg(MqttMsgReceiver mqttMsgReceive) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
        _log.info("服务端启动了");
        Socket socket = null;
        BufferedReader in = null;

        //业务处理循环
        //从客户端获取
        while (true) {
            String title = "";
            String topic = "";
            try {
                socket = serverSocket.accept();

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String temp = "";
                while ((temp = in.readLine()) != null) {
                    title = temp;
                    if((temp = in.readLine()) != null){
                      topic = temp;
                    }
                }
                socket.shutdownInput();
            } catch (IOException e) {
                _log.info("服务端启动失败!"+e);
                e.printStackTrace();
            } finally {
                //关闭资源
                try {
                    if (in != null) in.close();
                    if (socket != null) socket.close();
                    _log.info("服务端处理完毕");
                } catch (IOException e) {
                    _log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
            //将从客户端获取的信息发送给MqttMsgReceive
            mqttMsgReceive.reSubscribe(title,topic);
        }
    }
}
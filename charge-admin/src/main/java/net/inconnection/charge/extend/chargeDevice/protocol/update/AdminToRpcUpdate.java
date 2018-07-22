package net.inconnection.charge.extend.chargeDevice.protocol.update;

import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_UPDATE_PROCESS_PORT;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_UPDATE_WEB_PORT;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.TOPIC_UPDATE;


public class AdminToRpcUpdate {
    private static Logger _log = LoggerFactory.getLogger(AdminToRpcUpdate.class);
    //存放socket对象的map
    private Map<String, Socket> socketMap = new Hashtable();
    //存放topic的map
    private Map<String, String> topicMap = new Hashtable();
    //存放最后一次反馈时间的map
    private Map<String, Map<BigInteger, Date>> lastUpdateTime = new Hashtable();

    //单例模式获取唯一对象
    private volatile static AdminToRpcUpdate adminToRpcUpdate;

    //无参私有构造方法
    private AdminToRpcUpdate() {
    }

    //获取对象的方法
    public static AdminToRpcUpdate getInstance() {
        if (adminToRpcUpdate == null) {
            synchronized (AdminToRpcUpdate.class) {
                if (adminToRpcUpdate == null) {
                    adminToRpcUpdate = new AdminToRpcUpdate();
                }
            }
        }
        return adminToRpcUpdate;
    }

    //web传来信息
    public void webSocketManage() {
        ServerSocket serverSocket = null;
        BufferedReader reader = null;
        try {
            serverSocket = new ServerSocket(MSG_UPDATE_WEB_PORT);
            while (true) {
                _log.info("请求服务等待数据");
                Socket socket = serverSocket.accept();
                _log.info("请求服务建立连接完成");

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String adminMsg = null;
                String temp;
                //读取键

                if ((temp = reader.readLine()) != null) {
                    adminMsg = temp;
                    _log.info("读取Key=" + adminMsg);
                }

                String[] keyStr = adminMsg.split("/");
                String industry = keyStr[0];
                String protocolVersion = keyStr[1];
                BigInteger gwid = new BigInteger(keyStr[2]);
                String hardwareVersion = keyStr[3];

                String key = industry+"/"+protocolVersion + "/" + gwid;

                //生成时间
                String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String seq = String.valueOf(SEQGeneration.getInstance().getSEQ());//sequenceNum生成

                //生成主题
                GeneralTopic generalTopic = new GeneralTopic();
                String topic = generalTopic.generateTopic(industry, protocolVersion, gwid.toString() , TOPIC_UPDATE);
                byte[] buff = RedisUtil.get((industry + "/" + protocolVersion + "/" + gwid.toString()).getBytes());

                //UpdateDevice ud = new UpdateDevice(buff);
                //ud.analysisFile(0);
                UpdateMsgProcesser ump = new UpdateMsgProcesser(seq, timeStr, gwid, hardwareVersion, 0, 0, buff.length, 0);
                byte[] bytes = ump.updateEncode();
                //byte[] bytes2 = ud.getBytes();
                //byte[] bytes = new byte[bytes1.length + bytes2.length];
                //System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
                //System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);

                send(topic,bytes);
                socket.shutdownInput();
                socketMap.put(key, socket);
                topicMap.put(key, topic);
                Map<BigInteger, Date> timeMap = new Hashtable<>();
                timeMap.put(gwid,new Date());
                lastUpdateTime.put(key,timeMap);
                final String finalKey = key;
                final BigInteger finalGwid = gwid;

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        checkTimeout(finalKey,finalGwid);
                    }
                };
                thread.start();
            }
        } catch (IOException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    //
    public void processSocketManage() {
        ServerSocket serverSocket = null;
        BufferedReader reader = null;
        try {
            serverSocket = new ServerSocket(MSG_UPDATE_PROCESS_PORT);
            while (true) {
                _log.info("数据服务等待数据");
                Socket socket = serverSocket.accept();

                //加入一个无故退出清理相关数据的逻辑
                for(String key:topicMap.keySet()) {
                    Socket temp = socketMap.get(key);
                    if (temp.isClosed()) {
                        socketMap.remove(key);
                        topicMap.remove(key);
                        lastUpdateTime.remove(key);
                        _log.info("链接已关闭!清空中间数据!");
                    }
                }

                _log.info("数据服务建立连接完成");
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String temp;
                String industryAndVersion = null;
                //获取业务唯一识别码的逻辑
                if ((temp = reader.readLine()) != null) {
                    industryAndVersion = temp;
                }
                String message = null;
                //获取反馈的信息的逻辑
                while ((temp = reader.readLine()) != null) {
                    message = temp;
                }

                //update消息解析类获取message的各个属性
                UpdateMsgProcesser ump = new UpdateMsgProcesser();
                ump.updateDecode(message);
                String seq = ump.getSequenceNum();
                String timeStr = ump.getTimeStr();
                BigInteger gwid = ump.getGwid();
                String version = ump.getUpdate();
                int offset = ump.getOffset();
                int status = ump.getStatus();

                //socketMap管理socket的唯一key
                String key = industryAndVersion+"/"+gwid;

                //获取主题
                String topic = topicMap.get(key);

                byte[] keyBytes = (industryAndVersion + "/" + gwid.toString()).getBytes();
                byte[] buff = RedisUtil.get(keyBytes);
                byte[] bytes = null;
                if(!(buff.length == offset)){
                    UpdateDevice ud = new UpdateDevice(buff);
                    ud.analysisFile(offset);

                    UpdateMsgProcesser updateMsgProcesser = new UpdateMsgProcesser(seq, timeStr, gwid, version, offset, ud.getLength(), ud.getLengthAll(), ud.getCrc());
                    byte[] bytes1 = updateMsgProcesser.updateEncode();
                    byte[] bytes2 = ud.getBytes();

                    bytes = new byte[bytes1.length + bytes2.length];
                    System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
                    System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);
                }
                //判断逻辑,如果此次返回的数据在管理仓库中没有唯一识别码,就跳出处理逻辑
                if (!socketMap.containsKey(key) | !lastUpdateTime.containsKey(key) | !topicMap.containsKey(key)) {
                    socket.shutdownInput();
                    reader.close();
                    socket.close();
                    continue;
                }

                _log.info("**********************");
                _log.info("网关已发来数据应答!");
                _log.info("status=" + status);
                _log.info("**********************");

                switch (status) {
                    case 0:
                        _log.info("校验有误,正在重发!");
                        send(topic, bytes);
                        lastUpdateTime.get(key).put(gwid,new Date());
                        break;
                    case 1:
                        if(buff.length == offset){
                            _log.info("数据发送成功,网关正在重启请稍后!");
                        }else {
                            _log.info("收到回复,正在发送下一数据包!");
                            send(topic, bytes);
                        }
                        lastUpdateTime.get(key).put(gwid,new Date());
                        break;
                    case 3:
                        _log.info("子站网关处于电池供电,退出更新!");
                        closeSocket(key , status);
                        break;
                    case 4:
                        _log.info("内部flash损坏,不可升级!");
                        closeSocket(key, status);
                        break;
                    case 5:
                        _log.info("版本已最新,无需升级!");
                        closeSocket(key, status);
                        break;
                    case 10:
                        _log.info("升级成功,正在返回页面!");
                        closeSocket(key, status);
                        break;
                    case 11:
                        _log.info("升级失败,正在返回页面!");
                        closeSocket(key, status);
                        break;
                    default:
                        _log.info("其他错误,正在返回页面!");
                        closeSocket(key, status);
                        break;
                }

                socket.shutdownInput();
                reader.close();
                socket.close();
            }
        } catch (IOException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void send(String topic, byte[] bytes) {
        try {
            MqttMsgSender.getInstance().Send(topic, bytes, 1);
        } catch (Exception e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeSocket(String key,int status) {
        Socket socket = socketMap.get(key);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(status+"");
            writer.newLine();
            writer.flush();
            socket.shutdownOutput();
            writer.close();
            socket.close();
            _log.info("本次升级:"+key+"-升级结束,准备退出!");
        } catch (IOException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
        final String finalTopic = topicMap.get(key);
        Thread thread = new Thread() {
            @Override
            public void run() {
                checkAllData(finalTopic);
            }
        };
        thread.start();
        socketMap.remove(key);
        topicMap.remove(key);
        lastUpdateTime.remove(key);
    }

    private void checkTimeout(String key,BigInteger gwid){
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
        if(!socketMap.containsKey(key) | !lastUpdateTime.containsKey(key) | !topicMap.containsKey(key)){
            return;
        }
        if((new Date().getTime()-lastUpdateTime.get(key).get(gwid).getTime())>(5*60*1000)){
            _log.info(">>>>>>>>>>网关"+gwid+"超时!");
            closeSocket(key,12);
        }
        checkTimeout(key,gwid);
    }

    private void checkAllData(String topic) {
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            send(topic,"".getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}

package net.inconnection.charge.extend.controller;

import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.extend.chargeDevice.jms.ActiveMQMsgClient;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_CUR_VERSION;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_INDUSTRY_CHARGE;


public class DeviceUpdateController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(DeviceUpdateController.class);

    private static int TIME_OUT = 10*30*1000;

    public void updateDevice() {

        render("/updatedevice/UpdateDevice.html");
    }


    public void updateDeviceData(File file, String gwId) {
//        String industry = "CHARGE";
//        String protocolVersion = "1";
//        String keyStr = industry + "/" + protocolVersion + "/" + gwId;
//        byte[] buff = null;
//        try {
//            buff = null;//todo file.getBytes();
//        } catch (IOException e) {
//            _log.error("文件获取byte数组错误!",e);
//        }
//        byte[] key = null;
//        try {
//            key = keyStr.getBytes("ISO-8859-1");
//        } catch (UnsupportedEncodingException e) {
//            _log.error("字符编码转换错误!",e);
//        }
//        boolean flag = RedisUtil.getJedis().exists(key);
//        if(!flag){
//            RedisUtil.set(key,buff,60*10);
//        }
        renderText("OK");
    }

    public void updateDeviceProcess(String gwid , String filename) {
        long start = System.currentTimeMillis();


        String industry = MQTT_TOPIC_INDUSTRY_CHARGE;
        String protocolVersion = MQTT_TOPIC_CUR_VERSION;
        String version = filename.split("\\.")[0];

        String key = industry + "/" + protocolVersion + "/" + gwid;

        //生成时间
        String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //sequenceNum生成
        String seq = String.valueOf(SEQGeneration.getInstance().getSEQ());


        JSONObject json = new JSONObject();
        json.put("industry",industry);
        json.put("protocolVersion",protocolVersion);
        json.put("version",version);
        json.put("gwid",gwid);
        json.put("seq",seq);
        json.put("timeStr",timeStr);

        ActiveMQMsgClient client = new ActiveMQMsgClient();
        try {
            client.start();
            _log.info("JMSCorrelationID"+key);
            client.request(key,json.toJSONString());
        } catch (Exception e) {
            _log.error("activeMQ客户端启动错误!",e);
        }

        //阻塞并从队列等待反馈
        String status = client.onMessage(TIME_OUT);
        try {
            client.stop();
        } catch (Exception e) {
            _log.error("activeMQ客户端停止错误!",e);
        }
        if(status == null){
            status = "13";
        }

        long end = System.currentTimeMillis();
        System.out.println("升级用时:" + (end - start) + "毫秒");
        System.out.println("升级状态列表:"+status);
        renderText(status);
    }
}

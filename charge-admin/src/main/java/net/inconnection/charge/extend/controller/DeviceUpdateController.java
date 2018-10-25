package net.inconnection.charge.extend.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.chargeDevice.jms.DeviceUpdateMQClient;
import net.inconnection.charge.extend.chargeDevice.utils.RedisUtil;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_CUR_VERSION;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_INDUSTRY_CHARGE;


public class DeviceUpdateController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(DeviceUpdateController.class);

    private static int TIME_OUT = 10*60*1000;

    public void listPage() {

        setAttr("powerStationId","222");
        render("UpdateDevice.html");
    }


    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "update_time desc";
        }
        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_charge_pile", properties, symbols, values, orderBy, this.getPager());
        for (Record record:list){
            record.set("sn",record.get("id"));
        }
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_charge_pile", properties, symbols, values));
    }


    public void updateDeviceData() {

        File file = getFiles().get(0).getFile();


        String fileName = getPara("fileName");
        String fileRedisKey = fileName;


        byte[] buff = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buff = bos.toByteArray();
        } catch (IOException e) {
            _log.error("文件获取byte数组错误!",e);
            renderText("file read error!");
        }
        byte[] key = null;
        try {
            key = fileRedisKey.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            _log.error("字符编码转换错误!",e);
            renderText("char code error");
        }
        RedisUtil.set(key,buff,60*20);
        renderText("OK");

    }

    public void updateDeviceProcess() {

        String gwid=getPara("chargePileId");
        String filename=getPara("filename");
        long start = System.currentTimeMillis();
        String industry = MQTT_TOPIC_INDUSTRY_CHARGE;
        String protocolVersion = MQTT_TOPIC_CUR_VERSION;
        String version = filename.split("\\.")[0];
        String key = gwid;
        //生成时间
        String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //sequenceNum生成
        String seq = String.valueOf(SEQGeneration.getInstance().getSEQ());
        JSONObject json = new JSONObject();
        json.put("industry",industry);
        json.put("protocolVersion",protocolVersion);
        json.put("version",version);
        json.put("fileName",filename);
        json.put("gwid",gwid);
        json.put("seq",seq);
        json.put("timeStr",timeStr);

        Long gwIdLong = Long.parseLong(gwid);

        RedisUtil.set(gwIdLong.toString().getBytes(), filename.getBytes(),60*20);


        DeviceUpdateMQClient deviceUpdateMQClient = new DeviceUpdateMQClient();
        try {
            deviceUpdateMQClient.start();
            _log.info("JMSCorrelationID  "+key);
            deviceUpdateMQClient.requestStartUpdateDevice(key,json.toJSONString());
        } catch (Exception e) {
            _log.error("DeviceUpdateMQClient 客户端启动错误!",e);
        }
        //阻塞并从队列等待反馈
        String status = deviceUpdateMQClient.getUpdateResult(TIME_OUT);
        try {
            deviceUpdateMQClient.stop();
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

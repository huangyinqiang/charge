package net.inconnection.charge.extend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.chargeDevice.deviceManage.ChargePileCommander;
import net.inconnection.charge.extend.chargeDevice.jms.ResponseMsgReceiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

public class DeviceControlController extends BaseController {
    public static final long TIMEOUT = 20 * 1000L;
    private static Log logger = Log.getLog(DeviceControlController.class);
    private static   ChargePileCommander chargePileCommander = ChargePileCommander.getInstance();




    public void listPage() {
        render("list.html");
    }

    public void listData() {
        String para = this.getPara("q");
        StringBuffer sql = new StringBuffer("select id,ifnull(name,'未安装') as `name` from yc_charge_pile where 1=1 ");
        if(para != null && StringUtil.isNotEmpty(para)){
            sql.append("and id like '%"+para+"%'");
        }
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find(sql.toString());
        this.renderJson(list);
    }

    /**
     * 允许上线
     */
    public void permissionOnLine() {
        String deviceId = this.getPara("deviceId");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.permissionOnLine(Long.parseLong(deviceId), callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }


    }
    /**
     * 关闭所有插座
     */
    public void shutDownAllSockets() {
        String deviceId = this.getPara("deviceId");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.shutDownAllSockets(Long.parseLong(deviceId), callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 关闭插座
     */
    public void shutDownSockets() {
        String deviceId = this.getPara("deviceId");
        String socketId = this.getPara("socketId");

        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(socketId == null || StringUtil.isEmpty(socketId)){
            this.renderFailed("缺少参数："+socketId);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        Vector vector = new Vector<Long>();
        vector.add(Long.parseLong(socketId));
        boolean sendSuccess = chargePileCommander.shutDownChargeSocket(Long.parseLong(deviceId),vector,callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 打开插座
     */
    public void startCharge() {
        String deviceId = this.getPara("deviceId");
        String socketId = this.getPara("socketId");
        String chargeTime = this.getPara("chargeTime");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(socketId == null || StringUtil.isEmpty(socketId)){
            this.renderFailed("缺少参数："+socketId);
            return;
        }
        if(chargeTime == null || StringUtil.isEmpty(chargeTime)){
            this.renderFailed("缺少参数："+chargeTime);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        Map<Long, Integer> socketIdChargeTimeMap  = new HashMap<>();
        socketIdChargeTimeMap.put(Long.parseLong(socketId), Integer.parseInt(chargeTime)*60);
        boolean sendSuccess = chargePileCommander.startCharge(Long.parseLong(deviceId),socketIdChargeTimeMap,callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 设置传输间隔
     * @tickTime 时间间隔：单位：秒
     */
    public void setTick() {
        String deviceId = this.getPara("deviceId");
        String tickTime = this.getPara("tickTime");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(tickTime == null || StringUtil.isEmpty(tickTime)){
            this.renderFailed("缺少参数："+tickTime);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.setTick(Long.parseLong(deviceId),Integer.parseInt(tickTime),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 查看是否在线
     */
    public void checkOnline() {
        String deviceId = this.getPara("deviceId");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.checkOnline(Long.parseLong(deviceId),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 设置端口最大功率
     */
    public void setPortMaxPow() {
        String deviceId = this.getPara("deviceId");
        String portMaxPow = this.getPara("portMaxPow");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(portMaxPow == null || StringUtil.isEmpty(portMaxPow)){
            this.renderFailed("缺少参数："+portMaxPow);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.setPortMaxPow(Long.parseLong(deviceId),Integer.parseInt(portMaxPow),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 设置强电板最大功率
     */
    public void setBoardMaxPow() {
        String deviceId = this.getPara("deviceId");
        String boardMaxPow = this.getPara("boardMaxPow");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(boardMaxPow == null || StringUtil.isEmpty(boardMaxPow)){
            this.renderFailed("缺少参数："+boardMaxPow);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.setBoardMaxPow(Long.parseLong(deviceId),Integer.parseInt(boardMaxPow),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 设置充电截止电流
     * @finishI 截止电流，单位：毫安
     */
    public void setFinishI() {
        String deviceId = this.getPara("deviceId");
        String finishI = this.getPara("finishI");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;
        }
        if(finishI == null || StringUtil.isEmpty(finishI)){
            this.renderFailed("缺少参数："+finishI);
            return;
        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.setFinishI(Long.parseLong(deviceId),Integer.parseInt(finishI),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }

    /**
     * 设置涓流充电时间
     * @jlTime 涓流时间，单位：分
     */
    public void setJlTime() {
        String deviceId = this.getPara("deviceId");
        String jlTime = this.getPara("jlTime");
        if(deviceId == null || StringUtil.isEmpty(deviceId)){
            this.renderFailed("缺少参数："+deviceId);
            return;

        }
        if(jlTime == null || StringUtil.isEmpty(jlTime)){
            this.renderFailed("缺少参数："+jlTime);
            return;

        }
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = chargePileCommander.setJlTime(Long.parseLong(deviceId),Integer.parseInt(jlTime),callBackQueueName);
        if (sendSuccess){
            getJsonObjResult(TIMEOUT, callBackQueueName);
        }
    }





    private void getJsonObjResult(Long timeout, String callBackQueueName){
        ResponseMsgReceiver cmsResponseMsgReceiver = new ResponseMsgReceiver();
        String processedResponseMsg = cmsResponseMsgReceiver.getProcessedResponseMsg(callBackQueueName,timeout);
        if(processedResponseMsg != null){
            JSONArray jsonArray = JSONArray.parseArray(processedResponseMsg);
            if(jsonArray.size() == 2){
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                Integer RESPONSE = jsonObject.getInteger("RESPONSE");
                if(RESPONSE != null && RESPONSE == 9){
                    this.renderSuccess();
                    return;
                }
                if(RESPONSE != null && (RESPONSE == 8 || RESPONSE ==12 || RESPONSE ==13 || RESPONSE ==15 || RESPONSE ==16)){
                    Integer result = jsonObject.getInteger("RESULT");
                    if(result != 0){
                        this.renderSuccess();
                        return;
                    }
                }
                // 入网
                if(RESPONSE != null && RESPONSE == 1){
                    Integer result = jsonObject.getInteger("RESULT");
                    if(result == 2){
                        this.renderSuccess();
                        return;
                    }
                }

                //切断所有
                if(RESPONSE != null && (RESPONSE == 2 || RESPONSE == 3 || RESPONSE == 5)){
                    Integer result = jsonObject.getInteger("RESULT");
                    if(result == 1){
                        this.renderSuccess();
                        return;
                    }
                }

            }
        }
        this.renderFailed("设置错误");
    }
}
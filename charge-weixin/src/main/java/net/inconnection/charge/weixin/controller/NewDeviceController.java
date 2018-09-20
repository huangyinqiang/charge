package net.inconnection.charge.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeBatteryInfo;
import net.inconnection.charge.weixin.model.NewDevice;
import net.inconnection.charge.weixin.model.WeiXin;
import net.inconnection.charge.weixin.plugin.ActiveMQ;
import net.inconnection.charge.weixin.plugin.JmsSender;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;
import net.inconnection.charge.weixin.service.NewDeviceChargePriceService;
import net.inconnection.charge.weixin.service.NewDeviceService;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

public class NewDeviceController extends Controller {
    public static final long TIMEOUT = 40 * 1000L;
    private static DeviceControlService deviceControlService = DubboServiceContrain.getInstance().getService(DeviceControlService.class);

    private static final NewDeviceService newDeviceService = new NewDeviceService();

    private static final NewDeviceChargePriceService newDeviceChargePriceService = new NewDeviceChargePriceService();

    private static ChargeInfoBatteryService chargeBatteryService = new ChargeInfoBatteryService();

    private static final Log log = Log.getLog(NewDeviceController.class);


    public void startCharge(){
        String openId = this.getPara("openId");
        String deviceId = this.getPara("deviceId");
        String devicePort = this.getPara("devicePort");
        String time = this.getPara("time");
        String chargeType = this.getPara("type");
        String money = this.getPara("money");
        String walletAccount = this.getPara("walletAccount");
        String operType = this.getPara("operType");
        String realGiftRate = this.getPara("realGiftRate");
        String companyId = this.getPara("companyId");
        String autoUnitPrice = this.getPara("autoUnitPrice");

        String autoUnitPriceA1 = this.getPara("power_a1");
        String autoUnitPriceA2 = this.getPara("power_a2");
        String autoUnitPriceA3 = this.getPara("power_a3");
        String autoUnitPriceA4 = this.getPara("power_a4");
        String autoUnitPriceA5 = this.getPara("power_a5");
        String autoUnitPriceA6 = this.getPara("power_a6");
        String autoUnitPriceA7 = this.getPara("power_a7");


        Integer startChargeStatus =9999;

        if (deviceId == null || devicePort == null || time == null){
            this.renderText(startChargeStatus.toString());
            return;
        }

        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(devicePort);
        Integer chargeTime = Integer.parseInt(time);//分钟
        Integer chargeTimeSenconds = chargeTime*60;
        log.info("开始充电 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId );

        JSONObject startChargeResltJson;
        if(chargeType.equals("auto")){
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, 0, TIMEOUT);
        }else {
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, chargeTimeSenconds, TIMEOUT);
        }

        Integer startPower = 0;
        if (null != startChargeResltJson){
            startChargeStatus = startChargeResltJson.getInteger("RESULT");
            startPower = startChargeResltJson.getInteger("POW");

        }


        log.info("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);

        if (startChargeStatus.equals(1) || startChargeStatus.equals(0)){
            //充电成功 todo 0状态是暂时处理，以后硬件解决了问题会去掉

            String powerSection;

            if (startPower < 200){
                autoUnitPrice = autoUnitPriceA1;
                powerSection = "A1";
            }else if (startPower < 300){
                autoUnitPrice = autoUnitPriceA2;
                powerSection = "A2";
            }else if (startPower < 350){
                autoUnitPrice = autoUnitPriceA3;
                powerSection = "A3";
            }else if (startPower < 500){
                autoUnitPrice = autoUnitPriceA4;
                powerSection = "A4";
            }else if (startPower < 700){
                autoUnitPrice = autoUnitPriceA5;
                powerSection = "A5";
            }else if (startPower < 1000){
                autoUnitPrice = autoUnitPriceA6;
                powerSection = "A6";
            }else {
                autoUnitPrice = autoUnitPriceA7;
                powerSection = "A7";
            }

            chargeBatteryService.saveNewDeviceChargeHistory(openId, deviceId, devicePort, time, chargeType, money, walletAccount, operType, realGiftRate, companyId ,autoUnitPrice);

            String title;

            if (operType.equals("M")){
                //微信充值,临时充电
                Integer chargeMoney = Integer.parseInt(money);
                title = "您选择临时充电，充电时间" + time + "分钟" + "充电费用" + chargeMoney/100D + "元";

            }else {
                //会员充电
                if (chargeType.equals("auto")){
                    //智能充电
                    title = "您选择会员充电，电车功率段" + powerSection + "段，充满自停,充电完成自动结费";

                }else {
                    //会员定时充电

                    Integer autoUnitPriceInt = Integer.parseInt(autoUnitPrice);
                    Integer moneyInt = new Double(Double.parseDouble(time)/60.0D * autoUnitPriceInt).intValue();

                    title = "您选择会员充电，电车功率段" + powerSection + "段，充电时间" +  time + "分钟" + "充电费用" + moneyInt/100D + "元";

                }

            }

            sendActiveMqStartCharge(openId, deviceId, devicePort, title);

        }else {
            log.error("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);
        }

        this.renderText(startChargeStatus.toString());
    }

    private void sendActiveMqStartCharge(String openId, String deviceId, String devicePort, String title){
        JmsSender jmsSender = ActiveMQ.getSender("sendtoWeixinPush");

        String area;
        NewDevice device = newDeviceService.queryDeviceInfo(deviceId);

        if (device != null){
            String province = device.get("province");
            String city = device.get("city");
            String detail_location = device.get("detail_location");
            String name = device.get("name");

            area =  province + city + detail_location + name;

        }else {
            log.error("查询不到充电设备");

            area =  "设备ID" + deviceId + "端口" + devicePort;
        }


        WeiXin weixin = new WeiXin();
        weixin.setArea(area);
        weixin.setChannelNum(devicePort);
        weixin.setDeviceId(deviceId);
        weixin.setMessage("开始充电");
        weixin.setOpenId(openId);
        weixin.setTitle(title );
        weixin.setType("AUTO");
        weixin.setMoney("");
        weixin.setWalletAccount(500);
        weixin.setChargeTime("200");
        weixin.setOperStartTime(new Date());
        String str = JSON.toJSONString(weixin);
        System.out.println(str);
        TextMessage msg = null;
        try {
            msg = jmsSender.getSession().createTextMessage(str);
            jmsSender.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void powerOff(){
        String id = this.getPara("id");
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + ",id=" + id + ",channelNum=" + channeNum + ",deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(channeNum);
        Integer powerOffStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, TIMEOUT);

        if (null == powerOffStatus){
            powerOffStatus = 9999;
        }

        log.info("断电结果 openId=" + openId + ",id=" + id + ",channelNum=" + channeNum + ",deviceId=" + deviceId + ",powerOffStatus=" + powerOffStatus);

        if (powerOffStatus == 1) {
            //断电成功
            ChargeBatteryInfo.dao.updateEndTimeById(id);
        }

        this.renderText(powerOffStatus.toString());

    }


    public void permissionOnlineAndUpdate(){
        String deviceId = this.getPara("deviceId");
        String openId = this.getPara("openId");
        String chargePileName = this.getPara("chargePileName");
        String province = this.getPara("province");
        String city = this.getPara("city");
        String location = this.getPara("location");
        String latitude = this.getPara("latitude");
        String longitude = this.getPara("longitude");
        String powerMax = this.getPara("powerMax");
        String socketSum = this.getPara("socketSum");
        String companyId = this.getPara("companyId");
        String projectIdStr = this.getPara("projectId");
        String installed = this.getPara("installed");


        Long chargePileId = Long.parseLong(deviceId);
        Double latitudeDouble = Double.parseDouble(latitude);
        Double longitudeDouble = Double.parseDouble(longitude);
        Long powerMaxLong = Long.parseLong(powerMax);
        Integer socketSumInt = Integer.parseInt(socketSum);
        Long companyIdLong = Long.parseLong(companyId);
        Long projectId = Long.parseLong(projectIdStr);

        log.info("开始入网 deviceId=" + deviceId );


        Boolean permissionOnlineSuccess;

        if (installed.equals("1")){
            //重新设置
            permissionOnlineSuccess = true;
        }else {
            permissionOnlineSuccess = deviceControlService.requestPermissionOnLine(chargePileId, TIMEOUT);
        }

        if (permissionOnlineSuccess != null && permissionOnlineSuccess){
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功");

            Boolean updateResult = newDeviceService.updateInstallInfo(chargePileId, chargePileName, province, city, location, latitudeDouble,
                    longitudeDouble, powerMaxLong, socketSumInt , companyIdLong, projectId);

            if (updateResult != null && updateResult.equals(true)) {

                log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功，信息更新成功");

                HnKejueResponse json = new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
                this.renderJson(json);
            }else {
                log.info("新设备入网结果 deviceId=" + deviceId + ", 安装信息更新失败");
                HnKejueResponse json = new HnKejueResponse(RespCode.DB_UPDATE_FAIL.getKey(), RespCode.DB_UPDATE_FAIL.getValue());
                this.renderJson(json);
            }
        }else {
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网失败");
            HnKejueResponse json = new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            this.renderJson(json);
        }

    }

    public void getDeviceInfo(){
        String deviceId = this.getPara("deviceId");
        log.info("根据二维码查询设备 deviceId = " + deviceId);

        //todo qr是设备sn，这里需要完成的工作是查出对应设备的插座数和总功率，最大功率，所属公司，传给前端   后续可以考虑查出该充电桩下面所有插座的使用情况
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);
    }

    public void getNotifyDeviceInfo(){

        String deviceId = this.getPara("deviceId");
        log.info("查询充电充电编号开始，deviceId=" + deviceId);
        if (deviceId.contains("deviceid") && deviceId.contains("=") && deviceId.contains("?")) {
            deviceId = deviceId.split("=")[1].replaceAll(" ", "");
            log.info("处理URL类型的二维码成功，deviceId＝" + deviceId);
        }

        HnKejueResponse json = newDeviceService.queryNotifyDevice(deviceId);
        log.info("根据二维码查询未安装设备结束：" + json);
        this.renderJson(json);

    }

    public void getDeviceCompanyInfo(){
        String deviceId = this.getPara("deviceId");
        log.info("根据deviceId查询设备 deviceId = " + deviceId);
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据deviceId查询设备结束：" + json);
        this.renderJson(json);
    }


    public void queryDeviceChargePrice() {
        String deviceId = this.getPara("deviceId");
        String projectIdStr = this.getPara("projectId");
        Long projectId = Long.parseLong(projectIdStr);
        log.info("查询充电价格开始,companyId=" + this.getPara("companyId"));
        HnKejueResponse json = newDeviceChargePriceService.queryProjectChargePrice(projectId);
        log.info("查询充电价格结束：" + json);
        this.renderJson(json);
    }

    public void quereChargeSocketStatus(){
        String deviceIdStr = this.getPara("deviceId");
        Long deviceId = Long.parseLong(deviceIdStr);
        log.info("查询设备充电插座状态开始,deviceId=" + this.getPara("deviceId"));
        HnKejueResponse json = newDeviceService.queryChargeSocketStatus(deviceId);
        log.info("根据设备充电插座状态结束：" + json);
        this.renderJson(json);

    }

    public void getAllCompany(){
        log.info("查询所有运营商公司");

        HnKejueResponse json = newDeviceService.getAllCompany();
        log.info("查询所有运营商公司结束：" + json);
        this.renderJson(json);

    }

    public void getProjectByCompanyId(){
        String companyIdStr = this.getPara("companyId");

        log.info("查询运营商公司下面所有的项目， companyId=" + companyIdStr);

        Long companyId = Long.parseLong(companyIdStr);

        HnKejueResponse json = newDeviceService.getProjectByCompanyId(companyId);
        log.info("查询运营商公司下面所有的项目结束：" + json);
        this.renderJson(json);

    }



}

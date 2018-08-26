package net.inconnection.charge.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeBatteryInfo;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;
import net.inconnection.charge.weixin.service.NewDeviceChargePriceService;
import net.inconnection.charge.weixin.service.NewDeviceService;

public class NewDeviceController extends Controller {
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


        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(devicePort);
        Integer chargeTime = Integer.parseInt(time);//分钟
        Integer chargeTimeSenconds = chargeTime*60;
        log.info("开始充电 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId );



        JSONObject startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, chargeTimeSenconds, 30*1000L);

        Integer startChargeStatus =9999;
        Integer startPower = 0;
        if (null != startChargeResltJson){
            startChargeStatus = startChargeResltJson.getInteger("RESULT");
            startPower = startChargeResltJson.getInteger("POW");

        }


        log.info("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);

        if (startChargeStatus.equals(1)){
            //充电成功

            if (startPower < 200){
                autoUnitPrice = autoUnitPriceA1;
            }else if (startPower < 300){
                autoUnitPrice = autoUnitPriceA2;
            }else if (startPower < 350){
                autoUnitPrice = autoUnitPriceA3;
            }else if (startPower < 500){
                autoUnitPrice = autoUnitPriceA4;
            }else if (startPower < 700){
                autoUnitPrice = autoUnitPriceA5;
            }else if (startPower < 1000){
                autoUnitPrice = autoUnitPriceA6;
            }else {
                autoUnitPrice = autoUnitPriceA7;
            }

            chargeBatteryService.saveNewDeviceChargeHistory(openId, deviceId, devicePort, time, chargeType, money, walletAccount, operType, realGiftRate, companyId ,autoUnitPrice);
        }


        this.renderText(startChargeStatus.toString());
    }

    public void powerOff(){
        String id = this.getPara("id");
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + ",id=" + id + ",channelNum=" + channeNum + ",deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(channeNum);
        Integer powerOffStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, 30*1000L);

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

        Long chargePileId = Long.parseLong(deviceId);
        Double latitudeDouble = Double.parseDouble(latitude);
        Double longitudeDouble = Double.parseDouble(longitude);
        Long powerMaxLong = Long.parseLong(powerMax);
        Integer socketSumInt = Integer.parseInt(socketSum);
        Long companyIdLong = Long.parseLong(companyId);

        Boolean permissonOnlineSuccess = deviceControlService.requestPermissionOnLine(chargePileId, 30*1000L);

        if (permissonOnlineSuccess){
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功");

            Boolean updateResult = newDeviceService.updateInstallInfo(chargePileId, chargePileName, province, city, location, latitudeDouble,
                    longitudeDouble, powerMaxLong, socketSumInt , companyIdLong);

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
        String deviceId = this.getPara("qr");

        //todo qr是设备sn，这里需要完成的工作是查出对应设备的插座数和总功率，最大功率，所属公司，传给前端   后续可以考虑查出该充电桩下面所有插座的使用情况
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);

    }

    public void getNotifyDeviceInfo(){
        String deviceId = this.getPara("deviceId");

        HnKejueResponse json = newDeviceService.queryNotifyDevice(deviceId);
        log.info("根据二维码查询未安装设备结束：" + json);
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



}

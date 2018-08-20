package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
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
        String type = this.getPara("type");
        String money = this.getPara("money");
        String walletAccount = this.getPara("walletAccount");
        String operType = this.getPara("operType");

        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(devicePort);
        Integer chargeTime = Integer.parseInt(time);
        Integer startChargeStatus = deviceControlService.requestStartCharge(deviceSN, socketSN, chargeTime, 30*1000L);

        if (null == startChargeStatus){
            startChargeStatus = 9999;
        }

        chargeBatteryService.saveNewDeviceChargeHistory(openId, deviceId, devicePort, time, type, money, walletAccount, operType);

        renderText(startChargeStatus.toString());
    }

    public void powerOff(){
        String id = this.getPara("id");
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + ",id=" + id + ",channeNum=" + channeNum + ",deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(channeNum);
        Integer powerOffStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, 30*1000L);



        if (null == powerOffStatus){
            powerOffStatus = 9999;
        }

        if (powerOffStatus == 1) {
            //断电成功
            ChargeBatteryInfo.dao.updateEndTimeById(id);
        }

        renderText(powerOffStatus.toString());

    }

    public void permissionOnLine(){
        String deviceId = this.getPara("deviceId");
        log.info("新设备允许入网 deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Boolean permissonOnlineSuccess = deviceControlService.requestPermissionOnLine(deviceSN, 30*1000L);

        if (permissonOnlineSuccess){
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功");
            renderText("success");
        }else {
            renderText("fail");
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网失败");
        }
    }

    public void getDeviceInfo(){
        String deviceId = this.getPara("qr");

        //todo qr是设备sn，这里需要完成的工作是查出对应设备的插座数和总功率，最大功率，所属公司，传给前端   后续可以考虑查出该充电桩下面所有插座的使用情况
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);

    }


    public void queryDeviceChargePrice() {
        String deviceId = this.getPara("deviceId");
        String companyIdStr = this.getPara("companyId");
        Long companyId = Long.parseLong(companyIdStr);
        log.info("查询充电价格开始,companyId=" + this.getPara("companyId"));
        HnKejueResponse json = newDeviceChargePriceService.queryChargePrice(companyId);
        log.info("根据内部编号查询设备结束：" + json);
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

}

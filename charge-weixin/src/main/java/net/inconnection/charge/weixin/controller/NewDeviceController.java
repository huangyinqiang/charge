package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.NewDeviceService;

public class NewDeviceController extends Controller {
    private static DeviceControlService deviceControlService = DubboServiceContrain.getInstance().getService(DeviceControlService.class);

    private static final NewDeviceService newDeviceService = new NewDeviceService();

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
        Integer startChargeStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, 30*1000L);

        renderText(startChargeStatus.toString());

    }

    public void permissionOnLine(){
        String deviceId = this.getPara("deviceId");
        String openId = this.getPara("openId");
        log.info("设备允许入网 openId=" + openId + "deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Boolean permissonOnlineSuccess = deviceControlService.requestPermissionOnLine(deviceSN, 30*1000L);

        if (permissonOnlineSuccess){
            renderText("success");
        }else {
            renderText("fail");
        }
    }

    public void getDeviceInfo(){
        String deviceId = this.getPara("qr");

        //todo qr是设备sn，这里需要完成的工作是查出对应设备的插座数和总功率，最大功率，所属公司，传给前端   后续可以考虑查出该充电桩下面所有插座的使用情况
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);

    }

}

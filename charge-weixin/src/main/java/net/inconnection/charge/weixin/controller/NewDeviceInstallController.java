package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.NewDeviceChargePriceService;
import net.inconnection.charge.weixin.service.NewDeviceService;

public class NewDeviceInstallController extends Controller {



    private static final Log log = Log.getLog(NewDeviceInstallController.class);

    public void index() {
        //转到设备安装页面
        log.info("转到设备安装页面 ");
        this.render("install/startInstallNewDevice.html");
    }

    public void editInstallInfo() {

        this.setAttr("deviceId", this.getPara("deviceId"));
        this.setAttr("latitude", this.getPara("latitude"));
        this.setAttr("longitude", this.getPara("longitude"));
        log.info("转到安装信息编辑页面 ,latitude=  " + this.getPara("latitude") + ", longitude=" + this.getPara("longitude"));
        this.render("install/chargePileInstallInfo.html");
    }





}

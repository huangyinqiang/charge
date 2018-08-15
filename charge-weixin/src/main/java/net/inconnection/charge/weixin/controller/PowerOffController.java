package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.PoweroffService;

public class PowerOffController extends Controller {
    private static final Log log = Log.getLog(PowerOffController.class);
    private static PoweroffService poweroffService = new PoweroffService();

    public PowerOffController() {
    }

    public void index() {
        this.render("poweroff/poweroff.html");
    }

    public void poweroff() {
        String id = this.getPara("id");
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + ",id=" + id + ",channeNum=" + channeNum + ",deviceId=" + deviceId);
        HnKejueResponse resp = poweroffService.poweroff(id, openId, deviceId, channeNum);
        log.info("断电结束：" + resp);
        this.renderJson(resp);
    }

    public void queryPoweroff() {
        String openId = this.getPara("openId");
        log.info("查看远程断电开始" + openId);
        HnKejueResponse resp = poweroffService.queryPoweroff(openId);
        log.info("查看远程断电结束" + resp);
        this.renderJson(resp);
    }



}


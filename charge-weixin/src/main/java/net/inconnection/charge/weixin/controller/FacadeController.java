package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;
import net.inconnection.charge.weixin.service.FacadeService;

public class FacadeController extends Controller {
    private static final Log log = Log.getLog(FacadeController.class);
    FacadeService facadeService = new FacadeService();
    ChargeInfoBatteryService chargeInfoBatteryService = new ChargeInfoBatteryService();

    public FacadeController() {
    }

    public void payer() {
        String openId = this.getPara("openId");
        String money = this.getPara("money");
        String desc = this.getPara("desc");
        String reUserName = this.getPara("reUserName");
        log.info("企业付款开始,openId=" + openId + ",money=" + money + ",desc=" + desc + ",reUserName=" + reUserName);
        HnKejueResponse resp = this.facadeService.payer(openId, money, desc, reUserName);
        log.info("企业付款结束:" + resp);
        this.renderJson(resp);
    }

    public void updateIntelCharge() {
        String devicelId = this.getPara("devicelId");
        String channelNum = this.getPara("channelNum");
        String openId = this.getPara("openId");
        String money = this.getPara("money");
        log.info("根据功率扣费，更新充电时间和金额开始,openId=" + openId + ",money=" + money + ",devicelId=" + devicelId + ",channelNum=" + channelNum);
        HnKejueResponse resp = this.chargeInfoBatteryService.updateIntelCharge(devicelId, channelNum, openId, money);
        log.info("根据功率扣费，更新充电时间和金额结束:" + resp);
        this.renderJson(resp);
    }
}


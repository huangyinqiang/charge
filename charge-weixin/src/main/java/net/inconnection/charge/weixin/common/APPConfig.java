package net.inconnection.charge.weixin.common;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import net.inconnection.charge.weixin.controller.*;
import net.inconnection.charge.weixin.model.*;
import net.inconnection.charge.weixin.scheduler.task.SchedulerPlugin;
import net.inconnection.charge.weixin.weixinCode.controller.OauthController;
import net.inconnection.charge.weixin.weixinCode.controller.ScanCodeOauthController;
import net.inconnection.charge.weixin.weixinCode.controller.WeiXinPayController;
import net.inconnection.charge.weixin.weixinCode.controller.WeiXinScanCodeOauthController;

import java.io.File;

public class APPConfig extends JFinalConfig {
    static Log log = Log.getLog(APPConfig.class);

    public APPConfig() {
    }

    public void loadProp(String pro, String dev) {
        try {
            PropKit.use(pro);
        } catch (Exception var4) {
            PropKit.use(dev);
        }

    }

    public void configConstant(Constants me) {
        this.loadProp("cjk_config_pro.txt", "cjk_config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setEncoding("utf-8");
        me.setViewType(ViewType.JSP);
        me.setBaseUploadPath(PathKit.getWebRootPath() + File.separator + "myupload");
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    public void configRoute(Routes me) {
        me.add("/", IndexController.class, "/front");
        me.add("/oauth", OauthController.class);
        me.add("/tokenCheck", TokenCheckController.class);
        me.add("/scanCodeOauth", ScanCodeOauthController.class);
        me.add("/weixinScanCodeOauth", WeiXinScanCodeOauthController.class);
        me.add("/pay", WeiXinPayController.class, "/front");
        me.add("/scan", ScanController.class, "/front");
        me.add("/weixinScanCode", ScanCodeController.class, "/front");
        me.add("/device", DeviceController.class, "/front");
        me.add("/user", UserController.class, "/front");
        me.add("/charging", ChargeController.class, "/front");
        me.add("/recharge", RechargeController.class, "/front");
        me.add("/poweroff", PowerOffController.class, "/front");
        me.add("/about", AboutController.class, "/front");
        me.add("/facade", FacadeController.class, "/front");
        me.add("/map", MapController.class, "/front");
    }

    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        me.add(c3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.addMapping("tuser", TUser.class);
        arp.addMapping("weixin_token", WeixinToken.class);
        arp.addMapping("qr_match_device", Device.class);
        arp.addMapping("orders", Orders.class);
        arp.addMapping("charge_battery_info", ChargeBatteryInfo.class);
        arp.addMapping("charge_money_info", ChargeMoneyInfo.class);
        arp.addMapping("money_match_activity", MoneyMatchActivity.class);
        arp.addMapping("pay_to_agent_req", PayToAgentResp.class);
        arp.addMapping("b_map", BaiDuMap.class);
        arp.setShowSql(true);
        me.add(arp);
        me.add(new EhCachePlugin());
        SchedulerPlugin sp = new SchedulerPlugin("job.properties");
        me.add(sp);
    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/", 5);
    }
}


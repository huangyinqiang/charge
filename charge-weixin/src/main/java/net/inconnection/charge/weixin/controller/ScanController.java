package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.weixinCode.service.WechatJsSdkService;

public class ScanController extends Controller {
    private static Log log = Log.getLog(ScanController.class);
    private static final WechatJsSdkService wechatJsSdkService = new WechatJsSdkService();

    public ScanController() {
    }

    public void getJsSdk() {
        String url = this.getPara("url");
        Object json = wechatJsSdkService.sign(url);
        log.info("获取jssdk签名结束：" + json);
        this.renderJson(json);
    }

    public void qrImage() {
        log.info("跳转到关注公众号页面");
        this.render("common/qrImage.html");
    }
}


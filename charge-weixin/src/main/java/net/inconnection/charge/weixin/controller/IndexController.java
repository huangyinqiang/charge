package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import org.apache.commons.lang.StringUtils;

public class IndexController extends Controller {
    private static Log log = Log.getLog(IndexController.class);
    private static final String openid = "";

    public IndexController() {
    }

    public void index() {
        String openId = (String)this.getSessionAttr("openId");
        log.info("开始获取session中的openid");
        String type = this.getPara("type");
        log.info("openid=" + openId + ",type=" + type);


//todo ceshi
//        this.render("ucenter.html");
//
//        if (1==1){
//            return;
//        }



        if (StringUtils.isBlank(openId) && type != null && type.equals("2")) {
            String calbackUrl = PropKit.get("domain") + "/scanCodeOauth";
            String url = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, "111", false);
            log.info("跳转到授权页scanCodeOauth" + url);
            this.redirect(url);
        } else if (StringUtils.isNotBlank(openId) && type != null && type.equals("2")) {
            log.info("跳转到我要充电页面 openId=" + openId);
            this.render("charging/startCharging.html");
        } else if (StringUtils.isBlank(openId)) {
            log.info("跳转到授权页 toOauth");
            this.forwardAction("/toOauth");
        } else if (StringUtils.isNotBlank(openId)) {
            log.info("开始访问个人中心：" + openId);
            this.render("ucenter.html");
        }
    }

    public void getOpenId() {
        log.info("开始获取session中的openid");
        HnKejueResponse respJson = new HnKejueResponse();
        String openId = (String)this.getSessionAttr("openId");

        //openId="oNFzS1F-DOqXNLIUeygz_KspS-i0";//todo ceshi

        log.info("session中的openid=" + openId);
        respJson.setRespCode("000000");
        respJson.setRespObj(openId);
        this.renderJson(respJson);
    }

    public void toOauth() {
        log.info("跳转到oauth授权页面");
        String calbackUrl = PropKit.get("domain") + "/oauth";
        String url = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, "111", false);
        this.redirect(url);
    }

    public void toscanCodeOauth() {
        String calbackUrl = PropKit.get("domain") + "/scanCodeOauth";
        String url = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, "111", false);
        log.info("跳转到授权页scanCodeOauth" + url);
        this.redirect(url);
    }
}


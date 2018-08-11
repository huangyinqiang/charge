package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.service.TuserService;
import net.inconnection.charge.weixin.utils.TencentSMSUtils;
import org.apache.commons.lang.StringUtils;

public class UserController extends Controller {
    private static Log log = Log.getLog(UserController.class);
    private static final TuserService tuserService = new TuserService();

    public UserController() {
    }

    public void findUserInfo() {
        String openId = this.getPara("openId");
        log.info("查询用户信息开始,openId=" + openId);
        HnKejueResponse resp = tuserService.findByOpenId(openId);
        log.info("查询用户信息结束：" + resp.toString());
        this.renderJson(resp);
    }

    public void userInfo() {
        this.render("user/userInfo.html");
    }

    public void bandTelNumber() {
        this.render("user/bandTelNumber.html");
    }

    public void sendSMSAuthCode(){
        String tel = this.getPara("tel");

        String smsAuthCode= net.inconnection.charge.weixin.utils.StringUtils.random(6, net.inconnection.charge.weixin.utils.StringUtils.RandomType.INT);
        int res_code = -9;
        //注册
        res_code= TencentSMSUtils.SMSCode(tel, smsAuthCode);

        if (res_code!=0) {
            log.error("短信验证码发送失败");
            HnKejueResponse resp =  new HnKejueResponse(RespCode.SEND_SMS_AUTH_FAILD.getKey(), RespCode.SEND_SMS_AUTH_FAILD.getValue());
            this.renderJson(resp);
            return;
        }
        //验证码放入缓存
        CacheKit.put("tenMinute", tel, smsAuthCode);

        HnKejueResponse resp =  new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        this.renderJson(resp);

    }

    public void updateTelNumber() {

        String tel = this.getPara("tel");
        String smsAuthCode = this.getPara("smsAuthCode");


        boolean mobileCodeEquals = mobileCodeEquals(tel, smsAuthCode);
        if (!mobileCodeEquals) {
            log.error("手机号校验失败");
            HnKejueResponse resp =  new HnKejueResponse(RespCode.SMS_AUTH_FAILD.getKey(), RespCode.SMS_AUTH_FAILD.getValue());
            this.renderJson(resp);
        }




        String openId = this.getPara("openId");
        log.info("修改绑定的电卡开始，openId=" + openId + ",tel=" + tel + ",openId=" + openId);
        if (StringUtils.isEmpty(openId)) {
            this.forwardAction("/toOauth");
        } else {
            HnKejueResponse resp = tuserService.updateCardNumberByOpendid(openId, tel);
            this.renderJson(resp);
        }
    }

    private boolean mobileCodeEquals(String tel,String smsAuthCode){
        //判断手机验证码是否正确
        String cache_code = CacheKit.get("tenMinute", tel);
        log.info("cache_code:"+cache_code +"tel_code:"+smsAuthCode);
        if (null==cache_code || !smsAuthCode.equals(cache_code)) {
            return false;
        }else {
            //如果相同则移除该手机验证码缓存
            CacheKit.remove("tenMinute", tel);
            return true;
        }
    }
}


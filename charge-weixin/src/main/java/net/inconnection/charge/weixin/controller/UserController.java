package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.TuserService;
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

    public void bandCard() {
        this.render("user/bandCard.html");
    }

    public void updateBandCard() {
        String cardNumber = this.getPara("cardNumber");
        String tel = this.getPara("tel");
        String openId = this.getPara("openId");
        log.info("修改绑定的电卡开始，openId=" + openId + ",tel=" + tel + ",openId=" + openId);
        if (StringUtils.isEmpty(openId)) {
            this.forwardAction("/toOauth");
        } else {
            HnKejueResponse resp = tuserService.updateCardNumberByOpendid(cardNumber, openId, tel);
            this.renderJson(resp);
        }
    }
}


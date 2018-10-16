package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ProjectActivity;
import net.inconnection.charge.weixin.service.ChargeMoneyService;
import net.inconnection.charge.weixin.service.CompanyActivityService;
import net.inconnection.charge.weixin.service.MoneyMatchActivityService;
import net.inconnection.charge.weixin.service.ProjectActivityService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class RechargeController extends Controller {
    private static Log log = Log.getLog(RechargeController.class);
    private static MoneyMatchActivityService activityService = new MoneyMatchActivityService();
    private static ChargeMoneyService chargeMoneyService = new ChargeMoneyService();

    private static CompanyActivityService companyActivityService = new CompanyActivityService();
    private static ProjectActivityService projectActivityService = new ProjectActivityService();

    public RechargeController() {
    }

    public void index() {
        String type = this.getPara("type");
        log.info("跳转到扫码充值页面  type=" + type);
        this.setAttr("type", type);
        this.render("recharge/startRecharge.html");
    }

    public void rechargeInfo() {
        log.info("跳转到充值记录页面");
        this.render("recharge/rechargeInfo.html");
    }

    public void recharge() {
        this.setAttr("deviceId", this.getPara("deviceId"));
        this.setAttr("activityId", this.getPara("activityId"));
        this.setAttr("type", this.getPara("type"));
        String decode = "";

        try {
            decode = URLDecoder.decode(this.getPara("area"), "UTF-8");
            this.setAttr("area", decode);
        } catch (UnsupportedEncodingException var3) {
            log.error("转码错误" + var3);
        }

        log.info("跳转到我要充值界面,deviceId=" + this.getPara("deviceId") + ",type=" + this.getPara("type") + ",activityId=" + this.getPara("activityId") + ",area=" + decode);
        this.render("recharge/recharge.html");
    }

    public void rechargeNew(){
        this.setAttr("companyId", this.getPara("companyId"));
        this.setAttr("projectId", this.getPara("projectId"));

        log.info("跳转到新充值界面,companyId=" + this.getPara("companyId")+",projectId="+ this.getPara("projectId"));
        this.render("recharge/rechargeNew.html");

    }


    public void queryActivityInfo() {
        String activityId = this.getPara("activityId");
        log.info("根据充值类型和优惠编号查询详情开始,activityId=" + this.getPara("activityId"));
        HnKejueResponse response = activityService.queryActivityByType(activityId);
        log.info("查询优惠信息结束：" + response);
        this.renderJson(response);
    }

    public void queryActivityInfoByCompanyId() {
        String companyId = this.getPara("companyId");
        log.info("根据充值类型和优惠编号查询详情开始,activityId=" + this.getPara("activityId"));
        HnKejueResponse response = companyActivityService.queryActivityByCompanyId(companyId);
        log.info("查询优惠信息结束：" + response);
        this.renderJson(response);
    }

    public void addChargeMoney() {
        String openId = this.getPara("openId");
        String chargeType = this.getPara("type");
        String money = this.getPara("money");
        String chargeNum = this.getPara("chargeNum");
        String coupon = this.getPara("coupon");
        String deviceId = this.getPara("deviceId");
        String walletAccount = this.getPara("walletAccount");
        String cardNumber = this.getPara("cardNumber");

        String walletRealMoney = this.getPara("wallet_real_money");
        String walletGiftMoney = this.getPara("wallet_gift_money");
        String companyId = this.getPara("companyId");

        log.info("新增充值信息结束开始 openId=" + openId + ",deviceId=" + deviceId + ",type=" + chargeType + ",money=" + money
                    + ",入帐金额=" + chargeNum + ",入帐赠送金额=" + coupon + ",电卡卡号=" + cardNumber + ",钱包实际充值余额=" + walletRealMoney + ",钱包赠费余额" + walletGiftMoney + ",公司ID" + companyId);
        HnKejueResponse response = chargeMoneyService.addRechargInfo(openId, deviceId, chargeType, walletAccount, money, chargeNum, coupon, cardNumber, walletRealMoney, walletGiftMoney, companyId);
        log.info("新增充值信息结束：" + response);
        this.renderJson(response);
    }

    public void queryRechargeInfo() {
        String openId = this.getPara("openId");
        String pageNumber = this.getPara("pageNumber");
        String pageSize = this.getPara("pageSize");
        log.info("分页查询充值记录开始 openId=" + openId + ",pageNumber=" + pageNumber + ",pageSize=" + pageSize);
        HnKejueResponse resp = chargeMoneyService.queryAllByOpenId(openId, pageNumber, pageSize);
        log.info("分页查询充值记录结束" + resp);
        this.renderJson(resp);
    }

    public void queryActivityInfoByProjectId() {
        log.info("queryActivityInfoByProjectId projectId=" +  this.getPara("projectId") + ",companyId=" + this.getPara("companyId") );
        String projectId = this.getPara("projectId");
        List<ProjectActivity> projectActivityList = projectActivityService.queryActivityByProjectId(projectId);
        log.info("根据项目查询优惠信息结束：" + projectActivityList);
        String companyId = this.getPara("companyId");
        if(projectActivityList == null || projectActivityList.size() == 0){
            HnKejueResponse response = companyActivityService.queryActivityByCompanyId(companyId);
            log.info("根据公司查询优惠信息结束：" + response);
            this.renderJson(response);
        }else{
            this.renderJson(new HnKejueResponse(projectActivityList,RespCode.FAILD.getKey(), RespCode.FAILD.getValue()));
        }

    }
}


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeHistory;
import net.inconnection.charge.extend.model.GiftRefund;
import net.inconnection.charge.extend.model.RechargeHistory;
import net.inconnection.charge.extend.model.Tuser;
import net.inconnection.charge.extend.model.TuserCharge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TuserController extends BaseController {
    public TuserController() {
    }

    public void listPage() {
        this.render("list.html");
    }

    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "tuser", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "tuser", properties, symbols, values));
    }

    public void addPage() {
        this.render("add.html");
    }

    public void updatePage() {
        this.setAttr("model", Tuser.me.findById(this.getPara("id")));
        this.render("update.html");
    }

    public void editRole() {
        this.setAttr("model", Tuser.me.findById(this.getPara("id")));
        this.render("editRole.html");
    }

    public void editCompany() {
        this.setAttr("model", Tuser.me.findById(this.getPara("id")));
        this.render("editCompany.html");
    }

    public void update() {
        String companyId = this.getPara("companyId");
        Tuser model = (Tuser)Tuser.me.findById(this.getPara("id"));
        Integer walletAccount = model.getWalletAccount();
        Integer walletRealMoney = model.getWalletRealMoney() == null ? 0 :model.getWalletRealMoney();
        int walletGiftMoney = model.getWalletGiftMoney() == null ? 0 : model.getWalletGiftMoney();

        int id = (Integer)model.get("id");
        int type = this.getParaToInt("type");
        String dataId = this.getPara("dataId");
        int money = this.getParaToInt("money");
//        String name = (String)model.get("nickName");
//        model.set("nickName", this.getPara("model.nickName"));
//        String chargeWall = this.getPara("model.walletAccount");
//        float chargeTmp = Float.parseFloat(chargeWall);
//        model.set("walletAccount", chargeTmp * 100.0F);
        GiftRefund giftRefund = new GiftRefund();
        giftRefund.setLast(Long.valueOf(walletAccount));
        if(type == 1){  //充值

            //修充值记录
            RechargeHistory rechargeHistory = RechargeHistory.dao.findById(dataId);
            Integer realMoney = rechargeHistory.getRealMoney();
            rechargeHistory.setCoupon(money);
            rechargeHistory.setMoneySum(money+realMoney);
            rechargeHistory.update();

            //修改赠费比

            double realGiftRate = walletRealMoney/(double)(walletAccount + money );
            model.setWalletAccount(walletAccount+money);
            model.setWalletGiftMoney(model.getWalletAccount()-walletRealMoney);
            model.setRealGitRate(realGiftRate);

            giftRefund.setRechargeId(Long.valueOf(dataId));
            giftRefund.setCompanyId(Integer.parseInt(rechargeHistory.getCompanyId().toString()));


        }else if(type == 2){//消费

            //修改消费记录
            ChargeHistory chargeHistory = ChargeHistory.dao.findById(dataId);
            chargeHistory.setChargeMoney(money);
            Double realRate = chargeHistory.getRealRate();
            int realMoney = new Double((double)money * realRate).intValue();
            chargeHistory.setRealMoney(realMoney);
            chargeHistory.update();

            //修改赠费比
            model.setWalletAccount(walletAccount+money);
            model.setWalletRealMoney(walletRealMoney-realMoney);
            model.setWalletGiftMoney(walletGiftMoney-(money-realMoney));

            giftRefund.setChargeId(Long.valueOf(dataId));
            giftRefund.setDeviceId(Long.valueOf(chargeHistory.getDeviceId()));
            giftRefund.setCompanyId(Integer.parseInt(chargeHistory.getCompanyId().toString()));

        }else if(type == 3){
            model.setWalletAccount(money);
            model.setWalletGiftMoney(0);
            model.setWalletRealMoney(0);
            model.setRealGitRate(0.0);
            //修充值记录
            RechargeHistory rechargeHistory = RechargeHistory.dao.findById(dataId);
            giftRefund.setRechargeId(Long.valueOf(dataId));
            giftRefund.setCompanyId(Integer.parseInt(rechargeHistory.getCompanyId().toString()));

        }else if(type == 4){
            model.setWalletAccount(money);
            model.setWalletGiftMoney(0);
            model.setWalletRealMoney(0);
            model.setRealGitRate(0.0);

            giftRefund.setCompanyId(Integer.parseInt(companyId));
        }


        //退费赠费记录
        giftRefund.setOpenId(model.getOpenId());
        giftRefund.setType(type);
        giftRefund.setMoney(Long.valueOf(money));
        giftRefund.save();



//        TuserCharge tCharge = new TuserCharge();
//        tCharge.set("tuserid", id);
//        tCharge.set("name", name);
//        tCharge.set("charge", chargeTmp);
//        tCharge.set("last", last);
//        tCharge.set("jointime", new Date());
//        tCharge.save();
        model.update();
        this.addOpLog("[在线用户] 修改");
        this.renderSuccess();
    }

    public void editRoleUpdate() {
        Tuser model = (Tuser)Tuser.me.findById(this.getPara("id"));
        int id = (Integer)model.get("id");
        int last = model.getInt("walletAccount");
        String name = (String)model.get("nickName");
        model.set("nickName", this.getPara("model.nickName"));
        String role = this.getPara("model.role");
        model.set("role", role);
        TuserCharge tCharge = new TuserCharge();
        tCharge.set("tuserid", id);
        tCharge.set("name", name);
        tCharge.set("last", last);
        tCharge.set("jointime", new Date());
        tCharge.save();
        model.update();
        this.addOpLog("[在线用户] 修改角色");
        this.renderSuccess();
    }

    public void detailPage() {
        Tuser model = (Tuser)Tuser.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        this.render("detail.html");
    }

    public void exportCsv() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "tuser", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        headers.add("昵称");
        clomuns.add("nickName");
        headers.add("头像");
        clomuns.add("headimgurl");
        headers.add("城市");
        clomuns.add("city");
        headers.add("省会");
        clomuns.add("province");
        headers.add("手机号码");
        clomuns.add("tel");
        headers.add("注册时间");
        clomuns.add("registerDate");
        headers.add("最后登录时间");
        clomuns.add("lastLoginDate");
        headers.add("卡号");
        clomuns.add("cardNumber");
        headers.add("是否绑定手机(Y:绑定 N:未绑定)");
        clomuns.add("band");
        headers.add("钱包");
        clomuns.add("walletAccount");
        CsvRender csvRender = new CsvRender(headers, list);
        csvRender.clomuns(clomuns);
        csvRender.fileName("在线用户");
        this.addOpLog("[在线用户] 导出cvs");
        this.render(csvRender);
    }

    public void queryRechargeByOpenId() {
        List<RechargeHistory> historyList = RechargeHistory.dao.queryRechargeHistoryByOpenId(this.getPara
                ("openId"));
        renderDatagrid(historyList,historyList.size());

    }
    public void queryChargeByOpenId() {
        List<ChargeHistory> chargeHistoryList = ChargeHistory.dao.queryChargeHistoryByOpenId(this.getPara
                ("openId"));
        renderDatagrid(chargeHistoryList,chargeHistoryList.size());

    }
}

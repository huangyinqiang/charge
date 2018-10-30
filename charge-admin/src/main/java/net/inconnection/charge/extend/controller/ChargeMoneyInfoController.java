//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeMoneyInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChargeMoneyInfoController extends BaseController {
    public ChargeMoneyInfoController() {
    }

    public void listPage() {
        this.render("list.html");
    }

    public void listSumPage() {
        this.render("sum.html");
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
        SysUser sysUser = getSessionUser();
        List<String> properties1 = new ArrayList(Arrays.asList(properties));
        List<String> symbols1 = new ArrayList(Arrays.asList(symbols));
        List<Object> values1 = new ArrayList(Arrays.asList(values));
        if(1 != sysUser.getId()){
            properties1.add("gid");
            symbols1.add("=");
            values1.add(sysUser.getId());
            properties =  properties1.toArray(new String[properties1.size()]);
            symbols =  symbols1.toArray(new String[symbols1.size()]);
            values =  values1.toArray();
        }
        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "weixin_query_view", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "weixin_query_view", properties, symbols, values));
    }

    public void listSumData() {
        String sql = "select chargetype,sum(money)/100 as money,sum(amount)/100 as amount,sum(walletAccount)/100 as walletaccount from charge_money_info inner join tuser on charge_money_info.openId = tuser.openId";
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        List<Object> list = DBTool.findDbSource2("zcurd_busi", sql, properties, symbols, values);
        List<Record> result = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);
            String s1 = (String)a[0];
            BigDecimal s2 = (BigDecimal)a[1];
            BigDecimal s3 = (BigDecimal)a[2];
            BigDecimal s4 = (BigDecimal)a[3];
            r.set("chargetype", s1);
            r.set("money", s2);
            r.set("amount", s3);
            r.set("walletaccount", s4);
            result.add(r);
        }

        this.renderDatagrid(result, 2);
    }

    public void listSumData1() {
        String sql = "select chargetype,sum(money)/100 as money,sum(amount)/100 as amount from charge_money_info inner join qr_match_device on charge_money_info.deviceId = qr_match_device.match_num";
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        long gid = (Long)this.getSessionUser().get("id");
        List<Object> list = DBTool.findDbSource3("zcurd_busi", sql, properties, symbols, values, gid);
        List<Record> result = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);
            String s1 = (String)a[0];
            BigDecimal s2 = (BigDecimal)a[1];
            BigDecimal s3 = (BigDecimal)a[2];
            r.set("chargetype", s1);
            r.set("money", s2);
            r.set("amount", s3);
            result.add(r);
        }

        this.renderDatagrid(result, 2);
    }

    public void detailPage() {
        ChargeMoneyInfo model = (ChargeMoneyInfo)ChargeMoneyInfo.me.findById(this.getParaToInt("id"));
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

        SysUser sysUser = getSessionUser();
        List<String> properties1 = new ArrayList(Arrays.asList(properties));
        List<String> symbols1 = new ArrayList(Arrays.asList(symbols));
        List<Object> values1 = new ArrayList(Arrays.asList(values));
        if(1 != sysUser.getId()){
            properties1.add("gid");
            symbols1.add("=");
            values1.add(sysUser.getId());
            properties =  properties1.toArray(new String[properties1.size()]);
            symbols =  symbols1.toArray(new String[symbols1.size()]);
            values =  values1.toArray();
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "weixin_query_view", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        headers.add("用户名称");
        clomuns.add("nickName");
        headers.add("充值金额(元)");
        clomuns.add("money");
        headers.add("实到金额(元)");
        clomuns.add("amount");
        headers.add("充值时间");
        clomuns.add("createTime");
        headers.add("剩余金额(元)");
        clomuns.add("walletAccount");
        headers.add("充值类型(CH 物理充电卡,WA虚拟充电卡)");
        clomuns.add("chargeType");
        CsvRender csvRender = new CsvRender(headers, list);
        csvRender.clomuns(clomuns);
        csvRender.fileName("微信充值记录");
        this.addOpLog("[微信充值记录] 导出cvs");
        this.render(csvRender);
    }
}

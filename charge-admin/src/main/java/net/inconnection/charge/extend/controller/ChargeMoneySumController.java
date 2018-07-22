//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChargeMoneySumController extends BaseController {
    public ChargeMoneySumController() {
    }

    public void listSumPage() {
        this.render("sum.html");
    }

    public void listSumData() {
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
}

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
import net.inconnection.charge.extend.model.Cardlog;

import java.util.ArrayList;
import java.util.List;

public class CardlogController extends BaseController {
    public CardlogController() {
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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "cardlog", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "cardlog", properties, symbols, values));
    }

    public void detailPage() {
        Cardlog model = (Cardlog)Cardlog.me.findById(this.getParaToInt("id"));
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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "cardlog", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        CsvRender csvRender = new CsvRender(headers, list);
        csvRender.clomuns(clomuns);
        csvRender.fileName("刷卡记录");
        this.addOpLog("[刷卡记录] 导出cvs");
        this.render(csvRender);
    }
}

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import com.sun.org.apache.bcel.internal.generic.NEW;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.*;

import java.util.Date;
import java.util.List;

public class CompanyController extends BaseController{



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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_company", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_company", properties, symbols, values));
    }


    public void addPage() {
        this.render("add.html");
    }


    //增加
    public void add() {
        Company model = getModel(Company.class, "model");
        model.setCreateTime(new Date());
        model.save();
        renderSuccess();
    }


    //修改页面
    public void updatePage() {
        //setAttr("dictDatastatus", Company.me.getDictDatastatus());
        setAttr("model", Company.me.findById(getPara("id")));
        render("update.html");
    }

    public void update() {
        Company model = Company.me.findById(this.getPara("id"));
        model.set("company_name",this.getPara("model.company_name"));
        model.set("taxpayer_id",this.getPara("model.taxpayer_id"));
        model.set("province",this.getPara("model.province"));
        model.set("city",this.getPara("model.city"));
        model.set("detail_location",this.getPara("model.detail_location"));
        model.set("legal_person",this.getPara("model.legal_person"));
        model.set("weixin_account",this.getPara("model.weixin_account"));
        model.set("admin_id",this.getPara("model.admin_id"));
        model.set("balance_rate",this.getPara("model.balance_rate"));
        model.set("balance_type",this.getPara("model.balance_type"));
        model.set("contact",this.getPara("model.contact"));
        model.set("contact_phone",this.getPara("model.contact_phone"));
        model.set("update_time",new Date());
        model.update();
        this.renderSuccess();
    }

    public void delete() {
        Long[] ids = this.getParaValuesToLong("id[]");
        for(int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            ((new Company()).set("id", id)).delete();
        }

        this.addOpLog("[公司信息] 删除");
        this.renderSuccess();
    }

    //修改页面
    public void updatePricePage() {

        List<Chargeprice> chargeprices = Chargeprice.dao.find("select * from yc_chargeprice where company_id="+getPara("id"));
        if (chargeprices==null || chargeprices.size()==0){
            setAttr("company_name", getPara("company_name"));
            setAttr("company_id", getPara("id"));
            render("addPrice.html");
        }else {
            setAttr("company_name", getPara("company_name"));
            setAttr("model", chargeprices.get(0));
            setAttr("id", chargeprices.get(0).getId());
            render("updatePrice.html");
        }
    }


    //增加
    public void addPrice() {
        Chargeprice model = getModel(Chargeprice.class, "model");
        model.setCreatetime(new Date());
        model.save();
        renderSuccess();
    }


    public void updatePrice() {

        Chargeprice model = Chargeprice.me.findById(this.getPara("id"));
        model.set("tow_hours_price",this.getPara("model.tow_hours_price"));
        model.set("four_hours_price",this.getPara("model.four_hours_price"));
        model.set("eight_hours_price",this.getPara("model.eight_hours_price"));
        model.set("twelve_hours_price",this.getPara("model.twelve_hours_price"));
        model.set("tow_hours_mem_price",this.getPara("model.tow_hours_mem_price"));
        model.set("four_hours_mem_price",this.getPara("model.four_hours_mem_price"));
        model.set("eight_hours_mem_price",this.getPara("model.eight_hours_mem_price"));
        model.set("twelve_hours_mem_price",this.getPara("model.twelve_hours_mem_price"));
        model.set("auto_price",this.getPara("model.auto_price"));

        model.setUpdatetime(new Date());
        model.update();
        renderSuccess();
    }



    public void detailPage() {
        ChargeBatteryInfo model = (ChargeBatteryInfo)ChargeBatteryInfo.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        this.render("detail.html");
    }


}

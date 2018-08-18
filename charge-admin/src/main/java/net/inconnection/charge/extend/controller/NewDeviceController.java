package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.admin.common.util.XBeanUtils;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.ChargeSocket;
import net.inconnection.charge.extend.model.Chargeprice;
import net.inconnection.charge.extend.model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDeviceController extends BaseController {


    public void listPage() {
        this.render("list2.html");
    }

    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "update_time desc";
        }
        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_charge_pile", properties, symbols, values, orderBy, this.getPager());
        for (Record record:list){
            record.set("sn",record.get("id"));
            if (record.get("company_id")!=null){

                //todo 使用findById 须指定类型
                Long company_id = record.get("company_id");
                Company company=Company.me.findById(company_id);
                record.set("company_name",company.getCompanyName());
            }
        }
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_charge_socket", properties, symbols, values));
    }


    //查看充电插座
    public void socketListPage() {
       setAttr("pile_id",getPara("id"));
        render("socketList.html");
    }


    public void socketlistData() {
        Long pile_id = getParaToLong("pile_id");
        List<ChargeSocket> chargeSockets= ChargeSocket.dao.find("select * from yc_charge_socket where charge_pile_id=" + pile_id);
        this.renderDatagrid(chargeSockets, chargeSockets.size());
    }



}

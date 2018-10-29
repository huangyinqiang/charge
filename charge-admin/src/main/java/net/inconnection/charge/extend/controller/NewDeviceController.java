package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeSocket;
import net.inconnection.charge.extend.model.ChargeSocketHistory;
import net.inconnection.charge.extend.model.Company;

import java.util.ArrayList;
import java.util.Arrays;
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

        List<String> properties1 = new ArrayList(Arrays.asList(properties));
        List<String> symbols1 = new ArrayList(Arrays.asList(symbols));
        List<Object> values1 = new ArrayList(Arrays.asList(values));
        Company param = Company.dao.getCompanyByLoginUser();
        if(1 != param.getId()){
            properties1.add("company_id");
            symbols1.add("=");
            values1.add(param.getId());
            properties =  properties1.toArray(new String[properties1.size()]);
            symbols =  symbols1.toArray(new String[symbols1.size()]);
            values =  values1.toArray();
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
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_charge_pile", properties, symbols, values));
    }


    //查看充电插座
    public void socketListPage() {
       setAttr("pile_id",getPara("id"));
        render("socketList.html");
    }


    public void socketListData() {
        Long pile_id = getParaToLong("pile_id");
        List<ChargeSocket> chargeSockets= ChargeSocket.dao.find("select * from yc_charge_socket where charge_pile_id=" + pile_id);
        this.renderDatagrid(chargeSockets, chargeSockets.size());
    }

    public void socketPowerPage() {
        this.render("socketPower.html");
    }


    public void socketPowerDate() {
        String id = this.getPara("id");
        String socket = this.getPara("socket");
        String startDate = this.getPara("startDate");
        String endDate = this.getPara("endDate");

        Map map = new HashMap<String,List>();
        List socketList = new ArrayList();
        List electricityList = new ArrayList();
        List<ChargeSocketHistory> chargeSocketHistories = ChargeSocketHistory.dao.find("SELECT substring(charge_socket_id,13) as `charge_socket_id`, group_concat(charge_intensity) AS `charge_intensity`, group_concat(update_time) as `update_time`"+
                " FROM yc_charge_socket_history WHERE charge_pile_id="+id+" and update_time > '"+startDate+"' and update_time < '"+endDate+"' group by charge_socket_id");
       chargeSocketHistories.forEach(chargeSocketHistory -> {
           socketList.add(chargeSocketHistory.get("charge_socket_id"));
           electricityList.add(Arrays.asList(chargeSocketHistory.get("charge_intensity").toString().split(",")));
           map.put("dateList",Arrays.asList(chargeSocketHistory.get("update_time").toString().split(",")));
       });
        map.put("socketList",socketList);
        map.put("electricityList",electricityList);

        this.renderJson(map);
    }

    public void socketList() {
        Long deviceId = getParaToLong("deviceId");
        List<ChargeSocket> chargeSockets= ChargeSocket.dao.find("select charge_socket_sn from yc_charge_socket where charge_pile_id=" + deviceId);
        this.renderJson(chargeSockets);
    }

}

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.chargeDevice.deviceManage.ChargePileManager;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargeSocketComponent;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.ChargeSocket;
import net.inconnection.charge.extend.model.ChargeSocketHistory;
import net.inconnection.charge.extend.model.Company;
import net.inconnection.charge.extend.model.RechargeHistory;

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

    private static ChargePileManager chargePileManager = ChargePileManager.getInstance();



  public void queryMap(){

      List<ChargePile> chargePiles = ChargePile.dao.queryDeviceLocation();
      List<Map<String,Object>> list = new ArrayList<>();
      for (ChargePile chargePile : chargePiles){
          Map<String,Object> map = new HashMap<>();

          Company company = Company.me.findById(chargePile.getCompanyId());
          map.put("chargePile", chargePile);
          map.put("chargeSockets",getUsedSocketNum(chargePile.getId()));
          map.put("companyId", company.getId());
          map.put("companyName", company.getCompanyName());
          list.add(map);
      }
      this.renderJson(list);

  }

    public void queryDeviceStatus(){
        List<Map<String, Object>> list = new ArrayList<>();

        List<ChargePile> chargePileList = ChargePile.dao.find("select * from yc_charge_pile where is_online = 1");
        int onLine = 0;
        int offLine = 0;
        int fault = 0;
        for (int i = 0; i < chargePileList.size(); i++) {
            ChargePile chargePile = chargePileList.get(i);
            Integer status = chargePile.getStatus();
            if (1 == status) {
                onLine++;
            } else if (2 == status) {
                offLine++;
            } else if (3 == status) {
                fault++;
            }
        }
        // {value:348, name:'上线'},
        // {value:251, name:'离线'},
        // {value:147, name:'故障'},
        Map<String, Object> map = new HashMap<>();
        map.put("name", "上线");
        map.put("value", onLine);
        list.add(map);
        map = new HashMap<>();
        map.put("name", "离线");
        map.put("value", offLine);
        list.add(map);
        map = new HashMap<>();
        map.put("name", "故障");
        map.put("value", fault);
        list.add(map);

        this.renderJson(list);

    }


    public void queryRecharge(){
        Map<String, Object> map =new HashMap<>();
        List<Object> xAxis = new ArrayList<>();
        List<Object> series = new ArrayList<>();
        List<RechargeHistory> rechargeHistoryList = RechargeHistory.dao.find("select company_id,sum(real_money)/100 as " +
                "'real_money' from yc_recharge_history group by company_id");

        for (int i = 0; i < rechargeHistoryList.size(); i++) {
            RechargeHistory rechargeHistory = rechargeHistoryList.get(i);
            xAxis.add(rechargeHistory.getCompanyId());
            series.add(rechargeHistory.getRealMoney());

        }
        map.put("xAxis", xAxis);
        map.put("series", series);
        this.renderJson(map);

    }

    public void queryCharge(){
        Map<String, Object> map =new HashMap<>();
        List<Object> xAxis = new ArrayList<>();
        List<Object> series = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT yc.company_name as '',sum(ych.chargeMoney)/100 FROM yc_charge_history ych " +
                "LEFT JOIN yc_company yc on yc.id=ych.company_id " +
                "GROUP BY ych.company_id");
                List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql.toString());


        for (int i = 0; i < list.size(); i++) {
            Object[] a = (Object[])list.get(i);
//            ChargeHistory chargeHistory = list.get(i);
            xAxis.add(a[0]);
            series.add(a[1]);

        }
        map.put("xAxis", xAxis);
        map.put("series", series);
        this.renderJson(map);

    }

    public void querySocket(){
        Map<String, Object> map =new HashMap<>();
        List<Object> yAxis = new ArrayList<>();
        List<Object> series = new ArrayList<>();

        List<ChargePile> chargePileList = ChargePile.dao.find("select * from yc_charge_pile where is_online = 1");
        for (int i = 0; i < chargePileList.size(); i++) {
            Long id = chargePileList.get(i).getId();
            yAxis.add(id);
            ChargePileDevice chargePile = ChargePileManager.getInstance().getChargePile(id);
            Map<Long, ChargeSocketComponent> chargeSocketMap = chargePile.getChargeSocketMap();

            int num =0;
            for (Long socketId : chargeSocketMap.keySet()){
                ChargeSocketComponent chargeSocketComponent = chargeSocketMap.get(socketId);
                if( chargeSocketComponent.isUsed()){
                    num ++;
                }
            }
            series.add(num);

        }
        map.put("yAxis", yAxis);
        map.put("series", series);
        this.renderJson(map);

    }


    public int getUsedSocketNum(Long chargePileId){
        ChargePileDevice chargePile = ChargePileManager.getInstance().getChargePile(chargePileId);
        Map<Long, ChargeSocketComponent> chargeSocketMap = chargePile.getChargeSocketMap();
        int num = 0;
        for (Long socketId : chargeSocketMap.keySet()){
            ChargeSocketComponent chargeSocketComponent = chargeSocketMap.get(socketId);
            if( chargeSocketComponent.isUsed()){
                num ++;
            }
        }

        return num;
    }
}

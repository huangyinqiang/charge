package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.admin.common.util.XBeanUtils;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.ChargeSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDeviceController extends BaseController {


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
            orderBy = "update_time desc";
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_charge_pile", properties, symbols, values, orderBy, this.getPager());
        List<ChargeSocket> ChargeSockets = ChargeSocket.dao.find("select * from yc_charge_socket");

        List<Object> recordList = new ArrayList<>();

        for (Record record:list){
            Long id = record.getLong("id");
            for (ChargeSocket chargeSocket:ChargeSockets){
                Map<String, Object> chargeSocketMap = XBeanUtils.bean2Map(chargeSocket,"chargeSocket");
                if (id==chargeSocket.getChargePileId()){
                    chargeSocketMap.putAll(record.getColumns());
                    recordList.add(chargeSocketMap);
                }
            }
        }


/*        for (ChargeSocket chargeSocket:ChargeSockets){

            Map<String, Object> chargeSocketMap = XBeanUtils.bean2Map(chargeSocket,"chargeSocket");
            Long chargePileId = chargeSocket.getChargePileId();
            for (Record record:list){
               if (record.getLong("id")==chargePileId){
                   chargeSocketMap.putAll(record.getColumns());
               }
            }
            recordList.add(chargeSocketMap);

        }*/




        this.renderDatagrid(recordList, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_charge_socket", properties, symbols, values));

    }


}

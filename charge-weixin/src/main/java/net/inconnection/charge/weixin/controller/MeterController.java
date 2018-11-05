package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ElectricityMeter;
import net.inconnection.charge.weixin.model.ElectricityMeterHistory;

import java.util.Date;

public class MeterController extends Controller {
    private static Log log = Log.getLog(MeterController.class);


    public MeterController() {
    }

    public void index() {
        log.info("跳转到抄表页面");
        this.render("agent/elecMeter.html");
    }


    public void showElecMeter(){
        log.info("跳转到抄表页面");
        this.render("agent/elecMeter.html");
    }

    public void queryMeterHistory(){
        String meterId = this.getPara("meterId");
        ElectricityMeterHistory electricityMeterHistory = ElectricityMeterHistory.me.queryElectricityMeterHis(meterId);
        this.renderJson(new HnKejueResponse(electricityMeterHistory,RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue()));
    }


    public void addMeterHistory(){
        try {
            String materId = this.getPara("materId");
            String lastNumStr = this.getPara("lastNum");
            String currNumStr = this.getPara("currNum");
            String openId = this.getPara("openId");
            String currDate = this.getPara("currDate");


            log.info("添加抄表记录：materId="+materId+" lastNum="+lastNumStr+" currNum="+currNumStr+" openId="+openId);
            double lastNum = Double.parseDouble(lastNumStr);
            double currNum = Double.parseDouble(currNumStr);
            double count = currNum - lastNum;
            ElectricityMeter electricityMeter = ElectricityMeter.me.findById(materId);
            double total = count * electricityMeter.getInt("price")/100;
            ElectricityMeterHistory model = new ElectricityMeterHistory();
            model.set("meter_id", materId);
            model.set("last_num", lastNum);
            model.set("curr_num", currNum);
            model.set("openId", openId);
            model.set("count", count);
            model.set("total", total);
            model.set("curr_date", currDate);
            model.set("create_date", new Date());
            model.save();

            this.renderJson(new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue()));
        }catch (Exception e){
            e.printStackTrace();
            this.renderJson(new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue()));
        }

    }



}


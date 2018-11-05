package net.inconnection.charge.weixin.model;


import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class ElectricityMeter extends Model<ElectricityMeter> {
    private static final long serialVersionUID = -7922986157035810117L;
    private static final Log log = Log.getLog(ElectricityMeter.class);

    public static final ElectricityMeter me = new ElectricityMeter();

    public List<ElectricityMeter> getElectricityMeter(String projectId) {
        log.info("查询电表信息:"+projectId );
        StringBuffer sql =new StringBuffer("select * from yc_electricity_meter");
        if(projectId != null && !"".equals(projectId)){
            sql.append(" where project_id = " + projectId);
        }
        List<ElectricityMeter> electricityMeterList = me.find(sql.toString());
        log.info("查询电表信息:" + electricityMeterList);
        return electricityMeterList;
    }




}

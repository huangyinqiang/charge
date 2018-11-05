package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;


public class ElectricityMeterHistory extends Model<ElectricityMeterHistory> {
    private static final Log log = Log.getLog(ElectricityMeterHistory.class);

    public static final ElectricityMeterHistory me = new ElectricityMeterHistory();

    public ElectricityMeterHistory queryElectricityMeterHis(String meterId) {
        log.info("查询电表抄表信息:"+meterId );
        StringBuffer sql =new StringBuffer("select * from yc_electricity_meter_history");
        sql.append(" where meter_id = " + meterId);
        sql.append(" order by create_date desc");
        ElectricityMeterHistory electricityMeterHistory = me.findFirst(sql.toString());
        log.info("查询电表抄表信息:" + electricityMeterHistory);
        return electricityMeterHistory;
    }
}

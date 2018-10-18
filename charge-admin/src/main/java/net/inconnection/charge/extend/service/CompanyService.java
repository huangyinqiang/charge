package net.inconnection.charge.extend.service;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.ZcurdTool;

import java.util.Date;
import java.util.List;

public class CompanyService {
    private static Log logger = Log.getLog(CompanyService.class);

    public List<Record> getChargeSum(long companyId, String operType, Date startDate, Date endDate) {
        StringBuffer sql =new StringBuffer(" SELECT ifnull(sum(chargeMoney),0) as `moneySum` ," +
                " ifnull(sum(realMoney),0) as `realMoneySum` FROM yc_charge_history ");
        sql.append(" where feeStatus='S' ");
        sql.append(" and company_id = ").append(companyId);
        sql.append(" and operType ='").append(operType).append("'");

        if(startDate!= null ){
            sql.append(" and startTime >='").append(startDate).append("'");
        }
        if(endDate!= null ){
            sql.append(" and startTime <='").append(endDate).append("'");
        }
        List<Record> records = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find(sql.toString());
        return records;
    }

    public List<Record>  getRechargeSum(long companyId, Date startDate, Date endDate) {
        StringBuffer sql =new StringBuffer("SELECT ifnull(sum(real_money),0) as `realMoneySum` , ifnull(sum(money_sum),0) as " +
                "`moneySum` FROM " + "yc_recharge_history  where");
        sql.append(" company_id = ").append(companyId);
        if(startDate!= null){
            sql.append(" and recharge_time >='").append(startDate).append("'");
        }
        if(endDate!= null ){
            sql.append(" and recharge_time <='").append(endDate).append("'");
        }
        List<Record> records = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find(sql.toString());
        return records;
    }
}


package net.inconnection.charge.extend.service;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.util.Pager;
import net.inconnection.charge.admin.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class AnalysisService {
    private static Log logger = Log.getLog(AnalysisService.class);

    public List<Object>  getRechargeData(String companyId,String[] properties,String[] symbols,Object[] values) {

        StringBuffer rechargeSql =new StringBuffer();
        StringBuffer companySql =new StringBuffer();
        List<Object> params = new ArrayList<>();

        rechargeSql.append("SELECT " +
                "	qmd.match_num AS 'deviceId'," +
                "	ifnull( qmd.remark, qmd.match_num ) AS 'deviceName'," +
                "	sum( cmi.money )  AS realMoney," +
                "	sum( cmi.amount )  AS moneySum " +
                "FROM" +
                "	charge_money_info cmi " +
                "	LEFT JOIN qr_match_device qmd ON cmi.deviceId = qmd.match_num " +
                "	WHERE  deviceId <> '10100000001' " );
        for (int i = 0; i < properties.length; i++) {
            rechargeSql.append(" and cmi.createTime " + symbols[i] + " ?");
            params.add(values[i]);

        }

        rechargeSql.append(" GROUP BY cmi.deviceId");
        List<Object> deviceRechargeList = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(rechargeSql.toString(),
                params.toArray());
        //List<Record> deviceRechargeList = Db.find(rechargeSql.toString());

        companySql.append("SELECT " +
                "	yrh.company_id as 'deviceId'," +
                "	yc.company_name as 'deviceName'," +
                "	sum( real_money )  AS realMoney," +
                "	sum( money_sum )  AS moneySum " +
                "FROM " +
                "	yc_recharge_history yrh " +
                "	LEFT JOIN yc_company yc ON yrh.company_id = yc.id " +
                "WHERE  1=1 ");

        for (int i = 0; i < properties.length; i++) {
            companySql.append(" and yrh.recharge_time " + symbols[i] + " ?");
        }
        companySql.append(" GROUP BY yrh.company_id");
        List<Object> companyRechargeList = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(companySql.toString(),
                params.toArray());

        companyRechargeList.addAll(deviceRechargeList);
        return companyRechargeList;
    }

    public List<Object>  getChargeData(String companyId, String[] properties, String[] symbols, Object[] values,
                                       Pager pager,String order,String operType) {

        StringBuffer sql =new StringBuffer();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT" +
                "	cbi.deviceid AS `deviceId`," +
                "	ifnull( qmd.remark, ycp.NAME ) AS `deviceName`," +
                "	sum( cbi.charge ) / 100 AS `amount` " +
                "FROM" +
                "	charge_battery_info cbi" +
                "	LEFT JOIN qr_match_device qmd ON cbi.deviceId = qmd.match_num" +
                "	LEFT JOIN yc_charge_pile ycp ON ycp.id = cbi.deviceId " +
                "where  feeStatus='S' ");
        if("M".equals(operType)){
            sql.append(" and operType = 'M'");
        }

        for (int i = 0; i < properties.length; i++) {
            sql.append(" and cbi.operStartTime " + symbols[i] + " ?");
            params.add(values[i]);

        }
        sql.append("GROUP BY cbi.deviceId");
        if(StringUtil.isNotEmpty(order)) {
            sql.append(" order by " + order);
        }
        if(pager != null) {
            sql.append(" limit " + pager.getStartRow() + ", " + pager.getRows());
        }


        List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql.toString(),
                params.toArray());

        return list;
    }


    public List<Object>  getRechargeDetailData(String companyId,String[] properties,String[] symbols,Object[] values,Pager pager) {

        StringBuffer rechargeSql =new StringBuffer();
        StringBuffer companySql =new StringBuffer();
        List<Object> params = new ArrayList<>();
        List<Object> result = new ArrayList<>();


        if(companyId != null && StringUtil.isNotEmpty(companyId) && companyId.length() > 10){
            rechargeSql.append("SELECT " +
                        " cmi.openId as `openId`, "+
                    "	qmd.match_num AS 'deviceId'," +
                    "	ifnull( qmd.remark, qmd.match_num ) AS 'deviceName'," +
                    "   cmi.money   AS realMoney," +
                    "	cmi.amount   AS moneySum ," +
                    "   cmi.createTime as `createDate` "+
                    "FROM" +
                    "	charge_money_info cmi " +
                    "	LEFT JOIN qr_match_device qmd ON cmi.deviceId = qmd.match_num " +
                    "	WHERE 1=1  " );
            rechargeSql.append(" and cmi.deviceId = ").append(companyId);
            for (int i = 0; i < properties.length; i++) {
                rechargeSql.append(" and cmi.createTime " + symbols[i] + " ?");
                params.add(values[i]);

            }
            if(pager != null) {
                rechargeSql.append(" limit " + pager.getStartRow() + ", " + pager.getRows());
            }

            List<Object> deviceRechargeList = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(rechargeSql.toString(),
                    params.toArray());
            result.addAll(deviceRechargeList);
        }


        if(companyId != null && StringUtil.isNotEmpty(companyId) && companyId.length() < 10){
            companySql.append("SELECT " +
                    "   yrh.openId as `openId` ,"+
                    "	yrh.company_id as 'deviceId'," +
                    "	yc.company_name as 'deviceName'," +
                    "	real_money  AS realMoney," +
                    "	money_sum   AS moneySum ," +
                    "   yrh.recharge_time as `createDate` "+
                    "FROM " +
                    "	yc_recharge_history yrh " +
                    "	LEFT JOIN yc_company yc ON yrh.company_id = yc.id " +
                    "WHERE  1=1 ");
            companySql.append(" and yrh.company_id = ").append(companyId);
            for (int i = 0; i < properties.length; i++) {
                companySql.append(" and yrh.recharge_time " + symbols[i] + " ?");
                params.add(values[i]);
            }
            if(pager != null) {
                companySql.append(" limit " + pager.getStartRow() + ", " + pager.getRows());
            }
            List<Object> companyRechargeList = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(companySql.toString(),
                    params.toArray());
            result.addAll(companyRechargeList);
        }


        return result;
    }

    public List<Object>  getChargeDetailData(String deviceId, String[] properties, String[] symbols, Object[] values,
                                       Pager pager,String order,String operType) {

        StringBuffer sql =new StringBuffer();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT" +
                " cbi.openId ,"+
                "	cbi.deviceid AS `deviceId`," +
                "	ifnull( qmd.remark, ycp.NAME ) AS `deviceName`," +
                "	cbi.charge  / 100 AS `amount`, " +
                "   cbi.chargeTime ,"+
                "   cbi.realChargeTime "+
                "FROM" +
                "	charge_battery_info cbi" +
                "	LEFT JOIN qr_match_device qmd ON cbi.deviceId = qmd.match_num" +
                "	LEFT JOIN yc_charge_pile ycp ON ycp.id = cbi.deviceId " +
                "where  feeStatus='S' ");
        if("M".equals(operType)){
            sql.append(" and operType = 'M'");
        }
        if(deviceId != null && StringUtil.isNotEmpty(deviceId)){
            sql.append(" and cbi.deviceId =").append(deviceId);
        }

        for (int i = 0; i < properties.length; i++) {
            sql.append(" and cbi.operStartTime " + symbols[i] + " ?");
            params.add(values[i]);

        }
        if(StringUtil.isNotEmpty(order)) {
            sql.append(" order by " + order);
        }
        if(pager != null) {
            sql.append(" limit " + pager.getStartRow() + ", " + pager.getRows());
        }


        List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql.toString(), params.toArray());

        return list;
    }
}


package net.inconnection.charge.extend.controller;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.Pager;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.service.AnalysisService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalysisController extends BaseController{
    private static Log logger = Log.getLog(AnalysisController.class);
    private static AnalysisService analysisService = new AnalysisService();

    public void rechargeListPage() {
        this.render("rechargeList.html");
    }
    public void chargeListPage() {
        this.render("chargeList.html");
    }
    public void chargeTempListPage() {
        this.render("chargeTempList.html");
    }

    public void rechargeListData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();

        List<Object> list = analysisService.getRechargeData(null,properties, symbols, values);
       logger.info(list.toString());
        List<Record> result = new ArrayList();
        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);

            r.set("deviceId", a[0]);
            r.set("deviceName", a[1]);
            r.set("realMoney", Integer.valueOf(a[2].toString())/100);
            r.set("moneySum",  Integer.valueOf(a[3].toString())/100);
            result.add(r);
        }

        if(orderBy != null && StringUtil.isNotEmpty(orderBy)){
            String orderParam  = orderBy.split(" ")[0];
            String order  = orderBy.split(" ")[1];
            Collections.sort(result,
                    (p1,p2)->{
                        if( Integer.valueOf(p1.get(orderParam).toString()) >= Integer.valueOf(p2.get(orderParam)
                                .toString())){
                            System.out.println(p1.get(orderParam).toString());
                            System.out.println(p2.get(orderParam).toString());
                            return "desc".equals(order) ? -1 : 1;
                        }else{
                            return !"desc".equals(order) ? -1 : 1;
                        }

                    });
        }

        this.renderDatagrid(result,result.size());
    }


    public void rechargeExportCsv() {

        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();

        List<Object> list = analysisService.getRechargeData(null,properties, symbols, values);
        logger.info(list.toString());
        List<Record> result = new ArrayList();
        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);

            r.set("deviceId", a[0]);
            r.set("deviceName", a[1]);
            r.set("realMoney", Integer.valueOf(a[2].toString())/100);
            r.set("moneySum",  Integer.valueOf(a[3].toString())/100);
            result.add(r);
        }

        if(orderBy != null && StringUtil.isNotEmpty(orderBy)){
            String orderParam  = orderBy.split(" ")[0];
            String order  = orderBy.split(" ")[1];
            Collections.sort(result,
                    (p1,p2)->{
                        if( Integer.valueOf(p1.get(orderParam).toString()) >= Integer.valueOf(p2.get(orderParam)
                                .toString())){
                            System.out.println(p1.get(orderParam).toString());
                            System.out.println(p2.get(orderParam).toString());
                            return "desc".equals(order) ? -1 : 1;
                        }else{
                            return !"desc".equals(order) ? -1 : 1;
                        }

                    });
        }

        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();

        headers.add("设备编号");
        clomuns.add("deviceId");
        headers.add("设备名称");
        clomuns.add("deviceName");
        headers.add("充值金额(元)");
        clomuns.add("realMoney");
        headers.add("实到金额(元)");
        clomuns.add("moneySum");

        CsvRender csvRender = new CsvRender(headers, result);
        csvRender.clomuns(clomuns);
        csvRender.fileName("充值统计");
        this.addOpLog("[充值统计] 导出cvs");
        this.render(csvRender);
    }

    public void chargeListData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];

        List<Record> result = getChargeByOperType(null,properties, symbols, values,this.getPager(),this
                .getOrderBy(),"W");
        this.renderDatagrid(result,analysisService.getChargeData(null,properties, symbols, values,null,null,"W").size
                ());
    }

    public void chargeExportCsv() {

        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];

        List<Record> result = getChargeByOperType(null,properties, symbols, values,this.getPager(),this.getOrderBy(),"W");

        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();

        headers.add("设备编号");
        clomuns.add("deviceId");
        headers.add("设备名称");
        clomuns.add("deviceName");
        headers.add("消费金额(元)");
        clomuns.add("amount");


        CsvRender csvRender = new CsvRender(headers, result);
        csvRender.clomuns(clomuns);
        csvRender.fileName("消费统计");
        this.addOpLog("[消费统计] 导出cvs");
        this.render(csvRender);
    }


    public void chargeTempListData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];

        List<Record> result = getChargeByOperType(null,properties, symbols, values,this.getPager(),this
                .getOrderBy(),"M");
        this.renderDatagrid(result,analysisService.getChargeData(null,properties, symbols, values,null,null,"M").size());
    }

    public void chargeTempExportCsv() {

        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];

        List<Record> result = getChargeByOperType(null,properties, symbols, values,this.getPager(),this.getOrderBy(),"M");

        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();

        headers.add("设备编号");
        clomuns.add("deviceId");
        headers.add("设备名称");
        clomuns.add("deviceName");
        headers.add("临时消费金额(元)");
        clomuns.add("amount");


        CsvRender csvRender = new CsvRender(headers, result);
        csvRender.clomuns(clomuns);
        csvRender.fileName("临时消费统计");
        this.addOpLog("[临时消费统计] 导出cvs");
        this.render(csvRender);
    }

    private List<Record> getChargeByOperType(String companyId, String[] properties, String[] symbols, Object[] values,
                                             Pager pager, String order, String operType){
        List<Object> list = analysisService.getChargeData(null,properties, symbols, values,pager,order,operType);
        List<Record> result = new ArrayList();
        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);

            r.set("deviceId", a[0]);
            r.set("deviceName", a[1]);
            r.set("amount", a[2]);
            result.add(r);
        }
        return  result;
    }

}

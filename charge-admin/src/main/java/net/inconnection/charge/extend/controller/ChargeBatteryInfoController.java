//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdConst;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeBatteryInfo;
import net.inconnection.charge.extend.model.QrMatchDevice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChargeBatteryInfoController extends BaseController {
    public ChargeBatteryInfoController() {
    }

    public void listPage() {
        this.render("list.html");
    }

    public void listSumPage() {
        this.render("sum.html");
    }

    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }
        SysUser sysUser = getSessionUser();
        List<String> properties1 = new ArrayList(Arrays.asList(properties));
        List<String> symbols1 = new ArrayList(Arrays.asList(symbols));
        List<Object> values1 = new ArrayList(Arrays.asList(values));
        if(1 != sysUser.getId()){
            List<QrMatchDevice> qrMatchDeviceList = QrMatchDevice.dao.findByGid(sysUser.getId());
            if(qrMatchDeviceList != null && qrMatchDeviceList.size() > 0){
                StringBuffer sb = new StringBuffer();
                qrMatchDeviceList.forEach(qrMatchDevice -> {
                    sb.append(qrMatchDevice.getQrNum()).append(",");
                });
                properties1.add("qr_num");
                symbols1.add(ZcurdConst.SEARCH_TYPE_OR2IN);
                values1.add(sb.substring(0,sb.length()-1));
                properties =  properties1.toArray(new String[properties1.size()]);
                symbols =  symbols1.toArray(new String[symbols1.size()]);
                values =  values1.toArray();
            }
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "charge_info_view", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "charge_info_view", properties, symbols, values));
    }

    public void listSumData() {
        String sql = "select operType,sum(charge)/100 from charge_battery_info inner join qr_match_device on charge_battery_info.deviceId=qr_match_device.match_num";
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        List<Object> list = DBTool.findDbSource("zcurd_busi", sql, properties, symbols, values,null);
        List<Record> result = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            Record r = new Record();
            Object[] a = (Object[])list.get(i);
            String s1 = (String)a[0];
            Double s2 = (Double)a[1];
            r.set("type", s1);
            r.set("value", s2);
            result.add(r);
        }

        this.renderDatagrid(result, 2);
    }

    public void delete() {
        Integer[] ids = this.getParaValuesToInt("id[]");
        Integer[] var5 = ids;
        int var4 = ids.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Integer id = var5[var3];
            ((ChargeBatteryInfo)(new ChargeBatteryInfo()).set("id", id)).delete();
        }

        this.addOpLog("[消费记录] 删除");
        this.renderSuccess();
    }

    public void detailPage() {
        ChargeBatteryInfo model = (ChargeBatteryInfo)ChargeBatteryInfo.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        this.render("detail.html");
    }

    public void exportCsv() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        SysUser sysUser = getSessionUser();
        List<String> properties1 = new ArrayList(Arrays.asList(properties));
        List<String> symbols1 = new ArrayList(Arrays.asList(symbols));
        List<Object> values1 = new ArrayList(Arrays.asList(values));
        if(1 != sysUser.getId()){
            List<QrMatchDevice> qrMatchDeviceList = QrMatchDevice.dao.findByGid(sysUser.getId());
            if(qrMatchDeviceList != null && qrMatchDeviceList.size() > 0){
                StringBuffer sb = new StringBuffer();
                qrMatchDeviceList.forEach(qrMatchDevice -> {
                    sb.append(qrMatchDevice.getQrNum()).append(",");
                });
                properties1.add("qr_num");
                symbols1.add(ZcurdConst.SEARCH_TYPE_OR2IN);
                values1.add(sb.substring(0,sb.length()-1));
                properties =  properties1.toArray(new String[properties1.size()]);
                symbols =  symbols1.toArray(new String[symbols1.size()]);
                values =  values1.toArray();
            }
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "charge_info_view", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        headers.add("用户昵称");
        clomuns.add("nickName");
        headers.add("设备名称");
        clomuns.add("remark");
        headers.add("设备编号");
        clomuns.add("qr_num");
        headers.add("设备端口号");
        clomuns.add("devicePort");
        headers.add("操作开始时间");
        clomuns.add("operStartTime");
        headers.add("开始充电时间");
        clomuns.add("startTime");
        headers.add("结束时间");
        clomuns.add("endTime");
        headers.add("充电方式(手动:M,微信W)");
        clomuns.add("operType");
        headers.add("终端充电状态");
        clomuns.add("status");
        headers.add("消费金额");
        clomuns.add("charge");
        headers.add("充电时间(分钟)");
        clomuns.add("chargeTime");
        headers.add("操作时间");
        clomuns.add("createDate");
        CsvRender csvRender = new CsvRender(headers, list);
        csvRender.clomuns(clomuns);
        csvRender.fileName("微信消费记录");
        this.addOpLog("[消费记录] 导出cvs");
        this.render(csvRender);
    }
}

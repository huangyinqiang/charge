//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeBatteryInfo;

import java.util.ArrayList;
import java.util.List;

public class ChargeBatterySumController extends BaseController {
    public ChargeBatterySumController() {
    }

    public void listSumPage() {
        this.render("sum.html");
    }

    public void listSumData() {
        StringBuffer sql = new StringBuffer();
        List<Record> result = new ArrayList();
        sql.append("select operType,sum(charge)/100 from charge_battery_info inner join qr_match_device on charge_battery_info.deviceId=qr_match_device.match_num");
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }

        if (properties.length > 2) {
            String gid = "";
            String did = "";
            String startTime = "";
            String endTime = "";
            if (properties.length == 3) {
                gid = (String)values[0];
                startTime = (String)values[1];
                endTime = (String)values[2];
            } else {
                gid = (String)values[0];
                did = "(" + (String)values[1] + ")";
                startTime = (String)values[2];
                endTime = (String)values[3];
            }

            sql.append(" where 1=1");
            if (!"".equals(did) && !"(0)".equals(did)) {
                sql.append(" and qr_match_device.id in " + did);
            } else {
                StringBuffer dids = new StringBuffer();
                if (!"".equals(gid) && !"1".equals(gid)) {
                    String sql1 = "";
                    if (gid.startsWith("(")) {
                        sql1 = "select id from qr_match_device where gid in " + gid;
                    } else {
                        sql1 = "select id from qr_match_device where gid in (" + gid + ")";
                    }

                    List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql1);

                    for(int i = 0; i < list.size(); ++i) {
                        dids.append(list.get(i) + ",");
                    }

                    String tempStr = dids.substring(0, dids.length() - 1);
                    sql.append(" and qr_match_device.id in (" + tempStr + ")");
                }
            }

            if (!startTime.equals("") && !endTime.equals("")) {
                sql.append(" and charge_battery_info.startTime between '" + startTime + "' and '" + endTime + "'");
            }

            sql.append("  group by operType");
            List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql.toString());
            if (list.size() < 1) {
                String[] typeS = new String[]{"W", "M"};

                for(int i = 0; i < 2; ++i) {
                    Record r = new Record();
                    r.set("type", typeS[i]);
                    r.set("value", "0.00");
                    r.set("time", startTime + "至" + endTime);
                    result.add(r);
                }
            } else {
                for(int i = 0; i < list.size(); ++i) {
                    Record r = new Record();
                    Object[] a = (Object[])list.get(i);
                    String s1 = (String)a[0];
                    Double s2 = (Double)a[1];
                    r.set("type", s1);
                    r.set("value", s2);
                    r.set("time", startTime + "至" + endTime);
                    result.add(r);
                }
            }
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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "charge_info_view", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        headers.add("用户编号");
        clomuns.add("openId");
        headers.add("设备编号");
        clomuns.add("deviceId");
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
        headers.add("服务器返回充电结果");
        clomuns.add("serverResultCode");
        headers.add(" 服务器充电结果描述");
        clomuns.add("serverResultDesc");
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

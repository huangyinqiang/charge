//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.DeviceProject;
import net.inconnection.charge.extend.model.Project;
import net.inconnection.charge.extend.model.QrMatchDevice;
import net.inconnection.charge.extend.service.DeviceProjectService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QrMatchDeviceController extends BaseController {
    private static DeviceProjectService deviceProjectService = new DeviceProjectService();

    public QrMatchDeviceController() {
    }

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
            orderBy = "updateTime desc";
        }

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "qr_match_device", properties, symbols, values, orderBy, this.getPager());
        for (Record record:list){
            DeviceProject deviceProject = DeviceProject.me.findFirst("select * from yc_device_project where device_id="
                    + record .get("id"));
            if (deviceProject != null){
                Long projectId = deviceProject.get("project_id");
                Project project=Project.me.findById(projectId);
                if(project != null){
                    record.set("projectName",project.getName());
                }
            }
        }
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "qr_match_device", properties, symbols, values));
    }

    public void addPage() {
        this.render("add.html");
    }

    public void add() {
        QrMatchDevice model = (QrMatchDevice)this.getModel(QrMatchDevice.class, "model");
        model.set("createtime", new Date());
        model.set("updatetime", new Date());
        model.set("status", "N");
        model.save();
        this.addOpLog("[设备记录] 增加");
        this.renderSuccess();
    }

    public void updatePage() {
        this.setAttr("model", QrMatchDevice.me.findById(this.getPara("id")));
        this.render("update.html");
    }

    public void updatePage1() {
        this.setAttr("model", QrMatchDevice.me.findById(this.getPara("id")));
        this.render("update1.html");
    }

    public void configPage() {
        this.setAttr("model", QrMatchDevice.me.findById(this.getPara("id")));
        this.render("config.html");
    }

    public void config() {
        this.addOpLog("[设备记录] 增加");
        this.renderSuccess();
    }

    public void authorizePage() {
        this.setAttr("model", QrMatchDevice.me.findById(this.getPara("id")));
        this.setAttr("user", SysUser.me.findByMultiProperties((String[])null, (Object[])null));
        this.render("authorize.html");
    }

    public void authorize() {
        String id = this.getPara("id");
        String gid = this.getPara("parent_id");
        SysUser sysUser = (SysUser)SysUser.me.findById(gid);
        QrMatchDevice device = (QrMatchDevice)QrMatchDevice.me.findById(id);
        if (sysUser != null) {
            device.set("gid", gid);
            device.set("gname", sysUser.get("user_name"));
            this.addOpLog("[授权设备成功] 增加");
            device.update();
            this.renderSuccess();
        } else {
            this.addOpLog("[授权设备失败：缺少用户参数] ");
            this.renderFailed();
        }

    }

    public void listUserAll() {
        long gid = (Long)this.getSessionUser().get("id");
        if (gid == 1L) {
            this.renderJson(SysUser.me.findAll());
        } else {
            this.renderJson(SysUser.me.findByPid(gid));
        }

    }

    public void listDeviceAll() {
        long gid = (Long)this.getSessionUser().get("id");
        List<SysUser> list = SysUser.me.findByPid(gid);
        if (gid == 1L) {
            this.renderJson(QrMatchDevice.me.findAll());
        } else if (list != null && list.size() >= 1) {
            StringBuffer sb = new StringBuffer();
            sb.append(gid + ",");

            for(int i = 0; i < list.size(); ++i) {
                SysUser user = (SysUser)list.get(i);
                sb.append(user.get("id") + ",");
            }

            String tmpStr = sb.substring(0, sb.length() - 1);
            this.renderJson(QrMatchDevice.me.findInGid(tmpStr));
        } else {
            this.renderJson(QrMatchDevice.me.findByGid(gid));
        }

    }

    public void update() {
        QrMatchDevice model = (QrMatchDevice)QrMatchDevice.me.findById(this.getPara("id"));
        model.set("area", this.getPara("model.area"));
        model.set("tow_hours_price", this.getPara("model.tow_hours_price"));
        model.set("four_hours_price", this.getPara("model.four_hours_price"));
        model.set("eight_hours_price", this.getPara("model.eight_hours_price"));
        model.set("twelve_hours_price", this.getPara("model.twelve_hours_price"));
        model.set("power_a1", this.getPara("model.power_a1"));
        model.set("power_a2", this.getPara("model.power_a2"));
        model.set("power_a3", this.getPara("model.power_a3"));
        model.set("power_a4", this.getPara("model.power_a4"));
        model.set("power_a5", this.getPara("model.power_a5"));
        model.set("remark", this.getPara("model.remark"));
        model.set("area", this.getPara("model.area"));
        model.update();
        this.addOpLog("[设备记录] 修改");
        String deviceId = (String)model.get("match_num");
        //DeviceUpdateUtils.UpdateDevice(deviceId);
        this.renderSuccess();
    }

    public void update1() {
        QrMatchDevice model = (QrMatchDevice)QrMatchDevice.me.findById(this.getPara("id"));
        model.set("area", this.getPara("model.area"));
        model.set("tow_hours_price", this.getPara("model.tow_hours_price"));
        model.set("four_hours_price", this.getPara("model.four_hours_price"));
        model.set("eight_hours_price", this.getPara("model.eight_hours_price"));
        model.set("twelve_hours_price", this.getPara("model.twelve_hours_price"));
        model.set("power_a1", this.getPara("model.power_a1"));
        model.set("power_a2", this.getPara("model.power_a2"));
        model.set("power_a3", this.getPara("model.power_a3"));
        model.set("power_a4", this.getPara("model.power_a4"));
        model.set("power_a5", this.getPara("model.power_a5"));
        model.set("power_a6", this.getPara("model.power_a6"));
        model.set("power_a7", this.getPara("model.power_a7"));
        model.set("remark", this.getPara("model.remark"));
        model.set("area", this.getPara("model.area"));
        model.update();
        this.addOpLog("[设备记录] 修改");
        String deviceId = (String)model.get("match_num");
     //   DeviceUpdateUtils.UpdateDevice(deviceId);
        this.renderSuccess();
    }

    public void delete() {
        Integer[] ids = this.getParaValuesToInt("id[]");
        Integer[] var5 = ids;
        int var4 = ids.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Integer id = var5[var3];
            QrMatchDevice model = (QrMatchDevice)QrMatchDevice.me.findById(id);
            if (model.delete()) {
                String deviceId = (String)model.get("match_num");
                String area = (String)model.get("area");
          //      DeviceUpdateUtils.UpdateDevice(deviceId);
                this.addOpLog("设备" + area + ":" + deviceId + "被删除");
            }
        }

        this.addOpLog("[设备记录] 删除");
        this.renderSuccess();
    }

    public void detailPage() {
        QrMatchDevice model = (QrMatchDevice)QrMatchDevice.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        this.render("detail.html");
    }

    public void mapPage() {
        this.render("map.html");
    }

    public void statusPage() {
        QrMatchDevice model = (QrMatchDevice)QrMatchDevice.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        String deviceId = (String)model.get("match_num");
  //      DeviceStatus deviceStatus = DeviceRedisUtils.getDeviceStatus(deviceId);
   //     this.setAttr("status", deviceStatus);
        if ("S".equals(model.get("type"))) {
            this.render("status.html");
        } else {
            this.render("status1.html");
        }

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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "qr_match_device", properties, symbols, values);
        List<String> headers = new ArrayList();
        List<String> clomuns = new ArrayList();
        headers.add("设备序号");
        clomuns.add("QR_num");
        headers.add("通讯序号");
        clomuns.add("match_num");
        headers.add("设备位置");
        clomuns.add("area");
        headers.add("非员会2小时价格");
        clomuns.add("tow_hours_price");
        headers.add("非员会4小时价格");
        clomuns.add("four_hours_price");
        headers.add("非员会8小时价格");
        clomuns.add("eight_hours_price");
        headers.add("非员会8小时价格");
        clomuns.add("twelve_hours_price");
        headers.add("员会2小时价格");
        clomuns.add("tow_hours_mem_price");
        headers.add("员会4小时价格");
        clomuns.add("four_hours_mem_price");
        headers.add("员会8小时价格");
        clomuns.add("eight_hours_mem_price");
        headers.add("员会12小时价格");
        clomuns.add("twelve_hours_mem_price");
        headers.add("状态");
        clomuns.add("status");
        headers.add("备注");
        clomuns.add("remark");
        headers.add("最后更新时间");
        clomuns.add("updateTime");
        headers.add("设备加入时间");
        clomuns.add("createTime");
        CsvRender csvRender = new CsvRender(headers, list);
        csvRender.clomuns(clomuns);
        csvRender.fileName("设备记录");
        this.addOpLog("[设备记录] 导出cvs");
        this.render(csvRender);
    }

    public void bindProject() {
        this.setAttr("model", QrMatchDevice.me.findById(this.getPara("id")));
        this.render("bindProject.html");
    }

    public void getBindProjectByDeviceId() {
        List<Object> list = deviceProjectService.getProjectByDeviceId(this.getPara("id"));
        if(list.size() > 0){
            this.renderSuccess(null,list);
        }else{
            this.renderSuccess();
        }
    }
    public void saveProject() {
        String projectId = this.getPara("projectId");
        String deviceId = this.getPara("deviceId");
        List<DeviceProject> deviceProjectList = DeviceProject.me.findByDeviceId(deviceId);
        if (deviceProjectList.size() > 0) {
            DeviceProject deviceProject = deviceProjectList.get(0);
            deviceProject.set("project_id",projectId);
            this.addOpLog("[绑定项目成功] 修改成功");
            deviceProject.update();
            this.renderSuccess();
        } else {
            DeviceProject deviceProject = new DeviceProject();
            deviceProject.set("project_id",projectId);
            deviceProject.set("device_id",deviceId);
            this.addOpLog("[绑定项目成功] 增加成功");
            deviceProject.save();
            this.renderSuccess();
        }
    }


    public void queryProjectList() {
        List<Project> projectList = Project.me.findAll();
        this.renderJson(projectList);
    }


}

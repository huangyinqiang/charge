package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class NewDeviceProject extends Model<NewDeviceProject> {
    private static final long serialVersionUID = -8523028502752357786L;
    private static final Log log = Log.getLog(NewDeviceProject.class);
    public static final NewDeviceProject dao = new NewDeviceProject();

    public NewDeviceProject() {
    }

    public NewDeviceProject queryDeviceChargePriceByProjectID(Long projectId) {
        log.info("根据项目ID查询设备充电费用:" + projectId);
        NewDeviceProject newDeviceChargePrice = (NewDeviceProject)this.findFirst("select * from yc_project where id = ?", new Object[]{projectId});
        log.info("根据项目ID查询设备充电费用结果:" + newDeviceChargePrice);
        return newDeviceChargePrice;
    }

    public List<NewDeviceProject> queryProjectByCompanyId(Long companyId) {
        log.info("根据公司名称查询项目信息:" + companyId);
        List<NewDeviceProject> newDeviceProjects = this.find("select * from yc_project where company_id = ?", new Object[]{companyId});
        log.info("根据公司名称查询项目信息:" + newDeviceProjects);
        return newDeviceProjects;
    }
}


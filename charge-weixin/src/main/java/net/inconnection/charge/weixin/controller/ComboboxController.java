package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.model.ElectricityMeter;
import net.inconnection.charge.weixin.model.NewDeviceProject;

import java.util.List;


public class ComboboxController extends Controller {
	

	public void queryCompany(){
        List<Company> companyList = Company.dao.getAllCompany();
        this.renderJson(companyList);
    }

    public void queryProject(){
        String companyId = this.getPara("companyId");
        if(companyId == null || "".equals(companyId)){
            this.renderJson();
            return;
        }
        List<NewDeviceProject> projectList = NewDeviceProject.dao.queryProjectByCompanyId(Long.parseLong(companyId));
        this.renderJson(projectList);
    }

    public void queryMeter(){
        String projectId = this.getPara("projectId");
        List<ElectricityMeter> electricityMeterList = ElectricityMeter.me.getElectricityMeter(projectId);
        this.renderJson(electricityMeterList);
    }
	

}

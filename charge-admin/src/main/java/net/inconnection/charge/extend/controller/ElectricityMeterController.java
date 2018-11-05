package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.Company;
import net.inconnection.charge.extend.model.ElectricityMeter;
import net.inconnection.charge.extend.model.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 电表
 */
public class ElectricityMeterController extends BaseController {
	
	public void listPage() {
		render("list.html");
	}
	
	public void listData() {
		Object[] queryParams = getQueryParams();
		String[] properties = (String[]) queryParams[0];
		String[] symbols = (String[]) queryParams[1];
		Object[] values = (Object[]) queryParams[2];
		
		String orderBy = getOrderBy();
		if(StringUtil.isEmpty(orderBy)) {
			orderBy = "id desc";
		}
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter", properties, symbols, values, orderBy, getPager());
        for (int i = 0; i < list.size(); i++) {
            Record record = list.get(i);
            Long companyId = record.getLong("company_id");
            Long projectId = record.getLong("project_id");
            Company company = Company.me.findById(companyId);
            Project project = Project.me.findById(projectId);
            record.set("companyName", company.getCompanyName());
            record.set("projectName", project.getName());
        }
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter", properties, symbols, values)
		);
	}
	
	//增加页面
	public void addPage() {
		render("add.html");
	}
	
	//增加
	public void add() {
        ElectricityMeter model = getModel(ElectricityMeter.class, "model");
        model.setCreateTime(new Date());
        model.save();

        addOpLog("[电表] 增加");
		renderSuccess();
	}

	//修改页面
	public void updatePage() {
        ElectricityMeter model = ElectricityMeter.me.findById(getPara("id"));
        Company company = Company.me.findById(model.getCompanyId());
        Project project = Project.me.findById(model.getProjectId());
        setAttr("project", project);
        setAttr("company", company);
        setAttr("model", model);
		render("update.html");
	}
	
	//修改
	public void update() {
		ElectricityMeter model = ElectricityMeter.me.findById(getPara("id"));
		model.set("name", getPara("model.name"));
		model.set("price", getPara("model.price"));
		model.update();
		addOpLog("[电表] 修改");
		renderSuccess();
	}
	
	//删除
	public void delete() {
		Integer[] ids = getParaValuesToInt("id[]");
		for (Integer id : ids) {
			new ElectricityMeter().set("id", id).delete();
			
		}
		
		addOpLog("[电表] 删除");
		renderSuccess();
	}

	
	//导出csv
	public void exportCsv() {
		Object[] queryParams = getQueryParams();
		String[] properties = (String[]) queryParams[0];
		String[] symbols = (String[]) queryParams[1];
		Object[] values = (Object[]) queryParams[2];
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter", properties, symbols, values);
        for (int i = 0; i < list.size(); i++) {
            Record record = list.get(i);
            Long companyId = record.getLong("company_id");
            Long projectId = record.getLong("project_id");
            Company company = Company.me.findById(companyId);
            Project project = Project.me.findById(projectId);
            record.set("companyName", company.getCompanyName());
            record.set("projectName", project.getName());
        }
		
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("公司");
		clomuns.add("companyName");
		headers.add("项目");
		clomuns.add("projectName");
		headers.add("电表名称");
		clomuns.add("name");
		headers.add("类型");
		clomuns.add("type");
		headers.add("单价(分)");
		clomuns.add("price");
		headers.add("创建时间");
		clomuns.add("create_time");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("电表");
		
		addOpLog("[电表] 导出cvs");
		render(csvRender);
	}
}

package net.inconnection.charge.admin.online.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.admin.online.model.SysOplog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统操作日志
 */
public class SysOplogController extends BaseController {
	
	public void listPage() {
		setAttr("dictDatauser_id", SysOplog.me.getDictDatauser_id());
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_base", "sys_oplog", properties, symbols, values, orderBy, getPager());
		Map<String, Object> dictDatauser_id = SysOplog.me.getDictDatauser_id();
		for(Record record : list) {
			String fieldName = "user_id";
			if(dictDatauser_id.get(record.get(fieldName).toString()) != null) {
				record.set(fieldName, dictDatauser_id.get(record.get(fieldName).toString()));
			}
		}
		
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_base", "sys_oplog", properties, symbols, values)
		);
	}
	
	//导出csv
	public void exportCsv() {
		Object[] queryParams = getQueryParams();
		String[] properties = (String[]) queryParams[0];
		String[] symbols = (String[]) queryParams[1];
		Object[] values = (Object[]) queryParams[2];
		
		String orderBy = getOrderBy();
		if(StringUtil.isEmpty(orderBy)) {
			orderBy = "id desc";
		}
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_base", "sys_oplog", properties, symbols, values);
		Map<String, Object> dictDatauser_id = SysOplog.me.getDictDatauser_id();
		for(Record record : list) {
			String fieldName = "user_id";
			if(dictDatauser_id.get(record.get(fieldName).toString()) != null) {
				record.set(fieldName, dictDatauser_id.get(record.get(fieldName).toString()));
			}
		}
		
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("操作内容");
		clomuns.add("op_content");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("系统操作日志");
		
		addOpLog("[系统操作日志] 导出cvs");
		render(csvRender);
	}
}

package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;

import java.util.List;


/**
 * 支付日志
 */
public class OrdersController extends BaseController {
	
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "orders", properties, symbols, values, orderBy, getPager());
		
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "orders", properties, symbols, values)
		);
	}
	

}

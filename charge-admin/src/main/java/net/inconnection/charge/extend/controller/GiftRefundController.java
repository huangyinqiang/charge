package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.GiftRefund;

import java.util.ArrayList;
import java.util.List;


/**
 * 赠费退费记录表
 */
public class GiftRefundController extends BaseController {
	
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_gift_refund", properties, symbols, values, orderBy, getPager());
		
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_gift_refund", properties, symbols, values)
		);
	}
	
	//增加页面
	public void addPage() {
		render("add.html");
	}
	
	//增加
	public void add() {
		getModel(GiftRefund.class, "model").save();
		
		addOpLog("[赠费退费记录表] 增加");
		renderSuccess();
	}
	
	//修改页面
	public void updatePage() {
		setAttr("model", GiftRefund.me.findById(getPara("id")));
		render("update.html");
	}
	
	//修改
	public void update() {
		GiftRefund model = GiftRefund.me.findById(getPara("id"));
		model.set("openId", getPara("model.openId"));
		model.set("device_Id", getPara("model.device_Id"));
		model.set("company_Id", getPara("model.company_Id"));
		model.set("type", getPara("model.type"));
		model.set("charge_Id", getPara("model.charge_Id"));
		model.set("recharge_Id", getPara("model.recharge_Id"));
		model.set("money", getPara("model.money"));
		model.set("create_date", getPara("model.create_date"));
		model.update();
		addOpLog("[赠费退费记录表] 修改");
		renderSuccess();
	}
	
	//删除
	public void delete() {
		Integer[] ids = getParaValuesToInt("id[]");
		for (Integer id : ids) {
			new GiftRefund().set("id", id).delete();
			
		}
		
		addOpLog("[赠费退费记录表] 删除");
		renderSuccess();
	}
	
	//详情页面
	public void detailPage() {
		GiftRefund model = GiftRefund.me.findById(getParaToInt("id"));
		setAttr("model", model);
		render("detail.html");
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_gift_refund", properties, symbols, values);
		
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("用户");
		clomuns.add("openId");
		headers.add("设备ID");
		clomuns.add("device_Id");
		headers.add("公司ID");
		clomuns.add("company_Id");
		headers.add("类型 1:充值赠费 2:消费退费 3:充值退费");
		clomuns.add("type");
		headers.add("消费ID");
		clomuns.add("charge_Id");
		headers.add("充值ID");
		clomuns.add("recharge_Id");
		headers.add("金额 单位:分");
		clomuns.add("money");
		headers.add("创建时间");
		clomuns.add("create_date");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("赠费退费记录表");
		
		addOpLog("[赠费退费记录表] 导出cvs");
		render(csvRender);
	}
}

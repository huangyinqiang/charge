package net.inconnection.charge.extend.controller;


import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.StockHistoryLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票历史数据
 */
public class StockHistoryLogController extends BaseController {
	
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
		
		renderDatagrid(
			DBTool.findByMultPropertiesDbSource("zcurd_busi", "stock_history_log", properties, symbols, values, orderBy, getPager()),
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "stock_history_log", properties, symbols, values)
		);
	}
	
	//增加页面
	public void addPage() {
		render("add.html");
	}
	
	//增加
	public void add() {
		getModel(StockHistoryLog.class, "model").save();
		renderSuccess();
	}
	
	//修改页面
	public void updatePage() {
		setAttr("model", StockHistoryLog.me.findById(getPara("id")));
		render("update.html");
	}
	
	//修改
	public void update() {
		StockHistoryLog model = StockHistoryLog.me.findById(getPara("id"));
		model.set("dt", getPara("model.dt"));
		model.set("code", getPara("model.code"));
		model.set("name", getPara("model.name"));
		model.set("closing_price", getPara("model.closing_price"));
		model.set("top_price", getPara("model.top_price"));
		model.set("minimum_price", getPara("model.minimum_price"));
		model.set("opening_price", getPara("model.opening_price"));
		model.set("pre", getPara("model.pre"));
		model.set("change_amount", getPara("model.change_amount"));
		model.set("change_ratio", getPara("model.change_ratio"));
		model.set("turnover_volume", getPara("model.turnover_volume"));
		model.set("turnover_money", getPara("model.turnover_money"));
		model.set("create_time", getPara("model.create_time"));
		model.update();
		renderSuccess();
	}
	
	//删除
	public void delete() {
		Integer[] ids = getParaValuesToInt("id[]");
		for (Integer id : ids) {
			new StockHistoryLog().set("id", id).delete();
			
		}
		renderSuccess();
	}
	
	//详情页面
	public void detailPage() {
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "stock_history_log", properties, symbols, values);
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("日期");
		clomuns.add("dt");
		headers.add("股票代码");
		clomuns.add("code");
		headers.add("名称");
		clomuns.add("name");
		headers.add("收盘价");
		clomuns.add("closing_price");
		headers.add("最高价");
		clomuns.add("top_price");
		headers.add("最低价");
		clomuns.add("minimum_price");
		headers.add("开盘价");
		clomuns.add("opening_price");
		headers.add("前收盘");
		clomuns.add("pre");
		headers.add("涨跌额");
		clomuns.add("change_amount");
		headers.add("涨跌幅");
		clomuns.add("change_ratio");
		headers.add("成交量");
		clomuns.add("turnover_volume");
		headers.add("成交金额");
		clomuns.add("turnover_money");
		headers.add("创建时间");
		clomuns.add("create_time");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("股票历史数据");
		render(csvRender);
	}
}

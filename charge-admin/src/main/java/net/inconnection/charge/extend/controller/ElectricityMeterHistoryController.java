package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ElectricityMeter;
import net.inconnection.charge.extend.model.ElectricityMeterHistory;
import net.inconnection.charge.extend.model.Tuser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 电表记录
 */
public class ElectricityMeterHistoryController extends BaseController {
	
	public void listPage() {
		render("meterHisList.html");
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter_history", properties, symbols, values, orderBy, getPager());
        for (Record record:list){
            Object meterId = record.get("meter_id");
            ElectricityMeter model = ElectricityMeter.me.findById(meterId);
            record.set("meterName", model.getName());
            Object openId = record.get("openId");
            Tuser tuser = Tuser.me.queryTuserByOpenId(openId.toString());
            if(tuser != null){
                record.set("nickName", tuser.getNickName());
            }

        }
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter_history", properties, symbols, values)
		);
	}
	
	//增加页面
	public void addPage() {
		render("meterHisAdd.html");
	}
	
	//增加
	public void add() {
		getModel(ElectricityMeterHistory.class, "model").save();
		
		addOpLog("[电表记录] 增加");
		renderSuccess();
	}
	
	//修改页面
	public void updatePage() {
		setAttr("model", ElectricityMeterHistory.me.findById(getPara("id")));
		render("meterHisUpdate.html");
	}
	
	//修改
	public void update() {
		ElectricityMeterHistory model = ElectricityMeterHistory.me.findById(getPara("id"));
		model.set("meter_id", getPara("model.meter_id"));
		model.set("last_num", getPara("model.last_num"));
		model.set("curr_num", getPara("model.curr_num"));
		model.set("openId", getPara("model.openId"));
		model.set("count", getPara("model.count"));
		model.set("total", getPara("model.total"));
		model.set("create_date", getPara("model.create_date"));
		model.set("curr_date", getPara("model.curr_date"));
		model.update();
		addOpLog("[电表记录] 修改");
		renderSuccess();
	}
	
	//删除
	public void delete() {
		Integer[] ids = getParaValuesToInt("id[]");
		for (Integer id : ids) {
			new ElectricityMeterHistory().set("id", id).delete();
			
		}
		
		addOpLog("[电表记录] 删除");
		renderSuccess();
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_electricity_meter_history", properties, symbols, values);
		
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("电表ID");
		clomuns.add("meter_id");
		headers.add("上次电表度数");
		clomuns.add("last_num");
		headers.add("本次电表度数");
		clomuns.add("curr_num");
		headers.add("抄表人");
		clomuns.add("openId");
		headers.add("使用度数");
		clomuns.add("count");
		headers.add("金额");
		clomuns.add("total");
		headers.add("创建时间");
		clomuns.add("create_date");
		headers.add("抄表时间");
		clomuns.add("curr_date");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("电表记录");
		
		addOpLog("[电表记录] 导出cvs");
		render(csvRender);
	}

    public void img() {
        String id = this.getPara("id");
        ElectricityMeterHistory meterHistory = ElectricityMeterHistory.me.findById(id);
        HttpServletResponse response = this.getResponse();
//        response.setContentType("image/*");

            try {
                byte[] bytes = meterHistory.getImg();
                for (int i = 0; i < bytes.length; ++i) {
                    if (bytes[i] < 0) {// 调整异常数据
                        bytes[i] += 256;
                    }
                }

                ServletOutputStream out = response.getOutputStream();
                out.write(bytes);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

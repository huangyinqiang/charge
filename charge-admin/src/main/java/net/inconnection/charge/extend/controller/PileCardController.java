package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.csv.CsvRender;
import net.inconnection.charge.admin.common.util.ExcelUtil;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.PileCard;
import net.inconnection.charge.extend.model.QrMatchDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备卡
 */
public class PileCardController extends BaseController {
	
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_pile_card", properties, symbols, values, orderBy, getPager());
		for(Record record : list){
            Long deviceId = record.getLong("deviceId");
            if(deviceId.toString().length() > 9){
                ChargePile chargePile = ChargePile.dao.findById(deviceId);
                if(chargePile != null){
                    record.set("deviceName", chargePile.getName());
                }

            }else{
                QrMatchDevice qrMatchDevice = QrMatchDevice.me.findByQrNum(deviceId);
                if(qrMatchDevice != null){
                    record.set("deviceName", qrMatchDevice.getRemark());
                }
            }
        }
		renderDatagrid(
			list, 
			DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_pile_card", properties, symbols, values)
		);
	}
	
	//增加页面
	public void addPage() {
		render("add.html");
	}
    public void addBatchPage() {
        render("addBatch.html");
    }
	//增加
	public void add() {
        PileCard model = getModel(PileCard.class, "model");
        PileCard pileCard = PileCard.me.queryPileCardByColumn("iccid", model.getIccid());
        if(pileCard != null){
            renderFailed();
            return;
        }
        pileCard = PileCard.me.queryPileCardByColumn("deviceId", model.getDeviceId()+"");
        if(pileCard != null){
            renderFailed();
            return;
        }
        model.setCreateDate(new Date());
        model.setUpdateDate(new Date());
        model.save();
        addOpLog("[设备卡] 增加");
		renderSuccess();
	}
    public void addBatch() {
        File file = getFiles().get(0).getFile();


        String fileName = getPara("fileName","");
        String suffix =fileName.substring(fileName.indexOf(".")+1);

        List<List<String[]>> list = ExcelUtil.getExcelData(file,suffix);
        String[] rowData = ExcelUtil.getRowData(0, 0);
        if(!rowData[0].equals("iccid") || !rowData[1].equals("deviceId")
                || !rowData[2].equals("total")){
            renderFailed("模板错误");
            return;
        }
        for(List l : list){
            for (int i = 0; i < l.size(); i++) {
                if(i > 0){
                    String[] row = (String [])l.get(i);
                    if(row[1].length() > 9) {
                        ChargePile chargePile = ChargePile.dao.findById(row[1]);
                        if(chargePile == null){
                            renderFailed("未找到设备");
                            return;
                        }
                    }else{
                        QrMatchDevice qrMatchDevice = QrMatchDevice.me.findByQrNum(Long.valueOf(row[1]));
                        if(qrMatchDevice == null){
                            renderFailed("未找到设备");
                            return;
                        }
                    }

                    PileCard pileCard = PileCard.me.queryPileCardByColumn("iccid", rowData[0]);
                    if(pileCard != null){
                        renderFailed("iccid重复");
                        return;
                    }
                    pileCard = PileCard.me.queryPileCardByColumn("deviceId", rowData[1]);
                    if(pileCard != null){
                        renderFailed("device重复");
                        return;
                    }

                }
            }
        }
        for(List l : list){
            for (int i = 0; i < l.size(); i++) {
                if(i > 0){
                    String[] row = (String [])l.get(i);
                    new PileCard().setIccid(row[0]).setDeviceId(Long.parseLong(row[1]))
                            .setTotal(Double.valueOf(row[2])).setCreateDate(new Date())
                            .setUpdateDate(new Date()).setState("1").save();
                }
            }
        }

        renderSuccess();
    }
	
	//修改页面
	public void updatePage() {
		setAttr("model", PileCard.me.findById(getPara("id")));
		render("update.html");
	}
	
	//修改
	public void update() {
		PileCard model = PileCard.me.findById(getPara("id"));
		model.set("deviceId", getPara("model.deviceId"));
		model.set("msisdn", getPara("model.msisdn"));
		model.set("iccid", getPara("model.iccid"));
		model.set("update_date", new Date());
		model.update();
		addOpLog("[设备卡] 修改");
		renderSuccess();
	}
	
	//删除
	public void delete() {
		Integer[] ids = getParaValuesToInt("id[]");
		for (Integer id : ids) {
			new PileCard().set("id", id).delete();
			
		}
		
		addOpLog("[设备卡] 删除");
		renderSuccess();
	}

    //导出csv
    public void exportTemplate() {

        List<String> headers = new ArrayList<String>();
        headers.add("iccid");
        headers.add("device");
        headers.add("total");

        CsvRender csvRender = new CsvRender(headers, null);
        csvRender.fileName("导入模板");
        render(csvRender);
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
		
		List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_pile_card", properties, symbols, values);
		
		List<String> headers = new ArrayList<String>();
		List<String> clomuns = new ArrayList<String>();
		headers.add("设备Id");
		clomuns.add("deviceId");
		headers.add("物联卡号");
		clomuns.add("msisdn");
		headers.add("iccid");
		clomuns.add("iccid");
		headers.add("状态 1正常 0 停机 ");
		clomuns.add("state");
		headers.add("已使用流量");
		clomuns.add("user");
		headers.add("总流量");
		clomuns.add("total");
		headers.add("create_date");
		clomuns.add("create_date");
		headers.add("update_date");
		clomuns.add("update_date");
		
		CsvRender csvRender = new CsvRender(headers, list);
		csvRender.clomuns(clomuns);
		csvRender.fileName("设备卡");
		
		addOpLog("[设备卡] 导出cvs");
		render(csvRender);
	}
}

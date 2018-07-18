package net.inconnection.charge.admin.common.util;

import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class Pager {
	
	private int page;
	private int rows;
	private List<Record> dataList;
	
	public int getStartRow() {
		return (getPage() - 1) * rows;
	}
	
	public int getPage() {
		if(page == 0) {
			page = 1;
		}
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		if(rows == 0) {
			rows = 20;
		}
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<Record> getDataList() {
		return dataList;
	}

	public void setDataList(List<Record> dataList) {
		this.dataList = dataList;
	}
}

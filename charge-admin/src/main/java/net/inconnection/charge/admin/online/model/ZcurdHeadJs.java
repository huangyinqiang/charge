package net.inconnection.charge.admin.online.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class ZcurdHeadJs  extends Model<ZcurdHeadJs> {
	private static final long serialVersionUID = 1L;
	public static final ZcurdHeadJs me = new ZcurdHeadJs();
	
	/** 处理变量后的扩展js内容 **/
	private String jsContentData;
	
	public List<ZcurdHeadJs> findByHeadId(int headId) {
		List<ZcurdHeadJs> list = find("select * from zcurd_head_js where head_id=?", headId);
		return list;
	}
	
	public String getJsContentData() {
		return jsContentData;
	}

	public void setJsContentData(String jsContentData) {
		this.jsContentData = jsContentData;
	}
}

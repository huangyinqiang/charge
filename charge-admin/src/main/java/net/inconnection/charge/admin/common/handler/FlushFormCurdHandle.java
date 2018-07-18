package net.inconnection.charge.admin.common.handler;


import net.inconnection.charge.admin.common.DbMetaTool;
import net.inconnection.charge.admin.online.vo.ZcurdMeta;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 刷新表单CurdHandle
 *
 */
public class FlushFormCurdHandle implements CurdHandle {

	@Override
	public void add(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		//刷新扩展JS、按钮所属表单
		String[] headIdArr = paraMap.get("model.head_id");
		if(headIdArr != null && headIdArr.length > 0) {
			int fHeadId = Integer.parseInt(headIdArr[0]);
			DbMetaTool.updateMetaData(fHeadId);
		}
	}

	@Override
	public void update(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		//刷新扩展JS、按钮所属表单
		String[] headIdArr = paraMap.get("model.head_id");
		if(headIdArr != null && headIdArr.length > 0) {
			int fHeadId = Integer.parseInt(headIdArr[0]);
			DbMetaTool.updateMetaData(fHeadId);
		}
		
	}

	@Override
	public void delete(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		
	}

}

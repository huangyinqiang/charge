package net.inconnection.charge.admin.common.handler;


import net.inconnection.charge.admin.online.vo.ZcurdMeta;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CurdHandle {
	
	public void add(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap);
	
	public void update(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap);
	
	public void delete(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap);

}

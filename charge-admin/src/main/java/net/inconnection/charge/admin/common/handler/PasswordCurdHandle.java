package net.inconnection.charge.admin.common.handler;


import net.inconnection.charge.admin.common.util.PasswordUtil;
import net.inconnection.charge.admin.online.vo.ZcurdMeta;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 密码CurdHandle
 *
 */
public class PasswordCurdHandle implements CurdHandle {

	@Override
	public void add(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		String password = PasswordUtil.defaultPassword;
		String[] passwordPara = paraMap.get("model.password");
		if(passwordPara != null && passwordPara.length > 0) {
			password = passwordPara[0];
		}
		paraMap.put("model.password", new String[]{PasswordUtil.encodePassword(password)});
	}

	@Override
	public void update(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		System.out.println("------------------CurdHandle to update!");
		
	}

	@Override
	public void delete(ZcurdMeta zcurdMeta, HttpServletRequest req, Map<String, String[]> paraMap) {
		System.out.println("------------------CurdHandle to delete!");
		
	}

}

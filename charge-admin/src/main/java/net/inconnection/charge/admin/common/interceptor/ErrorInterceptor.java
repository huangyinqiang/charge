package net.inconnection.charge.admin.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import net.inconnection.charge.admin.common.ErrorMsgException;

import java.util.HashMap;
import java.util.Map;

/**
 *  错误消息
 */
public class ErrorInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		// HttpServletRequest request = c.getRequest();
		
		try {
			inv.invoke();
		} catch (ErrorMsgException e) {
			//返回失败结果。（页面会显示错误消息）
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", "fail");
			result.put("msg", e.getMessage());
			c.renderJson(result);
		}
	}

}

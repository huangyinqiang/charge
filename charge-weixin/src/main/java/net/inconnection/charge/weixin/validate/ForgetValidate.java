package net.inconnection.charge.weixin.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;
import net.inconnection.charge.weixin.utils.RegexUtils;
import net.inconnection.charge.weixin.validate.base.ShortCircuitValidate;


/**
 * 注册校验器
 * @author Javen
 * 2016年4月2日
 */
public class ForgetValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		validateRequired("pass_one", "message", "请输入您的新密码");
		validateString("pass_one", 6, 24, "message", "请输入6~24位的新密码");

		validateRequired("pass_two", "message", "请输入您的确认密码");
		validateString("pass_two", 6, 24, "message", "请输入6~24位的确认密码");

	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("code", 1);
		c.render(new JsonRender().forIE());
	}

}

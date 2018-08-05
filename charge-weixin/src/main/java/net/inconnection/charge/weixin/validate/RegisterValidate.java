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
public class RegisterValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {

		validateRequired("tel", "message", "请输入您的手机号");
		validateRegex("tel", RegexUtils.PHONE, "message", "请检查您的手机号");

		validateRequired("imgCode", "message", "请输入验证码");
		validateCaptcha("imgCode", "message", "验证码错误");
		
		validateRequired("number", "message", "请输入手机验证密码");
		validateString("number", 6, 6, "message", "请输入6位的验证密码");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("code", 1);
		c.render(new JsonRender().forIE());
	}

}

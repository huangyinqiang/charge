package net.inconnection.charge.admin.account.controller;

import com.jfinal.plugin.activerecord.Db;
import net.inconnection.charge.admin.account.common.annotation.ClearAuth;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.PasswordUtil;

/**
 * 用户管理
 */
public class SysUserController extends BaseController {
	
	//修改密码页面
	@ClearAuth
	public void updatePasswordPage() {
		setAttr("model", SysUser.dao.findById(getSessionUser().getId()));
		render("updatePassword.html");
	}
	
	//修改密码
	@ClearAuth
	public void updatePassword() {
		SysUser model = SysUser.dao.findById(getSessionUser().getId());
		
		if(!model.getStr("password").equals(PasswordUtil.encodePassword(getPara("model.old_password")))) {
			renderFailed("原始密码输入错误");
			return;
		}
		
		model.set("password", PasswordUtil.encodePassword(getPara("model.password")));
		model.update();
		addOpLog("[用户行为] 修改密码");
		renderSuccess();
	}
	
	//重置密码
	public void resetPassword() {
		Db.update("update sys_user set password='" + PasswordUtil.encodePassword(PasswordUtil.defaultPassword) + "' where id=" + getParaToInt("id"));
		addOpLog("[系统用户] 重置密码");
		renderSuccess();
	}
}

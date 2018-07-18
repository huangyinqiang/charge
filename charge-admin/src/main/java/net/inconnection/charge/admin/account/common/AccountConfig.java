package net.inconnection.charge.admin.account.common;

import com.jfinal.config.*;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.template.Engine;
import net.inconnection.charge.admin.account.common.interceptor.AuthInterceptor;
import net.inconnection.charge.admin.account.common.interceptor.SessionInterceptor;
import net.inconnection.charge.admin.account.controller.LoginController;
import net.inconnection.charge.admin.account.controller.MenuController;
import net.inconnection.charge.admin.account.controller.RoleController;
import net.inconnection.charge.admin.account.controller.SysUserController;
import net.inconnection.charge.admin.online.controller.MainController;

/**
 * 账号模块配置
 * @author 钟世云 2018年3月31日 上午1:48:59
 */
public class AccountConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/login", LoginController.class, "/zcurd/login");
		me.add("/menu", MenuController.class, "/zcurd/menu");
		me.add("/main", MainController.class, "/zcurd");
		me.add("/role", RoleController.class, "/zcurd/role");
		me.add("/user", SysUserController.class, "/zcurd/sysUser");
	}

	@Override
	public void configEngine(Engine me) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new SessionInterceptor());
		me.add(new AuthInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		
	}

}

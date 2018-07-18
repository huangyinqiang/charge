package net.inconnection.charge.admin.online.controller;

import com.jfinal.core.Controller;
import net.inconnection.charge.admin.account.common.annotation.ClearAuth;
import net.inconnection.charge.admin.account.model.SysUser;
import net.inconnection.charge.admin.account.service.AuthService;

import java.util.List;

@ClearAuth
public class MainController extends Controller {
	
	public void index() {
		render("main.html");
	}

	public void urls() {
		AuthService authService = enhance(AuthService.class);
		List<String> urls = authService.getAuthUrl(SysUser.dao.findById(getParaToInt("id")));
		urls.addAll(authService.getAuthBtnUrl(SysUser.dao.findById(getParaToInt("id"))));
		renderJson(urls);
	}
	
	public void btns() {
		AuthService authService = enhance(AuthService.class);
		renderJson(authService.getNoAuthPageBtn(SysUser.dao.findById(getParaToInt("id"))));
	}
	
	public void rules() {
		AuthService authService = enhance(AuthService.class);
		renderJson(authService.getAuthDataRule(SysUser.dao.findById(getParaToInt("id"))));
	}
	
	public void menus() {
		AuthService authService = enhance(AuthService.class);
		renderJson(authService.getAuthMenuForTree(SysUser.dao.findById(getParaToInt("id"))));
	}
}

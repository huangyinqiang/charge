package net.inconnection.charge.admin.common;

import com.jfinal.aop.Duang;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import freemarker.template.TemplateModelException;
import net.inconnection.charge.admin.account.common.AccountConfig;
import net.inconnection.charge.admin.account.model.SysLoginLog;
import net.inconnection.charge.admin.account.model._MappingKit;
import net.inconnection.charge.admin.common.handler.ZcurdHandler;
import net.inconnection.charge.admin.common.interceptor.ErrorInterceptor;
import net.inconnection.charge.admin.online.controller.*;
import net.inconnection.charge.admin.online.model.*;
import net.inconnection.charge.admin.online.service.TaskService;
import net.inconnection.charge.extend.controller.ClawBookUrlController;
import net.inconnection.charge.extend.controller.StockHistoryLogController;
import net.inconnection.charge.extend.model.ClawBookUrl;
import net.inconnection.charge.extend.model.StockHistoryLog;
import net.inconnection.charge.extend.model.busi_MappingKit;

/**
 * API引导式配置
 */
public class ZcurdConfig extends JFinalConfig {
	AccountConfig accountConfig = new AccountConfig();
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		accountConfig.configConstant(me);
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.FREE_MARKER);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		accountConfig.configRoute(me);
		me.add("/zcurd", ZcurdController.class, "/zcurd/zcurd");
		me.add("/zcurdHead", ZcurdHeadController.class, "/zcurd");
		me.add("/common", CommonController.class, "/zcurd");
		me.add("/oplog", SysOplogController.class, "/zcurd/sysOplog");
		me.add("/task", TaskBaseController.class, "/zcurd/taskBase");
		
		me.add("/stockHistoryLog", StockHistoryLogController.class, "/busi/stockHistoryLog");
		me.add("/clawBookUrl", ClawBookUrlController.class, "/busi/clawBookUrl");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		accountConfig.configPlugin(me);
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("base_jdbcUrl"), PropKit.get("base_user"), PropKit.get("base_password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin("zcurd_base", c3p0Plugin);
		arp.setShowSql(true);
		me.add(arp);
		arp.addMapping("zcurd_head", ZcurdHead.class);
		arp.addMapping("zcurd_field", ZcurdField.class);
		arp.addMapping("zcurd_head_btn", ZcurdHeadBtn.class);
		arp.addMapping("zcurd_head_js", ZcurdHeadJs.class);
		arp.addMapping("sys_menu_btn", SysMenuBtn.class);
		arp.addMapping("sys_oplog", SysOplog.class);
		arp.addMapping("common_file", CommonFile.class);
		arp.addMapping("task_base", TaskBase.class);
		arp.addMapping("task_log", "id", TaskLog.class);
		arp.addMapping("sys_login_log", "id", SysLoginLog.class);
		_MappingKit.mapping(arp);


		//业务数据库
		C3p0Plugin c3p0PluginAir = new C3p0Plugin(PropKit.get("busi_jdbcUrl"), PropKit.get("busi_user"), PropKit.get("busi_password").trim());
		me.add(c3p0PluginAir);
		ActiveRecordPlugin arpAir = new ActiveRecordPlugin("zcurd_busi", c3p0PluginAir);
		arpAir.setShowSql(true);
		busi_MappingKit.mapping(arpAir);
		me.add(arpAir);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		accountConfig.configInterceptor(me);
		me.add(new ErrorInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		accountConfig.configHandler(me);
		me.add(new ZcurdHandler());
		
	}
	
	@Override
	public void afterJFinalStart() {
		accountConfig.afterJFinalStart();
		try {
			FreeMarkerRender.getConfiguration().setSharedVariable("basePath", JFinal.me().getContextPath());
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		
		//定时任务
		TaskService taskService = Duang.duang(TaskService.class);
		taskService.startAll();
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);
	}

	@Override
	public void configEngine(Engine me) {
		accountConfig.configEngine(me);
	}
}

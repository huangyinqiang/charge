package net.inconnection.charge.push.config;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import net.inconnection.charge.push.action.IndexAction;
import net.inconnection.charge.push.model.WeixinToken;
import net.inconnection.charge.push.plugin.ActiveMQPlugin;

import java.io.File;

public class MainConfig extends JFinalConfig {
    public MainConfig() {
    }

    public void configConstant(Constants me) {
        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode"));
        me.setEncoding("utf-8");
        me.setBaseUploadPath(PathKit.getWebRootPath() + File.separator + "myupload");
        me.setViewType(ViewType.JSP);
    }

    public void configRoute(Routes me) {
        me.add("/", IndexAction.class);
    }

    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(PropKit.getBoolean("devMode"));
        arp.setDialect(new MysqlDialect());
        arp.addMapping("weixin_token", WeixinToken.class);
        me.add(c3p0Plugin);
        me.add(arp);
        ActiveMQPlugin p = new ActiveMQPlugin(PropKit.get("mqaddress"));
        p.start();
        me.add(p);
    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/", 5);
    }
}

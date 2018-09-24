package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.Company;

public class AboutController extends Controller {
    private static Log log = Log.getLog(AboutController.class);

    public AboutController() {
    }

    public void index() {
        log.info("跳转到关于我们页面");
        this.render("about/about.html");
    }

    public void tbts() {
        log.info("跳转到特别提示页面");
        this.render("about/tbts.html");
    }

    public void mzsm() {
        log.info("跳转到免责声明页面");
        this.render("about/mzsm.html");
    }

    public void bqsm() {
        log.info("跳转到版权声明页面");
        this.render("about/bqsm.html");
    }
    public void warning() {
        log.info("跳转到温馨提示页面");
        this.render("about/warning.html");
    }

    public void getCompanyInfo() {
        Company company = new Company();
        company.setCompany(PropKit.get("Company"));
        company.setShortName(PropKit.get("ShortName"));
        company.setWechat(PropKit.get("WeChat"));
        company.setTel(PropKit.get("TEL"));
        company.setQrCode(PropKit.get("qrCode"));
        this.renderJson(company);
    }

    public void getQrCode() {
        Company company = new Company();
        company.setQrCode(PropKit.get("qrCode"));
        log.info("二维码内容:" + company.toString());
        this.renderJson(company);
    }
}


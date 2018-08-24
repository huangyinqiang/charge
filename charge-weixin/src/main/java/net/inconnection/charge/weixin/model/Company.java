package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class Company extends Model<Company> {
    private static final long serialVersionUID = 3097666259122699383L;
    private static final Log log = Log.getLog(Company.class);
    public static final Company dao = new Company();

    public Company() {
    }

    public List<Company> getAllCompany() {
        log.info("查询所有运营商公司信息:" );
        List<Company> companies = dao.find("select * from yc_company ");
        log.info("查询所有运营商公司信息:" + companies);
        return companies;
    }


}

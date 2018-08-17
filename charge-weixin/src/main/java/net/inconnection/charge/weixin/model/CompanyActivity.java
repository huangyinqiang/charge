package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class CompanyActivity extends Model<CompanyActivity> {
    private static final long serialVersionUID = -8523028502750057786L;
    private static final Log log = Log.getLog(CompanyActivity.class);
    public static final CompanyActivity dao = new CompanyActivity();

    public CompanyActivity() {
    }

    public List<CompanyActivity> queryActivityByCompanyId(int companyId) {
        log.info("查询优惠活动信息，companyId=" + companyId);
        List<CompanyActivity> activityList = dao.find("select * from yc_company_activity where company_id = ?", new Object[]{companyId});
        log.info("查询优惠信息结果：" + activityList);
        return activityList;
    }
}


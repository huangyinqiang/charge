package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class MoneyMatchActivity extends Model<MoneyMatchActivity> {
    private static final long serialVersionUID = -8523028502750057786L;
    private static final Log log = Log.getLog(MoneyMatchActivity.class);
    public static final MoneyMatchActivity dao = new MoneyMatchActivity();

    public MoneyMatchActivity() {
    }

    public List<MoneyMatchActivity> queryActivityByType(int activityId) {
        log.info("查询优惠活动信息，activityId=" + activityId);
        List<MoneyMatchActivity> activityList = dao.find("select * from money_match_activity where activity_id = ?", new Object[]{activityId});
        log.info("查询优惠信息结果：" + activityList);
        return activityList;
    }
}


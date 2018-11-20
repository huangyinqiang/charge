package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import java.util.Date;

public class RechargeHistory extends Model<RechargeHistory> {
    private static final long serialVersionUID = 6913913317742830874L;
    private static final Log log = Log.getLog(RechargeHistory.class);
    public static final RechargeHistory dao = new RechargeHistory();

    public RechargeHistory() {
    }


    public boolean addRechargeHistoryLog(final String openId, final String companyId, int totalMoney, int chargeMoney, int couponMoney,String deviceId,String projectId) {
        log.info("增加充值历史记录：" );
        RechargeHistory rechargeHistory = new RechargeHistory();
        rechargeHistory.set("openId", openId);
        rechargeHistory.set("company_id", Long.parseLong(companyId));
        rechargeHistory.set("project_id", projectId);
        rechargeHistory.set("device_id", deviceId);
        rechargeHistory.set("money_sum", totalMoney);
        rechargeHistory.set("real_money", chargeMoney);
        rechargeHistory.set("coupon", couponMoney);
        rechargeHistory.set("pay_agent_status", 0);
        rechargeHistory.set("recharge_time", new Date());

        boolean save = rechargeHistory.save();
        log.info("增加充值历史记录结果:" + save);
        return save;
    }


}


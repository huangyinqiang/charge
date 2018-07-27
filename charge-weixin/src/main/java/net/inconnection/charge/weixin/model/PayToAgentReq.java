package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class PayToAgentReq extends Model<PayToAgentReq> {
    private static final long serialVersionUID = 165704430770525792L;
    static Log log = Log.getLog(PayToAgentReq.class);
    public static final PayToAgentReq dao = new PayToAgentReq();

    public PayToAgentReq() {
    }

    public void insert(String openid, String amount, String mchid, String mchAppid, String nonceStr, String sign, String partnerTradeNo, String checkName, String reUserName, String desc, String payType, String MD5) {
        PayToAgentReq payToAgentReq = new PayToAgentReq();
        payToAgentReq.set("openid", openid);
        payToAgentReq.set("amount", amount);
        payToAgentReq.set("mchid", mchid);
        payToAgentReq.set("mch_appid", mchAppid);
        payToAgentReq.set("nonce_str", nonceStr);
        payToAgentReq.set("sign", sign);
        payToAgentReq.set("partner_trade_no", partnerTradeNo);
        payToAgentReq.set("check_name", checkName);
        payToAgentReq.set("re_user_name", reUserName);
        payToAgentReq.set("desc", desc);
        payToAgentReq.set("pay_type", payType);
        payToAgentReq.set("MD5", MD5);
        payToAgentReq.set("create_time", new Date());
        payToAgentReq.set("id", (Object)null);
        log.info("增加企业记录：" + payToAgentReq.toString());
        boolean save = payToAgentReq.save();
        log.info("增加企业记录结果：" + save);
    }

    public void update(String status, String nonceStr, String partnerTradeNo) {
        log.info("更新企业付款:status=" + status + ",nonceStar=" + nonceStr + ",partnerTradeNo=" + partnerTradeNo);
        int i = Db.update("update pay_to_agent_req set status = ? where nonce_str = ? and partner_trade_no = ? ", new Object[]{status, nonceStr, partnerTradeNo});
        log.info("更新企业付款结果：" + i);
    }
}
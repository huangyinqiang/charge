package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class PayToAgentResp extends Model<PayToAgentResp> {
    private static final long serialVersionUID = -517518973764960180L;
    private static final Log log = Log.getLog(PayToAgentResp.class);
    public static final PayToAgentResp dao = new PayToAgentResp();

    public PayToAgentResp() {
    }

    public void insert(String return_code, String return_msg, String mch_appid, String mchid, String device_info, String err_code, String err_code_des, String result_code, String nonce_str, String partner_trade_no, String payment_no, String payment_time) {
        log.info("企业付款调用成功记录存储：");
        PayToAgentResp payToAgentResp = new PayToAgentResp();
        payToAgentResp.set("return_code", return_code);
        payToAgentResp.set("return_msg", return_msg);
        payToAgentResp.set("mch_appid", mch_appid);
        payToAgentResp.set("mchid", mchid);
        payToAgentResp.set("device_info", device_info);
        payToAgentResp.set("nonce_str", nonce_str);
        payToAgentResp.set("result_code", result_code);
        payToAgentResp.set("err_code", err_code);
        payToAgentResp.set("err_code_des", err_code_des);
        payToAgentResp.set("partner_trade_no", partner_trade_no);
        payToAgentResp.set("payment_no", payment_no);
        payToAgentResp.set("payment_time", payment_time);
        payToAgentResp.set("create_time", new Date());
        payToAgentResp.set("id", (Object)null);
        log.info("企业付款调用成功记录存储：" + payToAgentResp.toString());
        boolean save = payToAgentResp.save();
        log.info("企业付款调用成功记录存储结果：" + save);
    }
}


package net.inconnection.charge.weixin.weixinCode.service;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import net.inconnection.charge.weixin.model.PayToAgentReq;
import net.inconnection.charge.weixin.model.PayToAgentResp;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeinXinPayToAgentUtils {
    private static Log log = Log.getLog(WeinXinPayToAgentUtils.class);
    private static String appid = PropKit.get("appId");
    private static String partner = PropKit.get("mch_id");
    private static String paternerKey = PropKit.get("paternerKey");
    private static String transfer_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public WeinXinPayToAgentUtils() {
    }

    public boolean payer(String openid, String money, String desc, String reUserName, String MD5) {
        log.info("开始执行企业付款");
        Map<String, String> params = new HashMap();
        String cert = PropKit.get("cert");
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String cerPath = path + cert;
        params.put("amount", money);
        params.put("check_name", "NO_CHECK");
        desc = StringUtils.isNotBlank(desc) ? desc : "企业付款";
        params.put("desc", desc);
        params.put("mch_appid", appid);
        params.put("mchid", partner);
        String nonceStr = String.valueOf(System.currentTimeMillis() / 1000L);
        params.put("nonce_str", nonceStr);
        params.put("openid", openid);
        String partnerTradeNo = UUID.randomUUID().toString().replace("-", "");
        params.put("partner_trade_no", partnerTradeNo);
        params.put("re_user_name", reUserName);
        String checnName = "NO_CHECK";
        String ip = "127.0.0.1";
        params.put("spbill_create_ip", ip);
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        log.info("拼接调用企业付款：" + xml);
        String xmlResult = HttpUtils.postSSL(transfer_url, xml, cerPath, partner);
        PayToAgentReq.dao.insert(openid, money, partner, appid, nonceStr, sign, partnerTradeNo, checnName, reUserName, desc, "MA", MD5);
        Map<String, String> resultXML = PaymentKit.xmlToMap(xmlResult.toString());
        log.info("企业付款返回信息：" + resultXML);
        String return_code = (String)resultXML.get("return_code");
        String return_msg = (String)resultXML.get("return_msg");
        String mch_appid = (String)resultXML.get("mch_appid");
        String mchid = (String)resultXML.get("mchid");
        String device_info = (String)resultXML.get("device_info");
        String err_code = (String)resultXML.get("err_code");
        String err_code_des = (String)resultXML.get("err_code_des");
        String result_code = (String)resultXML.get("result_code");
        String payment_no = (String)resultXML.get("payment_no");
        String payment_time = (String)resultXML.get("payment_time");
        PayToAgentResp.dao.insert(return_code, return_msg, mch_appid, mchid, device_info, err_code, err_code_des, result_code, nonceStr, partnerTradeNo, payment_no, payment_time);
        if (StringUtils.isNotBlank(return_code) && StringUtils.isNotBlank(result_code) && return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
            log.info("企业付款成功！");
            return true;
        } else if (StringUtils.isNotBlank(return_code) && StringUtils.isNotBlank(result_code) && return_code.equals("SUCCESS") && !result_code.equals("SUCCESS")) {
            log.info("企业付款失败！");
            return false;
        } else {
            log.info("企业付款失败！");
            return false;
        }
    }
}


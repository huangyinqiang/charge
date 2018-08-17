package net.inconnection.charge.weixin.weixinCode.controller;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import net.inconnection.charge.weixin.bean.OrdersBean;
import net.inconnection.charge.weixin.bean.resp.AjaxResult;
import net.inconnection.charge.weixin.service.OrdersService;
import net.inconnection.charge.weixin.utils.DataEnDecryptionService;
import net.inconnection.charge.weixin.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WeiXinPayController extends ApiController
{
    private static final Log log = Log.getLog(WeiXinPayController.class);

    private static final OrdersService orderService = new OrdersService();

    String appid = PropKit.get("appId");

    String partner = PropKit.get("mch_id");

    String paternerKey = PropKit.get("paternerKey");
    String notify_url = PropKit.get("domain") + "/pay/pay_notify";

    public ApiConfig getApiConfig()
    {
        ApiConfig ac = new ApiConfig();
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", Boolean.valueOf(false)).booleanValue());
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    public void index() {
        AjaxResult ajaxResult = new AjaxResult();

//        String openId = (String)getSessionAttr("openId");
        String openId = (String)getPara("openId");
        log.info("开始第一次调用微信支付,openId:" + openId);
        if (StrKit.isBlank(openId)) {
            log.error("openId为空，支付失败");
            ajaxResult.addError("支付失败");
            renderJson(ajaxResult);
            return;
        }

        String total_fee = DataEnDecryptionService.decrypt(getPara("total_fee"));
        if (StrKit.isBlank(total_fee)) {
            log.error("未获取得支付金额，支付失败");
            ajaxResult.addError("请输入数字金额");
            renderJson(ajaxResult);
            return;
        }

        Map params = new HashMap();
        params.put("appid", this.appid);
        params.put("mch_id", this.partner);
        params.put("body", PropKit.get("WeChat"));
        params.put("out_trade_no", String.valueOf(System.currentTimeMillis()));
        params.put("total_fee", total_fee);
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("trade_type", PaymentApi.TradeType.JSAPI.name());
        params.put("nonce_str", String.valueOf(System.currentTimeMillis() / 1000L));
        params.put("notify_url", this.notify_url);
        params.put("openid", openId);
        params.put("sign", PaymentKit.createSign(params, this.paternerKey));

        String xmlResult = PaymentApi.pushOrder(params);
        log.info("第一次请求的结果:" + xmlResult);

        Map result = PaymentKit.xmlToMap(xmlResult);

        String return_code = (String)result.get("return_code");
        String return_msg = (String)result.get("return_msg");
        if ((StrKit.isBlank(return_code)) || (!("SUCCESS".equals(return_code)))) {
            log.error("第一次请求失败：" + result);
            ajaxResult.addError(return_msg);
            renderJson(ajaxResult);
            return;
        }
        String result_code = (String)result.get("result_code");
        if ((StrKit.isBlank(result_code)) || (!("SUCCESS".equals(result_code)))) {
            log.error("第一次请求失败：" + result);
            ajaxResult.addError(return_msg);
            renderJson(ajaxResult);
            return;
        }

        Map packageParams = new HashMap();
        packageParams.put("appId", this.appid);
        packageParams.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
        packageParams.put("nonceStr", String.valueOf(System.currentTimeMillis()));
        packageParams.put("package", "prepay_id=" + ((String)result.get("prepay_id")));
        packageParams.put("signType", "MD5");
        packageParams.put("paySign", PaymentKit.createSign(packageParams, this.paternerKey));
        ajaxResult.success(JsonUtils.toJson(packageParams));
        renderJson(ajaxResult);
    }

    public void pay_notify()
    {
        StringBuffer sbf = new StringBuffer();
        Enumeration en = getParaNames();
        while (en.hasMoreElements()) {
            Object o = en.nextElement();
            sbf.append(o.toString() + "=" + getPara(o.toString()));
        }
        log.info("支付通知参数：" + sbf.toString());

        String xmlMsg = HttpKit.readData(getRequest());
        log.info("支付通知=" + xmlMsg);
        Map params = PaymentKit.xmlToMap(xmlMsg);
        String appid = (String)params.get("appid");
        String mch_id = (String)params.get("mch_id");
        String result_code = (String)params.get("result_code");
        String openId = (String)params.get("openid");
        String trade_type = (String)params.get("trade_type");
        String bank_type = (String)params.get("bank_type");
        Integer total_fee = Integer.valueOf(Integer.parseInt((String)params.get("total_fee")));
        Integer cash_fee = Integer.valueOf(Integer.parseInt((String)params.get("cash_fee")));
        String transaction_id = (String)params.get("transaction_id");
        String out_trade_no = (String)params.get("out_trade_no");
        String time_end = (String)params.get("time_end");

        String attach = (String)params.get("attach");
        String fee_type = (String)params.get("fee_type");
        String is_subscribe = (String)params.get("is_subscribe");
        String err_code = (String)params.get("err_code");
        String err_code_des = (String)params.get("err_code_des");
        OrdersBean bean = new OrdersBean();
        bean.setAppid(appid);
        bean.setOutTradeNo(out_trade_no);
        bean.setOpenid(openId);
        bean.setMchId(mch_id);
        bean.setCashFee(cash_fee);
        bean.setTotalFee(total_fee);
        bean.setFeeType(fee_type);
        bean.setResultCode(result_code);
        bean.setErrCode(err_code);
        bean.setErrCodeDes(err_code_des);
        bean.setIsSubscribe(is_subscribe);
        bean.setTradeType(trade_type);
        bean.setBankType(bank_type);
        bean.setTransactionId(transaction_id);
        bean.setAttach(attach);
        bean.setTimeEnd(time_end);
        orderService.saveOrders(bean);

        if ((PaymentKit.verifyNotify(params, this.paternerKey)) &&
                ("SUCCESS".equals(result_code)))
        {
            Map xml = new HashMap();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            log.info("返回通知：" + PaymentKit.toXml(xml));
            renderText(PaymentKit.toXml(xml));
            return;
        }

        renderText("");
    }

    public void wxpay() {
        try {
            HttpServletRequest request = getRequest();

            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");

            Map map = PaymentKit.xmlToMap(result);
            for (Object key : map.keySet()) {
                log.info("key= " + key + " and value= " + ((String)map.get(key)));
            }

            Map packageParams = new HashMap();
            packageParams.put("appid", (String)map.get("appid"));
            packageParams.put("openid", (String)map.get("openid"));
            packageParams.put("mch_id", (String)map.get("mch_id"));
            packageParams.put("is_subscribe", (String)map.get("is_subscribe"));
            packageParams.put("nonce_str", (String)map.get("nonce_str"));
            packageParams.put("product_id", (String)map.get("product_id"));

            String packageSign = PaymentKit.createSign(packageParams, this.paternerKey);

            Map params = new HashMap();
            params.put("appid", this.appid);
            params.put("mch_id", (String)map.get("mch_id"));
            params.put("body", "测试扫码支付");
            String out_trade_no = Long.toString(System.currentTimeMillis());
            params.put("out_trade_no", out_trade_no);
            params.put("attach", out_trade_no);
            params.put("total_fee", String.valueOf(Float.valueOf(10.0F).floatValue() * 100.0F));
            params.put("spbill_create_ip", (StrKit.isBlank(IpKit.getRealIp(getRequest()))) ? "127.0.0.1" : IpKit.getRealIp(getRequest()));
            params.put("trade_type", PaymentApi.TradeType.NATIVE.name());
            params.put("nonce_str", String.valueOf(System.currentTimeMillis()));
            params.put("notify_url", this.notify_url);
            params.put("openid", (String)map.get("openid"));
            params.put("sign", PaymentKit.createSign(params, this.paternerKey));
            String xmlResult = PaymentApi.pushOrder(params);
            log.info("prepay_xml>>>" + xmlResult);

            Map payResult = PaymentKit.xmlToMap(xmlResult);
            String return_code = (String)payResult.get("return_code");
            String result_code = (String)payResult.get("result_code");
            if ((!(StrKit.notBlank(return_code))) || (!(StrKit.notBlank(result_code))) || (!(return_code.equalsIgnoreCase("SUCCESS"))) || (!(result_code.equalsIgnoreCase("SUCCESS"))))
                return;
            String prepay_id = (String)payResult.get("prepay_id");
            Map prepayParams = new HashMap();
            prepayParams.put("return_code", "SUCCESS");
            prepayParams.put("appId", this.appid);
            prepayParams.put("mch_id", (String)map.get("mch_id"));
            prepayParams.put("nonceStr", System.currentTimeMillis());
            prepayParams.put("prepay_id", prepay_id);
            String prepaySign = null;
            if (((String)map.get("sign")).equals(packageSign)) {
                prepayParams.put("result_code", "SUCCESS");
            } else {
                prepayParams.put("result_code", "FAIL");
                prepayParams.put("err_code_des", "订单失效");
            }
            prepaySign = PaymentKit.createSign(prepayParams, this.paternerKey);
            prepayParams.put("sign", prepaySign);
            String xml = PaymentKit.toXml(prepayParams);
            log.info("openId:" + ((String)map.get("openid")) + xml);
            renderText(xml);
        }
        catch (Exception e) {
            log.error("WeixinPayController.pay error:", e);
        }
    }

    public String getCodeUrl() {
        String url = "weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXX&time_stamp=XXXXX&nonce_str=XXXXX";
        String product_id = "001";
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000L);
        String nonceStr = Long.toString(System.currentTimeMillis());
        Map packageParams = new HashMap();
        packageParams.put("appid", this.appid);
        packageParams.put("mch_id", this.partner);
        packageParams.put("product_id", product_id);
        packageParams.put("time_stamp", timeStamp);
        packageParams.put("nonce_str", nonceStr);
        String packageSign = PaymentKit.createSign(packageParams, this.paternerKey);
        return StringUtils.replace(url, "XXXXX", new String[] { packageSign, this.appid, this.partner, product_id, timeStamp, nonceStr });
    }

    public void test() {
        renderText(getCodeUrl());
    }
}



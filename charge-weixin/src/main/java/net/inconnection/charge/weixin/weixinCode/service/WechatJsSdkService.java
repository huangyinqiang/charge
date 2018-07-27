package net.inconnection.charge.weixin.weixinCode.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.exception.BizException;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WechatJsSdkService {
    private static final Log logger = Log.getLog(WechatJsSdkService.class);
    private static final String appId = PropKit.get("appId");
    private static final WechatAccessTokenService wechatAccessTokenService = new WechatAccessTokenService();
    public static final String weixin_jssdk_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public WechatJsSdkService() {
    }

    public Object sign(String url) {
        String jsapi_ticket = getJSSDKTicket();
        Map<String, String> map = new HashMap();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String signature = "";
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (Exception var9) {
            logger.error("获取微信JSSDK签名异常", var9);
            throw new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }

        map.put("appId", appId);
        map.put("nonceStr", nonce_str);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        return JSONObject.toJSON(map);
    }

    private static String getJSSDKTicket() {
        logger.info("开始http请求微信获取ticket");

        try {
            String accessToken = wechatAccessTokenService.getAccessToken();
            String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("ACCESS_TOKEN", accessToken);
            String connect = HttpUrlConnectionUtil.connect(requestUrl);
            logger.info("http请求微信获取ticket结束:" + connect);
            JSONObject parseObject = JSONObject.parseObject(connect);
            Object ticket = parseObject.get("ticket");
            Object errmsg = parseObject.get("errmsg");
            if (ticket != null && errmsg != null && errmsg.equals("ok")) {
                return ticket.toString();
            } else {
                if (connect.contains("access_token is invalid")) {
                    logger.info("二次获取accessToken");
                    String accessToken2 = wechatAccessTokenService.updateAccessToken();
                    String requestUrl2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("ACCESS_TOKEN", accessToken2);
                    String connect2 = HttpUrlConnectionUtil.connect(requestUrl2);
                    logger.info("http请求微信获取ticket结束:" + connect2);
                    JSONObject parseObject2 = JSONObject.parseObject(connect2);
                    Object ticket2 = parseObject2.get("ticket");
                    Object errmsg2 = parseObject2.get("errmsg");
                    if (ticket2 != null && errmsg2 != null && errmsg2.equals("ok")) {
                        return ticket2.toString();
                    }
                }

                logger.error("http请求微信获取ticket失败");
                throw new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        } catch (Exception var12) {
            logger.error("http请求微信获取ticket异常", var12);
            throw new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var5 = hash;
        int var4 = hash.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }
}


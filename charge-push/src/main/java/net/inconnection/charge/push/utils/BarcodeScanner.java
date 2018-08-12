package net.inconnection.charge.push.utils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.push.model.AccessToken;
import net.inconnection.charge.push.model.Ticket;
import net.inconnection.charge.push.model.WeixinToken;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BarcodeScanner {
    static Log log = Log.getLog(BarcodeScanner.class);
    static String appId = PropKit.get("appId");
    static String appSecret = PropKit.get("appSecret");
    public static final String weixin_jssdk_acceToken_url;
    public static final String weixin_jssdk_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public BarcodeScanner() {
    }

    public static Map<String, String> getSign(String url) {
        log.info("判断acctoken是否过期");
        List<WeixinToken> token = WeixinToken.dao.findToken();
        if (token.size() > 0) {
            try {
                Date d1 = (Date)((WeixinToken)token.get(0)).get("createTime");
                Date d2 = new Date();
                long m = d2.getTime() - d1.getTime();
                long tokenTime = m / 1000L;
                if (tokenTime > 7200L) {
                    String jssdkAccessToken = getJSSDKAccessToken();
                    String jssdkTicket = getJSSDKTicket(jssdkAccessToken);
                    Map<String, String> sign = sign(jssdkTicket, url);
                    WeixinToken.dao.update(jssdkAccessToken, jssdkTicket, appId);
                    return sign;
                }

                Map<String, String> sign = sign((String)((WeixinToken)token.get(0)).get("jsapiTicket"), url);
                return sign;
            } catch (Exception var11) {
                log.error("BarcodeScanner.getSign判断token是否有效异常", var11);
            }
        }

        String jssdkAccessToken = getJSSDKAccessToken();
        String jssdkTicket = getJSSDKTicket(jssdkAccessToken);
        Map<String, String> sign = sign(jssdkTicket, url);
        WeixinToken.dao.insert(appId, jssdkAccessToken, jssdkTicket);
        return sign;
    }

    public static String getJSSDKAccessToken() {
        String returnString = "";
        String requestUrl = weixin_jssdk_acceToken_url;

        try {
            String connect = HttpUrlConnectionUtil.connect(requestUrl);
            log.info("AccessToken:" + connect);
            System.out.println(connect);
            AccessToken parseObject = (AccessToken)JSONObject.parseObject(connect, AccessToken.class);
            returnString = parseObject.getAccess_token();
        } catch (Exception var4) {
            log.error("BarcodeScanner.getJSSDKAccessToken获取微信access_token异常", var4);
            returnString = null;
        }

        return returnString;
    }

    public static String getJSSDKTicket(String access_token) {
        String returnString = "";
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("ACCESS_TOKEN", access_token);

        try {
            String connect = HttpUrlConnectionUtil.connect(requestUrl);
            log.info("获取微信JSSDK的ticket:" + connect);
            Ticket parseObject = (Ticket)JSONObject.parseObject(connect, Ticket.class);
            returnString = parseObject.getTicket();
        } catch (Exception var5) {
            returnString = null;
        }

        return returnString;
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String signature = "";
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException var8) {
            log.error("BarcodeScanner.sign获取微信JSSDK签名异常", var8);
        } catch (UnsupportedEncodingException var9) {
            log.error("BarcodeScanner.sign获取微信JSSDK签名异常", var9);
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
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

    public static void main(String[] args) {
    }

    static {
        weixin_jssdk_acceToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
    }
}


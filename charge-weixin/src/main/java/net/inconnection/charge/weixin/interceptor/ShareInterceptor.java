package net.inconnection.charge.weixin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;

import java.util.UUID;

public class ShareInterceptor
        implements Interceptor {
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", Boolean.valueOf(false)).booleanValue());
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    public void intercept(Invocation inv) {
        inv.invoke();
        Controller controller = inv.getController();
        ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
        JsTicket jsApiTicket = JsTicketApi.getTicket(JsTicketApi.JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String nonce_str = create_nonce_str();

        String url = "http://" + controller.getRequest().getServerName() +
                controller.getRequest().getContextPath() +
                controller.getRequest().getServletPath();
        String qs = controller.getRequest().getQueryString();
        if (qs != null) {
            url = url + "?" + controller.getRequest().getQueryString();
        }
        System.out.println("url>>>>" + url);
        String timestamp = create_timestamp();

        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        String signature = HashKit.sha1(str);

        System.out.println("appId " + ApiConfigKit.getApiConfig().getAppId() + "  nonceStr " + nonce_str + " timestamp " + timestamp);
        System.out.println("url " + url + " signature " + signature);
        System.out.println("nonceStr " + nonce_str + " timestamp " + timestamp);
        System.out.println(" jsapi_ticket " + jsapi_ticket);
        System.out.println("nonce_str  " + nonce_str);
        controller.setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
        controller.setAttr("nonceStr", nonce_str);
        controller.setAttr("timestamp", timestamp);
        controller.setAttr("url", url);
        controller.setAttr("signature", signature);
        controller.setAttr("jsapi_ticket", jsapi_ticket);
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
}
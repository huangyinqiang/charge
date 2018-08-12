package net.inconnection.charge.push.utils;

import com.jfinal.kit.PropKit;
import net.inconnection.charge.push.model.WeixinToken;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class WeiXinHttpUtils {
    public WeiXinHttpUtils() {
    }

    public static String SEND_WEIXIN_MESSAGE(String reqJson) throws Exception {
        String accessToken = getAccessToken();
        String action = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
        String urlstr = action + accessToken;
        URL httpclient = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection)httpclient.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(2000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        OutputStream os = conn.getOutputStream();
        os.write(reqJson.getBytes("UTF-8"));
        os.flush();
        os.close();
        InputStream is = conn.getInputStream();
        int size = is.available();
        byte[] jsonBytes = new byte[size];
        is.read(jsonBytes);
        String message = new String(jsonBytes, "UTF-8");
        return message;
    }

    public static String getAccessToken() {
        String appId = PropKit.get("appId");
        System.out.println(appId);
        List<WeixinToken> token = WeixinToken.dao.findToken();
        System.out.println(token);
        String accessToken = (String)((WeixinToken)token.get(0)).get("accessToken");
        if (token.size() > 0) {
            Date d1 = (Date)((WeixinToken)token.get(0)).get("createTime");
            Date d2 = new Date();
            long m = d2.getTime() - d1.getTime();
            long tokenTime = m / 1000L;
            if (tokenTime > 7200L) {
                accessToken = BarcodeScanner.getJSSDKAccessToken();
                String jssdkTicket = BarcodeScanner.getJSSDKTicket(accessToken);
                WeixinToken.dao.update(accessToken, jssdkTicket, appId);
            }
        }

        return accessToken;
    }
}


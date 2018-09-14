package net.inconnection.charge.push.weixin;

import com.jfinal.kit.PropKit;
import net.inconnection.charge.push.model.WeiXin;
import net.inconnection.charge.push.model.WeixinToken;
import net.inconnection.charge.push.utils.BarcodeScanner;
import net.inconnection.charge.push.utils.WeiXinHttpUtils;

public class WeiXinSender {
    public WeiXinSender() {
    }

    public static void SendToWeiXin(WeiXin weixin) {
        WeiXinTemplate template = new WeiXinTemplate();
        String reqJson = null;
        if (weixin.getType().equalsIgnoreCase("AUTO")) {
            reqJson = template.alarmTemp(weixin.getOpenId(), weixin.getTitle(), weixin.getMessage(), weixin.getArea());
        } else if (weixin.getType().equalsIgnoreCase("A")) {
            reqJson = template.fully(weixin.getOpenId(), weixin.getMessage(), weixin.getOperStartTime(), weixin.getTitle());
        } else if (weixin.getType().equalsIgnoreCase("B")) {
            reqJson = template.createSendDataToText(weixin.getOpenId(), weixin.getMessage(), weixin.getOperStartTime(), weixin.getTitle(), weixin.getChannelNum());
        } else if (weixin.getType().equalsIgnoreCase("C")) {
            reqJson = template.createSendDataToText(weixin.getOpenId(), weixin.getMessage(), weixin.getOperStartTime(), weixin.getTitle(), weixin.getChannelNum());
        } else if (weixin.getType().equalsIgnoreCase("D")) {
            reqJson = template.complete(weixin.getOpenId(), weixin.getChannelNum(), weixin.getTitle(), weixin.getMessage(), weixin.getMoney(), weixin.getWalletAccount(), weixin.getChargeTime());
        } else if (weixin.getType().equalsIgnoreCase("F")) {
            reqJson = template.createSendDataToText(weixin.getOpenId(), weixin.getMessage(), weixin.getOperStartTime(), weixin.getTitle(), weixin.getChannelNum());
        } else if (weixin.getType().equalsIgnoreCase("E")) {
            reqJson = template.createSendDataToText(weixin.getOpenId(), weixin.getMessage(), weixin.getOperStartTime(), weixin.getTitle(), weixin.getChannelNum());
        } else if (weixin.getType().equalsIgnoreCase("ALARM")) {
            reqJson = template.alarmTemp(weixin.getOpenId(), weixin.getTitle(), weixin.getMessage());
        }else if (weixin.getType().equalsIgnoreCase("NC")) {
            reqJson = template.newDeviceComplete(weixin.getOpenId(), weixin.getArea(), weixin.getTitle(), weixin.getMessage(), weixin.getMoney(), weixin.getWalletAccount());
        }

        try {
            String message = WeiXinHttpUtils.SEND_WEIXIN_MESSAGE(reqJson);
            if (message.contains("access_token is invalid")) {
                String accessToken = BarcodeScanner.getJSSDKAccessToken();
                String jssdkTicket = BarcodeScanner.getJSSDKTicket(accessToken);
                WeixinToken.dao.update(accessToken, jssdkTicket, PropKit.get("appId"));
                message = WeiXinHttpUtils.SEND_WEIXIN_MESSAGE(reqJson);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}


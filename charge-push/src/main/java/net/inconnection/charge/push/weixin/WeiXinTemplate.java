package net.inconnection.charge.push.weixin;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeiXinTemplate {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public WeiXinTemplate() {
    }

    public String alarmTemp(String openId, String title, String msg) {
        JSONObject first = new JSONObject();
        first.put("value", title);
        first.put("color", "#0084cc");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", msg + "充电设备");
        keyword1.put("color", "#0084cc");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", this.sdf.format(new Date()));
        keyword2.put("color", "#0084cc");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "掉线,请注意检查");
        keyword3.put("color", "#0084cc");
        JSONObject remark = new JSONObject();
        remark.put("value", "");
        remark.put("color", "#0084cc");
        JSONObject data2 = new JSONObject();
        data2.put("first", first);
        data2.put("keyword1", keyword1);
        data2.put("keyword2", keyword2);
        data2.put("keyword3", keyword3);
        data2.put("remark", remark);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("template_id", PropKit.get("alarm"));
        gjson.put("url", "");
        gjson.put("data", data2);
        return gjson.toString();
    }

    public String alarmTemp(String openId, String title, String msg, String area) {
        JSONObject first = new JSONObject();
        first.put("value", title);
        first.put("color", "#0084cc");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", area);
        keyword1.put("color", "#0084cc");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", msg);
        keyword2.put("color", "#0084cc");
        JSONObject remark = new JSONObject();
        remark.put("value", "");
        remark.put("color", "#0084cc");
        JSONObject data2 = new JSONObject();
        data2.put("first", first);
        data2.put("keynote1", keyword1);
        data2.put("keynote2", keyword2);
        data2.put("remark", remark);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("template_id", PropKit.get("chargingStarted"));
        gjson.put("url", "");
        gjson.put("data", data2);
        return gjson.toString();
    }

    public String complete(String openId, String devicePort, String title, String msg, String charge, Integer walletAccount, String chargeTime) {
        JSONObject first = new JSONObject();
        first.put("value", title);
        first.put("color", "#0084cc");
        JSONObject keynote1 = new JSONObject();
        keynote1.put("value", devicePort + "插座");
        keynote1.put("color", "#0084cc");
        JSONObject keynote2 = new JSONObject();
        keynote2.put("value", "选择智能充电" + chargeTime + "分钟");
        keynote2.put("color", "#0084cc");
        JSONObject keynote3 = new JSONObject();
        keynote3.put("value", Double.parseDouble(charge) / 100.0D + "元");
        keynote3.put("color", "#0084cc");
        JSONObject keynote4 = new JSONObject();
        keynote4.put("value", (double)walletAccount / 100.0D + "元");
        keynote4.put("color", "#0084cc");
        JSONObject remark = new JSONObject();
        remark.put("value", msg);
        remark.put("color", "#0084cc");
        JSONObject data2 = new JSONObject();
        data2.put("first", first);
        data2.put("keynote1", keynote1);
        data2.put("keynote2", keynote2);
        data2.put("keynote3", keynote3);
        data2.put("keynote4", keynote4);
        data2.put("remark", remark);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("template_id", PropKit.get("complete"));
        gjson.put("url", "");
        gjson.put("data", data2);
        return gjson.toString();
    }

    public String fully(String openId, String msg, Date operStartTime, String title) {
        JSONObject first = new JSONObject();
        first.put("value", title);
        first.put("color", "#0084cc");
        JSONObject keynote1 = new JSONObject();
        keynote1.put("value", this.sdf.format(operStartTime));
        keynote1.put("color", "#0084cc");
        JSONObject keynote2 = new JSONObject();
        keynote2.put("value", this.sdf.format(new Date()));
        keynote2.put("color", "#0084cc");
        JSONObject remark = new JSONObject();
        remark.put("value", msg);
        remark.put("color", "#0084cc");
        JSONObject data2 = new JSONObject();
        data2.put("first", first);
        data2.put("keyword1", keynote1);
        data2.put("keyword2", keynote2);
        data2.put("remark", remark);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("template_id", PropKit.get("fully"));
        gjson.put("url", "");
        gjson.put("data", data2);
        return gjson.toString();
    }

    public String createSendDataToText(String openId, String msg, Date operStartTime, String title, String devicePort) {
        JSONObject first = new JSONObject();
        first.put("value", title);
        first.put("color", "#0084cc");
        JSONObject keynote1 = new JSONObject();
        keynote1.put("value", "插座" + devicePort);
        keynote1.put("color", "#0084cc");
        JSONObject keynote2 = new JSONObject();
        keynote2.put("value", msg);
        keynote2.put("color", "#0084cc");
        JSONObject keynote3 = new JSONObject();
        keynote3.put("value", this.sdf.format(operStartTime));
        keynote3.put("color", "#0084cc");
        JSONObject remark = new JSONObject();
        remark.put("value", "");
        remark.put("color", "#0084cc");
        JSONObject data2 = new JSONObject();
        data2.put("first", first);
        data2.put("keyword1", keynote1);
        data2.put("keyword2", keynote2);
        data2.put("keyword3", keynote3);
        data2.put("remark", remark);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("template_id", PropKit.get("templateId"));
        gjson.put("url", "");
        gjson.put("data", data2);
        return gjson.toString();
    }
}


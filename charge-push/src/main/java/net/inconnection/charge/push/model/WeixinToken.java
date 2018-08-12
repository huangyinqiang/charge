package net.inconnection.charge.push.model;


import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;
import java.util.List;

public class WeixinToken extends Model<WeixinToken> {
    private static final long serialVersionUID = 3650252207247518642L;
    static Log log = Log.getLog(WeixinToken.class);
    public static final WeixinToken dao = new WeixinToken();

    public WeixinToken() {
    }

    public List<WeixinToken> findToken() {
        return dao.find("select * from weixin_token");
    }

    public void insert(String appId, String jssdkAccessToken, String jssdkTicket) {
        WeixinToken weixinToken = new WeixinToken();
        weixinToken.set("openId", appId);
        weixinToken.set("accessToken", jssdkAccessToken);
        weixinToken.set("jsapiTicket", jssdkTicket);
        weixinToken.set("createTime", new Date());
        weixinToken.set("id", (Object)null);
        weixinToken.save();
    }

    public void update(String jssdkAccessToken, String jssdkTicket, String appId) {
        Db.update("update weixin_token set accessToken = ? , jsapiTicket= ? ,createTime = ? where openId = ? ", new Object[]{jssdkAccessToken, jssdkTicket, new Date(), appId});
    }
}


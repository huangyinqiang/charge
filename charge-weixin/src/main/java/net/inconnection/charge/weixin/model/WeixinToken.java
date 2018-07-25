package net.inconnection.charge.weixin.model;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import net.inconnection.charge.weixin.bean.token.WeixinTokenBean;

import java.util.Date;

public class WeixinToken extends Model<WeixinToken> {
    private static final long serialVersionUID = 192303408725696297L;
    private static final Log log = Log.getLog(WeixinToken.class);
    public static final WeixinToken dao = new WeixinToken();

    public WeixinToken() {
    }

    public WeixinToken queryAccessTokenByAppId(String appId) throws Exception {
        log.info("开始查询accessToken:" + appId);
        WeixinToken accessToken = (WeixinToken)this.findFirst("select * from weixin_token where app_id = ?", new Object[]{appId});
        if (accessToken.get("status") == null) {
            throw new Exception();
        } else {
            String status = (String)accessToken.get("status");
            if (status.equals("N")) {
                throw new Exception();
            } else {
                log.info("查询accessToken结果" + accessToken);
                return accessToken;
            }
        }
    }

    public int updateAccessTokenByAppId(WeixinTokenBean req) {
        log.info("开始更新accessToken:" + req.toString());
        int update = Db.update("update weixin_token set accessToken = ? ,createTime = ? where app_id = ? ", new Object[]{req.getAccessToken(), new Date(), req.getAppId()});
        log.info("更新accessToken结果：" + update);
        return update;
    }

    public boolean addAccessToken(WeixinTokenBean req) {
        log.info("开始保存accessToken：" + req.toString());
        String appId = PropKit.get("appId");
        boolean save = ((WeixinToken)((WeixinToken)((WeixinToken)((WeixinToken)dao.set("id", (Object)null)).set("accessToken", req.getAccessToken())).set("createTime", new Date())).set("app_id", appId)).save();
        log.info("保存accessToken结果：" + save);
        return save;
    }

    public int updateStatus(String status) {
        log.info("开始更新sts:" + status);
        int update = Db.update("update weixin_token set status = ?  ", new Object[]{status});
        log.info("更新sts结果：" + update);
        return update;
    }
}


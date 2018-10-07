package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import net.inconnection.charge.weixin.bean.TuserBean;

import java.util.Date;

public class TUser extends Model<TUser> {
    private static final long serialVersionUID = 6204222383226990020L;
    private static Log log = Log.getLog(TUser.class);
    public static final TUser dao = new TUser();

    public TUser() {
    }

    public boolean saveOrUpdateUser(TuserBean req) {
        log.info("判断TUser信息是否存在:openid=" + req.getOpenid());
        TUser user = this.queryUserByOpenId(req.getOpenid());
        if (user != null) {
            log.info("更新用户信息:" + req.toString());
            user.set("nickName", req.getNickname());
            user.set("unionid", req.getUnionid());
            user.set("headimgurl", req.getHeadimgurl());
            user.set("country", req.getCountry());
            user.set("city", req.getCity());
            user.set("province", req.getProvince());
            user.set("sex", req.getSex());
            user.set("updateTime", new Date());
            boolean update = user.update();
            log.info("更新用户信息结果:" + update);
            return update;
        } else {
            return this.saveUser(req);
        }
    }

    public boolean saveUser(TuserBean req) {
        log.info("保存用户信息:" + req.toString());
        TUser me = new TUser();
        me.set("openId", req.getOpenid());
        me.set("nickName", req.getNickname());
        me.set("unionid", req.getUnionid());
        me.set("headimgurl", req.getHeadimgurl());
        me.set("country", req.getCountry());
        me.set("city", req.getCity());
        me.set("province", req.getProvince());
        me.set("sex", req.getSex());
        me.set("registerDate", new Date());
        me.set("walletAccount", 0);
        me.set("cardAccount", 0);
        me.set("band", "N");
        me.set("status", "Y");
        boolean save = me.save();
        log.info("保存用户信息结果:" + save);
        return save;
    }

    public TUser queryUserByOpenId(String openId) {
        log.info("根据openid查询用户：" + openId);
        TUser findFirst = (TUser)this.findFirst("select * from tuser where openId = ?", new Object[]{openId});
        if (findFirst.get("real_git_rate") == null){
            findFirst.set("real_git_rate", 1.0D);
        }
        log.info("根据openid查询用户结果：" + findFirst);
        return findFirst;
    }

    public int updateWalletAccount(int walletAccount, String openId) {
        log.info("根据openId更新微信钱包，walletAccount=" + walletAccount + ",openId=" + openId);
        int i = Db.update("update tuser set walletAccount = ?  where openId = ? ", new Object[]{walletAccount, openId});
        log.info("根据openId更新微信钱包结果：" + i);
        return i;
    }

    public int updateWalletAccount(int walletAccount, int chargeMoney, int couponMoney, String openId) {
        log.info("根据openId更新微信钱包，walletAccount=" + walletAccount + ",openId=" + openId);
        double realGiftRate = chargeMoney/(double)(chargeMoney + couponMoney);
        int i = Db.update("update tuser set walletAccount = ? , wallet_real_money = ? , wallet_gift_money = ?, real_git_rate = ? where openId = ? ", new Object[]{walletAccount, chargeMoney, couponMoney, realGiftRate, openId});
        log.info("根据openId更新微信钱包结果：" + i);
        return i;
    }

    public int updateCardNumberByOpendid(String cardNumber, String openId, String tel) {
        log.info("根据openId更新电卡，cardNumber=" + cardNumber + ",openId=" + openId + ",tel=" + tel);
        int i = Db.update("update tuser set cardNumber = ? ,tel = ? ,band = ? where openId = ? ", new Object[]{cardNumber, tel, "Y", openId});
        log.info("根据openId更新电卡结果：" + i);
        return i;
    }

    public int updateTelByOpendid(String openId, String tel) {
        log.info("根据openId更新用户手机号，openId=" + openId + ",tel=" + tel);
        int i = Db.update("update tuser set tel = ? ,band = ? where openId = ? ", new Object[]{tel, "Y", openId});
        log.info("根据openId更新用户手机号码结果：" + i);
        return i;
    }
}

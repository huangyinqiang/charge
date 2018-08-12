package net.inconnnection.charge.cost.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class TUser extends Model<TUser> {
    private static final long serialVersionUID = -4524389446074190114L;
    static Log log = Log.getLog(TUser.class);
    public static final TUser dao = new TUser();

    public TUser() {
    }

    public TUser findUserByOpenid(String openId) {
        return (TUser)dao.findFirst("select * from tuser where openId = ?", new Object[]{openId});
    }

    public int updateWalletAccountByOpenid(String openId, int account) {
        return Db.update("update tuser set walletAccount = ? where openId = ? ", new Object[]{account, openId});
    }
}

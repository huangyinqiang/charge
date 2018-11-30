package net.inconnnection.charge.cost.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class ChargeHistory extends Model<ChargeHistory> {
    private static final long serialVersionUID = -5647371899351248047L;
    static Log log = Log.getLog(ChargeHistory.class);
    public static final ChargeHistory dao = new ChargeHistory();

    public ChargeHistory() {
    }

    public ChargeHistory findFirst(String deviceId,String socketSn,String openId) {
        return (ChargeHistory)dao.findFirst("select * from yc_charge_history where deviceId=? and socketSn=? and openId=? order by id desc limit 0,1", new Object[]{deviceId, socketSn, openId});
    }
}

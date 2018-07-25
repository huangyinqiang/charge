package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import net.inconnection.charge.weixin.bean.ChargeMoneyInfoBean;

public class ChargeMoneyInfo extends Model<ChargeMoneyInfo> {
    private static final long serialVersionUID = 6913913317742830874L;
    static Log log = Log.getLog(ChargeMoneyInfo.class);
    public static final ChargeMoneyInfo dao = new ChargeMoneyInfo();

    public ChargeMoneyInfo() {
    }

    public boolean addChargeMoneyLog(ChargeMoneyInfoBean chargeMoneyInfoBean) {
        log.info("增加充值记录：" + chargeMoneyInfoBean.toString());
        ChargeMoneyInfo info = new ChargeMoneyInfo();
        info.set("openId", chargeMoneyInfoBean.getOpenid());
        info.set("money", chargeMoneyInfoBean.getMoney());
        info.set("amount", chargeMoneyInfoBean.getAmount());
        info.set("createTime", chargeMoneyInfoBean.getCreatetime());
        info.set("chargeType", chargeMoneyInfoBean.getChargetype());
        info.set("card_amount", chargeMoneyInfoBean.getCardAmount());
        info.set("deviceId", chargeMoneyInfoBean.getDeviceid());
        info.set("MD5", chargeMoneyInfoBean.getMd5());
        boolean save = info.save();
        log.info("增加充值记录结果:" + save);
        return save;
    }

    public Page<ChargeMoneyInfo> queryAllByOpenId(String openId, int pageNumber, int pageSize) {
        log.info("分页查询充值信息，openid=" + openId + ",pageNumber=" + pageNumber + ",pageSize=" + pageSize);
        StringBuilder sb = new StringBuilder();
        sb.append("from charge_money_info where ").append("openId = '").append(openId).append("' order by id desc");
        Page<ChargeMoneyInfo> chargelog = dao.paginate(pageNumber, pageSize, "select amount,card_amount,money,createTime,chargeType,id", sb.toString());
        log.info("分页查询充值信息结果：" + chargelog);
        return chargelog;
    }
}


package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import net.inconnection.charge.weixin.bean.OrdersBean;

import java.util.Date;
import java.util.List;

public class Orders extends Model<Orders> {
    private static final long serialVersionUID = 2250011153516428633L;
    private static Log log = Log.getLog(Orders.class);
    public static final Orders dao = new Orders();

    public Orders() {
    }

    public boolean saveOrders(OrdersBean req) {
        log.info("微信支付信息:" + req.toString());
        Orders me = new Orders();
        me.set("appid", req.getAppid());
        me.set("out_trade_no", req.getOutTradeNo());
        me.set("openId", req.getOpenid());
        me.set("mch_id", req.getMchId());
        me.set("cash_fee", req.getCashFee());
        me.set("total_fee", req.getTotalFee());
        me.set("fee_type", req.getFeeType());
        me.set("result_code", req.getResultCode());
        me.set("err_code", req.getErrCode());
        me.set("err_code_des", req.getErrCodeDes());
        me.set("is_subscribe", req.getIsSubscribe());
        me.set("trade_type", req.getTradeType());
        me.set("bank_type", req.getBankType());
        me.set("transaction_id", req.getTransactionId());
        me.set("coupon_id", req.getCouponId());
        me.set("coupon_fee", req.getCouponFee());
        me.set("coupon_count", req.getCouponCount());
        me.set("attach", req.getAttach());
        me.set("time_end", req.getTimeEnd());
        me.set("couresCount", req.getCourescount());
        me.set("couresId", req.getCouresid());
        me.set("url", req.getUrl());
        me.set("create_date", new Date());
        boolean save = me.save();
        log.info("保存微信支付信息结果:" + save);
        return save;
    }

    public List<Orders> selectByTranId(String transactionId) {
        log.info("根据支付定单号是否存在：transactionId=" + transactionId);
        List<Orders> orders = this.find("select id from orders where transaction_id = ?", new Object[]{transactionId});
        log.info("根据支付定单号是否存在结果:" + orders);
        return orders;
    }
}


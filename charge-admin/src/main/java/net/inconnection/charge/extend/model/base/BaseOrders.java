package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrders<M extends BaseOrders<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public M setAppid(String appid) {
		set("appid", appid);
		return (M)this;
	}
	
	public String getAppid() {
		return getStr("appid");
	}

	public M setOutTradeNo(String outTradeNo) {
		set("out_trade_no", outTradeNo);
		return (M)this;
	}
	
	public String getOutTradeNo() {
		return getStr("out_trade_no");
	}

	public M setOpenId(String openId) {
		set("openId", openId);
		return (M)this;
	}
	
	public String getOpenId() {
		return getStr("openId");
	}

	public M setMchId(String mchId) {
		set("mch_id", mchId);
		return (M)this;
	}
	
	public String getMchId() {
		return getStr("mch_id");
	}

	public M setCashFee(Integer cashFee) {
		set("cash_fee", cashFee);
		return (M)this;
	}
	
	public Integer getCashFee() {
		return getInt("cash_fee");
	}

	public M setTotalFee(Integer totalFee) {
		set("total_fee", totalFee);
		return (M)this;
	}
	
	public Integer getTotalFee() {
		return getInt("total_fee");
	}

	public M setFeeType(String feeType) {
		set("fee_type", feeType);
		return (M)this;
	}
	
	public String getFeeType() {
		return getStr("fee_type");
	}

	public M setResultCode(String resultCode) {
		set("result_code", resultCode);
		return (M)this;
	}
	
	public String getResultCode() {
		return getStr("result_code");
	}

	public M setErrCode(String errCode) {
		set("err_code", errCode);
		return (M)this;
	}
	
	public String getErrCode() {
		return getStr("err_code");
	}

	public M setErrCodeDes(String errCodeDes) {
		set("err_code_des", errCodeDes);
		return (M)this;
	}
	
	public String getErrCodeDes() {
		return getStr("err_code_des");
	}

	public M setIsSubscribe(String isSubscribe) {
		set("is_subscribe", isSubscribe);
		return (M)this;
	}
	
	public String getIsSubscribe() {
		return getStr("is_subscribe");
	}

	public M setTradeType(String tradeType) {
		set("trade_type", tradeType);
		return (M)this;
	}
	
	public String getTradeType() {
		return getStr("trade_type");
	}

	public M setBankType(String bankType) {
		set("bank_type", bankType);
		return (M)this;
	}
	
	public String getBankType() {
		return getStr("bank_type");
	}

	public M setTransactionId(String transactionId) {
		set("transaction_id", transactionId);
		return (M)this;
	}
	
	public String getTransactionId() {
		return getStr("transaction_id");
	}

	public M setCouponId(String couponId) {
		set("coupon_id", couponId);
		return (M)this;
	}
	
	public String getCouponId() {
		return getStr("coupon_id");
	}

	public M setCouponFee(Integer couponFee) {
		set("coupon_fee", couponFee);
		return (M)this;
	}
	
	public Integer getCouponFee() {
		return getInt("coupon_fee");
	}

	public M setCouponCount(Integer couponCount) {
		set("coupon_count", couponCount);
		return (M)this;
	}
	
	public Integer getCouponCount() {
		return getInt("coupon_count");
	}

	public M setAttach(String attach) {
		set("attach", attach);
		return (M)this;
	}
	
	public String getAttach() {
		return getStr("attach");
	}

	public M setTimeEnd(String timeEnd) {
		set("time_end", timeEnd);
		return (M)this;
	}
	
	public String getTimeEnd() {
		return getStr("time_end");
	}

	public M setCouresCount(Integer couresCount) {
		set("couresCount", couresCount);
		return (M)this;
	}
	
	public Integer getCouresCount() {
		return getInt("couresCount");
	}

	public M setCouresId(Integer couresId) {
		set("couresId", couresId);
		return (M)this;
	}
	
	public Integer getCouresId() {
		return getInt("couresId");
	}

	public M setUrl(String url) {
		set("url", url);
		return (M)this;
	}
	
	public String getUrl() {
		return getStr("url");
	}

	public M setCreateDate(java.util.Date createDate) {
		set("create_date", createDate);
		return (M)this;
	}
	
	public java.util.Date getCreateDate() {
		return get("create_date");
	}

}

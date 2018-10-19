package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePayAgentHistory<M extends BasePayAgentHistory<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOpenId(java.lang.String openId) {
		set("openId", openId);
		return (M)this;
	}
	
	public java.lang.String getOpenId() {
		return getStr("openId");
	}

	public M setNickName(java.lang.String nickName) {
		set("nickName", nickName);
		return (M)this;
	}
	
	public java.lang.String getNickName() {
		return getStr("nickName");
	}

	public M setTel(java.lang.String tel) {
		set("tel", tel);
		return (M)this;
	}
	
	public java.lang.String getTel() {
		return getStr("tel");
	}

	public M setCompanyId(java.lang.Long companyId) {
		set("company_id", companyId);
		return (M)this;
	}
	
	public java.lang.Long getCompanyId() {
		return getLong("company_id");
	}

	public M setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return (M)this;
	}
	
	public java.util.Date getStartTime() {
		return get("start_time");
	}

	public M setEndTime(java.util.Date endTime) {
		set("end_time", endTime);
		return (M)this;
	}
	
	public java.util.Date getEndTime() {
		return get("end_time");
	}

	public M setRechargeMoney(java.lang.Integer rechargeMoney) {
		set("recharge_money", rechargeMoney);
		return (M)this;
	}
	
	public java.lang.Integer getRechargeMoney() {
		return getInt("recharge_money");
	}

	public M setRechargeMoneyReal(java.lang.Integer rechargeMoneyReal) {
		set("recharge_money_real", rechargeMoneyReal);
		return (M)this;
	}
	
	public java.lang.Integer getRechargeMoneyReal() {
		return getInt("recharge_money_real");
	}

	public M setChargeMoney(java.lang.Integer chargeMoney) {
		set("charge_money", chargeMoney);
		return (M)this;
	}
	
	public java.lang.Integer getChargeMoney() {
		return getInt("charge_money");
	}

	public M setChargeMoneyReal(java.lang.Integer chargeMoneyReal) {
		set("charge_money_real", chargeMoneyReal);
		return (M)this;
	}
	
	public java.lang.Integer getChargeMoneyReal() {
		return getInt("charge_money_real");
	}

	public M setTempMoney(java.lang.Integer tempMoney) {
		set("temp_money", tempMoney);
		return (M)this;
	}
	
	public java.lang.Integer getTempMoney() {
		return getInt("temp_money");
	}

	public M setBalanceRate(java.lang.Double balanceRate) {
		set("balance_rate", balanceRate);
		return (M)this;
	}
	
	public java.lang.Double getBalanceRate() {
		return getDouble("balance_rate");
	}

	public M setPaySum(java.lang.Integer paySum) {
		set("pay_sum", paySum);
		return (M)this;
	}
	
	public java.lang.Integer getPaySum() {
		return getInt("pay_sum");
	}

	public M setPayTime(java.util.Date payTime) {
		set("pay_time", payTime);
		return (M)this;
	}
	
	public java.util.Date getPayTime() {
		return get("pay_time");
	}

	public M setLastSurplus(java.lang.Integer lastSurplus) {
		set("last_surplus", lastSurplus);
		return (M)this;
	}
	
	public java.lang.Integer getLastSurplus() {
		return getInt("last_surplus");
	}

	public M setSurplus(java.lang.Integer surplus) {
		set("surplus", surplus);
		return (M)this;
	}
	
	public java.lang.Integer getSurplus() {
		return getInt("surplus");
	}

	public M setPayType(java.lang.Integer payType) {
		set("pay_type", payType);
		return (M)this;
	}
	
	public java.lang.Integer getPayType() {
		return getInt("pay_type");
	}

	public M setWeixinAccount(java.lang.String weixinAccount) {
		set("weixin_account", weixinAccount);
		return (M)this;
	}
	
	public java.lang.String getWeixinAccount() {
		return getStr("weixin_account");
	}

	public M setBankAccount(java.lang.String bankAccount) {
		set("bank_account", bankAccount);
		return (M)this;
	}
	
	public java.lang.String getBankAccount() {
		return getStr("bank_account");
	}

	public M setBankName(java.lang.String bankName) {
		set("bank_name", bankName);
		return (M)this;
	}
	
	public java.lang.String getBankName() {
		return getStr("bank_name");
	}

	public M setOperatorName(java.lang.String operatorName) {
		set("operator_name", operatorName);
		return (M)this;
	}
	
	public java.lang.String getOperatorName() {
		return getStr("operator_name");
	}

	public M setOperatorTime(java.util.Date operatorTime) {
		set("operator_time", operatorTime);
		return (M)this;
	}
	
	public java.util.Date getOperatorTime() {
		return get("operator_time");
	}

}
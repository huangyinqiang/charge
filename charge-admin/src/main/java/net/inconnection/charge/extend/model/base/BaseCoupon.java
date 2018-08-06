package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCoupon<M extends BaseCoupon<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setVirtualMoney(java.lang.Integer virtualMoney) {
		set("virtual_money", virtualMoney);
		return (M)this;
	}
	
	public java.lang.Integer getVirtualMoney() {
		return getInt("virtual_money");
	}

	public M setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
		return (M)this;
	}
	
	public java.util.Date getStartTime() {
		return get("start_time");
	}

	public M setExpiryTime(java.util.Date expiryTime) {
		set("expiry_time", expiryTime);
		return (M)this;
	}
	
	public java.util.Date getExpiryTime() {
		return get("expiry_time");
	}

	public M setSum(java.lang.Integer sum) {
		set("sum", sum);
		return (M)this;
	}
	
	public java.lang.Integer getSum() {
		return getInt("sum");
	}

	public M setBalanceRate(java.lang.Double balanceRate) {
		set("balance_rate", balanceRate);
		return (M)this;
	}
	
	public java.lang.Double getBalanceRate() {
		return getDouble("balance_rate");
	}

	public M setCondition(java.lang.Integer condition) {
		set("condition", condition);
		return (M)this;
	}
	
	public java.lang.Integer getCondition() {
		return getInt("condition");
	}

}
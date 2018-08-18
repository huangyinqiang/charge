package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCompanyActivity<M extends BaseCompanyActivity<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCompanyId(java.lang.Long companyId) {
		set("company_id", companyId);
		return (M)this;
	}
	
	public java.lang.Long getCompanyId() {
		return getLong("company_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}
	
	public java.lang.String getType() {
		return getStr("type");
	}

	public M setMoney(java.lang.Integer money) {
		set("money", money);
		return (M)this;
	}
	
	public java.lang.Integer getMoney() {
		return getInt("money");
	}

	public M setChargeNum(java.lang.Integer chargeNum) {
		set("chargeNum", chargeNum);
		return (M)this;
	}
	
	public java.lang.Integer getChargeNum() {
		return getInt("chargeNum");
	}

	public M setCoupon(java.lang.Integer coupon) {
		set("coupon", coupon);
		return (M)this;
	}
	
	public java.lang.Integer getCoupon() {
		return getInt("coupon");
	}

	public M setStatus(java.lang.String status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.String getStatus() {
		return getStr("status");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
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

	public M setProvince(java.lang.String province) {
		set("province", province);
		return (M)this;
	}
	
	public java.lang.String getProvince() {
		return getStr("province");
	}

	public M setCity(java.lang.String city) {
		set("city", city);
		return (M)this;
	}
	
	public java.lang.String getCity() {
		return getStr("city");
	}

	public M setLocation(java.lang.String location) {
		set("location", location);
		return (M)this;
	}
	
	public java.lang.String getLocation() {
		return getStr("location");
	}

	public M setActNum(java.lang.Integer actNum) {
		set("actNum", actNum);
		return (M)this;
	}
	
	public java.lang.Integer getActNum() {
		return getInt("actNum");
	}

}

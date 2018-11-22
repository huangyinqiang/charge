package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGiftRefund<M extends BaseGiftRefund<M>> extends Model<M> implements IBean {

	public M setId(Long id) {
		set("id", id);
		return (M)this;
	}

	public Long getId() {
		return getLong("id");
	}

	public M setOpenId(String openId) {
		set("openId", openId);
		return (M)this;
	}

	public String getOpenId() {
		return getStr("openId");
	}

	public M setDeviceId(Long deviceId) {
		set("device_Id", deviceId);
		return (M)this;
	}

	public Long getDeviceId() {
		return getLong("device_Id");
	}

	public M setCompanyId(Integer companyId) {
		set("company_Id", companyId);
		return (M)this;
	}

	public Integer getCompanyId() {
		return getInt("company_Id");
	}

	public M setType(Integer type) {
		set("type", type);
		return (M)this;
	}

	public Integer getType() {
		return getInt("type");
	}

	public M setChargeId(Long chargeId) {
		set("charge_Id", chargeId);
		return (M)this;
	}

	public Long getChargeId() {
		return getLong("charge_Id");
	}

	public M setRechargeId(Long rechargeId) {
		set("recharge_Id", rechargeId);
		return (M)this;
	}

	public Long getRechargeId() {
		return getLong("recharge_Id");
	}

	public M setMoney(Long money) {
		set("money", money);
		return (M)this;
	}

	public Long getMoney() {
		return getLong("money");
	}

    public M setLast(Long last) {
        set("last", last);
        return (M)this;
    }

    public Long getLast() {
        return getLong("last");
    }

	public M setCreateDate(java.util.Date createDate) {
		set("create_date", createDate);
		return (M)this;
	}
	
	public java.util.Date getCreateDate() {
		return get("create_date");
	}

}
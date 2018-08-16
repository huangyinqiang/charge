package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseChargeprice<M extends BaseChargeprice<M>> extends Model<M> implements IBean {

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

	public M setTowHoursPrice(java.lang.Integer towHoursPrice) {
		set("tow_hours_price", towHoursPrice);
		return (M)this;
	}
	
	public java.lang.Integer getTowHoursPrice() {
		return getInt("tow_hours_price");
	}

	public M setFourHoursPrice(java.lang.Integer fourHoursPrice) {
		set("four_hours_price", fourHoursPrice);
		return (M)this;
	}
	
	public java.lang.Integer getFourHoursPrice() {
		return getInt("four_hours_price");
	}

	public M setEightHoursPrice(java.lang.Integer eightHoursPrice) {
		set("eight_hours_price", eightHoursPrice);
		return (M)this;
	}
	
	public java.lang.Integer getEightHoursPrice() {
		return getInt("eight_hours_price");
	}

	public M setTwelveHoursPrice(java.lang.Integer twelveHoursPrice) {
		set("twelve_hours_price", twelveHoursPrice);
		return (M)this;
	}
	
	public java.lang.Integer getTwelveHoursPrice() {
		return getInt("twelve_hours_price");
	}

	public M setTowHoursMemPrice(java.lang.Integer towHoursMemPrice) {
		set("tow_hours_mem_price", towHoursMemPrice);
		return (M)this;
	}
	
	public java.lang.Integer getTowHoursMemPrice() {
		return getInt("tow_hours_mem_price");
	}

	public M setFourHoursMemPrice(java.lang.Integer fourHoursMemPrice) {
		set("four_hours_mem_price", fourHoursMemPrice);
		return (M)this;
	}
	
	public java.lang.Integer getFourHoursMemPrice() {
		return getInt("four_hours_mem_price");
	}

	public M setEightHoursMemPrice(java.lang.Integer eightHoursMemPrice) {
		set("eight_hours_mem_price", eightHoursMemPrice);
		return (M)this;
	}
	
	public java.lang.Integer getEightHoursMemPrice() {
		return getInt("eight_hours_mem_price");
	}

	public M setTwelveHoursMemPrice(java.lang.Integer twelveHoursMemPrice) {
		set("twelve_hours_mem_price", twelveHoursMemPrice);
		return (M)this;
	}
	
	public java.lang.Integer getTwelveHoursMemPrice() {
		return getInt("twelve_hours_mem_price");
	}

	public M setAutoPrice(java.lang.Integer autoPrice) {
		set("auto_price", autoPrice);
		return (M)this;
	}
	
	public java.lang.Integer getAutoPrice() {
		return getInt("auto_price");
	}

	public M setUpdatetime(java.util.Date updatetime) {
		set("updatetime", updatetime);
		return (M)this;
	}
	
	public java.util.Date getUpdatetime() {
		return get("updatetime");
	}

	public M setCreatetime(java.util.Date createtime) {
		set("createtime", createtime);
		return (M)this;
	}
	
	public java.util.Date getCreatetime() {
		return get("createtime");
	}

	public M setPowerA1(java.lang.String powerA1) {
		set("power_a1", powerA1);
		return (M)this;
	}
	
	public java.lang.String getPowerA1() {
		return getStr("power_a1");
	}

	public M setPowerA2(java.lang.String powerA2) {
		set("power_a2", powerA2);
		return (M)this;
	}
	
	public java.lang.String getPowerA2() {
		return getStr("power_a2");
	}

	public M setPowerA3(java.lang.String powerA3) {
		set("power_a3", powerA3);
		return (M)this;
	}
	
	public java.lang.String getPowerA3() {
		return getStr("power_a3");
	}

	public M setPowerA4(java.lang.String powerA4) {
		set("power_a4", powerA4);
		return (M)this;
	}
	
	public java.lang.String getPowerA4() {
		return getStr("power_a4");
	}

	public M setPowerA5(java.lang.String powerA5) {
		set("power_a5", powerA5);
		return (M)this;
	}
	
	public java.lang.String getPowerA5() {
		return getStr("power_a5");
	}

	public M setPowerA6(java.lang.String powerA6) {
		set("power_a6", powerA6);
		return (M)this;
	}
	
	public java.lang.String getPowerA6() {
		return getStr("power_a6");
	}

	public M setPowerA7(java.lang.String powerA7) {
		set("power_a7", powerA7);
		return (M)this;
	}
	
	public java.lang.String getPowerA7() {
		return getStr("power_a7");
	}

	public M setFree(java.lang.String free) {
		set("free", free);
		return (M)this;
	}
	
	public java.lang.String getFree() {
		return getStr("free");
	}

}

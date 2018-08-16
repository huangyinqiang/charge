package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseChargePile<M extends BaseChargePile<M>> extends Model<M> implements IBean {

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

	public M setDetailLocation(java.lang.String detailLocation) {
		set("detail_location", detailLocation);
		return (M)this;
	}
	
	public java.lang.String getDetailLocation() {
		return getStr("detail_location");
	}

	public M setLat(java.lang.Double lat) {
		set("lat", lat);
		return (M)this;
	}
	
	public java.lang.Double getLat() {
		return getDouble("lat");
	}

	public M setLng(java.lang.Double lng) {
		set("lng", lng);
		return (M)this;
	}
	
	public java.lang.Double getLng() {
		return getDouble("lng");
	}

	public M setPowerMax(java.lang.Long powerMax) {
		set("power_max", powerMax);
		return (M)this;
	}
	
	public java.lang.Long getPowerMax() {
		return getLong("power_max");
	}

	public M setSocketSum(java.lang.Integer socketSum) {
		set("socket_sum", socketSum);
		return (M)this;
	}
	
	public java.lang.Integer getSocketSum() {
		return getInt("socket_sum");
	}

	public M setCompanyId(java.lang.Long companyId) {
		set("company_id", companyId);
		return (M)this;
	}
	
	public java.lang.Long getCompanyId() {
		return getLong("company_id");
	}

	public M setTotalIntensity(java.lang.Long totalIntensity) {
		set("total_intensity", totalIntensity);
		return (M)this;
	}
	
	public java.lang.Long getTotalIntensity() {
		return getLong("total_intensity");
	}

	public M setTotalVoltage(java.lang.Long totalVoltage) {
		set("total_voltage", totalVoltage);
		return (M)this;
	}
	
	public java.lang.Long getTotalVoltage() {
		return getLong("total_voltage");
	}

	public M setIsOnline(java.lang.Boolean isOnline) {
		set("is_online", isOnline);
		return (M)this;
	}
	
	public java.lang.Boolean getIsOnline() {
		return get("is_online");
	}

	public M setOnlineDate(java.util.Date onlineDate) {
		set("online_date", onlineDate);
		return (M)this;
	}
	
	public java.util.Date getOnlineDate() {
		return get("online_date");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}
	
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setBatVol(java.lang.Integer batVol) {
		set("bat_vol", batVol);
		return (M)this;
	}
	
	public java.lang.Integer getBatVol() {
		return getInt("bat_vol");
	}

	public M setControllerVol(java.lang.Integer controllerVol) {
		set("controller_vol", controllerVol);
		return (M)this;
	}
	
	public java.lang.Integer getControllerVol() {
		return getInt("controller_vol");
	}

	public M setPowerTotal(java.lang.Long powerTotal) {
		set("power_total", powerTotal);
		return (M)this;
	}
	
	public java.lang.Long getPowerTotal() {
		return getLong("power_total");
	}

}

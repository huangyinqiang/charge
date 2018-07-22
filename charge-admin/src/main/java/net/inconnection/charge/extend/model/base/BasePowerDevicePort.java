package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePowerDevicePort<M extends BasePowerDevicePort<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public M setDeviceid(String deviceid) {
		set("deviceid", deviceid);
		return (M)this;
	}
	
	public String getDeviceid() {
		return getStr("deviceid");
	}

	public M setPort(String port) {
		set("port", port);
		return (M)this;
	}
	
	public String getPort() {
		return getStr("port");
	}

	public M setOpenid(String openid) {
		set("openid", openid);
		return (M)this;
	}
	
	public String getOpenid() {
		return getStr("openid");
	}

	public M setStatus(String status) {
		set("status", status);
		return (M)this;
	}
	
	public String getStatus() {
		return getStr("status");
	}

	public M setUpdatedate(java.util.Date updatedate) {
		set("updatedate", updatedate);
		return (M)this;
	}
	
	public java.util.Date getUpdatedate() {
		return get("updatedate");
	}

}
package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceLocation<M extends BaseDeviceLocation<M>> extends Model<M> implements IBean {

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

	public M setDevicename(String devicename) {
		set("devicename", devicename);
		return (M)this;
	}
	
	public String getDevicename() {
		return getStr("devicename");
	}

	public M setLac(String lac) {
		set("lac", lac);
		return (M)this;
	}
	
	public String getLac() {
		return getStr("lac");
	}

	public M setCid(String cid) {
		set("cid", cid);
		return (M)this;
	}
	
	public String getCid() {
		return getStr("cid");
	}

	public M setLng(String lng) {
		set("lng", lng);
		return (M)this;
	}
	
	public String getLng() {
		return getStr("lng");
	}

	public M setLat(String lat) {
		set("lat", lat);
		return (M)this;
	}
	
	public String getLat() {
		return getStr("lat");
	}

	public M setOlng(String olng) {
		set("olng", olng);
		return (M)this;
	}
	
	public String getOlng() {
		return getStr("olng");
	}

	public M setOlat(String olat) {
		set("olat", olat);
		return (M)this;
	}
	
	public String getOlat() {
		return getStr("olat");
	}

	public M setJointime(java.util.Date jointime) {
		set("jointime", jointime);
		return (M)this;
	}
	
	public java.util.Date getJointime() {
		return get("jointime");
	}

	public M setUpdatetime(java.util.Date updatetime) {
		set("updatetime", updatetime);
		return (M)this;
	}
	
	public java.util.Date getUpdatetime() {
		return get("updatetime");
	}

	public M setUpdatenum(Integer updatenum) {
		set("updatenum", updatenum);
		return (M)this;
	}
	
	public Integer getUpdatenum() {
		return getInt("updatenum");
	}

	public M setRemark(String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public String getRemark() {
		return getStr("remark");
	}

}

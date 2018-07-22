package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDevicemsg<M extends BaseDevicemsg<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public M setType(Integer type) {
		set("type", type);
		return (M)this;
	}
	
	public Integer getType() {
		return getInt("type");
	}

	public M setDeviceid(String deviceid) {
		set("deviceid", deviceid);
		return (M)this;
	}
	
	public String getDeviceid() {
		return getStr("deviceid");
	}

	public M setContent(String content) {
		set("content", content);
		return (M)this;
	}
	
	public String getContent() {
		return getStr("content");
	}

	public M setJointime(java.util.Date jointime) {
		set("jointime", jointime);
		return (M)this;
	}
	
	public java.util.Date getJointime() {
		return get("jointime");
	}

}

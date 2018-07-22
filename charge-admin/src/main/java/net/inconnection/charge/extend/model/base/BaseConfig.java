package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseConfig<M extends BaseConfig<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public M setGid(Integer gid) {
		set("gid", gid);
		return (M)this;
	}
	
	public Integer getGid() {
		return getInt("gid");
	}

	public M setGname(String gname) {
		set("gname", gname);
		return (M)this;
	}
	
	public String getGname() {
		return getStr("gname");
	}

	public M setKeya1value(String keya1value) {
		set("keya1value", keya1value);
		return (M)this;
	}
	
	public String getKeya1value() {
		return getStr("keya1value");
	}

	public M setKeya2value(String keya2value) {
		set("keya2value", keya2value);
		return (M)this;
	}
	
	public String getKeya2value() {
		return getStr("keya2value");
	}

	public M setKeyb1value(String keyb1value) {
		set("keyb1value", keyb1value);
		return (M)this;
	}
	
	public String getKeyb1value() {
		return getStr("keyb1value");
	}

	public M setKeyb2value(String keyb2value) {
		set("keyb2value", keyb2value);
		return (M)this;
	}
	
	public String getKeyb2value() {
		return getStr("keyb2value");
	}

	public M setControlvalue(String controlvalue) {
		set("controlvalue", controlvalue);
		return (M)this;
	}
	
	public String getControlvalue() {
		return getStr("controlvalue");
	}

	public M setRemark(String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public String getRemark() {
		return getStr("remark");
	}

}

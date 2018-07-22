package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLostcard<M extends BaseLostcard<M>> extends Model<M> implements IBean {

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

	public M setUname(String uname) {
		set("uname", uname);
		return (M)this;
	}
	
	public String getUname() {
		return getStr("uname");
	}

	public M setCardnum(String cardnum) {
		set("cardnum", cardnum);
		return (M)this;
	}
	
	public String getCardnum() {
		return getStr("cardnum");
	}

	public M setLosttime(java.util.Date losttime) {
		set("losttime", losttime);
		return (M)this;
	}
	
	public java.util.Date getLosttime() {
		return get("losttime");
	}

	public M setBalance(String balance) {
		set("balance", balance);
		return (M)this;
	}
	
	public String getBalance() {
		return getStr("balance");
	}

}
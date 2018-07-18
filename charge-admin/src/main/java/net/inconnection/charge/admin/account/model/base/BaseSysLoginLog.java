package net.inconnection.charge.admin.account.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * 登陆日志
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysLoginLog<M extends BaseSysLoginLog<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}

	public Integer getId() {
		return get("id");
	}

	public M setUsername(String username) {
		set("username", username);
		return (M)this;
	}

	public String getUsername() {
		return get("username");
	}

	public M setSessionId(String sessionId) {
		set("session_id", sessionId);
		return (M)this;
	}

	public String getSessionId() {
		return get("session_id");
	}

	public M setIp(String ip) {
		set("ip", ip);
		return (M)this;
	}

	public String getIp() {
		return get("ip");
	}

	public M setIsSuccess(Integer isSuccess) {
		set("is_success", isSuccess);
		return (M)this;
	}

	public Integer getIsSuccess() {
		return get("is_success");
	}

	public M setRemark(String remark) {
		set("remark", remark);
		return (M)this;
	}

	public String getRemark() {
		return get("remark");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}

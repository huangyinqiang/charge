package net.inconnection.charge.extend.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStockHistoryLog<M extends BaseStockHistoryLog<M>> extends Model<M> implements IBean {

	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}

	public M setDt(java.util.Date dt) {
		set("dt", dt);
		return (M)this;
	}
	
	public java.util.Date getDt() {
		return get("dt");
	}

	public M setCode(String code) {
		set("code", code);
		return (M)this;
	}
	
	public String getCode() {
		return getStr("code");
	}

	public M setName(String name) {
		set("name", name);
		return (M)this;
	}
	
	public String getName() {
		return getStr("name");
	}

	public M setClosingPrice(Double closingPrice) {
		set("closing_price", closingPrice);
		return (M)this;
	}
	
	public Double getClosingPrice() {
		return getDouble("closing_price");
	}

	public M setTopPrice(Double topPrice) {
		set("top_price", topPrice);
		return (M)this;
	}
	
	public Double getTopPrice() {
		return getDouble("top_price");
	}

	public M setMinimumPrice(Double minimumPrice) {
		set("minimum_price", minimumPrice);
		return (M)this;
	}
	
	public Double getMinimumPrice() {
		return getDouble("minimum_price");
	}

	public M setOpeningPrice(Double openingPrice) {
		set("opening_price", openingPrice);
		return (M)this;
	}
	
	public Double getOpeningPrice() {
		return getDouble("opening_price");
	}

	public M setPre(Double pre) {
		set("pre", pre);
		return (M)this;
	}
	
	public Double getPre() {
		return getDouble("pre");
	}

	public M setChangeAmount(Double changeAmount) {
		set("change_amount", changeAmount);
		return (M)this;
	}
	
	public Double getChangeAmount() {
		return getDouble("change_amount");
	}

	public M setChangeRatio(Double changeRatio) {
		set("change_ratio", changeRatio);
		return (M)this;
	}
	
	public Double getChangeRatio() {
		return getDouble("change_ratio");
	}

	public M setTurnoverVolume(Long turnoverVolume) {
		set("turnover_volume", turnoverVolume);
		return (M)this;
	}
	
	public Long getTurnoverVolume() {
		return getLong("turnover_volume");
	}

	public M setTurnoverMoney(Long turnoverMoney) {
		set("turnover_money", turnoverMoney);
		return (M)this;
	}
	
	public Long getTurnoverMoney() {
		return getLong("turnover_money");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}

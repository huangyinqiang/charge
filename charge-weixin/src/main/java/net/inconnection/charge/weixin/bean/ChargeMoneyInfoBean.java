package net.inconnection.charge.weixin.bean;

import java.util.Date;

public class ChargeMoneyInfoBean extends SuperReq {
    private static final long serialVersionUID = -3342937533797756118L;
    private Integer id;
    private String openid;
    private Integer money;
    private Integer amount;
    private Date createtime;
    private String chargetype;
    private Integer cardAmount;
    private String deviceid;
    private String md5;
    private String payToAgentStatus;
    private Date payToAgentTime;

    public ChargeMoneyInfoBean() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getChargetype() {
        return this.chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype == null ? null : chargetype.trim();
    }

    public Integer getCardAmount() {
        return this.cardAmount;
    }

    public void setCardAmount(Integer cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getPayToAgentStatus() {
        return this.payToAgentStatus;
    }

    public void setPayToAgentStatus(String payToAgentStatus) {
        this.payToAgentStatus = payToAgentStatus == null ? null : payToAgentStatus.trim();
    }

    public Date getPayToAgentTime() {
        return this.payToAgentTime;
    }

    public void setPayToAgentTime(Date payToAgentTime) {
        this.payToAgentTime = payToAgentTime;
    }
}


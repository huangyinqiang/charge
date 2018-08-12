package net.inconnection.charge.push.model;

import java.util.Date;

public class WeiXin {
    private String area;
    private String deviceId;
    private String channelNum;
    private String message;
    private String openId;
    private String title;
    private String type;
    private String money;
    private int walletAccount;
    private String chargeTime;
    private Date operStartTime;

    public WeiXin() {
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getChannelNum() {
        return this.channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getWalletAccount() {
        return this.walletAccount;
    }

    public void setWalletAccount(int walletAccount) {
        this.walletAccount = walletAccount;
    }

    public String getChargeTime() {
        return this.chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Date getOperStartTime() {
        return this.operStartTime;
    }

    public void setOperStartTime(Date operStartTime) {
        this.operStartTime = operStartTime;
    }
}


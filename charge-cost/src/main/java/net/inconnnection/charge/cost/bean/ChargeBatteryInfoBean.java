package net.inconnnection.charge.cost.bean;

import java.util.Date;

public class ChargeBatteryInfoBean {
    private String openId;
    private String deviceId;
    private String devicePort;
    private String money;
    private Date startTime;
    private String feeStatus;
    private String chargeTime;
    private String realChargeTime;
    private String type;
    private String status;

    public ChargeBatteryInfoBean() {
    }

    public String getRealChargeTime() {
        return this.realChargeTime;
    }

    public void setRealChargeTime(String realChargeTime) {
        this.realChargeTime = realChargeTime;
    }

    public String getChargeTime() {
        return this.chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePort() {
        return this.devicePort;
    }

    public void setDevicePort(String devicePort) {
        this.devicePort = devicePort;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getFeeStatus() {
        return this.feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String toString() {
        return "ChargeBatteryInfoBean [openId=" + this.openId + ", deviceId=" + this.deviceId + ", devicePort=" + this.devicePort + ", money=" + this.money + ", startTime=" + this.startTime + ", feeStatus=" + this.feeStatus + "]";
    }
}

package net.inconnection.charge.weixin.bean;

import java.util.Date;

public class QrMatchDevice extends SuperReq {
    private static final long serialVersionUID = 1365139409100877506L;
    private Integer id;
    private String qrNum;
    private String matchNum;
    private String area;
    private Integer towHoursPrice;
    private Integer fourHoursPrice;
    private Integer eightHoursPrice;
    private Integer twelveHoursPrice;
    private Integer towHoursMemPrice;
    private Integer fourHoursMemPrice;
    private Integer eightHoursMemPrice;
    private Integer twelveHoursMemPrice;
    private Integer autoPrice;
    private String status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String payToAgentStatus;
    private String payToAgentOpenid;
    private String payToAgentName;
    private String highPower;
    private Integer activityId;

    public QrMatchDevice() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrNum() {
        return this.qrNum;
    }

    public void setQrNum(String qrNum) {
        this.qrNum = qrNum == null ? null : qrNum.trim();
    }

    public String getMatchNum() {
        return this.matchNum;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum == null ? null : matchNum.trim();
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getTowHoursPrice() {
        return this.towHoursPrice;
    }

    public void setTowHoursPrice(Integer towHoursPrice) {
        this.towHoursPrice = towHoursPrice;
    }

    public Integer getFourHoursPrice() {
        return this.fourHoursPrice;
    }

    public void setFourHoursPrice(Integer fourHoursPrice) {
        this.fourHoursPrice = fourHoursPrice;
    }

    public Integer getEightHoursPrice() {
        return this.eightHoursPrice;
    }

    public void setEightHoursPrice(Integer eightHoursPrice) {
        this.eightHoursPrice = eightHoursPrice;
    }

    public Integer getTwelveHoursPrice() {
        return this.twelveHoursPrice;
    }

    public void setTwelveHoursPrice(Integer twelveHoursPrice) {
        this.twelveHoursPrice = twelveHoursPrice;
    }

    public Integer getTowHoursMemPrice() {
        return this.towHoursMemPrice;
    }

    public void setTowHoursMemPrice(Integer towHoursMemPrice) {
        this.towHoursMemPrice = towHoursMemPrice;
    }

    public Integer getFourHoursMemPrice() {
        return this.fourHoursMemPrice;
    }

    public void setFourHoursMemPrice(Integer fourHoursMemPrice) {
        this.fourHoursMemPrice = fourHoursMemPrice;
    }

    public Integer getEightHoursMemPrice() {
        return this.eightHoursMemPrice;
    }

    public void setEightHoursMemPrice(Integer eightHoursMemPrice) {
        this.eightHoursMemPrice = eightHoursMemPrice;
    }

    public Integer getTwelveHoursMemPrice() {
        return this.twelveHoursMemPrice;
    }

    public void setTwelveHoursMemPrice(Integer twelveHoursMemPrice) {
        this.twelveHoursMemPrice = twelveHoursMemPrice;
    }

    public Integer getAutoPrice() {
        return this.autoPrice;
    }

    public void setAutoPrice(Integer autoPrice) {
        this.autoPrice = autoPrice;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayToAgentStatus() {
        return this.payToAgentStatus;
    }

    public void setPayToAgentStatus(String payToAgentStatus) {
        this.payToAgentStatus = payToAgentStatus == null ? null : payToAgentStatus.trim();
    }

    public String getPayToAgentOpenid() {
        return this.payToAgentOpenid;
    }

    public void setPayToAgentOpenid(String payToAgentOpenid) {
        this.payToAgentOpenid = payToAgentOpenid == null ? null : payToAgentOpenid.trim();
    }

    public String getPayToAgentName() {
        return this.payToAgentName;
    }

    public void setPayToAgentName(String payToAgentName) {
        this.payToAgentName = payToAgentName == null ? null : payToAgentName.trim();
    }

    public String getHighPower() {
        return this.highPower;
    }

    public void setHighPower(String highPower) {
        this.highPower = highPower == null ? null : highPower.trim();
    }

    public Integer getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}


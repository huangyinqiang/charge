package net.inconnection.charge.log.bean;

import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class CardLog extends Model<CardLog> {
    private Integer id;
    private String deviceId;
    private String phyicalId;
    private String cardCode;
    private String balance;
    private Date joinDate;
    private String name;
    private String ganme;
    private String area;
    private String phone;
    private String channelNum;
    private Integer gid;

    public CardLog() {
    }

    public Integer getGid() {
        return this.gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGanme() {
        return this.ganme;
    }

    public void setGanme(String ganme) {
        this.ganme = ganme;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannelNum() {
        return this.channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhyicalId() {
        return this.phyicalId;
    }

    public void setPhyicalId(String phyicalId) {
        this.phyicalId = phyicalId;
    }

    public String getCardCode() {
        return this.cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getJoinDate() {
        return this.joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}


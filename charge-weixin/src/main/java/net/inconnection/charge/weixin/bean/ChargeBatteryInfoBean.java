package net.inconnection.charge.weixin.bean;

import java.util.Date;

public class ChargeBatteryInfoBean extends SuperReq {
    private static final long serialVersionUID = -4515553839185651540L;
    private Integer id;
    private String openid;
    private String deviceid;
    private String deviceport;
    private Date operstarttime;
    private Date starttime;
    private Date endtime;
    private String opertype;
    private String serverresultcode;
    private String serverresultdesc;
    private String charge;
    private Integer walletaccount;
    private String status;
    private String feestatus;
    private String chargetime;
    private Date createdate;
    private String md5;
    private String payToAgentStatus;
    private String autoCharge;
    private Date payToAgentTime;

    public ChargeBatteryInfoBean() {
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

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getDeviceport() {
        return this.deviceport;
    }

    public void setDeviceport(String deviceport) {
        this.deviceport = deviceport == null ? null : deviceport.trim();
    }

    public Date getOperstarttime() {
        return this.operstarttime;
    }

    public void setOperstarttime(Date operstarttime) {
        this.operstarttime = operstarttime;
    }

    public Date getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getOpertype() {
        return this.opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype == null ? null : opertype.trim();
    }

    public String getServerresultcode() {
        return this.serverresultcode;
    }

    public void setServerresultcode(String serverresultcode) {
        this.serverresultcode = serverresultcode == null ? null : serverresultcode.trim();
    }

    public String getServerresultdesc() {
        return this.serverresultdesc;
    }

    public void setServerresultdesc(String serverresultdesc) {
        this.serverresultdesc = serverresultdesc == null ? null : serverresultdesc.trim();
    }

    public String getCharge() {
        return this.charge;
    }

    public void setCharge(String charge) {
        this.charge = charge == null ? null : charge.trim();
    }

    public Integer getWalletaccount() {
        return this.walletaccount;
    }

    public void setWalletaccount(Integer walletaccount) {
        this.walletaccount = walletaccount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFeestatus() {
        return this.feestatus;
    }

    public void setFeestatus(String feestatus) {
        this.feestatus = feestatus == null ? null : feestatus.trim();
    }

    public String getChargetime() {
        return this.chargetime;
    }

    public void setChargetime(String chargetime) {
        this.chargetime = chargetime == null ? null : chargetime.trim();
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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

    public String getAutoCharge() {
        return this.autoCharge;
    }

    public void setAutoCharge(String autoCharge) {
        this.autoCharge = autoCharge == null ? null : autoCharge.trim();
    }

    public Date getPayToAgentTime() {
        return this.payToAgentTime;
    }

    public void setPayToAgentTime(Date payToAgentTime) {
        this.payToAgentTime = payToAgentTime;
    }
}


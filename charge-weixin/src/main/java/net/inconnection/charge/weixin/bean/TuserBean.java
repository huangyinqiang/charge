package net.inconnection.charge.weixin.bean;

import java.util.Date;

public class TuserBean extends SuperReq {
    private static final long serialVersionUID = -902894723746424093L;
    private Integer id;
    private String openid;
    private String nickname;
    private String headimgurl;
    private Integer sex;
    private String unionid;
    private String privilege;
    private String city;
    private String country;
    private String province;
    private String tel;
    private Date registerdate;
    private Integer level;
    private String cardnumber;
    private Integer cardaccount;
    private String band;
    private String status;
    private Integer walletaccount;
    private Date updatetime;

    public TuserBean() {
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

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadimgurl() {
        return this.headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege == null ? null : privilege.trim();
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Date getRegisterdate() {
        return this.registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCardnumber() {
        return this.cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public Integer getCardaccount() {
        return this.cardaccount;
    }

    public void setCardaccount(Integer cardaccount) {
        this.cardaccount = cardaccount;
    }

    public String getBand() {
        return this.band;
    }

    public void setBand(String band) {
        this.band = band == null ? null : band.trim();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getWalletaccount() {
        return this.walletaccount;
    }

    public void setWalletaccount(Integer walletaccount) {
        this.walletaccount = walletaccount;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}

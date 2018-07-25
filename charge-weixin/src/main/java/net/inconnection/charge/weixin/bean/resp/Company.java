package net.inconnection.charge.weixin.bean.resp;

public class Company {
    public String tel;
    public String shortName;
    public String company;
    public String wechat;
    public String qrCode;

    public Company() {
    }

    public String getWechat() {
        return this.wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String toString() {
        return "Company [tel=" + this.tel + ", shortName=" + this.shortName + ", company=" + this.company + ", wechat=" + this.wechat + ", qrCode=" + this.qrCode + "]";
    }
}


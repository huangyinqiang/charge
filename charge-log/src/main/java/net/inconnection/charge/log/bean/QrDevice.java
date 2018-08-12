package net.inconnection.charge.log.bean;

import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class QrDevice extends Model<QrDevice> {
    private static final long serialVersionUID = 1L;
    public static final QrDevice me = new QrDevice();
    private int id;
    private int gid;
    private String gname;
    private String type;
    private String qr_num;
    private String match_num;
    private String area;
    private String once_price;
    private String hour_price2;
    private String hour_price4;
    private String hour_price8;
    private String hour_price12;
    private String hour_charge_time2;
    private String tow_hours_price;
    private String four_hours_price;
    private String eight_hours_price;
    private String twelve_hours_price;
    private String remark;
    private String status;
    private Date createtime;
    private String power_a1;
    private String power_a2;
    private String power_a3;
    private String power_a4;
    private String power_a5;
    private String power_a6;
    private String power_a7;

    public QrDevice() {
    }

    public int getGid() {
        return this.gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return this.gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getTow_hours_price() {
        return this.tow_hours_price;
    }

    public void setTow_hours_price(String tow_hours_price) {
        this.tow_hours_price = tow_hours_price;
    }

    public String getFour_hours_price() {
        return this.four_hours_price;
    }

    public void setFour_hours_price(String four_hours_price) {
        this.four_hours_price = four_hours_price;
    }

    public String getEight_hours_price() {
        return this.eight_hours_price;
    }

    public void setEight_hours_price(String eight_hours_price) {
        this.eight_hours_price = eight_hours_price;
    }

    public String getTwelve_hours_price() {
        return this.twelve_hours_price;
    }

    public void setTwelve_hours_price(String twelve_hours_price) {
        this.twelve_hours_price = twelve_hours_price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPower_a1() {
        return this.power_a1;
    }

    public void setPower_a1(String power_a1) {
        this.power_a1 = power_a1;
    }

    public String getPower_a2() {
        return this.power_a2;
    }

    public void setPower_a2(String power_a2) {
        this.power_a2 = power_a2;
    }

    public String getPower_a3() {
        return this.power_a3;
    }

    public void setPower_a3(String power_a3) {
        this.power_a3 = power_a3;
    }

    public String getPower_a4() {
        return this.power_a4;
    }

    public void setPower_a4(String power_a4) {
        this.power_a4 = power_a4;
    }

    public String getPower_a5() {
        return this.power_a5;
    }

    public void setPower_a5(String power_a5) {
        this.power_a5 = power_a5;
    }

    public String getPower_a6() {
        return this.power_a6;
    }

    public void setPower_a6(String power_a6) {
        this.power_a6 = power_a6;
    }

    public String getPower_a7() {
        return this.power_a7;
    }

    public void setPower_a7(String power_a7) {
        this.power_a7 = power_a7;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQr_num() {
        return this.qr_num;
    }

    public void setQr_num(String qr_num) {
        this.qr_num = qr_num;
    }

    public String getMatch_num() {
        return this.match_num;
    }

    public void setMatch_num(String match_num) {
        this.match_num = match_num;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOnce_price() {
        return this.once_price;
    }

    public void setOnce_price(String once_price) {
        this.once_price = once_price;
    }

    public String getHour_price2() {
        return this.hour_price2;
    }

    public void setHour_price2(String hour_price2) {
        this.hour_price2 = hour_price2;
    }

    public String getHour_price4() {
        return this.hour_price4;
    }

    public void setHour_price4(String hour_price4) {
        this.hour_price4 = hour_price4;
    }

    public String getHour_price8() {
        return this.hour_price8;
    }

    public void setHour_price8(String hour_price8) {
        this.hour_price8 = hour_price8;
    }

    public String getHour_price12() {
        return this.hour_price12;
    }

    public void setHour_price12(String hour_price12) {
        this.hour_price12 = hour_price12;
    }

    public String getHour_charge_time2() {
        return this.hour_charge_time2;
    }

    public void setHour_charge_time2(String hour_charge_time2) {
        this.hour_charge_time2 = hour_charge_time2;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}


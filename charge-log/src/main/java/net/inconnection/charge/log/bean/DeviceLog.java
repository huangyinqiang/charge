package net.inconnection.charge.log.bean;

import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class DeviceLog extends Model<DeviceLog> implements Serializable {
    private static final long serialVersionUID = 5375032284538764047L;
    private Integer id;
    private String type;
    private String openid;
    private String deviceid;
    private String ip;
    private String content;
    private Date jointime;
    private QrDevice qrDevice;
    public static final DeviceLog me = new DeviceLog();

    public DeviceLog() {
    }

    public QrDevice getQrDevice() {
        return this.qrDevice;
    }

    public void setQrDevice(QrDevice qrDevice) {
        this.qrDevice = qrDevice;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public static long getSerialversionuid() {
        return 5375032284538764047L;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getJointime() {
        return this.jointime;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }
}


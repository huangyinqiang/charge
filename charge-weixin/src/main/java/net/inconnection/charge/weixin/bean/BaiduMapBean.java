package net.inconnection.charge.weixin.bean;

public class BaiduMapBean extends SuperReq {
    private static final long serialVersionUID = -373065250814909126L;
    private String title;
    private String point;
    private Double longitude;
    private Double latitude;
    private Double distance;
    private String address;
    private String tel;

    public BaiduMapBean() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDistance() {
        return this.distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}


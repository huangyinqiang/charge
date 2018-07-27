package net.inconnection.charge.weixin.bean;

public class TencentMapBean extends SuperReq {
    private static final long serialVersionUID = 3117756361644341969L;
    private Integer id;
    private String name;
    private String locate;
    private String latitude;
    private String longitude;

    public TencentMapBean() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocate() {
        return this.locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}


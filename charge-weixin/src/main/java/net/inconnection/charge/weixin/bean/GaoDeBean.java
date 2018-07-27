package net.inconnection.charge.weixin.bean;

public class GaoDeBean extends SuperReq {
    private static final long serialVersionUID = -5183590627608184293L;
    private String icon;
    private Object position;

    public GaoDeBean() {
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Object getPosition() {
        return this.position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }
}

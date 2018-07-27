package net.inconnection.charge.weixin.bean;

public class TencentMapSuperBean extends SuperReq {
    private static final long serialVersionUID = 2171565957624151919L;
    private Integer code;
    private String msg;
    private Object data;

    public TencentMapSuperBean() {
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

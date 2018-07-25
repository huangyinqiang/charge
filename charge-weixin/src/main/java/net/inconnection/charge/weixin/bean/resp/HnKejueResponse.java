package net.inconnection.charge.weixin.bean.resp;

import java.io.Serializable;

public class HnKejueResponse implements Serializable {
    private static final long serialVersionUID = -6357178428424697849L;
    public static final int SUCESS_STATUS = 0;
    public static final int FAILUER_STATUS = 999999;
    private Object respObj;
    private String respCode;
    private String respMsg;

    public HnKejueResponse() {
    }

    public HnKejueResponse(Object responseObj, String errorCode, String errorMsg) {
        this.respObj = responseObj;
        this.respCode = errorCode;
        this.respMsg = errorMsg;
    }

    public HnKejueResponse(String errorCode, String errorMsg) {
        this.respCode = errorCode;
        this.respMsg = errorMsg;
    }

    public Object getRespObj() {
        return this.respObj;
    }

    public void setRespObj(Object respObj) {
        this.respObj = respObj;
    }

    public String getRespCode() {
        return this.respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return this.respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String toString() {
        return "HnKejueResponse [respObj=" + this.respObj + ", respCode=" + this.respCode + ", respMsg=" + this.respMsg + "]";
    }
}


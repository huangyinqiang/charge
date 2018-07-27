package net.inconnection.charge.weixin.bean.resp;

import com.jfinal.kit.JsonKit;

public class AjaxResult {
    private int code = 0;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean hasError() {
        return this.code != 0;
    }

    public AjaxResult addError(String message) {
        this.message = message;
        this.code = 1;
        return this;
    }

    public AjaxResult addConfirmError(String message) {
        this.message = message;
        this.code = 2;
        return this;
    }

    public AjaxResult success(Object data) {
        this.data = data;
        return this;
    }

    public String toString() {
        return JsonKit.toJson(this);
    }
}


package net.inconnection.charge.weixin.bean.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultPageVo implements Serializable {
    private static final long serialVersionUID = -2419125128859263818L;
    public static final int SUCCESS = 0;
    public static final int BIZ_ERROR = 1;
    public static final int SYSTEM_ERROR = -1;
    private int code;
    private String message;
    private int total = 0;
    private List<? extends Object> rows = new ArrayList();

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<? extends Object> getRows() {
        return this.rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    public ResultPageVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultPageVo(int code, String message, int total, List<? extends Object> rows) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.rows = rows;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultPageVo createBizErrorPageVo(String message) {
        return new ResultPageVo(1, message);
    }

    public static ResultPageVo createSuccessPageVo(int total, List<? extends Object> rows) {
        return new ResultPageVo(0, "成功", total, rows);
    }

    public static ResultPageVo createSystemErrorPageVo(String message) {
        return new ResultPageVo(-1, message);
    }

    public static ResultVo createSuccessResultVo(Object message) {
        return new ResultVo(0, message);
    }
}


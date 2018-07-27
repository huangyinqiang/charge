package net.inconnection.charge.weixin.bean.resp;

import java.io.Serializable;

public class ResultVo implements Serializable {
    public static final int SUCCESS = 0;
    public static final int BIZ_ERROR = 1;
    public static final int SYSTEM_ERROR = -1;
    private static final long serialVersionUID = 1L;
    private int code;
    private Object attachment;

    public ResultVo() {
    }

    public ResultVo(int code) {
        this(code, (Object)null);
    }

    public ResultVo(int code, Object attachment) {
        this.code = code;
        this.attachment = attachment;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public static ResultVo createBizErrorResultVo(Object attachment) {
        return new ResultVo(1, attachment);
    }

    public static ResultVo createSystemErrorResultVo(Object attachment) {
        return new ResultVo(-1, attachment);
    }

    public static ResultVo createSuccessResultVo(Object attachment) {
        return new ResultVo(0, attachment);
    }
}

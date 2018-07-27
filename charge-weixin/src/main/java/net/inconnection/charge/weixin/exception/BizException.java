package net.inconnection.charge.weixin.exception;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = -3154995870783109536L;
    private String errorCode;
    private String errorMessage;

    public BizException() {
    }

    public BizException(String errorCode) {
        super(errorCode);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BizException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public BizException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


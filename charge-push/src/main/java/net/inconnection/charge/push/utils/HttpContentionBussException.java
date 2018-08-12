package net.inconnection.charge.push.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HttpContentionBussException extends Exception {
    private static final long serialVersionUID = -706523173134480338L;
    public static final int GENERATORERROR = 1;
    public static final int PARSERERROR = 2;
    public static final int DISPATCHERERROR = 3;
    public static final int FACTORYERROR = 4;
    public static final int PREPAREERROR = 5;
    public static final int VALIDATORERROR = 6;
    public static final int PROCESSORERROR = 7;
    public static final int FUNCTIONERROR = 8;
    public static final int DATABASEERROR = 9;
    protected Logger log = Logger.getLogger(this.getClass());
    private Class className;
    private String appCode;
    private String description;
    private String errorCode;
    private String parameterInfo = "";
    private Throwable superException = new Throwable();
    private int errorType;

    public HttpContentionBussException(Class className, int errorType, String errorCode, String description, String param) {
        this.errorType = errorType;
        this.className = className;
        this.errorCode = errorCode;
        this.description = description;
        this.parameterInfo = param;
    }

    public HttpContentionBussException(Class className, int errorType, String errorCode, String appCode, String description, String param) {
        this.errorType = errorType;
        this.className = className;
        this.errorCode = errorCode;
        this.appCode = appCode;
        this.description = description;
        this.parameterInfo = param;
    }

    public HttpContentionBussException(Class className, int errorType, String errorCode, String description, String param, Throwable superException) {
        this.errorType = errorType;
        this.className = className;
        this.errorCode = errorCode;
        this.description = description;
        this.parameterInfo = param;
        this.superException = superException;
    }

    public HttpContentionBussException(Class className, int errorType, String errorCode, String appCode, String description, String param, Throwable superException) {
        this.errorType = errorType;
        this.className = className;
        this.errorCode = errorCode;
        this.appCode = appCode;
        this.description = description;
        this.parameterInfo = param;
        this.superException = superException;
    }

    public void printError() {
        this.log.error("Class Name = " + this.getClass().getName());
        this.log.error("Error Code = " + this.getErrorCode());
        this.log.error("Error Description = " + this.getDescription());
        this.log.error("Input Param = " + this.getParameterInfo());
    }

    public void printStackTrace() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(buf);
        this.printStackTrace(p);
        this.log.error("Class Name = " + this.getClass().getName());
        this.log.error("Error Code = " + this.getErrorCode());
        this.log.error("Error Description = " + this.getDescription());
        this.log.error("Input Param = " + this.getParameterInfo());
        this.log.error("Error Cause = " + buf.toString());
        buf.reset();
        Throwable t = this.getSuperException();
        t.printStackTrace(p);

        while(t instanceof HttpContentionBussException) {
            HttpContentionBussException e = (HttpContentionBussException)t;
            e.printStackTrace(p);
            t = e.getSuperException();
        }

        t.printStackTrace(p);
        this.log.error("Error Super Cause = " + buf.toString());
        super.printStackTrace();
    }

    public String getErrorCauseMessage() {
        StringBuffer logBuffer = new StringBuffer();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(buf);
        this.printStackTrace(p);
        logBuffer.append(buf.toString());
        return logBuffer.toString();
    }

    public String getErrorSuperCauseMessage() {
        StringBuffer logBuffer = new StringBuffer();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(buf);
        Throwable t = this.getSuperException();
        t.printStackTrace(p);

        while(t instanceof HttpContentionBussException) {
            HttpContentionBussException e = (HttpContentionBussException)t;
            e.printStackTrace(p);
            t = e.getSuperException();
        }

        t.printStackTrace(p);
        super.printStackTrace();
        logBuffer.append(buf.toString());
        return logBuffer.toString();
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Class getClassName() {
        return this.className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorType() {
        return this.errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getParameterInfo() {
        return this.parameterInfo;
    }

    public void setParameterInfo(String parameterInfo) {
        this.parameterInfo = parameterInfo;
    }

    public Throwable getSuperException() {
        return this.superException;
    }

    public void setSuperException(Throwable superException) {
        this.superException = superException;
    }
}


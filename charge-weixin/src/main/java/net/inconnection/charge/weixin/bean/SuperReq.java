package net.inconnection.charge.weixin.bean;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class SuperReq implements Serializable {
    private static final long serialVersionUID = 821704976691950726L;

    public SuperReq() {
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

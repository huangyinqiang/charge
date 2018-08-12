package net.inconnnection.charge.cost.model;

import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = -4154249139152327761L;
    private String errcode;
    private String errmsg;
    private String ticket;
    private String expires_in;

    public Ticket() {
    }

    public String getErrcode() {
        return this.errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}

package net.inconnection.charge.push.model;

import java.io.Serializable;

public class AccessToken implements Serializable {
    private static final long serialVersionUID = 2037146062513153467L;
    private String access_token;
    private String expires_in;

    public AccessToken() {
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}

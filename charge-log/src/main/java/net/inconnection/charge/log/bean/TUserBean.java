package net.inconnection.charge.log.bean;

public class TUserBean {
    private String openId;
    private Integer walletAccount;

    public TUserBean() {
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getWalletAccount() {
        return this.walletAccount;
    }

    public void setWalletAccount(Integer walletAccount) {
        this.walletAccount = walletAccount;
    }
}


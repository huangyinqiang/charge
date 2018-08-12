package net.inconnection.charge.push.model;

public class Result {
    private String tilte;
    private String msg;
    private int status;
    private String result;

    public Result() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTilte() {
        return this.tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

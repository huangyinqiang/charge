package net.inconnnection.charge.cost.model;


public class Result {
    private String title;
    private String result;
    private String msg;
    private int status;

    public Result() {
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toString() {
        return this.status + " " + this.msg;
    }
}

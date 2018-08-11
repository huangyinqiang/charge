package net.inconnection.charge.weixin.code;

public enum RespCode {
    TEL_FAIL("999999", "请输入正确的手机号"),
    DB_ADD_FAIL("999999", "保存失败"),
    DB_QUERY_FAIL("999999", "查询失败"),
    DB_UPDATE_FAIL("999999", "更新失败"),
    SUCCESS("000000", "成功"),
    ACCEPT("000001", "已经受理"),
    FAILD("999999", "失败"),
    SEND_SMS_AUTH_FAILD("999999", "发送短信验证失败"),
    SMS_AUTH_FAILD("999999", "短信验证失败"),
    TIMEOUT("100000", "请求超时");

    private String key;
    private String value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private RespCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValueMsg(String msg) {
        return this.value = msg;
    }

    public static String getValueFromKey(String key) {
        RespCode[] arr = values();
        RespCode[] var5 = arr;
        int var4 = arr.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            RespCode code = var5[var3];
            if (code.getKey().equals(key)) {
                return code.getValue();
            }
        }

        return key;
    }
}


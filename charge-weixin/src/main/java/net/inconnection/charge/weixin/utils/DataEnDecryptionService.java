package net.inconnection.charge.weixin.utils;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;

import java.security.PrivateKey;

public class DataEnDecryptionService {
    static Log log = Log.getLog(DataEnDecryptionService.class);
    private static PrivateKey privateKey;

    public DataEnDecryptionService() {
    }

    public static String decrypt(String data) {
        log.info("开始解密 data=" + data);

        try {
            String key = PropKit.get("privateKey");
            privateKey = RSAUtils.getPrivateKey(key);
            data = RSAUtils.decryptString(privateKey, data);
            log.info("解密成功 data=" + data);
            return data;
        } catch (Exception var2) {
            log.error("RSA解密失败", var2);
            return null;
        }
    }
}

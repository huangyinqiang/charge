package net.inconnection.charge.weixin.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class DESUtils {
    private static Key key;

    public DESUtils() {
        this.setkey(this.getClass().getName());
    }

    public DESUtils(String keyStr) {
        this.setkey(keyStr);
    }

    private void setkey(String keyStr) {
        try {
            String desKey = Base64.encodeBase64String(keyStr.getBytes("UTF-8"));
            DESKeySpec objDesKeySpec = new DESKeySpec(desKey.getBytes("UTF-8"));
            SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
            key = objKeyFactory.generateSecret(objDesKeySpec);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public final String encryptString(String str) {
        byte[] bytes = str.getBytes();

        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, key);
            byte[] encryptStrBytes = cipher.doFinal(bytes);
            return Base64.encodeBase64URLSafeString(encryptStrBytes);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public final String decryptString(String str) {
        try {
            byte[] bytes = Base64.decodeBase64(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, key);
            bytes = cipher.doFinal(bytes);
            return new String(bytes);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }
}


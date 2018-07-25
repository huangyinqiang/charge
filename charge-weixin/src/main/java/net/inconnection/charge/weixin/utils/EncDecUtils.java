package net.inconnection.charge.weixin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncDecUtils {
    public EncDecUtils() {
    }

    public static String toMD5(String str) {
        MessageDigest alg = null;

        try {
            alg = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        byte[] digesta = null;
        if (alg != null) {
            alg.update(str.getBytes());
            digesta = alg.digest();
        }

        return digesta == null ? "" : byte2Hex(digesta);
    }

    static String byte2Hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        String str = "";

        for(int i = 0; i < paramArrayOfByte.length; ++i) {
            str = Integer.toHexString(paramArrayOfByte[i] & 255);
            if (str.length() == 1) {
                localStringBuffer.append("0");
            }

            localStringBuffer.append(str);
        }

        return localStringBuffer.toString().toUpperCase();
    }

    public static String getMD5(String str) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        StringBuffer sb = null;
        if (md != null) {
            byte[] result = md.digest();
            sb = new StringBuffer(32);

            for(int i = 0; i < result.length; ++i) {
                int val = result[i] & 255;
                if (val < 15) {
                    sb.append("0");
                }

                sb.append(Integer.toHexString(val));
            }
        }

        return sb == null ? "" : sb.toString();
    }

    public static String mpMark(String mp) {
        return mp.substring(0, 3) + "****" + mp.substring(mp.length() - 4);
    }
}


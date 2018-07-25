package net.inconnection.charge.weixin.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;

public abstract class RSAUtils {
    private static final Logger logger = Logger.getLogger(RSAUtils.class);
    private static final String ALGORITHOM = "RSA";
    private static final String RSA_PAIR_FILENAME = "/__RSA_PAIR.txt";
    private static final int KEY_SIZE = 1024;
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    private static KeyPair oneKeyPair = null;
    private static File rsaPairFile = null;
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    private RSAUtils() {
    }

    public static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGen.initialize(1024);
            oneKeyPair = keyPairGen.generateKeyPair();
            saveKeyPair(oneKeyPair);
            return oneKeyPair;
        } catch (InvalidParameterException var1) {
            logger.error("KeyPairGenerator does not support a key length of 1024.", var1);
        } catch (NullPointerException var2) {
            logger.error("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.", var2);
        }

        return null;
    }

    private static String getRSAPairFilePath() {
        String urlPath = RSAUtils.class.getResource("/").getPath();
        return (new File(urlPath)).getParent() + "/__RSA_PAIR.txt";
    }

    private static boolean isCreateKeyPairFile() {
        boolean createNewKeyPair = false;
        if (!rsaPairFile.exists() || rsaPairFile.isDirectory()) {
            createNewKeyPair = true;
        }

        return createNewKeyPair;
    }

    private static void saveKeyPair(KeyPair keyPair) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = FileUtils.openOutputStream(rsaPairFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(keyPair);
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
        }

    }

    public static KeyPair getKeyPair() {
        if (isCreateKeyPairFile()) {
            return generateKeyPair();
        } else {
            return oneKeyPair != null ? oneKeyPair : readKeyPair();
        }
    }

    public static KeyPair readKeyPair() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = FileUtils.openInputStream(rsaPairFile);
            ois = new ObjectInputStream(fis);
            oneKeyPair = (KeyPair)ois.readObject();
            KeyPair var4 = oneKeyPair;
            return var4;
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }

        return null;
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));

        try {
            return (RSAPublicKey)keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException var4) {
            logger.error("RSAPublicKeySpec is unavailable.", var4);
        } catch (NullPointerException var5) {
            logger.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", var5);
        }

        return null;
    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));

        try {
            return (RSAPrivateKey)keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException var4) {
            logger.error("RSAPrivateKeySpec is unavailable.", var4);
        } catch (NullPointerException var5) {
            logger.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", var5);
        }

        return null;
    }

    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if (!StringUtils.isBlank(hexModulus) && !StringUtils.isBlank(hexPrivateExponent)) {
            byte[] modulus = null;
            byte[] privateExponent = null;

            try {
                modulus = Hex.decodeHex(hexModulus.toCharArray());
                privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
            } catch (DecoderException var5) {
                logger.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
            }

            return modulus != null && privateExponent != null ? generateRSAPrivateKey(modulus, privateExponent) : null;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
            }

            return null;
        }
    }

    public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if (!StringUtils.isBlank(hexModulus) && !StringUtils.isBlank(hexPublicExponent)) {
            byte[] modulus = null;
            byte[] publicExponent = null;

            try {
                modulus = Hex.decodeHex(hexModulus.toCharArray());
                publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
            } catch (DecoderException var5) {
                logger.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
            }

            return modulus != null && publicExponent != null ? generateRSAPublicKey(modulus, publicExponent) : null;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            }

            return null;
        }
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
        ci.init(1, publicKey);
        return ci.doFinal(data);
    }

    public static String decryptData(String data) {
        try {
            byte[] en_result = (new BigInteger(data, 16)).toByteArray();
            byte[] de_result = decrypt(readKeyPair().getPrivate(), en_result);
            StringBuffer sb = new StringBuffer();
            sb.append(new String(de_result));
            return sb.reverse().toString();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
        ci.init(2, privateKey);
        return ci.doFinal(data);
    }

    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey != null && plaintext != null) {
            byte[] data = plaintext.getBytes();

            try {
                byte[] en_data = encryptByPublicKey(publicKey, data);
                return new String(Hex.encodeHex(en_data));
            } catch (Exception var4) {
                logger.error(var4.getCause().getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public static String encryptString(String plaintext) {
        if (plaintext == null) {
            return null;
        } else {
            byte[] data = plaintext.getBytes();
            KeyPair keyPair = getKeyPair();

            try {
                byte[] en_data = encrypt((RSAPublicKey)keyPair.getPublic(), data);
                return new String(Hex.encodeHex(en_data));
            } catch (NullPointerException var4) {
                logger.error("keyPair cannot be null.");
            } catch (Exception var5) {
                logger.error(var5.getCause().getMessage());
            }

            return null;
        }
    }

    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey != null && !StringUtils.isBlank(encrypttext)) {
            try {
                byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
                byte[] data = decryptByPrivateKey(privateKey, en_data);
                return new String(data);
            } catch (Exception var4) {
                logger.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, var4.getCause().getMessage()));
                return null;
            }
        } else {
            return null;
        }
    }

    public static String decryptString(String encrypttext) {
        if (StringUtils.isBlank(encrypttext)) {
            return null;
        } else {
            KeyPair keyPair = getKeyPair();

            try {
                byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
                byte[] data = decrypt((RSAPrivateKey)keyPair.getPrivate(), en_data);
                return new String(data);
            } catch (NullPointerException var4) {
                logger.error("keyPair cannot be null.");
            } catch (Exception var5) {
                logger.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, var5.getMessage()));
            }

            return null;
        }
    }

    public static String decryptStringByJs(String encrypttext) {
        String text = decryptString(encrypttext);
        return text == null ? null : StringUtils.reverse(text);
    }

    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = getKeyPair();
        return keyPair != null ? (RSAPublicKey)keyPair.getPublic() : null;
    }

    public static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        return keyPair != null ? (RSAPrivateKey)keyPair.getPrivate() : null;
    }

    public static byte[] encryptByPublicKey(Key publicK, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] decryptByPrivateKey(Key privateK, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
        cipher.init(2, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicKeyStr = "";
        String privateKeyStr = "";
        publicKeyStr = FileUtils.readFileToString(new File("F:\\key\\rsa_public_key.pem"));
        privateKeyStr = FileUtils.readFileToString(new File("F:\\key\\pkcs8_private_key.pem"));
        publicKey = getPublicKey(publicKeyStr);
        privateKey = getPrivateKey(privateKeyStr);
        String data = encryptString(publicKey, "测试");
        System.out.println(data);
        String deData = decryptString(privateKey, "46074b52e2aad2a15e0e8e1807855d6b2cd618a2f1e502bb2dea5733d1fdda5ded2d7435928cee08e0b73b7a75f4a1283ca67f9b6bd52a175fd11467c6e4e2d83757c262e36deb74f1a97e0b219e94b9deb6d9b3fb8ae72d3fbe2a7e4e6fe54ce4b632b8031886c432d1b1dc4ee13e6aa04fc10461e3fe827cc77e2bf066fff2");
        System.out.println(deData);
    }
}

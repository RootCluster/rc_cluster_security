package org.incoder.security.asymmetric.rsa;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Jerry xu
 * @date 2018-07-07 09:36
 */
public class RSAUtils {

    /**
     * 指定加密算法为RSA
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 密钥长度，用来初始化
     */
    private static int KEY_SIZE = 1024;
    /**
     * 指定公钥存放文件
     */
    public static String PUBLIC_KEY_FILE = "PublicKey";
    /**
     * 指定私钥存放文件
     */
    public static String PRIVATE_KEY_FILE = "PrivateKey";

    /**
     * 生成RSA密钥对
     *
     * @throws Exception 异常
     */
    public static void generateKeyPair() throws Exception {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 利用上面的随机数据源初始化这个KeyPairGenerator对象
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        // 生成密匙对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        // Base64进行编码得到公钥字符串
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // Base64进行编码得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        OutputStreamWriter oos1 = null;
        OutputStreamWriter oos2 = null;

        try {
            // 用对象流将生成的密钥写入文件
            oos1 = new OutputStreamWriter(new FileOutputStream(PUBLIC_KEY_FILE));
            oos2 = new OutputStreamWriter(new FileOutputStream(PRIVATE_KEY_FILE));
            oos1.write(publicKeyString, 0, publicKeyString.length());
            oos2.write(privateKeyString, 0, privateKeyString.length());
        } finally {
            // 清空缓存，关闭文件输出流
            Objects.requireNonNull(oos1).close();
            Objects.requireNonNull(oos2).close();
        }
    }

    /**
     * 根据文件获取密钥字符串
     *
     * @param keyFile 文件
     * @return 密钥字符串
     * @throws IOException IO异常
     */
    public static String getKeyPairString(String keyFile) throws IOException {
        if (keyFile != null) {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(keyFile));
            StringBuilder clientPrivateKey = new StringBuilder();
            int i;
            while ((i = isr.read()) != -1) {
                clientPrivateKey.append((char) i);
            }
            isr.close();
            return clientPrivateKey.toString();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param pubKeyStr 公钥字符串
     * @param data      加密数据源
     * @return 密文字节码
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(String pubKeyStr, byte[] data) throws Exception {
        // Base64解码公钥
        byte[] publicBT = Base64.getDecoder().decode(pubKeyStr);
        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(publicBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥分段加密
     *
     * @param pubKeyStr 公钥字符串
     * @param data      加密数据源
     * @return 密文字节码
     * @throws Exception 异常
     */
    public static byte[] encryptSectionByPublicKey(String pubKeyStr, byte[] data) throws Exception {
        // Base64解码公钥
        byte[] publicBT = Base64.getDecoder().decode(pubKeyStr);
        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(publicBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 对数据分段加密
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 32) {
                cache = cipher.doFinal(data, offSet, 32);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 32;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥解密
     *
     * @param priKeyStr 私钥字符串
     * @param data      密文
     * @return 原数据
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(String priKeyStr, byte[] data) throws Exception {
        // Base64解码私钥
        byte[] privateBT = Base64.getDecoder().decode(priKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥分段解密
     *
     * @param priKeyStr 私钥字符串
     * @param data      密文
     * @return 原数据
     * @throws Exception 异常
     */
    public static byte[] decryptSectionByPrivateKey(String priKeyStr, byte[] data) throws Exception {
        // Base64解码私钥
        byte[] privateBT = Base64.getDecoder().decode(priKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 对数据分段解密
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 64) {
                cache = cipher.doFinal(data, offSet, 64);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 64;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥字符串转PublicKey
     *
     * @param publicKeyString 公钥字符串
     * @return PublicKey
     * @throws Exception 异常
     */
    public static PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        // Base64解码公钥
        byte[] publicBT = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(publicBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(bobPubKeySpec);
    }

    /**
     * 私钥字符串转PrivateKey
     *
     * @param privateKeyString 私钥字符串
     * @return PrivateKey
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        // Base64解码私钥
        byte[] privateBT = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateBT);
        // RSA
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param encrypted 编码
     * @return byte
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param message    密文
     * @return byte
     * @throws Exception 异常
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(message);
    }

}

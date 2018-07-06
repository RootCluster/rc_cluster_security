package org.incoder.security.asymmetric.rsa;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 */
@RestController
public class RSAController {

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
    private static String PUBLIC_KEY_FILE = "PublicKey";
    /**
     * 指定私钥存放文件
     */
    private static String PRIVATE_KEY_FILE = "PrivateKey";

    /**
     * 获取RSA公钥
     *
     * @return getPublicKey
     * @throws IOException 算法异常
     */
    @ResponseBody
    @RequestMapping(value = "getPublicKey", method = RequestMethod.GET)
    public Object getPublicKey() throws IOException {
        Map<String, String> result = new HashMap<>();
        result.put("publicKey", getKeyPairString(PUBLIC_KEY_FILE));
        return result;
    }

    /**
     * 获取RSA私钥
     *
     * @return getPrivateKey
     * @throws IOException 算法异常
     */
    @ResponseBody
    @RequestMapping(value = "getPrivateKey", method = RequestMethod.GET)
    public Object getPrivateKey() throws IOException {
        Map<String, String> result = new HashMap<>();
        result.put("getPrivateKey", getKeyPairString(PRIVATE_KEY_FILE));
        return result;
    }

    /**
     * 服务端解密客户端数据
     *
     * @return 解密结果
     */
    @ResponseBody
    @RequestMapping(value = "decryptClient", method = RequestMethod.POST)
    public boolean decryptClient(MessageBean message) throws Exception {
        if (null != message) {
            // 解密客户端公钥加密内容
            // 转换私钥
            PrivateKey privateKey = getPrivateKeyFromString(getKeyPairString(PRIVATE_KEY_FILE));

            String serverDecryptContent = new String(decrypt(privateKey, message.getEncryptContent().getBytes()));
            System.out.println("客户端原数据（未公钥加密）：" + message.getUnencryptedContent());
            System.out.println("服务端解密客户端公钥数据：" + serverDecryptContent);
            return serverDecryptContent.equals(message.getUnencryptedContent());
        }
        return false;
    }

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
        // 得到公钥字符串
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
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
    private String getKeyPairString(String keyFile) throws IOException {
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
     * 公钥字符串转PublicKey
     *
     * @param publicKeyString 公钥字符串
     * @return PublicKey
     * @throws Exception 异常
     */
    public static PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        BASE64Decoder b64 = new BASE64Decoder();
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(b64.decodeBuffer(publicKeyString));
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 私钥字符串转PrivateKey
     *
     * @param privateKeyString 私钥字符串
     * @return PrivateKey
     * @throws Exception 异常
     */
    private static PrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        BASE64Decoder b64 = new BASE64Decoder();
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(b64.decodeBuffer(privateKeyString));
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param encrypted 编码
     * @return byte
     * @throws Exception 异常
     */
    private static byte[] encrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
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
    private static byte[] decrypt(PrivateKey privateKey, byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(message);
    }

}

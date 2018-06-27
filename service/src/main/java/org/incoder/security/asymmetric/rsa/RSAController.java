package org.incoder.security.asymmetric.rsa;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 */
@RestController
public class RSAController {

    /**
     * 获取RSA公钥
     *
     * @return getPublicKey
     * @throws NoSuchAlgorithmException 算法异常
     */
    @ResponseBody
    @RequestMapping(value = "getPublicKey", method = RequestMethod.GET)
    public byte[] getPublicKey() throws NoSuchAlgorithmException {
        return Base64.getEncoder().encode(buildKeyPair(2048).getPublic().getEncoded());
    }

    /**
     * 获取RSA私钥
     *
     * @return getPrivateKey
     * @throws NoSuchAlgorithmException 算法异常
     */
    @ResponseBody
    @RequestMapping(value = "getPrivateKey", method = RequestMethod.GET)
    public byte[] getPrivateKey() throws NoSuchAlgorithmException {
        return Base64.getEncoder().encode(buildKeyPair(2048).getPrivate().getEncoded());
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
            String serverDecryptContent = new String(decrypt(buildKeyPair(2048).getPrivate(), message.getEncryptContent().getBytes()));
            System.out.println("客户端原数据（未公钥加密）：" + message.getUnencryptedContent());
            System.out.println("服务端解密客户端公钥数据：" + serverDecryptContent);
            return serverDecryptContent.equals(message.getUnencryptedContent());
        }
        return false;
    }

    /**
     * 生成RSA密钥对
     *
     * @return 公钥
     * @throws NoSuchAlgorithmException 异常
     */
    public static KeyPair buildKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param encrypted 编码
     * @return byte
     * @throws Exception 异常
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
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
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(message);
    }

}

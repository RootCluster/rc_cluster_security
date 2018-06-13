package org.incoder.security.asymmetric.rsa;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.security.*;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 */
@RestController
public class RSAController {

    /**
     * 获取RSA加密公钥
     *
     * @return KeyPair
     */
    @ResponseBody
    @RequestMapping(value = "getPublicKey", method = RequestMethod.GET)
    public String getPublicKey() throws NoSuchAlgorithmException {
        return buildKeyPair().getPublic().toString();
    }

    /**
     * 服务端解密客户端数据
     *
     * @return 解密结果
     */
    @ResponseBody
    @RequestMapping(value = "decryptClient", method = RequestMethod.POST)
    public boolean decryptClient() {
        // TODO: 2018/6/13
        return false;
    }

    /**
     * 生成RSA公钥
     *
     * @return 公钥
     * @throws NoSuchAlgorithmException 异常
     */
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }

}

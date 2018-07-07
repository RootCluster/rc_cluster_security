package org.incoder.security;


import org.incoder.security.asymmetric.rsa.RSAUtils;

import static org.incoder.security.asymmetric.rsa.RSAUtils.PRIVATE_KEY_FILE;
import static org.incoder.security.asymmetric.rsa.RSAUtils.PUBLIC_KEY_FILE;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 */
public class RSATest {

    public static void main(String[] args) throws Exception {
        // 生成密钥对
//        generateKeyPair();
        // 公钥加密
        String contentString = "hello world";
        System.out.println("原数据：" + contentString);
        byte[] sentence = RSAUtils.encryptByPublicKey(RSAUtils.getKeyPairString(PUBLIC_KEY_FILE), contentString.getBytes());
        System.out.println("公钥加密结果：" + new String(sentence));
        // 私钥解密
        byte[] rule = RSAUtils.decryptByPrivateKey(RSAUtils.getKeyPairString(PRIVATE_KEY_FILE), sentence);
        System.out.println("私钥解密结果：" + new String(rule));

    }

}

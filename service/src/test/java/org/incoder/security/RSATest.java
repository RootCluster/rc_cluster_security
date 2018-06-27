package org.incoder.security;

import java.security.*;

import static org.incoder.security.asymmetric.rsa.RSAController.buildKeyPair;
import static org.incoder.security.asymmetric.rsa.RSAController.decrypt;
import static org.incoder.security.asymmetric.rsa.RSAController.encrypt;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 */
public class RSATest {

    public static void main(String[] args) throws Exception {

        // generate public and private keys
        KeyPair keyPair = buildKeyPair(2048);
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String message = "This is a message";

        // encrypt the message
        byte[] encrypted = encrypt(pubKey, message.getBytes());
        System.out.println("加密结果：\n" + new String(encrypted));

        // decrypt the message
        byte[] secret = decrypt(privateKey, encrypted);
        System.out.println("解密结果：" + new String(secret));
    }


}

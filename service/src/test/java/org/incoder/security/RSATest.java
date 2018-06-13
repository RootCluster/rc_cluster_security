package org.incoder.security;

import java.security.*;

import static org.incoder.security.asymmetric.rsa.RSAController.buildKeyPair;
import static org.incoder.security.asymmetric.rsa.RSAController.decrypt;
import static org.incoder.security.asymmetric.rsa.RSAController.encrypt;

/**
 * @author Jerry xu
 * @date 2018-06-13 09:36
 * reference：https://gist.github.com/dmydlarz/32c58f537bb7e0ab9ebf
 */
public class RSATest {

    public static void main(String[] args) throws Exception {

        // generate public and private keys
        KeyPair keyPair = buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // encrypt the message
        byte[] encrypted = encrypt(privateKey, "This is a secret message");
        System.out.println("加密结果\n" + new String(encrypted));

        // decrypt the message
        byte[] secret = decrypt(pubKey, encrypted);
        System.out.println("解密结果\n" + new String(secret));
    }


}

package org.incoder.security;

import java.io.Serializable;

/**
 * PublicKeyBean
 *
 * @author Jerry xu
 * @date 2018/6/28 16:36
 */
public class PublicKeyBean implements Serializable {

    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

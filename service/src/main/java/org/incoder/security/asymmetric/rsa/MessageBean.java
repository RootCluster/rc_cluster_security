package org.incoder.security.asymmetric.rsa;

/**
 * .MessageBean
 *
 * @author : Jerry xu
 * @date : 6/28/2018 1:21 AM
 */
public class MessageBean {

    /**
     * 原数据（未加密数据）
     */
    private String unencryptedContent;

    /**
     * 公钥加密后数据
     */
    private String encryptContent;

    public String getUnencryptedContent() {
        return unencryptedContent;
    }

    public void setUnencryptedContent(String unencryptedContent) {
        this.unencryptedContent = unencryptedContent;
    }

    public String getEncryptContent() {
        return encryptContent;
    }

    public void setEncryptContent(String encryptContent) {
        this.encryptContent = encryptContent;
    }
}

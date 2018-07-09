package org.incoder.security.asymmetric.rsa;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * .MessageVO
 *
 * @author : Jerry xu
 * @date : 6/28/2018 1:21 AM
 */
public class MessageVO {

    /**
     * 原数据（未加密数据）
     */
    @JsonProperty(value = "unencryptedContent")
    private String unencryptedContent;

    /**
     * 公钥加密后数据
     */
    @JsonProperty(value = "encryptContent")
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

    @Override
    public String toString() {
        return "MessageVO{" +
                "unencryptedContent='" + unencryptedContent + '\'' +
                ", encryptContent='" + encryptContent + '\'' +
                '}';
    }
}

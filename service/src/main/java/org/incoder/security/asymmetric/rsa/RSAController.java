package org.incoder.security.asymmetric.rsa;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.incoder.security.asymmetric.rsa.RSAUtils.PRIVATE_KEY_FILE;
import static org.incoder.security.asymmetric.rsa.RSAUtils.PUBLIC_KEY_FILE;

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
     * @throws IOException 算法异常
     */
    @ResponseBody
    @RequestMapping(value = "getPublicKey", method = RequestMethod.GET)
    public Object getPublicKey() throws IOException {
        Map<String, String> result = new HashMap<>(2);
        result.put("publicKey", RSAUtils.getKeyPairString(PUBLIC_KEY_FILE));
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
        Map<String, String> result = new HashMap<>(2);
        result.put("getPrivateKey", RSAUtils.getKeyPairString(PRIVATE_KEY_FILE));
        return result;
    }

    /**
     * 服务端解密客户端数据
     *
     * @return 解密结果
     */
    @ResponseBody
    @RequestMapping(value = "decryptClient", method = RequestMethod.POST)
    public boolean decryptClient(@RequestBody MessageBean message) throws Exception {
        if (null != message) {
            // 解密客户端公钥加密内容
            String serverDecryptContent = new String(RSAUtils.decryptByPrivateKey(RSAUtils.getKeyPairString(PRIVATE_KEY_FILE), message.getEncryptContent().getBytes()));
            System.out.println("客户端原数据（未公钥加密）：" + message.getUnencryptedContent());
            System.out.println("服务端解密客户端公钥数据：" + serverDecryptContent);
            return serverDecryptContent.equals(message.getUnencryptedContent());
        }
        return false;
    }
}

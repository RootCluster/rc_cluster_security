package org.incoder.security;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * android
 *
 * @author Jerry xu
 * @date 6/28/2018 2:09 AM.
 */
public interface ApiService {

    /**
     * 获取公钥
     *
     * @return 公钥byte
     */
    @GET("getPublicKey")
    Call<Byte> getPublicKey();

    /**
     * 获取私钥
     *
     * @return 私钥byte
     */
    @GET("getPrivateKey")
    Call<Byte> getPrivateKey();

    /**
     * 发送公钥加密内容服务端进行解密
     *
     * @param message 原数据&&公钥加密数据
     * @return 服务端是否解密成功
     */
    @POST("decryptClient")
    Call<Boolean> decryptClient(MessageBean message);
}

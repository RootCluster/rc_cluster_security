package org.incoder.security;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jerry xu
 * @date 2018/06/14 3:32 AM.
 */
public class RetrofitManage {

    // 根据需要修改要访问的地址
    String BASE_URL = "http://192.168.31.180:8080/";
    private Retrofit retrofit;

    private static class SingletonHolder {
        private static final RetrofitManage INSTANCE = new RetrofitManage();
    }

    public static RetrofitManage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    RetrofitManage() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}

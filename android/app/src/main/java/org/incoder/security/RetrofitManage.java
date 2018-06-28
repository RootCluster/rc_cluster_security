package org.incoder.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jerry xu
 * @date 2018/06/14 3:32 AM.
 */
public class RetrofitManage {

    // 根据需要修改要访问的地址
    String BASE_URL = "http://10.20.30.243:8080/";
    private Retrofit retrofit;
    private static RetrofitManage instance;
    private final Gson mGson = new GsonBuilder()
            .setLenient()  // 设置GSON的非严格模式setLenient()
            .create();

    public static RetrofitManage getInstance() {
        if (instance == null) {
            synchronized (RetrofitManage.class) {
                if (instance == null) {
                    instance = new RetrofitManage();
                }
            }
        }
        return instance;
    }

    private RetrofitManage() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }


    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

    public ApiService createService() {
        return retrofit.create(ApiService.class);
    }
}

package com.bank.library.https;


import okhttp3.OkHttpClient;

/**
 * Created by  on 2018/6/21.
 */

class SDKManager {

    public static OkHttpClient.Builder initInterceptor(OkHttpClient.Builder builder){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder;
    }
}

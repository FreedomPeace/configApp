package com.bank.library.https;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.checkDuration;

/**
 * Created by  on 2018/6/1.<pre>
 * 可以动态的修改请求服务器的路径{@link RetrofitManager.Builder#baseUrl(String)}
 * 修改连接超时时间{@link RetrofitManager.Builder#connectTimeout(int)}
 * 修改读取超时时间{@link RetrofitManager.Builder#readTimeout(int)}
 * 修改写入超时时间{@link RetrofitManager.Builder#writeTimeout(int)}
 * </pre>
 * <p>
 * 获取默认配置的Retrofit的 for example
 *  <pre><code>
 *     Retrofit retrofit = RetrofitManager.newBuilder().build();
 *     LoginApi loginApi = retrofit.create(LoginApi.class);
 *  </code></pre>
 *  </p>
 *  <p>
 * 获取修改服务器url的Retrofit example
 *  <pre><code>
 *     Retrofit retrofit = RetrofitManager.newBuilder().message("Login")
 *     .baseUrl("http://www.baidu.com").build();
 *     LoginApi loginApi = retrofit.create(LoginApi.class);
 *  </code></pre>
 *  </p>
 */

public final class RetrofitManager {
    private RetrofitManager() {

    }

    private static RetrofitManager.Builder retrofitManagerBuilder;
    private static final java.lang.Object Object = new Object();

    public static RetrofitManager.Builder newBuilder(String identity, String employeeNum, String iMei) {
        synchronized (Object) {
            if (retrofitManagerBuilder == null) {
                retrofitManagerBuilder = new Builder(identity,employeeNum,iMei);
            }
            return retrofitManagerBuilder;
        }
    }

    public static final class Builder {
        private Retrofit.Builder retrofitBuilder;
        private OkHttpClient.Builder okHttpBuilder;

        public Builder(String identity, String employeeNum, String iMei) {
            // 指定缓存路径,缓存大小 50Mb
//            Cache cache = new Cache(new File(BaseApplication.mGlobalApp.getCacheDir(), "HttpCache"), 1024 * 1024 * 50);

            // Cookie 持久化
//            ClearableCookieJar cookieJar =
//                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.mGlobalApp));
            okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder/*.cookieJar(cookieJar)*/
//                    .cache(cache)
//                        .sslSocketFactory(new CustomSSLSocketFactory(),new CustomX509TrustManager())
//                        .authenticator(new ClientAuthenticator())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .eventListenerFactory(ConcurrentPrintingEventListener.FACTORY)
                    .addInterceptor(new EncryptCompressInterceptor(
                            identity,employeeNum,iMei
                    ));
            //LOG拦截器的配置
//            if (BuildConfig.DEBUG) {
                okHttpBuilder = SDKManager.initInterceptor(okHttpBuilder);
//            }
            try {
                // 获取SSL上下文
                SSLContext context = SSLContext.getInstance("SSL");
                // 用证书重新初始化
                context.init(null, new TrustManager[] { new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[] {};
                    }
                } }, new java.security.SecureRandom());
                // 设置SSL套接字工厂
                okHttpBuilder.sslSocketFactory(context.getSocketFactory(), new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                });
                okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {

                        return true;
                    }
                });
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            retrofitBuilder = new Retrofit.Builder();
            String url = FrameworkConstant.BASE_URL;
            retrofitBuilder.baseUrl(url)
                   .addConverterFactory(GsonConverterFactory.create())//;
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        }

        public Builder readTimeout(int timeout) {
            int readTimeout = checkDuration("timeout", timeout, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder writeTimeout(int timeout) {
            int writeTimeout = checkDuration("timeout", timeout, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(writeTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder connectTimeout(int timeout) {
            int connectTimeout = checkDuration("timeout", timeout, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(connectTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            retrofitBuilder.baseUrl(baseUrl);
            return this;
        }

        public Retrofit build() {
            retrofitBuilder.client(okHttpBuilder.build());
            return retrofitBuilder.build();
        }
    }

}

package com.bank.library.https;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLClient {

	/**
	 * 初始化SSL上下文，使其能接受来自任何机器的所有证书。
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static void initializeSSLContext() throws KeyManagementException,
			NoSuchAlgorithmException {
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
		HttpsURLConnection.setDefaultSSLSocketFactory(context
				.getSocketFactory());
		// 设置主机名验证方法
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}
	
}

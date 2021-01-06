package com.bank.library.https.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5String("password"));
		System.out.println(MD5Util.getMD5String("A000031"));
		System.out.println(MD5Util.getMD5String("00601"));
		System.out.println(MD5Util.getMD5String("A000031:5f4dcc3b5aa765d61d8327deb882cf99"));
		System.out.println(MD5Util.getMD5String("00601:5f4dcc3b5aa765d61d8327deb882cf99"));
	}

	public static String getMD5String(byte[] data) {
		try {
			
			return Coder.encodeAsHexString(encodeAsMD5(data));
		} catch (Exception e) {
		}
		return "";
	}

	public static String getMD5String(String text) {
	
		return getMD5String(text.getBytes());
	}

	public static byte[] getMd5(String text) throws Exception {
		byte[]bt= encodeAsMD5(text.getBytes("UTF-8"));
//		bt=Base64.encode(bt, Base64.DEFAULT);
		//String st=Base64.encodeToString(bt, 0);
		return bt;
	}

	public static byte[] encodeAsMD5(byte[] data)
			throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

//	public static String encodeAsHexString(byte[] data) {
//		StringBuffer buf = new StringBuffer();
//		for (byte ch : data) {
//			String s = Integer.toHexString(ch & 0xff);
//			buf.append(s.length() == 1 ? s = "0" + s : s);
//		}
//		return buf.toString();
//	}

}

package com.bank.library.https.encrypt;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
	/*
	 * 数据加密（outputstream）
	 * */
	public static CipherOutputStream encrypt(OutputStream os,byte[] secretkey){
		try{
		 // get cipher
	       Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	       // get secret key
	       SecretKeySpec key = new SecretKeySpec((byte[]) secretkey//context.getAttribute("SecretKey")
	    		   , "DESede");
	       // initialize cipher
	       cipher.init(Cipher.ENCRYPT_MODE, key);
	       // set output stream
	       return new CipherOutputStream(os,cipher);
	      // response.setOutputStream(new CipherOutputStream(response.getOutputStream(), cipher));
	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static byte[] encryptbyte(byte[] data,byte[] secretkey){
		try{
		 // get cipher
	       Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	       // get secret key
	       SecretKeySpec key = new SecretKeySpec((byte[]) secretkey//context.getAttribute("SecretKey")
	    		   , "DESede");
	       // initialize cipher
	       cipher.init(Cipher.ENCRYPT_MODE,key);
	       // set output stream
	       return cipher.doFinal(data);



	      // response.setOutputStream(new CipherOutputStream(response.getOutputStream(), cipher));

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * 数据解密（inputstream）
	 * */
	public static CipherInputStream decrypt(InputStream is,byte[] secretkey){	
		try{
			// get cipher      
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			// get secret key
			SecretKeySpec key = new SecretKeySpec((byte[]) secretkey//context.getAttribute("SecretKey")
	    		   , "DESede");
			// initialize cipher
			cipher.init(Cipher.DECRYPT_MODE, key);
			// set output stream
			return new CipherInputStream(is, cipher);
		}	catch(Exception e){
			return null;
		}
	}
}

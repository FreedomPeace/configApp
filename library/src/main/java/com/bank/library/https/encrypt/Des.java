package com.bank.library.https.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Des {
	
    private static final String Algorithm = "DESede"; //定义 加密算法,可用  DES,DESede,Blowfish 等
    
    public static byte[] EncryptMode(byte[] key, byte[] src) {
       try {
           
            SecretKey deskey = new SecretKeySpec(key, Algorithm); //生成密钥
          
            Cipher c1 = Cipher.getInstance(Algorithm); //加密类实例化
            
            c1.init(Cipher.ENCRYPT_MODE, deskey); //实例根据密钥初始化
            
            //return c1.update(src);
            return c1.doFinal(src); //返回加密byte数组
            
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


}

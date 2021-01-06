package com.bank.library.https.encrypt;

/**
 * 编码、解码
 * 
 * @author 谢磊
 * 
 */
public class Coder {

	/**
	 * 将字节数组转成字符串，按16进制；例如：数组是{ 1, 2, 3, 10, 11, 1}, 转换后的字符串是"0102030a0b01"
	 * 
	 * @param data
	 *            数据数组
	 * @return 字符串
	 */
	public static String encodeAsHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (byte ch : data) {
			String s = Integer.toHexString(ch & 0xff);
			buf.append(s.length() == 1 ? s = "0" + s : s);
		}
		
		return buf.toString();
	}

}

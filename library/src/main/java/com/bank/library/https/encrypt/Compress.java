
package com.bank.library.https.encrypt;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class Compress {
		
	public static byte[] compress(byte[] data) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		gos.write(data);
		//gos.finish();
		gos.flush();
		gos.close();
		byte[] output = baos.toByteArray();
		baos.flush();
		baos.close();
		return output;
		
	}
}

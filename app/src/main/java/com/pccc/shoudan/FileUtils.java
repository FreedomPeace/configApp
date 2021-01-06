package com.pccc.shoudan;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FileUtils {

	private String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 得到当前外部存储设备的目录
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * 在sd卡上创建文件
	 */
	public File createSDFILE(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在sd卡上创建目录
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 将一个Inputstream里的数据写入sd卡中
	 */
	public File write2SDFromInput(String path, String fileName,
                                  InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFILE(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int tmp;
			while ((tmp = (input.read(buffer))) != -1) {
				output.write(buffer, 0, tmp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public boolean deleteSDFILE(String fileName) {
		boolean isDelete = false;
		if (isFileExist(fileName)) {
			File file = new File(SDPATH +fileName);
			isDelete = file.delete();
		}
		return isDelete;
	}

	/**
	 * 删除文件夹下所有文件
	 * 
	 * @param folderPath
	 */
	public void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tmpList = file.list();
		File tmp = null;

		for (int i = 0; i < tmpList.length; i++) {
			if (path.endsWith(File.separator)) {
				tmp = new File(path + tmpList[i]);
			} else {
				tmp = new File(path + File.separator + tmpList[i]);
			}

			if (tmp.isFile()) {
				tmp.delete();
			}
			if (tmp.isDirectory()) {
				delAllFile(path + "/" + tmpList[i]);
				delFolder(path + "/" + tmpList[i]);
				flag = true;
			}
		}
		return flag;
	}

	public void saveBitmap(String path, String fileName, Bitmap bitmap) {
		try {
			createSDDir(path);
			deleteSDFILE(path + fileName + ".jpg");
			File fPicture = createSDFILE(path + fileName + ".jpg");

			FileOutputStream fout = new FileOutputStream(fPicture);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			bitmap.compress(Bitmap.CompressFormat.JPEG, // 图片格式
					48, // 品质0-100
					bos // 使用的输出流
			);
			fout.close();
			bos.flush();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveBitmap1(String path, String fileName, Bitmap bitmap) {
		try {
			createSDDir(path);
			deleteSDFILE(path + fileName);
			File fPicture = createSDFILE(path + fileName);

			FileOutputStream fout = new FileOutputStream(fPicture);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			bitmap.compress(Bitmap.CompressFormat.JPEG, // 图片格式
					48, // 品质0-100
					bos // 使用的输出流
			);
			fout.close();
			bos.flush();
			bos.close();
			bitmap =null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void saveBitmap2(String path, String fileName, Bitmap bitmap) {
		try {
			createSDDir(path);
			deleteSDFILE(path + fileName);
			File fPicture = createSDFILE(path + fileName);

			FileOutputStream fout = new FileOutputStream(fPicture);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			bitmap.compress(Bitmap.CompressFormat.JPEG, // 图片格式
					48, // 品质0-100
					bos // 使用的输出流
			);
			fout.close();
			bos.flush();
			bos.close();
			bitmap =null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存版本号
	 * 
	 * @param ver
	 */
	public static void saveSHDBVer(String ver) {
		FileOutputStream fos = null;
		String fileName = "merchant.txt";
		try {
			FileUtils fu = new FileUtils();
			fu.createSDDir("TJB/ver/");
			if (fu.isFileExist("TJB/ver/" + fileName)) {
				fu.deleteSDFILE("TJB/ver/" + fileName);
			}
			File f = fu.createSDFILE("TJB/ver/" + fileName);
			fos = new FileOutputStream(f);
			byte[] buf = ver.getBytes();
			fos.write(buf);
		} catch (Exception e) {
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
			}
		}
	}

	public static int getDBVer(String path) {

		File f = null;
		StringBuilder text = new StringBuilder();
		f = new File(path);// 这是对应文件名
		if (f.exists()) {
			InputStream in = null;
			try {
				in = new BufferedInputStream(new FileInputStream(f));
			} catch (FileNotFoundException e3) {
				Log.getInstance().writeLog(
						"数据库版本号读取异常");
			}
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(in, "gb2312"));
			} catch (UnsupportedEncodingException e1) {

				Log.getInstance().writeLog(
						"数据库版本号读取异常");
			}
			String tmp;

			try {
				while ((tmp = br.readLine()) != null) {
					text.append(tmp);
				}
				br.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.getInstance().writeLog(
						"数据库版本号读取异常");

			}
		} else {
			text.append("1");
		}
		return Integer.parseInt(text.toString());

	}
}

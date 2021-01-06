package com.pccc.shoudan;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.techown.merchant.utils.CurrentInfo;

import org.json.JSONObject;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class CrashApplication extends Application {

	public interface ShopApi{
		@POST("proxy.do")
		Flowable<String> getShop(@Body JSONObject b, @Header("wms-message") String message);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		CurrentInfo.loginDBVer = FileUtils
				.getDBVer("/sdcard/TJB/ver/login.txt");
		getdata(this);
//		CrashApplication.context = getApplicationContext();
//		retrofit.create(ShopApi.class).getShop()

	}
	public void getdata(Context con) {
		Context friendContext = null;
		String[] str = null;
		Cursor cur = null;
		SQLiteDatabase sld = null;
		try {
			friendContext = con.createPackageContext("techown.login",
					Context.CONTEXT_IGNORE_SECURITY);
			MyDatabaseHelper myHelper = new MyDatabaseHelper(friendContext,
					"techown_login.db",null, MyDatabaseHelper.loginDBVer);
			str = new String[3];
			sld = myHelper.getWritableDatabase();
			cur = sld.query("EntityUser", new String[] { "username", "imei",
					"identity" }, null, null, null, null, null);
			while (cur.moveToNext()) {
				for (int i = 0; i < 3; i++) {
					if (cur.getString(i) != null) {
						str[i] = cur.getString(i);
					}
				}

			}

			CurrentInfo.userName = str[0];
			CurrentInfo.imei = str[1];
			CurrentInfo.identity = str[2];
		} catch (Exception e) {
			Log.getInstance().writeLog("常用工具" + e.getMessage());
		} finally {
			if (cur != null)
				cur.close();
			if (sld != null)
				sld.close();
		}
	}
	/*private static Context context;
	
	public static Context getAppContext(){
		return context;
	}*/
	
}

package com.techown.merchant.utils;

import com.techown.merchant.BuildConfig;
import com.techown.merchant.db.MyDatabaseHelper;

public class CurrentInfo {

//	请求服务器地址
	public static String uriAPI = "https://182.180.50.105:443/tjbproxy/" ;
	static {
		if (BuildConfig.isCloudApk) {
			//云版本地址
			uriAPI ="https://182.180.199.147:443/ppdaPdabProxyWeb/";
		} else {
			//常规本地址
			uriAPI = "https://182.180.50.105:443/tjbproxy/" ;
		}

	}

	//查询员工信息表所得
	public static String userNum; //员工号
	public static String imei; //设备imei号
	public static String sim; //sim卡号？
	public static String identity;//凭证
	public static String userName;//业代姓名
	public static String userPro;//业代所在省
	public static String userCity;//业代所在市

	public static int loginDBVer = 1;//登录版本号
	public static String model = null;//凭证
	
}

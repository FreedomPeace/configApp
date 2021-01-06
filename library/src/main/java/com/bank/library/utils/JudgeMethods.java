package com.bank.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.widget.RadioButton;

import com.bank.library.https.MLog;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeMethods {

	/**
	 * 判断邮箱是否有效
	 * 
	 * @param inputEmail
	 * @return true or false
	 */
	public boolean isEmail(String inputEmail) {
		if (inputEmail.getBytes().length<51&&inputEmail
				.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
			return (true);
		else
			return (false);
	}

	/**
	 * 判断邮编是否有效
	 * 
	 * @param inputPostcode
	 * @return true or false
	 */
	public boolean isPostcode(String inputPostcode) {
//		if (inputPostcode.matches("^[1-9]\\d{5}(?!\\d)$"))
		if (inputPostcode.matches("^\\d{6}(?!\\d)$"))  //有0开头的邮编
			return true;
		else
			return false;
	}

	/**
	 * 字串s是否为英文
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglish(String s) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher m = pattern.matcher(s);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字串str是否为英文和中文
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndChinese(String str) {
		int j = 0;
		int k = 0;
		int i = str.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			j++;
		}
		for (int idx = 0; idx < i; idx++) {
			char c = str.charAt(idx);
			int tmp = (int) c;
			if ((tmp >= 'a' && tmp <= 'z') || (tmp >= 'A' && tmp <= 'Z')) {
				k++;
			}
		}
		if (i == j + k) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 字串str是否为英文和中文,'.'
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndChineseAndDot(String str) {
		int j = 0;
		int k = 0;
		int i = str.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			j++;
		}
		for (int idx = 0; idx < i; idx++) {
			char c = str.charAt(idx);
			int tmp = (int) c;
			if ((tmp >= 'a' && tmp <= 'z') || (tmp >= 'A' && tmp <= 'Z')||tmp=='.') {
				k++;
			}
		}
		if (i == j + k) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 字串str是否为英文和数字
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigit(String str) {
		int j = 0;
		int k = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		if (k + j == str.length()) {
			return true;
		}

		return false;
	}
	/**
	 * 字串str是否为大写英文和数字“()”、“[]”、“{}”、“-” 证件号、组织机构代码
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigit2(String str) {
		int j = 0;
		int k = 0;
		int i = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int ic = 0; ic < str.length(); ic++) {
			char c = str.charAt(ic);
			if (c=='('||c==')'||c=='['||c==']'||c=='{'||c=='}'||c=='-') {
				i++;
			}
		}
		if (i + k + j == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断[]{}()是否成对出现
	 * false 表示不成对出现
	 */

	public  boolean isDouble(String str) {
		// TODO Auto-generated method stub
		Stack<Character> sc=new Stack<Character>();
		try{
			char[] c=str.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i]=='('||c[i]=='['||c[i]=='{'||c[i]=='（'||c[i]=='【') {
					sc.push(c[i]);
				}
				else if (c[i]==')') {
					if (sc.peek()=='(') {
						sc.pop();
					}
				}else if (c[i]==']') {
					if (sc.peek()=='[') {
						sc.pop();
					}
				}else if (c[i]=='}') {
					if (sc.peek()=='{') {
						sc.pop();
					}
				}else if (c[i]=='）') {
					if (sc.peek()=='（') {
						sc.pop();
					}
				}else if (c[i]=='】') {
					if (sc.peek()=='【') {
						sc.pop();
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		if (sc.empty()) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 字串str是否为英文,数字和中文，不能有特殊符号
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChinese(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q == str.length()) {
			return true;
		}

		return false;
	}
	
	/**
	 * @param str
	 * @return 是否包含 # $ * | / ” “特殊字符
	 * true 为不包含特殊字符
	 */
	public boolean isContainSpecialCharact(String str) {
		if (str.contains("#")||str.contains("$")||str.contains("*")||str.contains("|")||str.contains("/")||str.contains("”")||str.contains("“")) {
			return false;
		}
		return true;
	}
	
	public boolean isEnglishAndDigitAndChineseAndOther(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='\''||c=='.'||c=='（'||c=='）'||c=='’'||c=='‘') {
				n++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}
	public boolean isEnglishAndDigitAndChineseAndOther2(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='\''||c=='.'||c=='—'||c=='-'||c=='-'||c=='_'||c=='\"'||c=='（'||c=='）'||c=='’'||c=='‘'||c=='”'||c=='“') {
				n++;
			}
//			if (c>=0&&c<=9) {
//				s++;
//			}
		}
//		Pattern pattern = Pattern.compile("[\\u0800-\\u9fa5\\x3130-\\x318f\\xAC00-\\xD7A3]");
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 字串s是否为数字、字母、汉字、中文符号顿号、‘’；和中英文状态符号。，-:[]{}() 公司信息校验：经营范围  门店信息页面：门店介绍
	 * 
	 * @param str
	 * @return true or false
	 */
	
	public boolean isEnglishAndDigitAndChineseAndOther3(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='['||c==']'||c=='{'||c=='}'||c=='（'||c=='）'||c=='【'||c=='】'||c=='。'||c=='、'||c=='‘'||c=='’'||c==':'||c=='：'||c=='；'||c=='-'||c=='，'||c=='.'||c==',') {
				n++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}
	

	/**
	 * 字串s是否为数字、大写字母、汉字、和中英文状态符号-[]{}()，不能仅为数字   商户姓名 法人姓名、授权人姓名
	 * @param s
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChineseAndOther4(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='['||c==']'||c=='（'||c=='）'||c=='【'||c=='】'||c=='{'||c=='}'||c=='-') {
				n++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}
	/**
	 * 字串s是否为数字、字母、汉字、中文符号顿号‘、’和中英文状态符号-:[]{}() 商户注册地址、 营业办公地址、门店地址
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChineseAndOther5(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z'||'A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='['||c==']'||c=='（'||c=='）'||c=='【'||c=='】'||c=='{'||c=='}'||c=='-'||c==':'||c=='：'||c=='、') {
				n++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}
	/**
	 * 字串s是否为数字、字母、汉字、中文符号‘’顿号‘、’和中英文状态符号-:[]{}() 商户注册名、商户简称、门店名称、门店简称
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChineseAndOther6(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		int n = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z'||'A' <= c && c <= 'Z') {
				k++;
			}
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c=='('||c==')'||c=='['||c==']'||c=='{'||c=='}'||c=='（'||c=='）'||c=='【'||c=='】'||c=='-'||c==':'||c=='：'||c=='、'||c=='‘'||c=='’') {
				n++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q+n == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 字串s是否仅为汉字
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isChinese(String name) {
		int idx = 0;
		name = name.trim();
		int len = name.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(name);
		while (m.find()) {
			idx++;
		}
		if (len == idx) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字串s是否仅为数字
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isAllDigit(String s) {
		int len = 0;
		for (int idx = 0; idx < s.length(); idx++) {
			if (Character.isDigit(s.charAt(idx))) {
				len++;
			}
		}
		if (len == s.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 字串s是否由英文和空格构成
	 * 
	 * @param s
	 * @return true or false
	 */
	public boolean isEnglishAndBackSpace(String s) {
		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == ' ') {
				len++;
			}
		}
		if (len == s.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 字串s是否超过maxLen(规则：全角字符2位，半角1位；汉字2位，字母和数字1位；)
	 * 
	 * @param s
	 *            and maxLen
	 * @return s是否超过最大长度maxLen,是返回false，否则true
	 */
	public boolean allowMaxLenthOfString(String s, int maxLen) {
		int num = 0;
		for (int i = 0; i < s.length(); i++) { 
			String tmp = s.substring(i, i + 1);
			if (isChinese(tmp)) {
				num += 2;
			} else {
				if (tmp.getBytes().length == 3) {
					num += 2;
				} else if (tmp.getBytes().length == 1) {
					num += 1;
				}
			}
		}
		if (num <= maxLen) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为手机号码（规则：11位数字，130-139,145,147-149,150-159,170,171,173,175-178,180-189，10649(以这个开头即可)）
	 * 13X 15X 18X 170 {0-35-9} 14{578} 17{135-9} 166 19{89} 
	 * @param phone
	 * @return true or false
	 */
	public boolean isPhoneNum2(String phone) {

		String re1 ="^(1[358]\\d{9})$";
		String re2 ="^(14[578]\\d{8})$";
		String re3 ="^(170[0-35-9]\\d{7})$";
		String re4 ="^(10649\\d+)$";
		String re5 ="^(17[135-9]\\d{8})$";
		String re6 ="^(166\\d{8})$";
		String re7 ="^(19[89]\\d{8})$";
		//130-139,145,147-149,150-159,170,171,173,175-178,180-189，10649
//		if (string.matches(ree) || isNumTel(string, 12)||string.matches(ree1)) {
		if(phone.matches(re1)||phone.matches(re2)||phone.matches(re3)||phone.matches(re4)
				||phone.matches(re5)||phone.matches(re6)||phone.matches(re7)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为手机号码（规则：11位数字，首位为1）
	 * 
	 * @param phone
	 * @return true or false
	 */
	public boolean isPhoneNum(String phone) {

		if (isAllDigit(phone)) {
			if (phone.length() != 0) {
				if (phone.length() != 11 || !phone.substring(0, 1).equals("1")) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断字串是否由数字和点构成
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isDigitAndDot(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '.') {
				len++;
			}
		}
		if (len == str.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字串是否由数字和点构成  必须带有小数点
	 * 
	 * @param inputPostcode
	 * @return true or false
	 */
	public boolean isDigitAndDot2(String str) {
		if (str.matches("[0-9]*(\\.)[0-9]*"))  
			return true;
		else
			return false;
	}
	
	/**
	 * 判断字串 是否只有数字和 -
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isg(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '-') {
				len++;
			}
		}
		if (len == str.length()) {
			return true;
		}
		return false;
	}
	/**
	 * 判断字串 是否只有数字、 字母 ‘-’
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isnc(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			
			if (('A'<=c && c<='Z')||('a'<=c && c<='z')||Character.isDigit(c) || c == '-') {
				len++;
			}
		}
		if (len == str.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 全是重复数字校验
	 * 
	 * @param numOrStr
	 * @return
	 */
	public  boolean equalStr(String numOrStr) {
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
			if (str != numOrStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	/**
	 * 根据出生日期判断是否大于18岁
	 * 
	 * @param birthDate
	 * @return 大于18岁，返回true,否则false
	 */
	public boolean isValidateBirth(String birthDate) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		String strBegin[] = birthDate.split("-");

		int birthYear = Integer.parseInt(strBegin[0]);

		if ((year - birthYear) < 18 || (year - birthYear) > 65) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 移除数字前面为零的字符
	 * 
	 * @param digit
	 * @return 一串由数字组成的字串
	 */
	public String removeFontZero(String digit) {
		if (digit == null || digit.equals("")) {
			return "";
		}
		if (isJustDigitStar(digit) && !isExistStar(digit)) {

			String[] str = digit.split("\\.", 2);
			if (str.length > 1) {
				if (str[1].contains(".")) {
					return "1";
				}
			}
			DecimalFormat d = new DecimalFormat("0.00");

			digit = d.format(Double.parseDouble(digit));// .split("\\.")[0];
		} else {

			return "1";
		}
		return digit;
	}

	/**是否包含特殊符号|#$&
	 * 
	 * @param str
	 * @return
	 */
	public  boolean isExist(String str){
		boolean isExist = false;
		if(str==null ||"".equals(str)){
			return isExist;
		}
		
		if((str.indexOf("|")!=-1)||(str.indexOf("#")!=-1) 
				||(str.indexOf("$")!=-1)||(str.indexOf("&")!=-1)){
			isExist = true;
		}
		return isExist;
		
	}
	
	/**是否包含特殊符号& # $ | * 
	 * 
	 * @param str
	 * @return
	 */
	public  boolean isExistsd(String str){
		boolean isExist = false;
		if(str==null ||"".equals(str)){
			return isExist;
		}
		
		if((str.indexOf("&")!=-1)||(str.indexOf("#")!=-1) 
				||(str.indexOf("$")!=-1)||(str.indexOf("|")!=-1)||(str.indexOf("*")!=-1)){
			isExist = true;
		}
		return isExist;
		
	}
	/**是否包含特殊符号|#$&/“”*
	 * 
	 * @param str
	 * @return
	 */
	public  boolean isExistUp(String str){
		boolean isExist = false;
		if(str==null ||"".equals(str)){
			return isExist;
		}
		
		if((str.indexOf("|")!=-1)||(str.indexOf("#")!=-1) 
				||(str.indexOf("$")!=-1)||(str.indexOf("&")!=-1)
				||(str.indexOf("*")!=-1)||(str.indexOf("/")!=-1)
				||(str.indexOf("”")!=-1)||(str.indexOf("“")!=-1)){
			isExist = true;
		}
		return isExist;
		
	}
	/**
	 * 判断是否是数字和"."号组成的
	 */
	public boolean isJustDigitStar(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '.') {
				len++;
			}
		}

		if (len == str.length()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断仅有"."号组成的
	 * 
	 * @param str
	 * @return
	 */
	public boolean isExistStar(String str) {

		int len = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (str.charAt(idx) == '.') {
				len++;
			}
		}

		if (len == str.length()) {
			return true;
		}

		return false;
	}

	public int countStr(String str, String charstr) {
		int count = 0;
		char find = charstr.charAt(0);
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == find) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 电话的保存
	 * 
	 * @param strs
	 * @return
	 */
	public String[] savaTetlePhone(String strs) {
		if (!strs.equals("")) {
			String[] abc = new String[3];
			abc[0] = strs.split("-", 2)[0];
			String strsss = strs.split("-", 2)[1];
			if (strsss.contains("-")) {
				abc[1] = strsss.split("-", 2)[0];
				abc[2] = strsss.split("-", 2)[1];
			} else {
				abc[1] = strsss;
				abc[2] = "";
			}
			return abc;
		} else {
			return new String[] { "", "", "" };
		}

	}

	/**
	 * 时间中间去除“-”
	 * 
	 * @param strs
	 * @return
	 */
	public String dateChange(String date) {
		StringBuilder sb = new StringBuilder();
		String[] dateStr = date.split("-");
		for (int i = 0; i < dateStr.length; i++) {
			sb.append(dateStr[i]);
		}
		return sb.toString();
	}

	/**
	 * 将手机号码中间4为隐藏
	 * 
	 * @param str
	 *            传入要隐藏的字符串
	 * @return
	 */
	public String[] hiddenMobileInformation(String str) {
		String[] mobileStr = new String[2];
		mobileStr[0] = str.substring(0, 3) + "****" + str.substring(8, 10);
		mobileStr[1] = str;
		return mobileStr;
	}

	/**
	 * 将字符串后4位隐藏
	 * 
	 * @param str
	 *            传入要隐藏的字符串
	 * @return
	 */
	public String[] hiddenInformationString(String str) {
		String[] mobileStr = new String[2];
		int in = str.length();
		mobileStr[0] = str.substring(0, (in - 4)) + "****";
		mobileStr[1] = str;
		return mobileStr;
	}

	/**
	 * 隐藏指定的字符串
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public String hiddenInformationString(String str, int start, int end) {
		if (str.length() >= end) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < end - start; i++) {
				sb.append("*");
			}
			str = str.substring(0, start) + sb.toString() + str.substring(end);
		}
		return str;
	}

	/**
	 * 获取当前系统时间 格式：yyyy-mm-dd hh:mm:ss
	 * 
	 * @return resultStr
	 */
	public String GetCurrentTime() {
		String resultStr;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		String sec = String.valueOf(c.get(Calendar.SECOND));

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		if (sec.length() == 1) {
			sec = "0" + sec;
		}

		resultStr = year + "-" + month + "-" + day + " " + hour + ":" + minute
				+ ":" + sec;
		return resultStr;
	}

	/**
	 * 获取当前系统日期 格式：yyyymmdd
	 * 
	 * @return resultStr
	 */
	public String GetCurrentDate() {
		String resultStr;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}

		resultStr = year + month + day;
		return resultStr;
	}

	/**
	 * 获取当前系统日期 格式：yyyy-mm-dd
	 * 
	 * @return resultStr
	 */
	public String GetCurrentDate1() {
		String resultStr;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}

		resultStr = year + "-" + month + "-" + day;
		return resultStr;
	}

	/**
	 * 两个二维数组合并到一个
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public String[][] mergeArray(String[][] array1, String[][] array2) {
		int length1 = array1 != null && array1.length > 0 ? array1.length : 0;
		int length = length1
				+ (array2 != null && array2.length > 0 ? array2.length : 0);
		String[][] array = new String[length][];
		for (int i = 0; i < length1; i++) {
			array[i] = array1[i];
		}
		if (array2 != null && array2.length > 0) {
			for (int i = length1, j = 0; j < array2.length; i++, j++) {
				array[i] = array2[j];
			}
		}
		return array;
	}

	/**
	 * 设置选择字体颜色为白色,未选中为灰色
	 * 
	 * @param btns
	 *            btns[0]为选中
	 */
	public void setTextColor(RadioButton[] btns) {
		for (int i = 0; i < btns.length; i++) {
			if (i == 0) {
				btns[i].setTextColor(Color.parseColor("#FFFFFF"));
			} else {
				btns[i].setTextColor(Color.parseColor("#808080"));
			}
		}
	}

	/**
	 * 得到本机号码
	 * @param context
	 * @return
	 */
	public String getMyPhoneNumber(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = manager.getLine1Number();
		if (number != null) {
			MLog.getInstance().writeLog(
					"NewMerchant...JudgeMethods...getMyPhoneNumber(): "
							+ number);
			return number.replace("+86", "");
		} else {
			MLog.getInstance()
					.writeLog(
							"NewMerchant...JudgeMethods...getMyPhoneNumber(): 凭证错误，请先登录!");
			return "";
		}
	}
	
	/**
	 * 输入的时间不能晚于当前时间
	 * 
	 * @param beginTime 
	 * @return
	 */
	
	public  boolean validate(String beginTime) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String strBegin[] = beginTime.split("-");

		int beginYear = Integer.parseInt(strBegin[0]);
		int beginMonth = Integer.parseInt(strBegin[1]);
		
		int beginDay = Integer.parseInt(strBegin[2].trim());
		// 输入的开始日期不能晚于当前时间!
		if ((beginYear * 372 + beginMonth * 31 + beginDay) <= (year * 372
				+ month * 31 + day)) {
			return true;
		} else {
			return false;
		}
	}
	
	public  boolean compareDate(String beginTime) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String strBegin[] = beginTime.split("-");

		int beginYear = Integer.parseInt(strBegin[0]);
		int beginMonth = Integer.parseInt(strBegin[1]);
		
		int beginDay = Integer.parseInt(strBegin[2].trim());
		// 输入的开始日期不能晚于当前时间!
		if ((beginYear * 372 + beginMonth * 31 + beginDay) < (year * 372
				+ month * 31 + day)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**开始日期与结束日期间隔gap天之内
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param gap 开始日期结束日期间隔天数
	 * @return 
	 */
	public static boolean validateTime(String beginTime,String  endTime, int gap) {
		
		String strBegin[] = beginTime.split("-");
		String strEnd[] = endTime.split("-");

		int beginYear = Integer.parseInt(strBegin[0].trim());
		int beginMonth = Integer.parseInt(strBegin[1].trim());
		int beginDay = Integer.parseInt(strBegin[2].trim());
		
		int endYear = Integer.parseInt(strEnd[0].trim());
		int endMonth = Integer.parseInt(strEnd[1].trim());
		int endDay = Integer.parseInt(strEnd[2].trim());
		// 输入的开始日期不能晚于当前时间!
		if ((beginYear * 372 + beginMonth * 31 + beginDay+gap) <= (endYear * 372
				+ endMonth * 31 + endDay)) {
			return true;
		} else {
			return false;
		}
	}
}

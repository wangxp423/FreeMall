package com.aero.droid.dutyfree.talent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxp 2015/08/11 正则表达式工具类
 */
public class PatternUtil {

	/**
	 * 判断邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		String regex = null;
		if (email != null) {
			regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*";
			flag = checkStr(regex, email);
		}
		return flag;
	}

	/**
	 * 判断手机号码 并且满足号码格式
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkTelPhone(String mobile) {
		boolean flag = false;
		if (mobile != null && !"".equals(mobile)) {
			String regex = "^(1(([358][0-9])|(47)))\\d{8}$";// 判定电话号码
			flag = checkStr(regex, mobile);
		}
		return flag;
	}

	/**
	 * 判断密码 字符串 字母或者数字
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		boolean flag = false;
		String regex = null;
		int len = password.length();
		if (len < 6 || len > 18) {
			flag = false;
		} else {
			regex = "([a-z]|[A-Z]|[0-9]|[_*])+";// 判定数字或者字母或者下划线
			flag = checkStr(regex, password);
		}
		return flag;
	}
	/**
	 * 判断昵称 中英文，数字，下划线
	 *
	 * @param nickName
	 * @return
	 */
	public static boolean checkNickName(String nickName) {
		boolean flag = false;
		String regex = null;
		int len = nickName.replaceAll("[^\\x00-\\xff]", "**").length();
		if (len < 6 || len > 20) {
			flag = false;
		} else {
			regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
			flag = checkStr(regex, nickName);
		}
		return flag;
	}
	/**
	 * 判断 不能超过十个汉字
	 *
	 * @param text
	 * @return
	 */
	public static boolean checkHanziCount(String text) {
		boolean flag = false;
		String regex = null;
		regex = "^[\u4e00-\u9fa5]{0,10}$";
		flag = checkStr(regex, text);
		return flag;
	}

	/**
	 * 判断执业医师证书编码 18位数字 可为null
	 * 
	 * @param doctorLicence
	 * @return
	 */
	public static boolean checkDoctorLicenceStr(String doctorLicence) {
		boolean flag = false;
		String regex = null;
		if (doctorLicence != null && !"".equals(doctorLicence)) {
			regex = "^\\d{15}$";// 判定执业医师证书编码
			flag = checkStr(regex, doctorLicence);
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 正则检查字符串
	 * 
	 * @param regex
	 * @param input
	 * @return
	 */
	private static boolean checkStr(String regex, String input) {

		boolean flag = false;
		if (regex != null) {
			Pattern p = Pattern.compile(regex);
			try{
				Matcher m = p.matcher(input);
				flag = m.matches();
			}
			catch(Exception e)
			{
				return flag;
			}

		}
		return flag;
	}

	/**
	 * 判定数字或者字母或者中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkAll(String str) {
		boolean flag = false;
		String regex = null;
		regex = "([a-z]|[A-Z]|[0-9]|[\u4e00-\u9fa5])+";// 判定数字或者字母或者中文
		flag = checkStr(regex, str);
		return flag;
	}

	/**
	 * 判断用户名
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkContainChinese(String str) {
		boolean flag = false;
		String regex = null;
		regex = "([\u4e00-\u9fa5])+";// 判定中文
		flag = checkStr(regex, str);
		return flag;
	}


	/**
	 * 判断真实姓名 姓名为汉字，并且至少两位
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkTrueName(String str) {
		boolean flag = false;
		String regex = null;
		regex = "([\u4e00-\u9fa5])+";// 判定中文
		flag = checkStr(regex, str);
		if (flag) {
			return str.length() > 1 ? true : false;
		} else
			return flag;
	}

	public String[] getFileByPattern(String content) {

		String[] strs = null;

		return strs;

	}

	/**
	 * 过滤标点符号
	 * 
	 * @param str
	 * @return
	 */
	public static String filterPunctuation(String str, String replaceStr) {
		if (str != null)
			str = str.replaceAll("\\pP|\\pS|\\s", replaceStr);
		return str;
	}

	/**
	 * 过滤单个重复字符
	 * 
	 * @param str
	 * @return
	 */
	public static String filterRepitChar(String str) {
		if (str != null) {
			str = str.replaceAll("(?s)(.)(?=.*\\1)", "");
		}
		return str;

	}
	/**
	 * 验证是否是手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^1(3[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}

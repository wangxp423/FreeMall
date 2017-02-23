package com.aero.droid.dutyfree.talent.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class CheckInternetUtil {
	/**
	 * @author wangxp 2015/08/11 检查网络状况工具类
	 */
	public enum NetState {

		unNetConnect, WifiConnect, Net3GComnect, Net2GComnect;
	}

	/**
	 * 判断是否有网络
	 * 
	 * @param mcontext
	 * @return
	 */
	public static boolean hasNetConnect(Context mcontext) {

		NetState netstate = JudgeCurrentNetState(mcontext);
		if (netstate.equals(NetState.unNetConnect)) {
			return false;
		}
		return true;
	}

	/***
	 * 移动网络
	 * 
	 * @param mcontext
	 * @return
	 */
	public static boolean isMobileNetConnect(Context mcontext) {
		NetState netstate = JudgeCurrentNetState(mcontext);
		if (netstate.equals(NetState.Net2GComnect)
				|| netstate.equals(NetState.Net3GComnect)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络状态
	 */
	public static NetState JudgeCurrentNetState(Context mcontext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mcontext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			TelephonyManager mTelephony = (TelephonyManager) mcontext
					.getSystemService(Context.TELEPHONY_SERVICE);
			int netType = info.getType();
			int netSubtype = info.getSubtype();
			if (netType == ConnectivityManager.TYPE_WIFI) {// WIFI
				return NetState.WifiConnect;
			} else if (netType == ConnectivityManager.TYPE_MOBILE
					&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
					&& !mTelephony.isNetworkRoaming()) {// 3G
				return NetState.Net3GComnect;
			} else {
				return NetState.Net2GComnect;
			}
		}
		return NetState.unNetConnect;
	}
}

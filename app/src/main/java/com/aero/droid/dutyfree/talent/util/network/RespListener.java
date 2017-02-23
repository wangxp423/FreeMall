package com.aero.droid.dutyfree.talent.util.network;

import org.json.JSONObject;

/**
 * @author wangxp 2015/08/17 请求回调接口
 */
public interface RespListener {
	/**
	 * 成功回调
	 * @param data
	 * @param code
	 * @param msg
	 */
	void onRespSucc(JSONObject data, String code, String msg);

	/**
	 * 失败回调
	 * @param code
	 * @param msg
	 */
	void onRespError(String code, String msg);
	
}

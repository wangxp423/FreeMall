package com.aero.droid.dutyfree.talent.util;



import com.aero.droid.dutyfree.talent.util.encode.Encryption;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Verify {

	/*
	 * appId:包名 appKey:后台根据包名生成
	 */

	/**
	 * 根据传递的参数生成token
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getToken(HashMap<String, String> params, String appKey) {
		String token = "";
		if (params.size() > 0) {
			Iterator<String> it = params.keySet().iterator();
			List<String> list = new ArrayList<String>();
			while (it.hasNext()) {
				String paramName = (String) it.next();
				String paramValue = params.get(paramName);
				// 处理你得到的参数名与值
				if (!paramName.equals("token")) {
					list.add(paramName + "=" + paramValue);
				}
			}
			Collections.sort(list);
			String paramsString = "";
			for (String s : list) {
				paramsString += s + ",";
			}
			
			try {
				String paramMD5 = Encryption.md5(paramsString.substring(0,
						paramsString.length() - 1));
				String newMD5 = paramMD5.substring(paramMD5.length() - 1,
						paramMD5.length())
						+ paramMD5.substring(1, paramMD5.length() - 1)
						+ paramMD5.substring(0, 1) + appKey;
				token = Encryption.md5(newMD5);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return token;
	}

}

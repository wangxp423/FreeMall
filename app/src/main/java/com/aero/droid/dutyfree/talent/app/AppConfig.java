package com.aero.droid.dutyfree.talent.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.Verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * 
 * @author mz
 * 
 */
public class AppConfig {
	private final static String APP_CONFIG = "config";

	private Context mContext;
	// 单例模式
	private static AppConfig appConfig;

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	// URL身份认证
	public final static String CONF_AUTHPARAMS_SDKVERSION = "sdkVersion";
	public final static String CONF_AUTHPARAMS_APPID = "appId";
	public final static String CONF_AUTHPARAMS_CURRENT_TIME_ST = "ts";
	public final static String CONF_AUTHPARAMS_APPTOKEN = "token";


	private static String APPID;
	private static String APPKEY;
	private static String SDK_VERSION = "1.0";

	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
//			initAppIdAndAppKey(context);
		}
		return appConfig;
	}

	/**
	 * 获取SDK验证有效性的Params
	 * @param result
	 * 
	 * @return
	 */
	public static HashMap<String, String> getSdkAuthParams(LinkedHashMap<String, String> result) {

//		HashMap<String, String> result = new HashMap<String, String>();

		result.put(AppConfig.CONF_AUTHPARAMS_APPID, AppConfig.APPID);
		result.put(AppConfig.CONF_AUTHPARAMS_SDKVERSION, SDK_VERSION);
		try {
			result.put(AppConfig.CONF_AUTHPARAMS_CURRENT_TIME_ST, TimeFormatUtil.formatDate("yyMMddHHmmss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put(AppConfig.CONF_AUTHPARAMS_APPTOKEN,
				Verify.getToken(result, AppConfig.APPKEY));

		return result;
	}

	/**
	 * 初始化APPID 获取App的包名<br>
	 * 初始化APP_KEY 获取在manifest.xml中注册的数据
	 * 
	 * @param context
	 */
	public static void initAppIdAndAppKey(Context context) {
		if (TextUtils.isEmpty(AppConfig.APPID)) {
			AppConfig.APPID = context.getPackageName();
			String appKey = "";
			try {
				ApplicationInfo applicationInfo = context.getPackageManager()
						.getApplicationInfo(context.getPackageName(),
								PackageManager.GET_META_DATA);
				appKey = applicationInfo.metaData.getString("Dutyfree_Key");
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			AppConfig.APPKEY = appKey;
		}
	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void setAccessInfo(String accessToken, String accessSecret,
			long expiresIn) {
		// if (accessInfo == null)
		// accessInfo = new AccessInfo();
		// accessInfo.setAccessToken(accessToken);
		// accessInfo.setAccessSecret(accessSecret);
		// accessInfo.setExpiresIn(expiresIn);
		// // 保存到配置
		// this.setAccessToken(accessToken);
		// this.setAccessSecret(accessSecret);
		// this.setExpiresIn(expiresIn);
	}


	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);

			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator
					+ APP_CONFIG);

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
}

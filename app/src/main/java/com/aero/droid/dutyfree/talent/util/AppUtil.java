package com.aero.droid.dutyfree.talent.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

/**
 * @author wangxp 2015/08/11 获取一些设备相关信息
 */
public final class AppUtil {

    //获取设备Id
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    //获取应用包名
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    //获取应用版本号
    public static int getVersionCode(Context context) {

        try {
            int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //获取应用版本名称
    public static String getVersionName(Context context) {

        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取manifest配置的免税品key
    public static String getDutyKey(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String appKey = applicationInfo.metaData.getString("Dutyfree_Key");
            return appKey;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.aero.droid.dutyfree.talent.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.app.AppConfig;
import com.aero.droid.dutyfree.talent.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

/**
 * Created by wangxp on 2015/7/19.
 * 用户信息 工具类
 */
public class UserUtil {

    /**
     * 清除用户登录信息
     *
     * @param context
     */
    public static void cleanLoginInfo(Context context) {
        setUserInfo(context,"");
        setUserId(context,"");
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static User getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String userInfo = sp.getString("UserInfo", "");
        try {
            if (!TextUtils.isEmpty(userInfo)){
                JSONObject json = new JSONObject(userInfo);
                User user = JsonAnalysis.getLoginUser(json);
                return user;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息
     * @param context
     * @param userinfo
     */
    public static void setUserInfo(Context context, String userinfo) {
        SharedPreferences sp = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        sp.edit().putString("UserInfo", userinfo).commit();
    }

    /**
     * 获取用户ID
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String userId = sp.getString("UserId", "");
        return userId;
    }

    /**
     * 保存用户Id
     * @param context
     * @param userId
     */
    public static void setUserId(Context context, String userId) {
        SharedPreferences sp = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        sp.edit().putString("UserId", userId).commit();
    }
}

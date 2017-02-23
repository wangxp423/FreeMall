package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;
import android.view.animation.Animation;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/11/27
 * Desc : 启动页交互器类
 */
public interface AppStartInteractor extends ShopBagsInteractor{
    /**
     * 跳转到主页
     */
    void go2MainActivity(Context context);

    /**
     * 跳转到app轮播图介绍页
     */
    void go2ViewPagerActivity(Context context);

    /**
     * 获取启动页的动画
     *
     * @param context
     * @return
     */
    Animation getImgAnimation(Context context);

    /**
     * 获取一些Url(关于我们，用户协议等)
     *
     * @param context
     * @param params
     * @param log_tag
     */
    void getSomeFunctionUrl(String log_tag,Context context, HashMap<String, String> params);
}

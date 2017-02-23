package com.aero.droid.dutyfree.talent.interactor;

import android.content.Context;

import com.aero.droid.dutyfree.talent.util.network.RespListener;

import java.util.HashMap;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 商品品牌分类 交互器 类
 */
public interface BrandCategoryInteractor {
    /**
     * 获取品牌分类数据
     * @param log_tag
     * @param event_tag
     * @param context
     * @param params
     */
    void getBrandCategoryData(String log_tag,int event_tag,Context context,HashMap<String,String> params);
}

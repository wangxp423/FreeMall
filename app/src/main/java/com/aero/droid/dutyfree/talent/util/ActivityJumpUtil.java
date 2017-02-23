package com.aero.droid.dutyfree.talent.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aero.droid.dutyfree.talent.activity.BrandGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.DiscountAeraActivity;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.InviteFriendActivity;
import com.aero.droid.dutyfree.talent.activity.MainActivity;
import com.aero.droid.dutyfree.talent.activity.MyTaskActivity;
import com.aero.droid.dutyfree.talent.activity.PosterGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.SpecialGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.TopicGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.UserInfoActivity;
import com.aero.droid.dutyfree.talent.activity.WebViewActivity;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;

/**
 * Author : wangxp
 * Date : 2015/12/18
 * Desc : 首页 轮播图 页面跳转工具类
 */
public class ActivityJumpUtil {
    public static void activityJump(Context activity, HandpickBanner banner) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (Constants.ACTIVITY_TO_IMG == Integer.parseInt((banner.getAcType()))) {
            //这里操作暂定为  商品详情 的轮播图  无点击事件或者留着以后查看大图操作
        } else if (Constants.ACTIVITY_TO_H5 == Integer.parseInt(banner.getAcType())) {
            //H5 页面
            bundle.putString("url", banner.getAcParams());
            intent.setClass(activity, WebViewActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        } else if (Constants.ACTIVITY_TO_ACTIVE == Integer.parseInt(banner.getAcType())) {
            bundle.putString("activeId", banner.getAcParams());
            intent.putExtras(bundle);
            if (Constants.ACTIVE_TO_SPECIAL == Integer.parseInt(banner.getInsideLayout())) {
                //专题活动
                intent.setClass(activity, SpecialGoodsActivity.class);
            } else if (Constants.ACTIVE_TO_POSTER == Integer.parseInt(banner.getInsideLayout())) {
                //海报活动
                intent.setClass(activity, PosterGoodsActivity.class);
            } else if (Constants.ACTIVE_TO_TOPIC == Integer.parseInt(banner.getInsideLayout())) {
                //专场活动
                intent.setClass(activity, TopicGoodsActivity.class);
            }
            activity.startActivity(intent);
        } else if (Constants.ACTIVITY_TO_GOODDETAIL == Integer.parseInt(banner.getAcType())) {
            //商品详情
            bundle.putString("goodId", banner.getAcParams());
            bundle.putString("srcType", "0");
            bundle.putString("srcId", "0");
            intent.setClass(activity, GoodsDetailActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_DISCOUNT == Integer.parseInt(banner.getAcType())) {
            //折扣专区
            intent.setClass(activity, DiscountAeraActivity.class);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_INVITE == Integer.parseInt(banner.getAcType())) {
            //邀请得奖  应该 H5
            intent.setClass(activity, InviteFriendActivity.class);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_TASK == Integer.parseInt(banner.getAcType())) {
            //特权任务
            intent.setClass(activity, MyTaskActivity.class);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_USERDETAIL == Integer.parseInt(banner.getAcType())) {
            //个人资料
            intent.setClass(activity, UserInfoActivity.class);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_GOODCATEGORY == Integer.parseInt(banner.getAcType())) {
            //该 商品分类
            bundle.putString("paramType","1");
            bundle.putString("categoryId",banner.getAcParams());
            intent.setClass(activity, BrandGoodsActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_BRANDCATEGORY == Integer.parseInt(banner.getAcType())) {
            //该 品牌商品
            bundle.putString("paramType","2");
            bundle.putString("categoryId",banner.getAcParams());
            intent.setClass(activity, BrandGoodsActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_USER == Integer.parseInt(banner.getAcType())) {
            //首页(个人中心)
            bundle.putInt("jumpType",3);
            intent.setClass(activity, MainActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_MAIN == Integer.parseInt(banner.getAcType())) {
            //首页(精选)
            bundle.putInt("jumpType",0);
            intent.setClass(activity, MainActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }else if (Constants.ACTIVITY_TO_USER == Integer.parseInt(banner.getAcType())) {
            //认证页面(注册)
            intent.setClass(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}

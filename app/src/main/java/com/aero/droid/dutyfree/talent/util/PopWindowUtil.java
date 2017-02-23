package com.aero.droid.dutyfree.talent.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.activity.GoodsDetailActivity;
import com.aero.droid.dutyfree.talent.activity.LoginActivity;
import com.aero.droid.dutyfree.talent.activity.PosterGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.SecondCategoryActivity;
import com.aero.droid.dutyfree.talent.activity.SpecialGoodsActivity;
import com.aero.droid.dutyfree.talent.activity.TopicGoodsActivity;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.bean.Advertise;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.listener.ChoiseAirportDateListener;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.widgets.DateWheelLayout;
import com.bumptech.glide.Glide;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.utils.OauthHelper;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import org.json.JSONObject;

import java.util.HashMap;

public class PopWindowUtil {
    /**
     * 分享窗口
     *
     * @param context
     * @param imgUrl
     * @param contentUrl
     * @param content
     * @param shareListener
     * @return
     */
    public static PopupWindow showSharePop(final Activity context, String title, final String imgUrl, final String contentUrl, final String content, final SnsPostListener shareListener) {
        final UMSocialService mController = UMServiceFactory
                .getUMSocialService("com.umeng.share");
        if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.WEIXIN)) {
            // 支持微信朋友圈
//            String appId = "wx775967fe97775153";
//            String appSecret = "81f3a811e1755a33f44d9a78972be176";
//            UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
//            wxHandler.addToSocialSDK();
//            UMWXHandler wxCircleHandler = new UMWXHandler(context, appId,
//                    appSecret);
//            wxCircleHandler.setToCircle(true);
//            wxCircleHandler.addToSocialSDK();
        }
        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_share, null);
        final PopupWindow sharePop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        sharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = context.getWindow().getAttributes();
                params.alpha = 1f;
                context.getWindow().setAttributes(params);
            }
        });
        sharePop.setOutsideTouchable(true);
        sharePop.setFocusable(true);
        sharePop.setBackgroundDrawable(new BitmapDrawable());
        sharePop.setAnimationStyle(R.style.share_pop_style);
        sharePop.update();
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        context.getWindow().setAttributes(params);
        ImageView finish = (ImageView) view.findViewById(R.id.share_title_left);
        TextView share_title = (TextView) view.findViewById(R.id.share_title_content);
        share_title.setText(title);
        TextView share_weixin = (TextView) view.findViewById(R.id.share_weixin);
        TextView share_friend = (TextView) view.findViewById(R.id.share_friend);
        TextView share_sina = (TextView) view.findViewById(R.id.share_sina);
        finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePop.dismiss();
            }
        });
        share_weixin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //设置微信分享
                WeiXinShareContent weixinContent = new WeiXinShareContent();
                //设置分享文字
                weixinContent.setShareContent(content);
                //设置title
                weixinContent.setTitle(content);
                //设置分享内容跳转URL
                weixinContent.setTargetUrl(contentUrl);
                //设置分享图片
                if (!TextUtils.isEmpty(imgUrl)) {
                    weixinContent.setShareImage(new UMImage(context, imgUrl));
                } else {
                    weixinContent.setShareImage(new UMImage(context, R.mipmap.square_normal_img));
                }
                if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.WEIXIN) && TextUtils.isEmpty(UserUtil.getUserId(context))) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    context.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
                } else if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.WEIXIN)) {
                    UMSocialService loginController = (UMSocialService) UMServiceFactory
                            .getUMSocialService("com.umeng.login");
                    loginController.doOauthVerify(context, SHARE_MEDIA.WEIXIN,
                            new SocializeListeners.UMAuthListener() {
                                @Override
                                public void onStart(SHARE_MEDIA platform) {
                                    Toast.makeText(context, R.string.oauth_start,
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(SocializeException e,
                                                    SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(Bundle value,
                                                       SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_complete, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel(SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_cancel, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    mController.setShareMedia(weixinContent);
                    mController.postShare(context, SHARE_MEDIA.WEIXIN, shareListener);
                    sharePop.dismiss();
                }

            }
        });
        share_friend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //设置微信分享
                CircleShareContent circleContent = new CircleShareContent();
                //设置分享文字
                circleContent.setShareContent(content);
                //设置title
                circleContent.setTitle(content);
                //设置分享内容跳转URL
                circleContent.setTargetUrl(contentUrl);
                //设置分享图片
                if (!TextUtils.isEmpty(imgUrl)) {
                    circleContent.setShareImage(new UMImage(context, imgUrl));
                } else {
                    circleContent.setShareImage(new UMImage(context, R.mipmap.square_normal_img));
                }
                if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.WEIXIN) && TextUtils.isEmpty(UserUtil.getUserId(context))) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    context.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
                } else if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.WEIXIN)) {
                    UMSocialService loginController = (UMSocialService) UMServiceFactory
                            .getUMSocialService("com.umeng.login");
                    loginController.doOauthVerify(context, SHARE_MEDIA.WEIXIN,
                            new SocializeListeners.UMAuthListener() {
                                @Override
                                public void onStart(SHARE_MEDIA platform) {
                                    Toast.makeText(context, R.string.oauth_start,
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(SocializeException e,
                                                    SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(Bundle value,
                                                       SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_complete, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel(SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_cancel, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    mController.setShareMedia(circleContent);
                    mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE, shareListener);
                    sharePop.dismiss();
                }

            }
        });
        share_sina.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.SINA) && TextUtils.isEmpty(UserUtil.getUserId(context))) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    context.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
                } else if (!OauthHelper.isAuthenticated(context, SHARE_MEDIA.SINA)) {
                    UMSocialService loginController = (UMSocialService) UMServiceFactory
                            .getUMSocialService("com.umeng.login");
                    // 添加新浪SSO登录
                    loginController.getConfig().setSsoHandler(new SinaSsoHandler());
                    loginController.doOauthVerify(context, SHARE_MEDIA.SINA,
                            new SocializeListeners.UMAuthListener() {
                                @Override
                                public void onStart(SHARE_MEDIA platform) {
                                    Toast.makeText(context, R.string.oauth_start,
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(SocializeException e,
                                                    SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete(Bundle value,
                                                       SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_complete, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel(SHARE_MEDIA platform) {
                                    Toast.makeText(
                                            context, R.string.oauth_cancel, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // 设置分享内容
                    mController.setShareContent(content + "," + contentUrl);
                    // 设置分享图片, 参数2为图片的url地址
                    if (!TextUtils.isEmpty(imgUrl)) {
                        mController.setShareMedia(new UMImage(context, imgUrl));
                    } else {
                        mController.setShareMedia(new UMImage(context, R.mipmap.square_normal_img));
                    }
                    mController.openShare(context, true);
                    mController.postShare(context, SHARE_MEDIA.SINA, shareListener);
                    sharePop.dismiss();
                }
            }
        });

        sharePop.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        return sharePop;
    }

    /**
     * 广告窗口
     *
     * @param context
     * @param advert
     * @return
     */
    public static PopupWindow showAdvertPop(final Activity context, final Advertise advert) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.advert_layout, null);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
        int heigh = width / 5 * 6;
        final PopupWindow advertPop = new PopupWindow(view, width, heigh);
        advertPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = context.getWindow().getAttributes();
                params.alpha = 1f;
                context.getWindow().setAttributes(params);
            }
        });
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        context.getWindow().setAttributes(params);
        advertPop.setOutsideTouchable(true);
        advertPop.setFocusable(true);
        advertPop.setBackgroundDrawable(new BitmapDrawable());
//        advertPop.setAnimationStyle(R.style.share_pop_style);
        advertPop.update();
        ImageView productImg = (ImageView) view.findViewById(R.id.advert_product_img);
        ImageView confirmImg = (ImageView) view.findViewById(R.id.advert_confirm_btn);
        ImageView finish = (ImageView) view.findViewById(R.id.advert_finish);
        Glide.with(context).load(advert.getAdvertImage()).into(productImg);
        Glide.with(context).load(advert.getBtnImage()).into(confirmImg);
        confirmImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(advert.getRuleType()) && advert.getRuleType().equals("1")) {//进入活动
                    if (!TextUtils.isEmpty(advert.getRuleItemType()) && advert.getRuleItemType().equals("1")) {
                        intent.setClass(context, SpecialGoodsActivity.class);
                        bundle.putString("activeId", advert.getRuleItemId());
                        bundle.putString("title", advert.getAdvertName());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (!TextUtils.isEmpty(advert.getRuleItemType()) && advert.getRuleItemType().equals("2")) {
                        intent.setClass(context, PosterGoodsActivity.class);
                        bundle.putString("activeId",advert.getRuleItemId());
                        bundle.putString("title",advert.getAdvertName());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (!TextUtils.isEmpty(advert.getRuleItemType()) && advert.getRuleItemType().equals("3")) {
                        intent.setClass(context, TopicGoodsActivity.class);
                        bundle.putString("activeId", advert.getRuleItemId());
                        bundle.putString("title", advert.getAdvertName());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (!TextUtils.isEmpty(advert.getRuleItemType()) && advert.getRuleItemType().equals("4")) {
//                        intent = new Intent(context, BargainActivity.class);
//                        intent.putExtra("activeId", advert.getRuleItemId());
//                        intent.putExtra("title", advert.getAdvertTitle());
//                        intent.putExtra("type", advert.getRuleItemType());
//                        context.startActivity(intent);
                    }
                    advertPop.dismiss();
                } else if (!TextUtils.isEmpty(advert.getRuleType()) && advert.getRuleType().equals("2")) {//进入商品详情
                    intent.setClass(context, GoodsDetailActivity.class);
                    bundle.putString("goodId", advert.getRuleItemId());
                    bundle.putString("srcType", "0");
                    bundle.putString("srcId", "0");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    advertPop.dismiss();
                } else if (!TextUtils.isEmpty(advert.getRuleType()) && advert.getRuleType().equals("3")) {//打开分组
                    intent.setClass(context, SecondCategoryActivity.class);
                    bundle.putString("discoverId", advert.getRuleItemId());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    advertPop.dismiss();
                } else if (!TextUtils.isEmpty(advert.getRuleType()) && advert.getRuleType().equals("4")) {//进入优惠券
                    if (TextUtils.isEmpty(UserUtil.getUserId(context))) {
                        intent.setClass(context, LoginActivity.class);
                        intent.putExtra("isFromGetCoupons", true);
                        context.startActivity(intent);
                    } else {
                        //添加优惠券
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("couponId", advert.getRuleItemId());
                        String userId = UserUtil.getUserId(context);
                        params.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
                        OkHttpRequest.okHttpGet(context, Url.ADDCOUPON, params, new RespListener() {
                            @Override
                            public void onRespSucc(JSONObject data, String code, final String msg) {
                                LogUtil.v("JSON", "广告中领取优惠券 = " + data.toString());
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showShortToast(context, msg);
                                        advertPop.dismiss();
                                    }
                                });

                            }

                            @Override
                            public void onRespError(String code, final String msg) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showShortToast(context, msg);
                                        advertPop.dismiss();
                                    }
                                });

                            }
                        });
                    }
                } else {
                }
            }
        });
        finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                advertPop.dismiss();
            }
        });
        //根据Id记录本次广告点击的次数
        if (!"-1".equals(advert.getPushTimes())) {
            int count = Integer.parseInt(SharePreUtil.getStringData(context, "advert_" + advert.getId(), "0"));
            count++;
            SharePreUtil.saveStringData(context, "advert_" + advert.getId(), String.valueOf(count));
        }

        advertPop.showAtLocation(view, Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        return advertPop;
    }


    /**
     * Loading 点击触发请求的时候的loading 框
     *
     * @param context
     * @param msg
     * @return
     */
    public static PopupWindow showLoadingPop(final Context context, String msg) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_circle, null);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
//        int heigh = width / 5 * 6;
        final PopupWindow loadingPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
//                params.alpha = 1f;
//                ((Activity) context).getWindow().setAttributes(params);
            }
        });
//        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
//        params.alpha = 0.7f;
//        ((Activity) context).getWindow().setAttributes(params);
        TextView loadingTv  = (TextView) view.findViewById(R.id.loading_msg);
        loadingTv.setVisibility(View.GONE);
        loadingPop.setOutsideTouchable(false);
        loadingPop.setFocusable(true);
//        loadingPop.setBackgroundDrawable(new BitmapDrawable());
        loadingPop.update();
        loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
        return  loadingPop;
    }


    /**
     * 航班日期
     *
     * @param context
     * @param listener
     * @return
     */
    public static PopupWindow showAirportDatePop(final Context context, final ChoiseAirportDateListener listener) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_date, null);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
//        int heigh = width / 5 * 6;
        final PopupWindow datePop = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        datePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
            }
        });
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(params);
        datePop.setOutsideTouchable(true);
        datePop.setFocusable(true);
        datePop.setBackgroundDrawable(new BitmapDrawable());
//        advertPop.setAnimationStyle(R.style.share_pop_style);
        datePop.update();
        final DateWheelLayout layout = (DateWheelLayout) view.findViewById(R.id.airport_chose_date_layout);
        TextView choiceDone = (TextView) view.findViewById(R.id.airport_chose_date_done);
        choiceDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.date(layout.getDate());
                datePop.dismiss();
            }
        });
        datePop.showAtLocation(view, Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        return datePop;
    }
    /**
     * 订单详情 价格明细
     *
     * @param context
     * @param detail
     * @return
     */
    public static PopupWindow showOrderDetailPricePop(final Context context,OrderDetail detail) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_order_price_detail, null);
//        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
//        int heigh = width / 5 * 6;
        final PopupWindow priceDetailPop = new PopupWindow(view,UiUtil.dip2px(context,210), UiUtil.dip2px(context,215));
        priceDetailPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
            }
        });
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(params);
        priceDetailPop.setOutsideTouchable(true);
        priceDetailPop.setFocusable(true);
        priceDetailPop.setBackgroundDrawable(new BitmapDrawable());
//        advertPop.setAnimationStyle(R.style.share_pop_style);
        priceDetailPop.update();
        //商品价格
        TextView totalPrice = (TextView) view.findViewById(R.id.pop_order_total_price);
        totalPrice.setText(detail.getOrderMoney());
        //优惠价格
        TextView couponsPrice = (TextView) view.findViewById(R.id.pop_order_coupons_price);
        couponsPrice.setText("-"+(StringUtils.parseInt(detail.getPayMoney())-StringUtils.parseInt(detail.getOrderMoney())));
        //合计
        TextView finalPrice = (TextView) view.findViewById(R.id.pop_order_all);
        finalPrice.setText(detail.getPayMoney());
        //关闭按钮
        ImageView close = (ImageView) view.findViewById(R.id.pop_order_close_btn);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                priceDetailPop.dismiss();
            }
        });
        priceDetailPop.showAtLocation(view, Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        return priceDetailPop;
    }

    /**
     * 订单详情 订单状态明细
     *
     * @param context
     * @param detail
     * @return
     */
    public static PopupWindow showOrderDetailStatusPop(final Context context,OrderDetail detail,int mScreenWidth) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_order_status_detail, null);
//        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
//        int heigh = width / 5 * 6;
        final PopupWindow statusDetailPop = new PopupWindow(view,UiUtil.dip2px(context,270), UiUtil.dip2px(context,315));
        statusDetailPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
                params.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(params);
            }
        });
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(params);
        statusDetailPop.setOutsideTouchable(true);
        statusDetailPop.setFocusable(true);
        statusDetailPop.setBackgroundDrawable(new BitmapDrawable());
//        advertPop.setAnimationStyle(R.style.share_pop_style);
        statusDetailPop.update();
        //第一步描述
        TextView step1Desc = (TextView) view.findViewById(R.id.pop_order_detail_step1_desc);
        step1Desc.setText(detail.getStatusInfos().get(0).getStatusMsg());
        //第二步描述
        TextView step2Desc = (TextView) view.findViewById(R.id.pop_order_detail_step2_desc);
        step2Desc.setText(detail.getStatusInfos().get(1).getStatusMsg());
        //下面线条
        View bottomLine = view.findViewById(R.id.pop_order_line_bottom);

        //第三步描述
        TextView step3Desc = (TextView) view.findViewById(R.id.pop_order_detail_step3_desc);
        step3Desc.setText(detail.getStatusInfos().get(2).getStatusMsg());
        //关闭按钮
        ImageView close = (ImageView) view.findViewById(R.id.pop_order_status_close_btn);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                statusDetailPop.dismiss();
            }
        });
        statusDetailPop.showAtLocation(view, Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        return statusDetailPop;
    }


    /**
     * 提醒 pop (添加收藏成功等)
     *
     * @param context
     * @param id 图片Id
     * @param msg
     * @return
     */
    public static PopupWindow showNotifyPop(final Context context,int id, String msg) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_notify_layout, null);
        view.setAlpha(0.8f);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int width = screenWidth - UiUtil.dip2px(context, 30) * 2;
//        int heigh = width / 5 * 6;
        final PopupWindow notyfyPop = new PopupWindow(view, UiUtil.dip2px(context,250), ViewGroup.LayoutParams.WRAP_CONTENT);
        notyfyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
//                params.alpha = 1f;
//                ((Activity) context).getWindow().setAttributes(params);
            }
        });
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                notyfyPop.dismiss();
            }
        },3000);
//        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
//        params.alpha = 0.7f;
//        ((Activity) context).getWindow().setAttributes(params);
        ImageView notifyImg = (ImageView) view.findViewById(R.id.notify_img);
        notifyImg.setImageResource(id);
        TextView notifyText  = (TextView) view.findViewById(R.id.notigy_text);
        notifyText.setText(msg);
        notyfyPop.setOutsideTouchable(false);
        notyfyPop.setFocusable(true);
        notyfyPop.setBackgroundDrawable(new BitmapDrawable());
        notyfyPop.update();
        notyfyPop.showAtLocation(view, Gravity.CENTER, 0, 0);
        return  notyfyPop;
    }


}

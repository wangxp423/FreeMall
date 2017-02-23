/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aero.droid.dutyfree.talent.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.view.base.BaseView;
import com.aero.droid.dutyfree.talent.widgets.loading.VaryViewHelperController;
import com.aero.droid.dutyfree.talent.util.netstatus.NetChangeObserver;
import com.aero.droid.dutyfree.talent.util.netstatus.NetStateReceiver;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.umeng.message.PushAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/9.
 * Description:
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * context
     */
    protected Context mContext = null;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.activity_top_in, R.anim.activity_top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.activity_scale_in, R.anim.activity_scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
//        SmartBarUtils.hide(getWindow().getDecorView());
        setTranslucentStatus(isApplyStatusBarTranslucency());

        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);

        initViewsAndEvents();
        //友盟统计
        PushAgent.getInstance(mContext).onAppStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void finish() {
                super.finish();
                BaseAppManager.getInstance().removeActivity(this);
                if (toggleOverridePendingTransition()) {
                    switch (getOverridePendingTransitionMode()) {
                        case LEFT:
                            overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
                            break;
                        case RIGHT:
                            overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
                            break;
                case TOP:
                    overridePendingTransition(R.anim.activity_top_in, R.anim.activity_top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.activity_scale_in, R.anim.activity_scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * when event comming
     *
     * @param eventCenter
     */
    protected abstract void onEventComming(EventCenter eventCenter);

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    /**
     * is applyStatusBarTranslucency
     *
     * @return
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * is bind eventBus
     *
     * @return
     */
    protected abstract boolean isBindEventBusHere();

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg,int imgId, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg,imgId, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }

//    /**
//     * use SytemBarTintManager
//     *
//     * @param tintDrawable
//     */
//    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
//            if (tintDrawable != null) {
//                mTintManager.setStatusBarTintEnabled(true);
//                mTintManager.setTintDrawable(tintDrawable);
//            } else {
//                mTintManager.setStatusBarTintEnabled(false);
//                mTintManager.setTintDrawable(null);
//            }
//        }
//
//    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
    public interface TitleRightClickListener{
        void titleRightClick();
    }

    /**
     * 初始化标题栏
     * @param content 标题栏中间文字
     */
    protected void initTitle(String content){
        TextView contentTv = (TextView) findViewById(R.id.title_content);
        contentTv.setText(content);
        findViewById(R.id.title_left_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化 标题栏(右侧是文字)
     * @param content  标题栏中间 文字
     * @param rightText  标题栏右侧文字
     * @param listener   标题栏右侧文字点击事件
     */
    protected void initTitleRightTv(String content,String rightText, View.OnClickListener listener ){
        TextView contentTv = (TextView) findViewById(R.id.title_content);
        contentTv.setText(content);
        findViewById(R.id.title_left_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView rightTv = (TextView) findViewById(R.id.title_right_tv);
        rightTv.setText(rightText);
        RelativeLayout titileRightLayout = (RelativeLayout) findViewById(R.id.title_right_layout);
        titileRightLayout.setVisibility(View.VISIBLE);
        titileRightLayout.setOnClickListener(listener);
    }
    /**
     * 初始化 标题栏(右侧是图片)
     * @param content  标题栏中间 文字
     * @param id  标题栏右侧图片
     * @param listener   标题栏右侧文字点击事件
     */
    protected void initTitleRightImg(String content,int  id, View.OnClickListener listener ){
        TextView contentTv = (TextView) findViewById(R.id.title_content);
        contentTv.setText(content);
        findViewById(R.id.title_left_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.title_right_img);
        imageView.setImageResource(id);
        RelativeLayout titileRightLayout = (RelativeLayout) findViewById(R.id.title_right_layout);
        titileRightLayout.setVisibility(View.VISIBLE);
        titileRightLayout.setOnClickListener(listener);
    }

    /**
     * 分享监听器
     */
    protected SocializeListeners.SnsPostListener mShareListener = new SocializeListeners.SnsPostListener() {

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int stCode,
                               SocializeEntity entity) {
            if (stCode == 200) {
                Toast.makeText(mContext, R.string.share_success, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 下面是 页面内请求 loading
     */
    private PopupWindow loadingPop;
    protected void getLoading() {
        if (null == loadingPop) {
            loadingPop = PopWindowUtil.showLoadingPop(mContext, "Loading");
        } else {
            loadingPop.showAtLocation(getLoadingTargetView(), Gravity.CENTER, 0, 0);
        }
    }

    protected void dismissLoading() {
        if (null != loadingPop)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingPop.dismiss();
                }
            });

    }
}
package com.aero.droid.dutyfree.talent.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.view.base.BaseView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;

public class BaseActivity extends BaseFragmentActivity implements BaseView{


    protected Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handlerMsg(msg);
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void handlerMsg(Message msg) {

    }

    /**
     * @param content 左边是箭头
     *                右侧是图片 id = title_right
     */
    protected void initTitleBack(String content) {
        TextView titleContent = (TextView) findViewById(R.id.title_content);
        titleContent.setText(content);
        FrameLayout back = (FrameLayout) findViewById(R.id.title_frame_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
            }
        });
    }


    /**
     * 左边是 X
     * @param content
     */
    protected void initTitleX(String content) {
        TextView titleContent = (TextView) findViewById(R.id.title_content);
        titleContent.setText(content);
        FrameLayout back = (FrameLayout) findViewById(R.id.title_frame_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(0, R.anim.slide_out_to_bottom);
            }
        });
    }


    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
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
                Toast.makeText(BaseActivity.this, R.string.share_success, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    @Override
    public void showEmpty(String msg,int imgId) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }
}

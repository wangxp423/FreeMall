package com.aero.droid.dutyfree.talent.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseActivity;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * 邀请好友页
 *
 * @author wangxp
 */
public class InviteFriendActivity extends BaseFragmentActivity implements View.OnTouchListener {
    @Bind(R.id.web_webview)
    WebView webWebview;
    /************************************/
    private String url = "http://115.28.49.160:8891/DFGManager/webapp/wx/invitation/app/index.html?memberId=";
    private String shareUrl = "http://115.28.49.160:8891/DFGManager/webapp/wx/invitation/shareWx/index.html?memberId=";
    private String memeberId;


    @Override
    protected void getBundleExtras(Bundle extras) {
//        url = extras.getString("url");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return webWebview;
    }

    @Override
    protected void initViewsAndEvents() {
        memeberId = UserUtil.getUserId(mContext);
        WebSettings settings = webWebview.getSettings();
        settings.setJavaScriptEnabled(true);
//		settings.setSavePassword(true);
//		settings.setAllowFileAccess(true);
//		settings.setBuiltInZoomControls(false);
//		settings.setDomStorageEnabled(true);
//		settings.setAppCacheMaxSize(1024*1024*8);
//		String appCacheDir = mContext.getDir("cache", Context.MODE_PRIVATE)
//				.getPath();
//		settings.setAppCachePath(appCacheDir);
//		settings.setAllowFileAccess(true);
//		settings.setAppCacheEnabled(true);
//		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webWebview.addJavascriptInterface(new BindJavaScript(), "Bind_JS");
        webWebview.setWebViewClient(new GcWebViewClient());
//		mWebView.setVerticalScrollBarEnabled(false);
//		mWebView.setHorizontalScrollBarEnabled(false);
        webWebview.requestFocus();
        webWebview.loadUrl(url + memeberId);
        webWebview.setOnTouchListener(this);

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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.BOTTOM;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                if (!v.hasFocus()) {
                    v.requestFocus();
                }
                break;
        }
        return false;
    }


    private class GcWebViewClient extends WebViewClient {
//		private ProgressDialog progressDialog;

        public GcWebViewClient() {
//			progressDialog = new ProgressDialog(mContext);
//			progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			progressDialog.setCanceledOnTouchOutside(false);
//			progressDialog.setMessage(getResources().getString(R.string.loading_text));
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //GcDialog.this.url = url;
            LogUtil.v(TAG_LOG, "shouldOverrideUrlLoading?" + url);
            webWebview.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            LogUtil.v(TAG_LOG, "onReceivedError: " + description);
        }

        @Override
        public void onPageStarted(WebView view, String url,
                                  Bitmap favicon) {
            LogUtil.v(TAG_LOG, "onPageStarted: " + url);
            toggleShowLoading(true, null);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtil.v(TAG_LOG, "onPageFinished: " + url);
            toggleShowLoading(false, null);
            //邀请好友 js注入
            String invite = "javascript:var btn = document.getElementById('shareWxBtn');btn.onclick=function(){window.Bind_JS.inviteFriend();};";
            String back = "javascript:var btn = document.getElementById('retBtn');btn.onclick=function(){window.Bind_JS.back();};";
            view.loadUrl(invite);
            view.loadUrl(back);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class BindJavaScript {
        @JavascriptInterface
        public void inviteFriend() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PopWindowUtil.showSharePop(InviteFriendActivity.this, "分享有礼", "", shareUrl + memeberId, "邀请好友一起赚钱", mShareListener);
                }
            });
        }
        @JavascriptInterface
        public void back(){
            finish();
        }
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
                sharedone();
            }
        }
    };

    private void sharedone() {
        HashMap<String, String> params = new HashMap<>();
        params.put("memeberId", memeberId);
        OkHttpRequest.okHttpGet(mContext, Url.INVITESHARE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "邀请好友分享成功 = " + data.toString());
            }

            @Override
            public void onRespError(String code, String msg) {
            }
        });
    }
}

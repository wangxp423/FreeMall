package com.aero.droid.dutyfree.talent.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * webView 加载网页
 *
 * @author wangxp
 */
public class WebViewActivity extends BaseFragmentActivity {
    @Bind(R.id.web_webview)
    WebView webWebview;
    /************************************/
    private String url;

    @Override
    protected void getBundleExtras(Bundle extras) {
        url = extras.getString("url");
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
        webWebview.setWebViewClient(new GcWebViewClient());
//		mWebView.setVerticalScrollBarEnabled(false);
//		mWebView.setHorizontalScrollBarEnabled(false);
        webWebview.requestFocus();
        webWebview.loadUrl(url);

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

            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(url));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return true;
                }
                startActivity(intent);
                return true;
            } else {
                webWebview.loadUrl(url);

                return false;
            }
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
            LogUtil.v(TAG_LOG, "onPageFinished: " + url);
            toggleShowLoading(false,null);
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
}

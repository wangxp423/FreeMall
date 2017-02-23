package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.presenter.LoginPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.LoginPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.LoginView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录页
 *
 * @author wangxp
 */
public class LoginActivity extends BaseFragmentActivity implements LoginView, OnClickListener {

    @Bind(R.id.title_left_img)
    ImageView titleLeftImg;
    @Bind(R.id.title_left_layout)
    RelativeLayout titleLeftLayout;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.title_right_tv)
    TextView titleRightTv;
    @Bind(R.id.title_right_layout)
    RelativeLayout titleRightLayout;
    @Bind(R.id.login_account_edit)
    EditText loginAccountEdit;
    @Bind(R.id.login_account_edit_clear)
    ImageView loginAccountEditClear;
    @Bind(R.id.login_password_edit)
    EditText loginPasswordEdit;
    @Bind(R.id.login__auto_password)
    Button loginAutoPassword;
    @Bind(R.id.login_login)
    TextView loginLogin;
    @Bind(R.id.login_weixin)
    TextView loginWeixin;

    /*************************/
    private UMSocialService mController;
    private LoginPresenter presenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
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
        presenter = new LoginPresenterImpl(LoginActivity.this, this);
        loginAccountEditClear.setOnClickListener(this);
        loginAutoPassword.setOnClickListener(this);
        loginLogin.setOnClickListener(this);
        loginWeixin.setOnClickListener(this);
        initTitleRightTv(getResources().getString(R.string.login), getResources().getString(R.string.login_register), new OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(RegisterActivity.class);
                finish();
            }
        });
        loginAccountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.inputingAccount(s.toString());
            }
        });
        loginPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.inputingAutoCode(s.toString());
            }
        });

        mController = (UMSocialService) UMServiceFactory
                .getUMSocialService("com.umeng.login");
        // 添加新浪SSO登录  (这个版本暂时没有新浪登录)
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login__auto_password:
                if (presenter.checkUserData(loginAccountEdit.getText().toString()))
                    presenter.clickGetAutoCode(TAG_LOG, loginAccountEdit.getText().toString());
                break;
            case R.id.login_login:
                presenter.clickLogin(TAG_LOG, loginAccountEdit.getText().toString(), loginPasswordEdit.getText().toString());
                break;
            case R.id.login_account_edit_clear:
                loginAccountEdit.setText("");
                loginPasswordEdit.setText("");
                break;
            case R.id.login_weixin:
                weixinLogin();
                break;
        }

    }

    @Override
    public void visibleEditClear() {
        loginAccountEditClear.setVisibility(View.VISIBLE);
    }

    @Override
    public void invisibleEditClear() {
        loginAccountEditClear.setVisibility(View.GONE);
    }

    @Override
    public void clickAbleGetAuthBtn() {
        loginAutoPassword.setClickable(true);
        loginAutoPassword.setTextColor(getResources().getColor(R.color.white));
        loginAutoPassword.setText(getResources().getString(R.string.get_auto_password));
        loginAutoPassword.setBackgroundResource(R.drawable.radius_right_solid_purple);
    }

    @Override
    public void unclickAbleGetAuthBtn(long millisUntilFinished) {
        loginAutoPassword.setClickable(false);
        loginAutoPassword.setTextColor(getResources().getColor(R.color.gray_text));
        loginAutoPassword.setText(getResources().getString(R.string.get_auto_password) + "(" + millisUntilFinished / 1000 + ")");
        loginAutoPassword.setBackgroundResource(R.drawable.radius_right_solid_gray3);
    }

    @Override
    public void clickAbleLoginBtn() {
        loginLogin.setClickable(true);
        loginLogin.setBackgroundResource(R.drawable.btn_pressed_selector);
    }

    @Override
    public void unclickAbleLoginBtn() {
        loginLogin.setClickable(false);
        loginLogin.setBackgroundResource(R.drawable.radius_solid_gray3);
    }

    @Override
    public void loginSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void thirdLoginSuccess() {
        dismissLoading();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getAutoPasswordSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.startTime();
            }
        });
    }

    @Override
    public void rquestDataError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    private void weixinLogin() {
        mController.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN,
                new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast.makeText(mContext, R.string.oauth_start,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Toast.makeText(
                                mContext,
                                getResources().getString(
                                        R.string.oauth_error)
                                        + "  code = "
                                        + e.getErrorCode()
                                        + "  message = " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value,
                                           SHARE_MEDIA platform) {
                        // 获取相关授权信息
                        mController.getPlatformInfo(mContext,
                                SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                                    @Override
                                    public void onStart() {
                                         Toast.makeText(mContext,
                                         "获取平台数据开始...",
                                         Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete(int status,
                                                           Map<String, Object> info) {
                                        if (status == 200 && info != null) {
                                            Toast.makeText(
                                                    mContext,
                                                    R.string.oauth_complete,
                                                    Toast.LENGTH_SHORT)
                                                    .show();
                                            String userId = (String) info
                                                    .get("openid");
                                            String userImg = (String) info
                                                    .get("headimgurl");
                                            String userName = (String) info
                                                    .get("nickname");
                                            String unionid = (String) info
                                                    .get("unionid");
                                            getLoading();
                                            presenter.clickThirdLogin(TAG_LOG,"wx",userId,unionid,userImg,userName);
                                        } else {
                                            Toast.makeText(
                                                    mContext,
                                                    getResources()
                                                            .getString(
                                                                    R.string.oauth_error)
                                                            + "  code = "
                                                            + status,
                                                    Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(mContext, R.string.oauth_cancel,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

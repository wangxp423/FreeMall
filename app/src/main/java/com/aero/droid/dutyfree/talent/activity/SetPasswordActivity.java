package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.MD5Kit;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 设置密码
 */
public class SetPasswordActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.set_password_passwrod)
    EditText passwrodEd;
    @Bind(R.id.set_password_again)
    EditText passwordAgainEd;
    @Bind(R.id.set_password_done)
    TextView doneTv;
    /***********************************/
    private String phoneNum, codeNum;

    @Override
    protected void getBundleExtras(Bundle extras) {
        phoneNum = extras.getString("phone");
        codeNum = extras.getString("authCode");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set_password;
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
        initTitle(getResources().getString(R.string.set_passwrod_title));
        doneTv.setOnClickListener(this);
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
            case R.id.set_password_done:
                if (checkInputData(passwrodEd.getText().toString(), passwordAgainEd.getText().toString()))
                    setPassword();
                break;
        }
    }

    private boolean checkInputData(String password, String passwordAgain) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showLongToast(mContext, getResources().getString(R.string.set_passwrod));
            return false;
        } else {
            if (TextUtils.isEmpty(passwordAgain)) {
                ToastUtil.showLongToast(mContext, getResources().getString(R.string.set_passwrod_again));
                return false;
            } else {
                if (!password.equals(passwordAgain)) {
                    ToastUtil.showLongToast(mContext, getResources().getString(R.string.set_passwrod_again_error));
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private void setPassword() {
        HashMap<String,String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("phone",phoneNum);
        params.put("code",codeNum);
        params.put("resetType","bind");
        params.put("password", MD5Kit.md5(passwordAgainEd.getText().toString()));
        params.put("deviceType","-3");
        OkHttpRequest.okHttpPost(mContext, Url.RESETPASSWORD, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, final String msg) {
                LogUtil.v("JSON", "设置密码成功 = " + data.toString());
                if (data.has("memberInfo")) {
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    UserUtil.setUserInfo(mContext, data.optJSONObject("memberInfo").toString());
                    UserUtil.setUserId(mContext, user.getMemberId());
                    finish();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShortToast(mContext, msg);
                        }
                    });
                }
            }

            @Override
            public void onRespError(String code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShortToast(mContext, msg);
                    }
                });

            }
        });
    }
}

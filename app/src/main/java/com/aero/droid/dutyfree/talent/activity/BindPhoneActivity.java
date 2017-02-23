package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.presenter.RegisterPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.RegisterPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.RegisterView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Author : wangxp
 * Date : 2016/1/12
 * Desc : 绑定手机号 这个页面和注册页部分相似 使用注册View 和 presenter
 */
public class BindPhoneActivity extends BaseFragmentActivity implements RegisterView, View.OnClickListener {
    @Bind(R.id.bind_phone_edit)
    EditText bindPhoneEdit;
    @Bind(R.id.bind_code_edit)
    EditText bindCodeEdit;
    @Bind(R.id.bind_auto_password)
    Button bindCodeBtn;
    @Bind(R.id.bind_next_step)
    TextView bindNextStepBtn;
    /*************************************/
    private RegisterPresenterImpl presenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bindphone;
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
        initTitle(getResources().getString(R.string.bound_phone_title));
        bindCodeBtn.setOnClickListener(this);
        bindNextStepBtn.setOnClickListener(this);
        presenter = new RegisterPresenterImpl(BindPhoneActivity.this, this);

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
    public void showAirportCompany(List<AirportCompany> companyList) {
        //这个方法 在此页面无用
    }

    @Override
    public void clickAbleGetAuthBtn() {
        bindCodeBtn.setClickable(true);
        bindCodeBtn.setTextColor(getResources().getColor(R.color.white));
        bindCodeBtn.setText(getResources().getString(R.string.find_password_get_authcode));
        bindCodeBtn.setBackgroundResource(R.drawable.radius_right_solid_purple);
    }

    @Override
    public void unclickAbleGetAuthBtn(long millisUntilFinished) {
        bindCodeBtn.setClickable(false);
        bindCodeBtn.setTextColor(getResources().getColor(R.color.gray_text));
        bindCodeBtn.setText(getResources().getString(R.string.find_password_get_authcode) + "(" + millisUntilFinished / 1000 + ")");
        bindCodeBtn.setBackgroundResource(R.drawable.radius_right_solid_gray3);
    }

    @Override
    public void verifySuccess() {
        //这个方法 在此页面无用
    }

    @Override
    public void getCodeSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.startTime();
            }
        });
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_auto_password:
                if (presenter.checkTelNo(bindPhoneEdit.getText().toString()))
                    presenter.clickAuthCodeButton(TAG_LOG, bindPhoneEdit.getText().toString());
                break;
            case R.id.bind_next_step:
                if (presenter.checkInputData(bindPhoneEdit.getText().toString(), bindCodeEdit.getText().toString())) {
                    bindPhone();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.finishTime();
    }

    /**
     * 绑定手机号
     */
    private void bindPhone() {
        HashMap<String,String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("phone",bindPhoneEdit.getText().toString());
        params.put("code",bindCodeEdit.getText().toString());
        params.put("deviceType","-3");
        OkHttpRequest.okHttpPost(mContext, Url.BINDPHONE, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, final String msg) {
                LogUtil.v("JSON","绑定手机号成功 = " + data.toString());
                if ("0".equals(code)){
                    if (data.has("memberInfo")) {
                        User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                        UserUtil.setUserInfo(mContext, data.optJSONObject("memberInfo").toString());
                        UserUtil.setUserId(mContext, user.getMemberId());
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", bindPhoneEdit.getText().toString());
                        bundle.putString("authCode", bindCodeEdit.getText().toString());
                        readyGo(SetPasswordActivity.class, bundle);
                        EventCenter<String> phoneEvent = new EventCenter<String>(Constants.EVENT_ADD_DATA,bindPhoneEdit.getText().toString());
                        EventBus.getDefault().post(phoneEvent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showShortToast(mContext, msg);
                            }
                        });

                    }
                }else if ("2".equals(code)){
                    mergeMember();
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

    /**
     * 合并账号
     */
    private void mergeMember() {
        HashMap<String,String> params = new HashMap<>();
        User user = UserUtil.getUserInfo(mContext);
        params.put("memberId", TextUtils.isEmpty(user.getMemberId()) ? "0" : user.getMemberId());
        params.put("third","wx");
        params.put("usid",user.getUsid());
        params.put("unionid",user.getUnionId());
        params.put("image",user.getPhoto());
        params.put("nickName",user.getNickName());
        params.put("deviceType","-3");
        OkHttpRequest.okHttpPost(mContext, Url.MERGEMEMBER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, final String msg) {
                LogUtil.v("JSON","合并账号成功 = " + data.toString());
                if (data.has("memberInfo")) {
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    UserUtil.setUserInfo(mContext, data.optJSONObject("memberInfo").toString());
                    UserUtil.setUserId(mContext, user.getMemberId());
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", bindPhoneEdit.getText().toString());
                    bundle.putString("authCode", bindCodeEdit.getText().toString());
                    readyGo(SetPasswordActivity.class, bundle);
                    EventCenter<String> phoneEvent = new EventCenter<String>(Constants.EVENT_ADD_DATA,bindPhoneEdit.getText().toString());
                    EventBus.getDefault().post(phoneEvent);
                    finish();
                } else {
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

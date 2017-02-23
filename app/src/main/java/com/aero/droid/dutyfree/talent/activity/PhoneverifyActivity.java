package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.presenter.impl.PhoneVerifyPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.PhoneVerifyView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/3
 * Desc : 手机验证
 */
public class PhoneverifyActivity extends BaseFragmentActivity implements PhoneVerifyView, View.OnClickListener {
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
    @Bind(R.id.verify_phone_edit)
    EditText verifyPhoneEdit;
    @Bind(R.id.verify_phone_edit_clear)
    ImageView verifyPhoneEditClear;
    @Bind(R.id.verify_password_edit)
    EditText verifyPasswordEdit;
    @Bind(R.id.verify_auto_password)
    Button verifyAutoPassword;
    @Bind(R.id.verify_phone_done)
    TextView verifyPhoneDone;
    @Bind(R.id.register_set_password)
    EditText setPasswordEd;
    @Bind(R.id.register_invitation_code_edit)
    EditText invitationCodeEd;
    @Bind(R.id.register_invitation_layout)
    LinearLayout invitationLayout;


    /**************************************/
    private PhoneVerifyPresenterImpl presenter;
    private String jobNum; //工号
    private String name; //名字
    private String phone; //手机号
    private String authCode; //验证码
    private String invateCode; //邀请码

    @Override
    protected void getBundleExtras(Bundle extras) {
        jobNum = extras.getString("jobNum");
        name = extras.getString("uname");
        invateCode = extras.getString("inviteCode");

        phone = extras.getString("phone");
        authCode = extras.getString("authCode");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_phone_verify;
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
        initTitle(getResources().getString(R.string.login_register));
        verifyAutoPassword.setOnClickListener(this);
        verifyPhoneDone.setOnClickListener(this);
        invitationLayout.setOnClickListener(this);
        presenter = new PhoneVerifyPresenterImpl(PhoneverifyActivity.this, this);

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
            case R.id.verify_auto_password:
                if (presenter.checkTelNo(verifyPhoneEdit.getText().toString()))
                    presenter.clickGetAutoCode(TAG_LOG, verifyPhoneEdit.getText().toString(), jobNum, name);
                break;
            case R.id.verify_phone_done:
//                presenter.commitVerify(TAG_LOG, verifyPhoneEdit.getText().toString(), jobNum, name, verifyPasswordEdit.getText().toString(), invateCode);
                //完成注册
                if (!TextUtils.isEmpty(setPasswordEd.getText().toString())) {
                    presenter.clickRegister(TAG_LOG, phone, setPasswordEd.getText().toString(), authCode, invitationCodeEd.getText().toString());
                } else {
                    ToastUtil.showShortToast(mContext, getResources().getString(R.string.set_passwrod_null));
                }
                break;
            case R.id.register_invitation_layout:
                ToastUtil.showShortToast(mContext, "跳转 邀请码协议 H5");
                break;
        }

    }

    @Override
    public void getAuthCodeSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.startTime();
            }
        });
    }

    @Override
    public void getVerifySuccess() {
        finish();
    }

    @Override
    public void registerSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void requestErroe(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(mContext, msg);
            }
        });
    }

    @Override
    public void clickAbleGetAuthBtn() {
        verifyAutoPassword.setClickable(true);
        verifyAutoPassword.setTextColor(getResources().getColor(R.color.white));
        verifyAutoPassword.setText(getResources().getString(R.string.get_auto_password));
        verifyAutoPassword.setBackgroundResource(R.drawable.radius_right_solid_purple);
    }

    @Override
    public void unclickAbleGetAuthBtn(long millisUntilFinished) {
        verifyAutoPassword.setClickable(false);
        verifyAutoPassword.setTextColor(getResources().getColor(R.color.gray_text));
        verifyAutoPassword.setText(getResources().getString(R.string.get_auto_password) + "(" + millisUntilFinished / 1000 + ")");
        verifyAutoPassword.setBackgroundResource(R.drawable.radius_right_solid_gray3);
    }

}

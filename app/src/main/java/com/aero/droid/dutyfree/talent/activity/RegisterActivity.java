package com.aero.droid.dutyfree.talent.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.presenter.impl.RegisterPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.RegisterView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 注册页面
 * Created by wangxp on 2015/9/29.
 */
public class RegisterActivity extends BaseFragmentActivity implements RegisterView, View.OnClickListener {

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
    @Bind(R.id.register_airport_company_edit)
    EditText airportCompanyEdit;
    @Bind(R.id.register_choise_airport_company_btn)
    Button choiseBtn;
    @Bind(R.id.register_work_num_edit)
    EditText workNumEdit;
    @Bind(R.id.register_name_edit)
    EditText nameEdit;
    @Bind(R.id.login_verify)
    TextView verifybtn;
    @Bind(R.id.register_invitation_code_edit)
    EditText invitationCodeEdit;
    @Bind(R.id.register_invitation_layout)
    LinearLayout invitationLayout;
    @Bind(R.id.register_verify_success_layout)
    LinearLayout verifySuccessLayout;
    @Bind(R.id.register_next_step_layout)
    LinearLayout nextStepLayout;
    @Bind(R.id.register_next_step)
    TextView nextStepTv;
    @Bind(R.id.register_agreement_layout)
    RelativeLayout agreementLayout;
    @Bind(R.id.register_auto_password)
    Button registerSecurCode;

    /*****************************************/
    private RegisterPresenterImpl presenter;
    private List<AirportCompany> companyList;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
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
        presenter = new RegisterPresenterImpl(RegisterActivity.this, this);
        initTitle(getResources().getString(R.string.login_register));
        choiseBtn.setOnClickListener(this);
        verifybtn.setOnClickListener(this);
        nextStepTv.setOnClickListener(this);
        invitationLayout.setOnClickListener(this);
        agreementLayout.setOnClickListener(this);
        registerSecurCode.setOnClickListener(this);
//        presenter.getAirportCompanyList(TAG_LOG);


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
            case R.id.register_choise_airport_company_btn:
                showPopWin();
                break;
            case R.id.register_invitation_layout:
                presenter.clickInvatationCode();
                break;
            case R.id.register_next_step:
                presenter.clickNextStep(workNumEdit.getText().toString(), nameEdit.getText().toString(), invitationCodeEdit.getText().toString());
                break;
            case R.id.register_agreement_layout:
                presenter.clickAgreement();
                break;
            case R.id.register_auto_password:
                if (presenter.checkTelNo(workNumEdit.getText().toString()))
                    presenter.clickAuthCodeButton(TAG_LOG,workNumEdit.getText().toString());
                break;
            case R.id.login_verify:
                String workNum = workNumEdit.getText().toString();
                String name = nameEdit.getText().toString();
                if (presenter.checkInputData(workNum, name))
                    clickRegisterNow();
//                    presenter.clickVerify(TAG_LOG, "", workNum, name);
                break;
        }

    }

    @Override
    public void showAirportCompany(List<AirportCompany> companyList) {
        this.companyList = companyList;
    }

    @Override
    public void clickAbleGetAuthBtn() {
        registerSecurCode.setClickable(true);
        registerSecurCode.setTextColor(getResources().getColor(R.color.white));
        registerSecurCode.setText(getResources().getString(R.string.find_password_get_authcode));
        registerSecurCode.setBackgroundResource(R.drawable.radius_right_solid_purple);
    }

    @Override
    public void unclickAbleGetAuthBtn(long millisUntilFinished) {
        registerSecurCode.setClickable(false);
        registerSecurCode.setTextColor(getResources().getColor(R.color.gray_text));
        registerSecurCode.setText(getResources().getString(R.string.find_password_get_authcode) + "(" + millisUntilFinished / 1000 + ")");
        registerSecurCode.setBackgroundResource(R.drawable.radius_right_solid_gray3);
    }

    @Override
    public void verifySuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //以下两个功能暂时注释掉
//                choiseBtn.setClickable(false);
//                airportCompanyEdit.setFocusable(false);
                workNumEdit.setFocusable(false);
                nameEdit.setFocusable(false);
                verifybtn.setVisibility(View.GONE);
                verifySuccessLayout.setVisibility(View.VISIBLE);
                nextStepLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 点击立即注册  显示下一步 等Veiw
     */
    private void clickRegisterNow(){
        workNumEdit.setFocusable(false);
        nameEdit.setFocusable(false);
        registerSecurCode.setClickable(false);
        verifybtn.setVisibility(View.GONE);
//        verifySuccessLayout.setVisibility(View.VISIBLE);
        nextStepLayout.setVisibility(View.VISIBLE);
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

    private PopupWindow airportCompaynWindow;
    private ListView airportCompaynLv;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showPopWin() {
        if (airportCompaynWindow == null) {
            View layout = LayoutInflater.from(mContext).inflate(
                    R.layout.pop_airport_company_layout, null);
            airportCompaynWindow = new PopupWindow(layout, choiseBtn.getWidth(), ViewPager.LayoutParams.WRAP_CONTENT);
            // 给popupWindow加进入动画
            airportCompaynWindow.setAnimationStyle(R.style.PopCategory);
            airportCompaynWindow.update();
            airportCompaynWindow.setOutsideTouchable(true);
            airportCompaynWindow.setFocusable(true);
            airportCompaynWindow.setBackgroundDrawable(new BitmapDrawable());
            airportCompaynLv = (ListView) layout.findViewById(R.id.pop_airport_company_lv);
            JavaBeanBaseAdapter<AirportCompany> adapter = new JavaBeanBaseAdapter<AirportCompany>(mContext, R.layout.item_airport_company, companyList) {
                @Override
                protected void bindView(int position, ViewHolder holder, AirportCompany object) {
                    TextView textView = holder.getView(R.id.airport_company_name);
                    textView.setText(object.getName());
                }
            };
            airportCompaynLv.setAdapter(adapter);
            airportCompaynLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    airportCompanyEdit.setText(companyList.get(position).getName());
                }
            });
        }
        airportCompaynWindow.showAsDropDown(choiseBtn);
//        categoryWindow.showAsDropDown(airportCompany, 0, 0, Gravity.CENTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constants.EVENT_START_ACTIVITY:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

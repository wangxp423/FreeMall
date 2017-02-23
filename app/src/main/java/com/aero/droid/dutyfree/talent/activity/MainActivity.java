package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.Advertise;
import com.aero.droid.dutyfree.talent.fragment.CategoryFragment;
import com.aero.droid.dutyfree.talent.fragment.HandPickFragment;
import com.aero.droid.dutyfree.talent.fragment.MeFragment;
import com.aero.droid.dutyfree.talent.fragment.ShopBagFragment;
import com.aero.droid.dutyfree.talent.presenter.MainViewPresenter;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.util.SharePreUtil;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @Bind(R.id.main_menu_handpick)
    RadioButton mainMenuHandpick;
    @Bind(R.id.main_menu_category)
    RadioButton mainMenuCategory;
    @Bind(R.id.main_menu_home)
    RadioButton mainMenuHome;
    @Bind(R.id.main_menu_shopbag)
    RadioButton mainMenuShopBag;
    @Bind(R.id.main_menu_my)
    RadioButton mainMenuMy;
    //    @Bind(R.id.main_radio_menu)
//    RadioGroup mainRadioMenu;
    @Bind(R.id.main_menu_layout)
    LinearLayout mainMenuLayout;
    @Bind(R.id.main_content_container)
    LinearLayout containerView;

    /********************************/
    private MainViewPresenter presenter;
    private List<Fragment> fragments;
    private FragmentTransaction transaction;
    private Fragment handpickFragment;
    private Fragment categoryFragment;
    private Fragment shopbagFragment;
    private MeFragment meFragment;
    private boolean isFirst = true;
    private long exitTime = 0;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
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
//        mainRadioMenu.setOnCheckedChangeListener(this);
        mainMenuHandpick.setOnClickListener(this);
        mainMenuCategory.setOnClickListener(this);
        mainMenuShopBag.setOnClickListener(this);
        mainMenuMy.setOnClickListener(this);
        initFragmentView();

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_menu_handpick:
                switchFragment(0);
                break;
            case R.id.main_menu_category:
                switchFragment(1);
                break;
            case R.id.main_menu_shopbag:
                if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
                    readyGo(LoginActivity.class);
                } else {
                    switchFragment(2);
                }

                break;
            case R.id.main_menu_my:
                switchFragment(3);
                break;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            getAdvertData();
            isFirst = false;
        }
    }

    /**
     * MainAcitivity的launchMode=singleTask
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                int jumpType = bundle.getInt("jumpType");
                switchFragment(jumpType);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.showShortToast(MainActivity.this, R.string.quit_alert);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化主页View
     */
    private void initFragmentView() {
        if (null == fragments)
            fragments = new ArrayList<Fragment>();
        if (null == handpickFragment) {
            handpickFragment = new HandPickFragment();
            fragments.add(handpickFragment);
        }
        if (null == categoryFragment) {
            categoryFragment = new CategoryFragment();
            fragments.add(categoryFragment);
        }
        if (null == shopbagFragment) {
            shopbagFragment = new ShopBagFragment();
            fragments.add(shopbagFragment);
        }
        if (null == meFragment) {
            meFragment = new MeFragment();
            fragments.add(meFragment);
        }


        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_container, handpickFragment);
        transaction.add(R.id.main_content_container, categoryFragment);
        transaction.add(R.id.main_content_container, shopbagFragment);
        transaction.add(R.id.main_content_container, meFragment);
        transaction.show(handpickFragment).hide(categoryFragment).hide(shopbagFragment).hide(meFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_handpick:
                switchFragment(0);
                break;
            case R.id.main_menu_category:
                switchFragment(1);
                break;
            case R.id.main_menu_shopbag:
                if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
                    mainMenuShopBag.setChecked(false);
                    readyGoForResult(LoginActivity.class, Constants.EVENT_START_ACTIVITY);
                } else {
                    switchFragment(2);
                }

                break;
            case R.id.main_menu_my:
                switchFragment(3);
                break;
        }
    }

    /**
     * 更改Fragment对象
     *
     * @param index
     */
    public void switchFragment(int index) {
        switchRadioButton(index);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                transaction.show(fragments.get(index));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

    public void switchRadioButton(int index) {
        switch (index) {
            case 0:
                mainMenuHandpick.setChecked(true);
                mainMenuCategory.setChecked(false);
                mainMenuShopBag.setChecked(false);
                mainMenuMy.setChecked(false);
                break;
            case 1:
                mainMenuHandpick.setChecked(false);
                mainMenuCategory.setChecked(true);
                mainMenuShopBag.setChecked(false);
                mainMenuMy.setChecked(false);
                break;
            case 2:
                mainMenuHandpick.setChecked(false);
                mainMenuCategory.setChecked(false);
                mainMenuShopBag.setChecked(true);
                mainMenuMy.setChecked(false);
                break;
            case 3:
                mainMenuHandpick.setChecked(false);
                mainMenuCategory.setChecked(false);
                mainMenuShopBag.setChecked(false);
                mainMenuMy.setChecked(true);
                break;
        }
    }

    //广告
    private List<Advertise> advertList;
    private PopupWindow advertPop;
    private Advertise advert;

    //获取广告信息
    private void getAdvertData() {
        if (null == advertList)
            advertList = new ArrayList<Advertise>();
        HashMap<String, String> mParams = new HashMap<String, String>();
        String userId = UserUtil.getUserId(mContext);
        mParams.put("memberId", TextUtils.isEmpty(userId) ? "0" : userId);
        OkHttpRequest.okHttpGet(MainActivity.this, Url.ADVERTISE, mParams, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "广告信息 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("advertise"))
                        advertList = JsonAnalysis.getAdvertList(data.optJSONArray("advertise"));
                    if (advertList != null && advertList.size() > 0) {
//                        for (int i = 0; i < advertList.size(); i++) {
                        advert = advertList.get(0);
//                            if ("1".equals(advert.getSort())) {
                        //根据广告的时间段和点击次数判断该广告是否显示
                        if (!isOutTime(advert.getBeginTime(), advert.getEndTime(), TimeFormatUtil.getTimestampFull("yyyy-MM-dd", TimeFormatUtil.getCurrentTime()))) {
                            if ("-1".equals(advert.getPushTimes()) || TextUtils.isEmpty(advert.getPushTimes())) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        advertPop = PopWindowUtil.showAdvertPop(MainActivity.this, advert);
                                    }
                                });
                            } else {
                                int count = Integer.parseInt(SharePreUtil.getStringData(MainActivity.this, "advert_" + advert.getId(), "0"));
                                if (count < Integer.parseInt(TextUtils.isEmpty(advert.getPushTimes()) ? "0" : advert.getPushTimes())) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            advertPop = PopWindowUtil.showAdvertPop(MainActivity.this, advert);
                                        }
                                    });
//                                    setAdvertTime(Long.parseLong(advert.getPostpone())  1000);
                                }
                            }
                        }

//                            }
//                        }
                    }
                } else {
                    ToastUtil.showShortToast(MainActivity.this, msg);
                }
            }

            @Override
            public void onRespError(String code, String msg) {
            }
        });
    }

    /**
     * 广告时间限制
     *
     * @param startTime
     * @param endTime
     * @param curTime
     * @return
     */
    private boolean isOutTime(String startTime, String endTime, String curTime) {
//        String[] startSplit = startTime.split("-");
//        String[] endSplit = endTime.split("-");
//        String[] curSpilt = curTime.split("-");
//        if (Integer.parseInt(curSpilt[0]) >= Integer.parseInt(startSplit[0]) && Integer.parseInt(curSpilt[0]) <= Integer.parseInt(endSplit[0])) {
//            if (Integer.parseInt(curSpilt[1]) >= Integer.parseInt(startSplit[1]) && Integer.parseInt(curSpilt[1]) <= Integer.parseInt(endSplit[1])) {
//                if (Integer.parseInt(curSpilt[2]) >= Integer.parseInt(startSplit[2]) && Integer.parseInt(curSpilt[2]) <= Integer.parseInt(endSplit[2])) {
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        }
//        return false;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dtStart = df.parse(startTime);
            Date dtEnd = df.parse(endTime);
            Date dtCurrent = df.parse(curTime);

            if (dtCurrent.getTime() >= dtStart.getTime()
                    && dtCurrent.getTime() <= dtEnd.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.EVENT_START_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                switchFragment(2);
            }
        }
    }
}

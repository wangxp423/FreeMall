package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.presenter.UserInfoPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.UserInfoPresenterImpl;
import com.aero.droid.dutyfree.talent.util.DialogUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.FileUtil;
import com.aero.droid.dutyfree.talent.util.ImageUtils;
import com.aero.droid.dutyfree.talent.util.JsonAnalysis;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.glidetrans.GlideCircleTransform;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;
import com.aero.droid.dutyfree.talent.view.UserInfoView;
import com.bumptech.glide.Glide;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Author : rongcl
 * Date : 2015/12/14
 * Desc : 个人信息
 */
public class UserInfoActivity extends BaseFragmentActivity implements UserInfoView, View.OnClickListener {
    @Bind(R.id.user_photo_cv_uf)
    ImageView mUserPhotoCv;
    @Bind(R.id.to_modify_photo_rl_uf)
    RelativeLayout mModifyPhotoRl;
    @Bind(R.id.nicheng_tv_uf)
    TextView userNickName;
    @Bind(R.id.to_edit_nicheng_rl_uf)
    RelativeLayout mEditNichengRl;
    @Bind(R.id.gonfhao_tv_uf)
    TextView userJobNum;
    @Bind(R.id.to_edit_gonghao_rl_uf)
    RelativeLayout mEditGonghaoRl;
    @Bind(R.id.user_name_tv_uf)
    TextView userName;
    @Bind(R.id.to_edit_user_name_rl_uf)
    RelativeLayout mEditUserNameRl;
    @Bind(R.id.phone_num_tv_uf)
    TextView userPhone;
    @Bind(R.id.to_bind_phone_num_rl_uf)
    RelativeLayout mBindPhoneNumRl;
    @Bind(R.id.weixin_id_tv_uf)
    TextView userWinxinStatus;
    @Bind(R.id.to_bind_weixin_rl_uf)
    RelativeLayout mBindWeixinRl;
    @Bind(R.id.sign_out_rl_uf)
    LinearLayout userLoginOut;
    /*********************************************/
    private UserInfoPresenter presenter;
    private User user;
    private UMSocialService mController;

    @Override
    protected void getBundleExtras(Bundle extras) {
        user = extras.getParcelable("user");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void onEventComming(final EventCenter eventCenter) {
        if (Constants.EVENT_ADD_DATA == eventCenter.getEventCode()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userPhone.setText((String) eventCenter.getData());
                }
            });
        }else if(Constants.EVENT_COMMIT_DATA == eventCenter.getEventCode()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userNickName.setText((String) eventCenter.getData());
                }
            });
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mController = (UMSocialService) UMServiceFactory
                .getUMSocialService("com.umeng.login");
        initTitle(getResources().getString(R.string.title_uf));
        mModifyPhotoRl.setOnClickListener(this);
        mEditNichengRl.setOnClickListener(this);
        mUserPhotoCv.setOnClickListener(this);
        userLoginOut.setOnClickListener(this);
        mBindPhoneNumRl.setOnClickListener(this);
        mBindWeixinRl.setOnClickListener(this);
        if (null != user) {
            userNickName.setText(user.getNickName());
            userJobNum.setText(user.getJobNum());
            userName.setText(user.getName());
            if (!TextUtils.isEmpty(user.getPhoto()))
                Glide.with(mContext).load(user.getPhoto()).transform(new GlideCircleTransform(mContext)).into(mUserPhotoCv);
            if ("0".equals(user.getBindStatus())) {
                userPhone.setText(user.getPhone());
                userWinxinStatus.setText(getResources().getString(R.string.weixin_bind));
            } else if ("1".equals(user.getBindStatus())) {
                userPhone.setText(getResources().getString(R.string.weixin_unbind));
                userWinxinStatus.setText(getResources().getString(R.string.weixin_bind));
            } else if ("2".equals(user.getBindStatus())) {
                userPhone.setText(user.getPhone());
                userWinxinStatus.setText(getResources().getString(R.string.weixin_unbind));
            }
        }

        presenter = new UserInfoPresenterImpl(UserInfoActivity.this, this);
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
        return true;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case Constants.EVENT_START_CAMERA_ACTIVITY:
                try {
                    String lastName = FileUtil.getFiles(FileUtil.getImagePath()).get(0);
                    File temp = new File(FileUtil.getImagePath() + lastName);
                    FileUtil.startPhotoZoom(UserInfoActivity.this, Uri.fromFile(temp));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case Constants.EVENT_START_PHOTO_ACTIVITY:
                if (data != null) {
                    FileUtil.startPhotoZoom(UserInfoActivity.this, data.getData());
                }
                break;
            case Constants.EVENT_CUT_PHOTO:
                if (data != null) {
                    FileUtil.savePic(data);
                    final Bitmap photo = data.getExtras().getParcelable("data");
                    mUserPhotoCv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ImageUtils.toRoundBitmap(mUserPhotoCv, photo);
                            presenter.uploadPhoto(TAG_LOG, FileUtil.getPhotoPath() + "photo_head.png");
                        }
                    }, 500);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_modify_photo_rl_uf:
            case R.id.user_photo_cv_uf:
                DialogUtil.getPhotoDialog(UserInfoActivity.this);
                break;
            case R.id.to_bind_phone_num_rl_uf:
                if (getResources().getString(R.string.weixin_unbind).equals(userPhone.getText())){
                    readyGo(BindPhoneActivity.class);
                }else {
                    ToastUtil.showShortToast(mContext, getResources().getString(R.string.weixin_bind));
                }
                break;
            case R.id.to_bind_weixin_rl_uf:
                if (getResources().getString(R.string.weixin_unbind).equals(userWinxinStatus.getText())){
                    setWeixinLogin();
                }else {
                    ToastUtil.showShortToast(mContext,getResources().getString(R.string.weixin_bind));
                }
                break;
            case R.id.sign_out_rl_uf:
                presenter.clickExit();
                break;
            case R.id.to_edit_nicheng_rl_uf:
                Bundle bundle = new Bundle();
                bundle.putString("nickName",userNickName.getText().toString());
                readyGo(RenameActivity.class,bundle);
                break;
        }
    }


    @Override
    public void changeNameSuccess() {

    }

    @Override
    public void bindPhoneSuccess() {

    }

    @Override
    public void bindWeixinSuccess() {

    }

    @Override
    public void uploadPhotoS(String msg) {

    }

    @Override
    public void requestFail(String msg) {

    }

    //微信登录
    private void setWeixinLogin() {
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
                                        // Toast.makeText(mContext,
                                        // "获取平台数据开始...",
                                        // Toast.LENGTH_SHORT).show();
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
                                            setLoginData(userId, userImg, userName, "wx", unionid);
                                            LogUtil.t("绑定微信 = " + info.toString());
                                            LogUtil.t("userId = " + userId);
                                            LogUtil.t("userImg = " + userImg);
                                            LogUtil.t("userName = " + userName);
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

    /**
     * 绑定微信号
     *
     * @param userId
     * @param userImg
     * @param nickName
     * @param loginType
     * @param unionid
     */
    private void setLoginData(final String userId, final String userImg, final String nickName, final String loginType, final String unionid) {
        getLoading();
        HashMap<String, String> params = new HashMap<String, String>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("third", loginType);
        params.put("usid", userId);
        params.put("unionid", unionid);
        params.put("image", userImg);
        params.put("nickName", nickName);
        params.put("deviceType", "-3");
        OkHttpRequest.okHttpPost(mContext, Url.BINDWX, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, final String msg) {
                LogUtil.v("JSON", "绑定微信帐号成功 = " + data.toString());
                if ("0".equals(code)) {
                    if (data.has("memberInfo")) {
                        User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                        UserUtil.setUserInfo(mContext, data.optJSONObject("memberInfo").toString());
                        UserUtil.setUserId(mContext, user.getMemberId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userNickName.setText(nickName);
                                userWinxinStatus.setText(getResources().getString(R.string.weixin_bind));
                                dismissLoading();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showShortToast(mContext, msg);
                                dismissLoading();
                            }
                        });

                    }
                } else if ("2".equals(code)) {
                    setMergeWinxinData(userId, userImg, nickName, loginType, unionid);
                }
            }

            @Override
            public void onRespError(String code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShortToast(mContext, msg);
                        dismissLoading();
                    }
                });

            }
        });
    }

    /**
     * 合并微信号数据(该手机号已经绑定过其他微信号)
     *
     * @param userId
     * @param userImg
     * @param nickName
     * @param loginType
     * @param unionid
     */
    private void setMergeWinxinData(String userId, String userImg, final String nickName, String loginType, String unionid) {
        HashMap<String, String> params = new HashMap<String, String>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("third", loginType);
        params.put("usid", userId);
        params.put("unionid", unionid);
        params.put("image", userImg);
        params.put("nickName", nickName);
        OkHttpRequest.okHttpPost(mContext, Url.MERGEMEMBER, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, final String msg) {
                LogUtil.v("JSON", "合并帐号成功 = " + data.toString());
                if (data.has("memberInfo")) {
                    User user = JsonAnalysis.getLoginUser(data.optJSONObject("memberInfo"));
                    UserUtil.setUserInfo(mContext, data.optJSONObject("memberInfo").toString());
                    UserUtil.setUserId(mContext, user.getMemberId());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userNickName.setText(nickName);
                            userWinxinStatus.setText(getResources().getString(R.string.weixin_bind));
                            dismissLoading();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShortToast(mContext, msg);
                            dismissLoading();
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
                        dismissLoading();
                    }
                });


            }
        });
    }

}

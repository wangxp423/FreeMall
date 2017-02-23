package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.Url;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.PatternUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.util.network.OkHttpRequest;
import com.aero.droid.dutyfree.talent.util.network.RespListener;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Author : wangxp
 * Date : 2016/1/20
 * Desc : 更改用户昵称 页面
 */
public class RenameActivity extends BaseFragmentActivity implements View.OnClickListener, TextWatcher {
    @Bind(R.id.rename_edit)
    EditText renameEdit;
    @Bind(R.id.rename_edit_clear)
    ImageView renameEditClear;
    @Bind(R.id.rename_desc)
    TextView renameDesc;
    /*************************************/
    private String nickName;

    @Override
    protected void getBundleExtras(Bundle extras) {
        nickName = extras.getString("nickName");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rename;
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
        initTitleRightTv(getResources().getString(R.string.rename_nickname), getResources().getString(R.string.rename_nickname_save), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(renameEdit.getText().toString());
            }
        });
        renameEdit.setText(nickName);
        renameEdit.addTextChangedListener(this);
        renameEditClear.setOnClickListener(this);
        if (TextUtils.isEmpty(nickName)) {
            renameEditClear.setVisibility(View.GONE);
        } else {
            renameEditClear.setVisibility(View.VISIBLE);
        }

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
            case R.id.rename_edit_clear:
                renameEdit.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            renameEditClear.setVisibility(View.GONE);
        } else {
            renameEditClear.setVisibility(View.VISIBLE);
        }
    }

    private void checkData(String nickName) {
        if (this.nickName.equals(nickName)) {
            finish();
        } else {
            if (TextUtils.isEmpty(nickName)) {
                ToastUtil.showShortToast(mContext, getResources().getString(R.string.rename_nickname_null));
            } else {
                if (PatternUtil.checkNickName(nickName)) {
                    renameNickName(nickName);
                } else {
                    ToastUtil.showShortToast(mContext, getResources().getString(R.string.rename_nickname_error));
                }
            }
        }
    }

    private void renameNickName(final String nickName) {
        HashMap<String, String> params = new HashMap<>();
        String memberId = UserUtil.getUserId(mContext);
        params.put("memberId", TextUtils.isEmpty(memberId) ? "0" : memberId);
        params.put("nickName", nickName);
        OkHttpRequest.okHttpGet(mContext, Url.MODIFYNICKNAME, params, new RespListener() {
            @Override
            public void onRespSucc(JSONObject data, String code, String msg) {
                LogUtil.v("JSON", "修改昵称成功 = " + data.toString());
                EventCenter<String> eventCenter = new EventCenter<String>(Constants.EVENT_COMMIT_DATA, nickName);
                EventBus.getDefault().post(eventCenter);
                finish();
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

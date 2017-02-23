package com.aero.droid.dutyfree.talent.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.WriteComentsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.WriteComentsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UserUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.WriteComentsView;
import com.aero.droid.dutyfree.talent.widgets.AhDiaLog;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/12
 * Desc : 写商品评论
 */
public class WriteComentsActivity extends BaseFragmentActivity implements WriteComentsView, TextWatcher, View.OnClickListener {

    @Bind(R.id.write_good_img)
    ImageView writeGoodImgIv;
    @Bind(R.id.write_good_name)
    TextView writeGoodNameTv;
    @Bind(R.id.write_good_fen)
    RatingBar writeGoodFenRb;
    @Bind(R.id.write_good_desc)
    EditText writeGoodDescEd;
    @Bind(R.id.write_good_desc_lengh)
    TextView writeDescLenghTv;
    @Bind(R.id.title_left_layout)
    RelativeLayout titleBack;
    /*****************************************/
    private WriteComentsPresenter presenter;
    private String goodId, goodName, goodImg;

    @Override
    protected void getBundleExtras(Bundle extras) {
        goodId = extras.getString("goodId");
        goodName = extras.getString("goodName");
        goodImg = extras.getString("goodImg");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_write_coments;
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
        initTitleRightTv(getResources().getString(R.string.coments_coments), getResources().getString(R.string.coments_commint), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(writeGoodDescEd.getText().toString())) {
                    ToastUtil.showLongToast(mContext, getResources().getString(R.string.coments_commint_null));
                } else {
                    if (TextUtils.isEmpty(UserUtil.getUserId(mContext))) {
                        readyGo(LoginActivity.class);
                    } else {
                        presenter.commitComtens(TAG_LOG, getCmtInfo());
                    }
                }
            }
        });
        titleBack.setOnClickListener(this);
        writeGoodDescEd.addTextChangedListener(this);
        Glide.with(mContext).load(goodImg).into(writeGoodImgIv);
        writeGoodNameTv.setText(goodName);
        presenter = new WriteComentsPresenterImpl(WriteComentsActivity.this, this);
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
    public void writeComentsData(String msg) {
        finish();
    }

    @Override
    public void requestError(String msg) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s.toString())) {
            writeDescLenghTv.setText(s.toString().length() + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_layout:
                if (TextUtils.isEmpty(writeGoodDescEd.getText().toString())) {
                    finish();
                } else {
                    showDialog();
                }
                break;
        }

    }

    private AhDiaLog myDialog;

    private void showDialog() {
        myDialog = new AhDiaLog(this, getResources().getString(R.string.coments_commint_forgive), getResources().getString(R.string.coments_commint_remind), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                finish();
            }
        });
        myDialog.show();
    }

    private String getCmtInfo() {
//        JSONObject cmtInfo = new JSONObject();
        JSONObject item = new JSONObject();
        String memeberId = UserUtil.getUserId(mContext);
        try {
            item.put("memberId", TextUtils.isEmpty(memeberId) ? "0" : memeberId);
            item.put("goodsId", goodId);
            item.put("content", writeGoodDescEd.getText().toString());
            item.put("starQty", String.valueOf((int) writeGoodFenRb.getRating()));
//            cmtInfo.put("cmtInfo",item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item.toString();
    }

}

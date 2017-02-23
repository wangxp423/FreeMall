package com.aero.droid.dutyfree.talent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.presenter.FeedbackPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.FeedbackPresenterImpl;
import com.aero.droid.dutyfree.talent.util.DialogUtil;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.FileUtil;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.ToastUtil;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.FeedbackView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/1/12
 * Desc : 反馈
 */

public class FeedBackActivity extends BaseFragmentActivity implements FeedbackView, RadioGroup.OnCheckedChangeListener, TextWatcher {

    @Bind(R.id.feed_back_radio1)
    RadioButton radio1;
    @Bind(R.id.feed_back_radio2)
    RadioButton radio2;
    @Bind(R.id.feed_back_radio3)
    RadioButton radio3;
    @Bind(R.id.feed_back_radio4)
    RadioButton radio4;
    @Bind(R.id.feed_back_radio5)
    RadioButton radio5;
    @Bind(R.id.feed_back_radiogroup)
    RadioGroup radiogroup;
    @Bind(R.id.feed_back_edit)
    EditText feedBackEdit;
    @Bind(R.id.feed_back_length)
    TextView feedBackLength;
    @Bind(R.id.feed_back_gv)
    GridView feedBackGv;
    @Bind(R.id.feed_back_add_imgs_title_layout)
    LinearLayout addImgsTitleLayout;
    /********************************************/
    private JavaBeanBaseAdapter<String> adapter;
    private List<String> imgList = new ArrayList<>();
    private String statQty = "5";//评星数量
    private FeedbackPresenter presenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return radiogroup;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitleRightTv(getResources().getString(R.string.title_feed_back), getResources().getString(R.string.commit_feed_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.checkData(statQty, feedBackEdit.getText().toString())) {
                    getLoading();
                    presenter.clickCommit(TAG_LOG, statQty, feedBackEdit.getText().toString(), imgList);
                }

            }
        });
        FrameLayout.LayoutParams imgTitleParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imgTitleParams.leftMargin = (mScreenWidth+UiUtil.dip2px(mContext,20))/3;
        imgTitleParams.gravity = Gravity.BOTTOM;
        addImgsTitleLayout.setLayoutParams(imgTitleParams);
        radiogroup.setOnCheckedChangeListener(this);
        feedBackEdit.addTextChangedListener(this);
        imgList.add("normal");//加一个默认图片
        adapter = new JavaBeanBaseAdapter<String>(mContext, R.layout.item_brand, imgList) {
            @Override
            protected void bindView(int position, ViewHolder holder, final String path) {
                int width = mScreenWidth - UiUtil.dip2px(mContext, 40);
                ImageView img = holder.getView(R.id.brand_logo);
                ViewGroup.LayoutParams params = img.getLayoutParams();
                params.width = width / 3;
                params.height = width / 3;
                img.setLayoutParams(params);
                LogUtil.t("path = " + path);
                if ("normal".equals(path)) {
                    Glide.with(mContext).load(R.mipmap.feedback_iv_normal).placeholder(R.mipmap.holder_place_square1).into(img);
                }else {
                    Glide.with(mContext).load(new File(path)).placeholder(R.mipmap.holder_place_square1).into(img);
                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("normal".equals(path))
                            DialogUtil.getPhotoDialog(FeedBackActivity.this);
                    }
                });

            }
        };
        feedBackGv.setAdapter(adapter);
        presenter = new FeedbackPresenterImpl(FeedBackActivity.this, this);
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
        if (checkedId == R.id.feed_back_radio1) {
            statQty = "1";
        } else if (checkedId == R.id.feed_back_radio2) {
            statQty = "2";
        } else if (checkedId == R.id.feed_back_radio3) {
            statQty = "3";
        } else if (checkedId == R.id.feed_back_radio4) {
            statQty = "4";
        } else if (checkedId == R.id.feed_back_radio5) {
            statQty = "5";
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
        feedBackLength.setText(s.toString().length() + "/200");
    }

    @Override
    public void feedbackSuccess(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                ToastUtil.showLongToast(mContext, msg);
                finish();
            }
        });
    }

    @Override
    public void feedbackFail(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                ToastUtil.showLongToast(mContext, msg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addImgsTitleLayout.setVisibility(View.GONE);
        switch (requestCode) {
            case Constants.EVENT_START_CAMERA_ACTIVITY:
                try {
                    String lastName = FileUtil.getFiles(FileUtil.getImagePath()).get(0);
                    File temp = new File(FileUtil.getImagePath() + lastName);
                    if (imgList.size()==3 && imgList.contains("normal"))
                        imgList.remove("normal");
                    imgList.add(0,temp.getAbsolutePath());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case Constants.EVENT_START_PHOTO_ACTIVITY:
                if (data != null) {
                    String path = FileUtil.getRealFilePath(mContext,data.getData());
                    if (imgList.size()==3 && imgList.contains("normal"))
                        imgList.remove("normal");
                    imgList.add(0,path);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

}

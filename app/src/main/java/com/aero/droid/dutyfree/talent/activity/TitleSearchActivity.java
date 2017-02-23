package com.aero.droid.dutyfree.talent.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragmentActivity;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.presenter.SearchGoodsPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.SearchGoodsPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.SearchGoodsView;
import com.aero.droid.dutyfree.talent.widgets.WithInputLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author : wangxp
 * Date : 2015/12/5
 * Desc :  搜索商品页
 */
public class TitleSearchActivity extends BaseFragmentActivity implements SearchGoodsView, View.OnClickListener, TextWatcher,AdapterView.OnItemClickListener {
    @Bind(R.id.title_search_edit)
    EditText searchEdit;
    @Bind(R.id.title_search_cancel)
    TextView searchCancel;
    @Bind(R.id.title_search_lv)
    ListView searchLv;
    @Bind(R.id.title_search_body_layout)
    WithInputLinearLayout searchBodyLayout;


    /*************************************/
    private boolean isShow = false;
    private InputMethodManager inputManager;
    private SearchGoodsPresenter presenter;
    private JavaBeanBaseAdapter<GoodsInfo> adapter;
    private String result; //搜索结果
    private List<GoodsInfo> goodsInfos = new ArrayList<>();
    private int curPage = 1;
    private int pageSize = 100; //因为是搜索结果，目前设置大一点
    private boolean isBack = true; //搜索结果是否返回

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_title_search;
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
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        searchCancel.setOnClickListener(this);
        searchEdit.addTextChangedListener(this);
        searchLv.setOnItemClickListener(this);
        searchBodyLayout.setOnSizeChangedListener(new WithInputLinearLayout.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                if (h > oldh) {
                    isShow = false;
                    if (goodsInfos.size() < 1)
                        searchBodyLayout.setBackgroundColor(0);
                } else {
                    isShow = true;
                    searchBodyLayout.setBackgroundColor(Color.parseColor("#c0000000"));
                }
            }
        });
        adapter = new JavaBeanBaseAdapter<GoodsInfo>(mContext, R.layout.item_airport_company, goodsInfos) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsInfo info) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = UiUtil.dip2px(mContext,5);
                TextView goodName = holder.getView(R.id.airport_company_name);
                goodName.setLayoutParams(params);
                goodName.setTextColor(getResources().getColor(R.color.black_text));
                String name = info.getGoodsName();
                if (name.contains(result)) {
                    SpannableStringBuilder style = new SpannableStringBuilder(name);
                    int start = name.indexOf(result);
                    int end = start + result.length();
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#ff7a04")), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    goodName.setText(style);
                } else {
                    goodName.setText(name);
                }
            }
        };
        searchLv.setAdapter(adapter);


        presenter = new SearchGoodsPresenterImpl(TitleSearchActivity.this, this);

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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    public void showGoodsList(final List<GoodsInfo> infoList) {
        isBack = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (goodsInfos.size() > 0) {
                    goodsInfos.clear();
                }
                goodsInfos.addAll(infoList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showEmptyData() {
        isBack = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchLv.setEmptyView(findViewById(R.id.title_search_empty));
            }
        });

    }

    @Override
    public void requestError(String msg) {
        isBack = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchLv.setEmptyView(findViewById(R.id.title_search_empty));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            searchBodyLayout.setBackgroundColor(0);
            if (isShow)
                inputManager.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_cancel:
                searchBodyLayout.setBackgroundColor(0);
                if (isShow)
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                finish();
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
        if (!TextUtils.isEmpty(s.toString()) && isBack) {
            isBack = false;
            result = s.toString();
            presenter.getGoodsList(TAG_LOG, s.toString(), curPage, pageSize);
        } else {
            goodsInfos.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsInfo info = goodsInfos .get(position);
        Bundle bundle = new Bundle();
        bundle.putString("goodId", info.getId());
        bundle.putString("srcType", TextUtils.isEmpty(info.getSrcType()) ? "0" : info.getSrcType());
        bundle.putString("srcId", TextUtils.isEmpty(info.getSrcId()) ? "0" : info.getSrcId());
        readyGo(GoodsDetailActivity.class, bundle);
        finish();
    }
}

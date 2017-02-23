package com.aero.droid.dutyfree.talent.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.base.BaseFragment;
import com.aero.droid.dutyfree.talent.base.JavaBeanBaseAdapter;
import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.presenter.GoodsCategoryPresenter;
import com.aero.droid.dutyfree.talent.presenter.impl.GoodsCategoryPresenterImpl;
import com.aero.droid.dutyfree.talent.util.EventCenter;
import com.aero.droid.dutyfree.talent.util.UiUtil;
import com.aero.droid.dutyfree.talent.util.netstatus.NetUtils;
import com.aero.droid.dutyfree.talent.view.GoodsCategoryView;
import com.bumptech.glide.Glide;
import com.ybao.pullrefreshview.layout.BaseFooterView;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.pullrefreshview.layout.RGPullRefreshLayout;
import com.ybao.pullrefreshview.view.PullableGridView;

import java.util.List;

import butterknife.Bind;

/**
 * Author : wangxp
 * Date : 2015/11/28
 * Desc : 商品分类页
 */
public class GoodsCategoryFragment extends BaseFragment implements BaseHeaderView.OnRefreshListener, BaseFooterView.OnLoadListener, GoodsCategoryView, AdapterView.OnItemClickListener {
    /*@Bind(R.id.goods_category_header)
    ExpandHeaderView goodsCategoryHeader;
    @Bind(R.id.goods_category_footer)
    ExpandFooterView goodsCategoryFooter;*/
    @Bind(R.id.goods_category_gv)
    PullableGridView goodsCategoryGv;
    @Bind(R.id.goods_category_pull_layout)
    RGPullRefreshLayout goodsCategoryPullLayout;

    /************************************/
    private JavaBeanBaseAdapter<GoodsCategory> adapter;
    private List<GoodsCategory> categoryList;
    private GoodsCategoryPresenter presenter;

    @Override
    protected void onFirstUserVisible() {
        presenter = new GoodsCategoryPresenterImpl(getActivity(), this);
        if (NetUtils.isNetworkConnected(getActivity())) {
            presenter.getData(TAG_LOG,getActivity(), 0, 0);
        } else {
            //没有网络的处理
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getData(TAG_LOG,getActivity(), 0, 0);
                }
            });
        }
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return goodsCategoryPullLayout;
    }

    @Override
    protected void initViewsAndEvents() {
//        goodsCategoryHeader.setOnRefreshListener(this);
//        goodsCategoryFooter.setOnLoadListener(this);
        goodsCategoryGv.setOnItemClickListener(this);
        adapter = new JavaBeanBaseAdapter<GoodsCategory>(getActivity(), R.layout.item_category) {
            @Override
            protected void bindView(int position, ViewHolder holder, GoodsCategory object) {
                ImageView categoryImg = holder.getView(R.id.category_img);
                TextView categoryTitle = holder.getView(R.id.category_title);
                categoryTitle.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = categoryImg.getLayoutParams();
                int mWidth = UiUtil.getWindowWidth(mContext) - UiUtil.dip2px(mContext, 8);
                params.width = mWidth / 3;
                params.height = mWidth / 3;
                categoryImg.setLayoutParams(params);
                categoryImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GoodsCategory category = getItem(position);
                categoryTitle.setText(category.getCategoryName());
                Glide.with(mContext).load(category.getCategoryImg()).into(categoryImg);
            }
        };
        goodsCategoryGv.setAdapter(adapter);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fm_goods_category;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected void onEventBackgroundComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    /**
     * 上拉加载控件 listener
     *
     * @param baseFooterView
     */
    @Override
    public void onLoad(BaseFooterView baseFooterView) {
//        baseFooterView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                goodsCategoryFooter.stopLoad();
//            }
//        }, 2000);
    }

    /**
     * 下拉刷新控件 listener
     *
     * @param baseHeaderView
     */
    @Override
    public void onRefresh(BaseHeaderView baseHeaderView) {
//        baseHeaderView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                goodsCategoryHeader.stopRefresh();
//            }
//        }, 2000);
    }

    /**
     * 第一次进入 页面 获取数据 回调
     *
     * @param categoryList
     */
    @Override
    public void showGoodsCategory(List<GoodsCategory> categoryList) {
        this.categoryList = categoryList;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(GoodsCategoryFragment.this.categoryList);
            }
        });

    }

    /**
     * 下拉刷新 数据回调
     *
     * @param categoryList
     */
    @Override
    public void showRefreshGoodsCategory(List<GoodsCategory> categoryList) {

    }

    /**
     * 上拉加载 数据回调
     *
     * @param categoryList
     */
    @Override
    public void showloadGoodsCategory(List<GoodsCategory> categoryList) {

    }

    /**
     * gridView Item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsCategory category = categoryList.get(position);
        presenter.clickCategory(category);
    }

    /**
     * 没有数据
     *
     * @param msg
     */
    @Override
    public void showEmpty(String msg,int imgId) {
        toggleShowEmpty(true, null,imgId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(TAG_LOG,getActivity(), 0, 0);
            }
        });
    }

    /**
     * 网络异常
     */
    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(TAG_LOG,getActivity(), 0, 0);
            }
        });
    }

}

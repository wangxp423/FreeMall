
package com.aero.droid.dutyfree.talent.base;

import android.view.Gravity;
import android.widget.PopupWindow;

import com.aero.droid.dutyfree.talent.util.PopWindowUtil;
import com.aero.droid.dutyfree.talent.view.base.BaseView;
import com.umeng.analytics.MobclickAgent;

/**
 * Author:  wangxp
 * Date:    15/11/22
 * Description:
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG_LOG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG_LOG);
    }


    @Override
    public void showEmpty(String msg,int imgId) {
        toggleShowEmpty(true, msg,imgId, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

}

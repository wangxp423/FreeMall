package com.aero.droid.dutyfree.talent.view;

import com.aero.droid.dutyfree.talent.bean.User;
import com.aero.droid.dutyfree.talent.view.base.BaseView;

/**
 * Author : rongCL
 * Date : 2015/12/10
 * Desc : 个人中心
 */
public interface MyCenterView extends BaseView {
    /**
     * 显示用户数据
     * @param user
     */
    void showUserData(User user);

    /**
     * 请求失败的显示
     * @param msg
     */
    void requestError(String msg);
}

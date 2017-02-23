package com.aero.droid.dutyfree.talent.presenter;

/**
 * Author : wangxp
 * Date : 2015/12/4
 * Desc : 品牌下商品 逻辑控制器
 */
public interface BrandGoodsPresenter {
    /**
     * 获取商品列表
     *
     * @param log_tag
     * @param paramType 1分类查 2品牌查 3属性查
     * @param param     对应楼上 1分类ID 2品牌ID 3关键字
     * @param curPage   当前页
     * @param pageSize  一页数量
     */
    void getGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize);

    /**
     * 下拉刷新商品列表
     *
     * @param log_tag
     * @param paramType 1分类查 2品牌查 3属性查
     * @param param     对应楼上 1分类ID 2品牌ID 3关键字
     * @param curPage   当前页
     * @param pageSize  一页数量
     */
    void refreshGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize);

    /**
     * 上拉加载商品列表
     *
     * @param log_tag
     * @param paramType 1分类查 2品牌查 3属性查
     * @param param     对应楼上 1分类ID 2品牌ID 3关键字
     * @param curPage   当前页
     * @param pageSize  一页数量
     */
    void loadGoodsList(String log_tag, String paramType, String param, int curPage, int pageSize);

}

package com.aero.droid.dutyfree.talent.util;


import com.aero.droid.dutyfree.talent.bean.ActiveInfo;
import com.aero.droid.dutyfree.talent.bean.Advertise;
import com.aero.droid.dutyfree.talent.bean.AirportCompany;
import com.aero.droid.dutyfree.talent.bean.ComentsInfo;
import com.aero.droid.dutyfree.talent.bean.CouponsInfo;
import com.aero.droid.dutyfree.talent.bean.DiscountInfo;
import com.aero.droid.dutyfree.talent.bean.Discover;
import com.aero.droid.dutyfree.talent.bean.FlightInfo;
import com.aero.droid.dutyfree.talent.bean.GoodComents;
import com.aero.droid.dutyfree.talent.bean.GoodsBrand;
import com.aero.droid.dutyfree.talent.bean.GoodsCategory;
import com.aero.droid.dutyfree.talent.bean.GoodsDetail;
import com.aero.droid.dutyfree.talent.bean.GoodsInfo;
import com.aero.droid.dutyfree.talent.bean.GoodsSort;
import com.aero.droid.dutyfree.talent.bean.HandpickBanner;
import com.aero.droid.dutyfree.talent.bean.HandpickType;
import com.aero.droid.dutyfree.talent.bean.MessageInfo;
import com.aero.droid.dutyfree.talent.bean.OrderDetail;
import com.aero.droid.dutyfree.talent.bean.OrderInfo;
import com.aero.droid.dutyfree.talent.bean.OrderListInfo;
import com.aero.droid.dutyfree.talent.bean.SecondDiscover;
import com.aero.droid.dutyfree.talent.bean.SpecialInfo;
import com.aero.droid.dutyfree.talent.bean.StatusInfo;
import com.aero.droid.dutyfree.talent.bean.SubFind;
import com.aero.droid.dutyfree.talent.bean.TaskInfo;
import com.aero.droid.dutyfree.talent.bean.TaskListInfo;
import com.aero.droid.dutyfree.talent.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * author wangxp Json解析工具类
 */
public class JsonAnalysis {

    /**
     * 获取航空公司列表
     *
     * @param airportArray
     * @return
     */
    public static List<AirportCompany> getAirlineList(JSONArray airportArray) {
        List<AirportCompany> airportList = new ArrayList<AirportCompany>();
        for (int i = 0; i < airportArray.length(); i++) {
            try {
                JSONObject airportItem = airportArray.getJSONObject(i);
                AirportCompany airport = new AirportCompany();
                airport.setName(airportItem.optString("airline"));
                airport.setTwocode(airportItem.optString("twoCode"));
                airportList.add(airport);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getAirlineList Excetion");
            }
        }
        return airportList;
    }

    /**
     * 获取航班信息
     *
     * @param flightJson
     * @return
     */
    public static FlightInfo getFlightInfo(JSONObject flightJson) {
        FlightInfo info = new FlightInfo();
        if (flightJson.has("airline"))
            info.setAirline(flightJson.optString("airline"));
        if (flightJson.has("flightNo"))
            info.setFlightNo(flightJson.optString("flightNo"));
        if (flightJson.has("depart"))
            info.setDepart(flightJson.optString("depart"));
        if (flightJson.has("departDate"))
            info.setDepartDate(flightJson.optString("departDate"));
        if (flightJson.has("departTime"))
            info.setDepartTime(flightJson.optString("departTime"));
        if (flightJson.has("arrive"))
            info.setArrive(flightJson.optString("arrive"));
        if (flightJson.has("arriveDate"))
            info.setArriveDate(flightJson.optString("arriveDate"));
        if (flightJson.has("arriveTime"))
            info.setArriveTime(flightJson.optString("arriveTime"));

        return info;
    }


    /**
     * 我的优惠卷列表
     */
    public static List<CouponsInfo> getMyCouponsList(JSONArray couponList) {
        List<CouponsInfo> couponsInfos = new ArrayList<CouponsInfo>();
        try {
            for (int i = 0; i < couponList.length(); i++) {
                JSONObject jsonInfo = couponList.getJSONObject(i);
                CouponsInfo info = new CouponsInfo();
                info.setCouponName(jsonInfo.optString("couponName"));
                info.setStatus(jsonInfo.optString("status"));
                info.setBeginTime(jsonInfo.optString("beginTime"));
                info.setEndTime(jsonInfo.optString("endTime"));
                info.setCouponValue(jsonInfo.optString("couponValue"));
                info.setCouponId(jsonInfo.optString("couponId"));
                info.setSubTitle(jsonInfo.optString("subTitle"));
                info.setType(jsonInfo.optString("couponType"));
                if (jsonInfo.has("minMoney"))
                    info.setMinMoney(jsonInfo.optString("minMoney"));
                if (jsonInfo.has("maxMoney"))
                    info.setMaxMoney(jsonInfo.optString("maxMoney"));
                couponsInfos.add(info);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getMyCouponsList Excetion");
        }
        return couponsInfos;
    }


    /**
     * 获取用户信息
     *
     * @param result
     * @return
     */

    public static User getLoginUser(JSONObject result) {
        User user = new User();
        try {
            if (result.has("memberId"))
                user.setMemberId(result.optString("memberId"));
            if (result.has("name"))
                user.setName(result.optString("name"));
            if (result.has("nickName"))
                user.setNickName(result.optString("nickName"));
            if (result.has("phone"))
                user.setPhone(result.optString("phone"));
            if (result.has("tpno"))
                user.setTpno(result.optString("tpno"));
            if (result.has("unionId"))
                user.setUnionId(result.optString("unionId"));
            if (result.has("usid"))
                user.setUsid(result.optString("usid"));
            if (result.has("photo"))
                user.setPhoto(result.optString("photo"));
            if (result.has("vipId"))
                user.setVipId(result.optString("vipId"));
            if (result.has("bindStatus"))
                user.setBindStatus(result.optString("bindStatus"));
            if (result.has("jobNum"))
                user.setJobNum(result.optString("jobNum"));
            if (result.has("twoCode"))
                user.setTwoCode(result.optString("twoCode"));
            if (result.has("boardingPass"))
                user.setBoardingPass(result.optString("boardingPass"));
            if (result.has("isAuth"))
                user.setIsAuth(result.optString("isAuth"));
            if (result.has("spInfo")) {
                List<SpecialInfo> specialList = new ArrayList<>();
                JSONArray spArray = result.optJSONArray("spInfo");
                for (int i = 0; i < spArray.length(); i++) {
                    JSONObject spInfo = spArray.getJSONObject(i);
                    SpecialInfo info = new SpecialInfo();
                    if (spInfo.has("spId"))
                        info.setSpId(spInfo.optString("spId"));
                    if (spInfo.has("spDesc"))
                        info.setSpDesc(spInfo.optString("spDesc"));
                    specialList.add(info);
                }
                user.setSpecialInfos(specialList);
            }
            if (result.has("unCmtQty"))
                user.setUnCmtQty(result.optString("unCmtQty"));
            if (result.has("couponQty"))
                user.setCouponQty(result.optString("couponQty"));
            if (result.has("favoQty"))
                user.setFavoQty(result.optString("favoQty"));
            if (result.has("msgQty"))
                user.setMsgQty(result.optString("msgQty"));
            if (result.has("unreadMsgQty"))
                user.setUnreadMsgQty(result.optString("unreadMsgQty"));
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getLoginUser Excetion");
        }

        return user;
    }


    /**
     * 获取商品排序列表
     *
     * @param sortArray
     * @return
     */
    public static List<GoodsSort> getSortList(JSONArray sortArray) {
        List<GoodsSort> sortList = new ArrayList<GoodsSort>();
        for (int i = 0; i < sortArray.length(); i++) {
            try {
                JSONObject sortItem = sortArray.getJSONObject(i);
                GoodsSort goodsSort = new GoodsSort();
                goodsSort.setSortName(sortItem.optString("sortName"));
                goodsSort.setSortParam(sortItem.optString("sortParam"));
                goodsSort.setSortType(sortItem.optString("sortType"));
                sortList.add(goodsSort);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getSortList Excetion");
            }
        }
        return sortList;
    }


    /**
     * 获取广告列表
     *
     * @param advertArray
     * @return
     */
    public static List<Advertise> getAdvertList(JSONArray advertArray) {
        List<Advertise> advertisesList = new ArrayList<Advertise>();
        for (int i = 0; i < advertArray.length(); i++) {
            try {
                JSONObject advertItem = advertArray.getJSONObject(i);
                Advertise advert = new Advertise();
                if (advertItem.has("id"))
                    advert.setId(advertItem.optString("id"));
                if (advertItem.has("advertImage"))
                    advert.setAdvertImage(advertItem.optString("advertImage"));
                if (advertItem.has("advertName"))
                    advert.setAdvertName(advertItem.optString("advertName"));
                if (advertItem.has("advertTitle"))
                    advert.setAdvertTitle(advertItem.optString("advertTitle"));
                if (advertItem.has("advertArea"))
                    advert.setAdvertArea(advertItem.optString("advertArea"));
                if (advertItem.has("postpone"))
                    advert.setPostpone(advertItem.optString("postpone"));
                if (advertItem.has("pushTimes"))
                    advert.setPushTimes(advertItem.optString("pushTimes"));
                if (advertItem.has("ruleType"))
                    advert.setRuleType(advertItem.optString("ruleType"));
                if (advertItem.has("ruleItemId"))
                    advert.setRuleItemId(advertItem.optString("ruleItemId"));
                if (advertItem.has("ruleItemType"))
                    advert.setRuleItemType(advertItem.optString("ruleItemType"));
                if (advertItem.has("beginTime"))
                    advert.setBeginTime(advertItem.optString("beginTime"));
                if (advertItem.has("endTime"))
                    advert.setEndTime(advertItem.optString("endTime"));
                if (advertItem.has("btnImage"))
                    advert.setBtnImage(advertItem.optString("btnImage"));
                if (advertItem.has("sort"))
                    advert.setSort(advertItem.optString("sort"));
                advertisesList.add(advert);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getAdvertList Excetion");
            }
        }
        return advertisesList;
    }

    /**
     * 获取商品信息集合
     *
     * @param goodsArray
     * @return
     */
    public static List<GoodsInfo> getGoodsInfoList(JSONArray goodsArray) {
        List<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < goodsArray.length(); i++) {
            try {
                JSONObject goodsItem = goodsArray.getJSONObject(i);
                GoodsInfo goodsInfo = new GoodsInfo();
                if (goodsItem.has("id"))
                    goodsInfo.setId(goodsItem.optString("id"));
                if (goodsItem.has("goodsId"))
                    goodsInfo.setId(goodsItem.optString("goodsId"));
                if (goodsItem.has("activeId"))
                    goodsInfo.setActiveId(goodsItem.optString("activeId"));
                if (goodsItem.has("goodsName"))
                    goodsInfo.setGoodsName(goodsItem.optString("goodsName"));
                if (goodsItem.has("goodsImg"))
                    goodsInfo.setGoodsImg(goodsItem.optString("goodsImg"));
                if (goodsItem.has("goodsDes"))
                    goodsInfo.setGoodsDes(goodsItem.optString("goodsDes"));
                if (goodsItem.has("memo"))
                    goodsInfo.setGoodsDes(goodsItem.optString("memo"));
                if (goodsItem.has("markName"))
                    goodsInfo.setMarkName(goodsItem.optString("markName"));
                if (goodsItem.has("markLogo"))
                    goodsInfo.setMarkLogo(goodsItem.optString("markLogo"));
                if (goodsItem.has("markId"))
                    goodsInfo.setMarkId(goodsItem.optString("markId"));
                if (goodsItem.has("markDes"))
                    goodsInfo.setMarkDes(goodsItem.optString("markDes"));
                if (goodsItem.has("lTagText"))
                    goodsInfo.setlTagText(goodsItem.optString("lTagText"));
                if (goodsItem.has("lTagModel"))
                    goodsInfo.setlTagModel(goodsItem.optString("lTagModel"));
                if (goodsItem.has("price_airport_rmb"))
                    goodsInfo.setPrice_airport_rmb(goodsItem.optString("price_airport_rmb"));
                if (goodsItem.has("price_airport_dollar"))
                    goodsInfo.setPrice_airport_dollar(goodsItem.optString("price_airport_dollar"));
                if (goodsItem.has("price_app_rmb"))
                    goodsInfo.setPrice_app_rmb(goodsItem.optString("price_app_rmb"));
                if (goodsItem.has("price_app_dollar"))
                    goodsInfo.setPrice_app_dollar(goodsItem.optString("price_app_dollar"));
                if (goodsItem.has("price_ref_rmb"))
                    goodsInfo.setPrice_ref_rmb(goodsItem.optString("price_ref_rmb"));
                if (goodsItem.has("price_ref_dollar"))
                    goodsInfo.setPrice_ref_dollar(goodsItem.optString("price_ref_dollar"));
                if (goodsItem.has("shareHtml"))
                    goodsInfo.setShareHtml(goodsItem.optString("shareHtml"));
                if (goodsItem.has("shareImage"))
                    goodsInfo.setShareImage(goodsItem.optString("shareImage"));
                if (goodsItem.has("shareText"))
                    goodsInfo.setShareText(goodsItem.optString("shareText"));
                if (goodsItem.has("quantity"))
                    goodsInfo.setQuantity(goodsItem.optString("quantity"));
                if (goodsItem.has("spotprice_dollar"))
                    goodsInfo.setSpotprice_dollar(goodsItem.optString("spotprice_dollar"));
                if (goodsItem.has("isBuy"))
                    goodsInfo.setIsBuy(goodsItem.optString("isBuy"));
                if (goodsItem.has("goodsPrice"))
                    goodsInfo.setGoodsPrice(goodsItem.optString("goodsPrice"));
                if (goodsItem.has("rTagModel"))
                    goodsInfo.setrTagModel(goodsItem.optString("rTagModel"));
                if (goodsItem.has("rTagText"))
                    goodsInfo.setrTagText(goodsItem.optString("rTagText"));
                if (goodsItem.has("status"))
                    goodsInfo.setStatus(goodsItem.optString("status"));
                if (goodsItem.has("beginTime"))
                    goodsInfo.setBeginTime(goodsItem.optString("beginTime"));
                if (goodsItem.has("endTime"))
                    goodsInfo.setEndTime(goodsItem.optString("endTime"));
                if (goodsItem.has("stock"))
                    goodsInfo.setStoke(goodsItem.optString("stock"));
                if (goodsItem.has("surStock"))
                    goodsInfo.setSurStock(goodsItem.optString("surStock"));
                if (goodsItem.has("surplusStock"))
                    goodsInfo.setSurStock(goodsItem.optString("surplusStock"));
                if (goodsItem.has("saleCount"))
                    goodsInfo.setSaleCount(goodsItem.optString("saleCount"));
                if (goodsItem.has("favoQty"))
                    goodsInfo.setFavoQty(goodsItem.optString("favoQty"));
                if (goodsItem.has("discSepcName"))
                    goodsInfo.setDiscSepcName(goodsItem.optString("discSepcName"));
                if (goodsItem.has("discSpecId"))
                    goodsInfo.setDiscSpecId(goodsItem.optString("discSpecId"));
                if (goodsItem.has("srcType"))
                    goodsInfo.setSrcType(goodsItem.optString("srcType"));
                if (goodsItem.has("srcId"))
                    goodsInfo.setSrcId(goodsItem.optString("srcId"));
                if (goodsItem.has("discSpecId"))
                    goodsInfo.setDiscSpecId(goodsItem.optString("discSpecId"));
                if (goodsItem.has("categoryId"))
                    goodsInfo.setTopId(goodsItem.optString("topId"));
                if (goodsItem.has("discount"))
                    goodsInfo.setDiscount(goodsItem.optString("discount"));
                goodsInfoList.add(goodsInfo);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getGoodsInfoList Excetion");
            }
        }
        return goodsInfoList;
    }

    /**
     * 获取商品详情
     *
     * @param goodsItem
     * @return
     */
    public static GoodsDetail getGoodsDetail(JSONObject goodsItem) {
        GoodsDetail goodsDetail = new GoodsDetail();
        try {
            if (goodsItem.has("id"))
                goodsDetail.setId(goodsItem.optString("id"));
            if (goodsItem.has("goodsId"))
                goodsDetail.setId(goodsItem.optString("goodsId"));
            if (goodsItem.has("activeId"))
                goodsDetail.setActiveId(goodsItem.optString("activeId"));
            if (goodsItem.has("activeName"))
                goodsDetail.setActiveName(goodsItem.optString("activeName"));
            if (goodsItem.has("goodsName"))
                goodsDetail.setGoodsName(goodsItem.optString("goodsName"));
            if (goodsItem.has("goodsImg"))
                goodsDetail.setGoodsImg(goodsItem.optString("goodsImg"));
            if (goodsItem.has("goodsImgs"))
                goodsDetail.setGoodsImags(goodsItem.optString("goodsImgs"));
            if (goodsItem.has("goodsDes"))
                goodsDetail.setGoodsDes(goodsItem.optString("goodsDes"));
            if (goodsItem.has("memo"))
                goodsDetail.setGoodsDes(goodsItem.optString("memo"));
            if (goodsItem.has("markName"))
                goodsDetail.setMarkName(goodsItem.optString("markName"));
            if (goodsItem.has("markLogo"))
                goodsDetail.setMarkLogo(goodsItem.optString("markLogo"));
            if (goodsItem.has("markId"))
                goodsDetail.setMarkId(goodsItem.optString("markId"));
            if (goodsItem.has("markDes"))
                goodsDetail.setMarkDes(goodsItem.optString("markDes"));
            if (goodsItem.has("lTagText"))
                goodsDetail.setlTagText(goodsItem.optString("lTagText"));
            if (goodsItem.has("lTagModel"))
                goodsDetail.setlTagModel(goodsItem.optString("lTagModel"));
            if (goodsItem.has("price_airport_rmb"))
                goodsDetail.setPrice_airport_rmb(goodsItem.optString("price_airport_rmb"));
            if (goodsItem.has("price_airport_dollar"))
                goodsDetail.setPrice_airport_dollar(goodsItem.optString("price_airport_dollar"));
            if (goodsItem.has("price_app_rmb"))
                goodsDetail.setPrice_app_rmb(goodsItem.optString("price_app_rmb"));
            if (goodsItem.has("price_app_dollar"))
                goodsDetail.setPrice_app_dollar(goodsItem.optString("price_app_dollar"));
            if (goodsItem.has("price_ref_rmb"))
                goodsDetail.setPrice_ref_rmb(goodsItem.optString("price_ref_rmb"));
            if (goodsItem.has("price_ref_dollar"))
                goodsDetail.setPrice_ref_dollar(goodsItem.optString("price_ref_dollar"));
            if (goodsItem.has("shareHtml"))
                goodsDetail.setShareHtml(goodsItem.optString("shareHtml"));
            if (goodsItem.has("shareImage"))
                goodsDetail.setShareImage(goodsItem.optString("shareImage"));
            if (goodsItem.has("shareText"))
                goodsDetail.setShareText(goodsItem.optString("shareText"));
            if (goodsItem.has("quantity"))
                goodsDetail.setQuantity(goodsItem.optString("quantity"));
            if (goodsItem.has("spotprice_dollar"))
                goodsDetail.setSpotprice_dollar(goodsItem.optString("spotprice_dollar"));
            if (goodsItem.has("isBuy"))
                goodsDetail.setIsBuy(goodsItem.optString("isBuy"));
            if (goodsItem.has("goodsPrice"))
                goodsDetail.setGoodsPrice(goodsItem.optString("goodsPrice"));
            if (goodsItem.has("rTagModel"))
                goodsDetail.setrTagModel(goodsItem.optString("rTagModel"));
            if (goodsItem.has("rTagText"))
                goodsDetail.setrTagText(goodsItem.optString("rTagText"));
            if (goodsItem.has("status"))
                goodsDetail.setStatus(goodsItem.optString("status"));
            if (goodsItem.has("beginTime"))
                goodsDetail.setBeginTime(goodsItem.optString("beginTime"));
            if (goodsItem.has("endTime"))
                goodsDetail.setEndTime(goodsItem.optString("endTime"));
            if (goodsItem.has("stock"))
                goodsDetail.setStoke(goodsItem.optString("stock"));
            if (goodsItem.has("surStock"))
                goodsDetail.setSurStock(goodsItem.optString("surStock"));
            if (goodsItem.has("surplusStock"))
                goodsDetail.setSurStock(goodsItem.optString("surplusStock"));
            if (goodsItem.has("saleCount"))
                goodsDetail.setSaleCount(goodsItem.optString("saleCount"));
            if (goodsItem.has("favoQty"))
                goodsDetail.setFavoQty(goodsItem.optString("favoQty"));
            if (goodsItem.has("discSepcName"))
                goodsDetail.setDiscSepcName(goodsItem.optString("discSepcName"));
            if (goodsItem.has("spotprice_dollar"))
                goodsDetail.setSpotprice_dollar(goodsItem.optString("spotprice_dollar"));
            if (goodsItem.has("inventory"))
                goodsDetail.setInventory(goodsItem.optString("inventory"));
            if (goodsItem.has("isReserve"))
                goodsDetail.setIsReserve(goodsItem.optString("isReserve"));
            if (goodsItem.has("isFavorite"))
                goodsDetail.setIsFavorite(goodsItem.optString("isFavorite"));
            if (goodsItem.has("prop"))
                goodsDetail.setProp(goodsItem.optJSONArray("prop").toString());
            if (goodsItem.has("showPriceDesc"))
                goodsDetail.setShowPriceDesc(goodsItem.optString("showPriceDesc"));
            if (goodsItem.has("priceDesc"))
                goodsDetail.setPriceDesc(goodsItem.optString("priceDesc"));
            if (goodsItem.has("priceIndicator"))
                goodsDetail.setPriceIndicator(goodsItem.optJSONArray("priceIndicator").toString());
            if (goodsItem.has("favoQty"))
                goodsDetail.setFavoQty(goodsItem.optString("favoQty"));
            if (goodsItem.has("srcType"))
                goodsDetail.setSrcType(goodsItem.optString("srcType"));
            if (goodsItem.has("srcId"))
                goodsDetail.setSrcId(goodsItem.optString("srcId"));
            if (goodsItem.has("discSpecId"))
                goodsDetail.setDiscSpecId(goodsItem.optString("discSpecId"));
            if (goodsItem.has("categoryId"))
                goodsDetail.setCategoryId(goodsItem.optString("categoryId"));
            if (goodsItem.has("topId"))
                goodsDetail.setTopId(goodsItem.optString("topId"));
            if (goodsItem.has("discount"))
                goodsDetail.setDiscount(goodsItem.optString("discount"));
            if (goodsItem.has("coments"))
                goodsDetail.setComents(getGoodComents(goodsItem.optJSONObject("coments")));

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getGoodsDetail Excetion");
        }
        return goodsDetail;
    }

    /**
     * 获取商品详情列表
     *
     * @param goodsArray
     * @return
     */
    public static List<GoodsDetail> getGoodsDetailList(JSONArray goodsArray) {
        List<GoodsDetail> detailList = new ArrayList<>();
        for (int i = 0; i < goodsArray.length(); i++) {
            try {
                JSONObject goodsItem = goodsArray.getJSONObject(i);
                GoodsDetail goodsDetail = getGoodsDetail(goodsItem);
                detailList.add(goodsDetail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return detailList;
    }

    /**
     * 获取精选轮播图列表
     *
     * @param handpickArray
     * @return
     */
    public static List<HandpickBanner> getHandpickList(JSONArray handpickArray) {
        List<HandpickBanner> handpickBannerList = new ArrayList<HandpickBanner>();
        for (int i = 0; i < handpickArray.length(); i++) {
            try {
                JSONObject handpickItem = handpickArray.getJSONObject(i);
                HandpickBanner handpickBanner = new HandpickBanner();
                if (handpickItem.has("acType"))
                    handpickBanner.setAcType(handpickItem.optString("acType"));
                if (handpickItem.has("acParams"))
                    handpickBanner.setAcParams(handpickItem.optString("acParams"));
                if (handpickItem.has("img"))
                    handpickBanner.setImg(handpickItem.optString("img"));
                if (handpickItem.has("sno"))
                    handpickBanner.setSno(handpickItem.optString("sno"));
                if (handpickItem.has("acParams"))
                    handpickBanner.setAcParams(handpickItem.optString("acParams"));
                if (handpickItem.has("shareImg"))
                    handpickBanner.setShareImg(handpickItem.optString("shareImg"));
                if (handpickItem.has("shareDes"))
                    handpickBanner.setShareDesc(handpickItem.optString("shareDes"));
                if (handpickItem.has("shareTheme"))
                    handpickBanner.setShareTheme(handpickItem.optString("shareTheme"));
                handpickBannerList.add(handpickBanner);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getHandpickList Excetion");
            }
        }
        return handpickBannerList;
    }

    /**
     * 获取发现列表
     *
     * @param discoverArray
     * @return
     */
    public static List<Discover> getDiscoverList(JSONArray discoverArray) {
        List<Discover> discoverList = new ArrayList<Discover>();
        for (int i = 0; i < discoverArray.length(); i++) {
            try {
                JSONObject discoverItem = discoverArray.getJSONObject(i);
                Discover discover = new Discover();
                if (discoverItem.has("bgColor"))
                    discover.setBgColor(discoverItem.optString("bgColor"));
                if (discoverItem.has("imgUrl"))
                    discover.setImgUrl(discoverItem.optString("imgUrl"));
                if (discoverItem.has("findName"))
                    discover.setFindName(discoverItem.optString("findName"));
                if (discoverItem.has("squareImg"))
                    discover.setSquareImg(discoverItem.optString("squareImg"));
                if (discoverItem.has("findId"))
                    discover.setFindId(discoverItem.optString("findId"));
                if (discoverItem.has("subFinds")) {
                    List<SubFind> subfindList = new ArrayList<SubFind>();
                    JSONArray subFinds = discoverItem.optJSONArray("subFinds");
                    for (int j = 0; j < subFinds.length(); j++) {
                        JSONObject subfindItem = subFinds.getJSONObject(j);
                        SubFind subfind = new SubFind();
                        if (subfindItem.has("subFindId"))
                            subfind.setSubFindId(subfindItem.optString("subFindId"));
                        if (subfindItem.has("subFindName"))
                            subfind.setSubFindName(subfindItem.optString("subFindName"));
                        if (subfindItem.has("subSquareImg"))
                            subfind.setSubSquareImg(subfindItem.optString("subSquareImg"));
                        if (subfindItem.has("end"))
                            subfind.setEnd(subfindItem.optString("end"));
                        subfindList.add(subfind);
                    }
                    discover.setSubFinds(subfindList);
                }
                discoverList.add(discover);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getDiscoverList Excetion");
            }
        }
        return discoverList;
    }

    /**
     * 获取二级发现列表
     *
     * @param secondDiscoverArray
     * @return
     */
    public static List<SecondDiscover> getSecondDiscoverList(JSONArray secondDiscoverArray) {
        List<SecondDiscover> discoverList = new ArrayList<SecondDiscover>();
        for (int i = 0; i < secondDiscoverArray.length(); i++) {
            try {
                JSONObject discoverItem = secondDiscoverArray.getJSONObject(i);
                SecondDiscover secondDiscover = new SecondDiscover();
                SubFind subFind = new SubFind();
                if (discoverItem.has("subFindId"))
                    subFind.setSubFindId(discoverItem.optString("subFindId"));
                if (discoverItem.has("subFindName"))
                    subFind.setSubFindName(discoverItem.optString("subFindName"));
                if (discoverItem.has("subSquareImg"))
                    subFind.setSubSquareImg(discoverItem.optString("subSquareImg"));
                if (discoverItem.has("end"))
                    subFind.setEnd(discoverItem.optString("end"));
                secondDiscover.setSubFind(subFind);

                if (discoverItem.has("goodsList")) {
                    JSONArray goodsArray = discoverItem.optJSONArray("goodsList");
                    List<GoodsInfo> goodsList = getGoodsInfoList(goodsArray);
                    secondDiscover.setGoodsInfoList(goodsList);
                }
                discoverList.add(secondDiscover);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getSecondDiscoverList Excetion");
            }
        }
        return discoverList;
    }

    /**
     * 获取商品评论信息
     *
     * @param json
     * @return
     */
    public static GoodComents getGoodComents(JSONObject json) {
        GoodComents coments = new GoodComents();
        try {
            if (json.has("starQty"))
                coments.setStarQty(json.optString("starQty"));
            if (json.has("niceRate"))
                coments.setNiceRate(json.optString("niceRate"));
            if (json.has("niceDesc"))
                coments.setNiceDesc(json.optString("niceDesc"));
            if (json.has("sumQty"))
                coments.setSumQty(json.optString("sumQty"));
            if (json.has("cmtList")) {
                List<ComentsInfo> comentsInfoList = new ArrayList<ComentsInfo>();
                JSONArray jsonArray = json.optJSONArray("cmtList");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject comentItem = jsonArray.getJSONObject(j);
                    ComentsInfo info = new ComentsInfo();
                    if (comentItem.has("id"))
                        info.setId(comentItem.optString("id"));
                    if (comentItem.has("icon"))
                        info.setIcon(comentItem.optString("icon"));
                    if (comentItem.has("name"))
                        info.setName(comentItem.optString("name"));
                    if (comentItem.has("content"))
                        info.setContent(comentItem.optString("content"));
                    if (comentItem.has("time"))
                        info.setTime(comentItem.optString("time"));
                    comentsInfoList.add(info);
                }
                coments.setComentsList(comentsInfoList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getDiscoverList Excetion");
        }
        return coments;
    }

    /**
     * 获取我的订单列表(订单信息)
     *
     * @param orderListArray
     * @return
     */
    public static List<OrderListInfo> getOrderList(JSONArray orderListArray) {
        List<OrderListInfo> orderList = new ArrayList<OrderListInfo>();
        for (int i = 0; i < orderListArray.length(); i++) {
            try {
                JSONObject orderItem = orderListArray.getJSONObject(i);
                OrderListInfo listInfo = new OrderListInfo();
                FlightInfo flightInfo = new FlightInfo();
                if (orderItem.has("flightNo"))
                    flightInfo.setFlightNo(orderItem.optString("flightNo"));
                if (orderItem.has("depart"))
                    flightInfo.setDepart(orderItem.optString("depart"));
                if (orderItem.has("takeoffDate"))
                    flightInfo.setDepartDate(orderItem.optString("takeoffDate"));
                if (orderItem.has("arrive"))
                    flightInfo.setArrive(orderItem.optString("arrive"));
                listInfo.setFlightInfo(flightInfo);
                if (orderItem.has("orders")) {
                    List<OrderInfo> infoList = getOrderInfoList(orderItem.optJSONArray("orders"));
                    listInfo.setOrderInfoList(infoList);
                }

                orderList.add(listInfo);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getOrderList Excetion");
            }
        }
        return orderList;
    }
    /**
     * 获取订单详情
     *
     * @param
     * @return
     */
    public static OrderDetail getOrderDetail(JSONObject detailJson) {
        OrderDetail orderDetail = new OrderDetail();
            try {
                if (detailJson.has("phone"))
                    orderDetail.setPhone(detailJson.optString("phone"));
                if (detailJson.has("memberName"))
                    orderDetail.setMemberName(detailJson.optString("memberName"));
                if (detailJson.has("payMoney"))
                    orderDetail.setPayMoney(detailJson.optString("payMoney"));
                if (detailJson.has("orderMoney"))
                    orderDetail.setOrderMoney(detailJson.optString("orderMoney"));
                if (detailJson.has("orderStatus"))
                    orderDetail.setOrderStatus(detailJson.optString("orderStatus"));
                if (detailJson.has("arrive"))
                    orderDetail.setArrive(detailJson.optString("arrive"));
                if (detailJson.has("depart"))
                    orderDetail.setDepart(detailJson.optString("depart"));
                if (detailJson.has("takeoffDate"))
                    orderDetail.setTakeoffDate(detailJson.optString("takeoffDate"));
                if (detailJson.has("orderDetail")) {
                    List<GoodsInfo> infoList = getGoodsInfoList(detailJson.optJSONArray("orderDetail"));
                    orderDetail.setGoodsInfoList(infoList);
                }
                List<StatusInfo> statusInfoList = new ArrayList<>();
                if (detailJson.has("statusDes")) {
                    JSONArray statusArray = detailJson.optJSONArray("statusDes");
                    for (int i = 0; i <statusArray.length() ; i++) {
                        JSONObject itemStatus = statusArray.getJSONObject(i);
                        StatusInfo info = new StatusInfo();
                        if (itemStatus.has("status"))
                            info.setStatus(itemStatus.optString("status"));
                        if (itemStatus.has("statusMsg"))
                            info.setStatusMsg(itemStatus.optString("statusMsg"));
                        statusInfoList.add(info);
                    }
                    orderDetail.setStatusInfos(statusInfoList);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getOrderDetail Excetion");
            }
        return orderDetail;
    }

    /**
     * 获取订单(订单信息)
     *
     * @param orderArray
     * @return
     */
    public static List<OrderInfo> getOrderInfoList(JSONArray orderArray) {
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        for (int i = 0; i < orderArray.length(); i++) {
            try {
                JSONObject orderItem = orderArray.getJSONObject(i);
                OrderInfo order = new OrderInfo();
                if (orderItem.has("status"))
                    order.setStatus(orderItem.optString("status"));
                if (orderItem.has("statusMsg"))
                    order.setStatusMsg(orderItem.optString("statusMsg"));
                if (orderItem.has("takeoffDate"))
                    order.setTakeoffDate(orderItem.optString("takeoffDate"));
                if (orderItem.has("orderId"))
                    order.setOrderId(orderItem.optString("orderId"));
                if (orderItem.has("flightNo"))
                    order.setFlightNo(orderItem.optString("flightNo"));
                if (orderItem.has("orderNo"))
                    order.setOrderNo(orderItem.optString("orderNo"));
                if (orderItem.has("orderTime"))
                    order.setOrderTime(orderItem.optString("orderTime"));
                if (orderItem.has("orderPrice"))
                    order.setOrderPrice_dollar(orderItem.optString("orderPrice"));
                if (orderItem.has("MemberID"))
                    order.setMemberId(orderItem.optString("MemberID"));
                orderList.add(order);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getOrderInfoList Excetion");
            }
        }
        return orderList;
    }

    /**
     * 获取商品分类
     *
     * @param categoryArray
     * @return
     */
    public static List<GoodsCategory> getGoodsCategorys(JSONArray categoryArray) {
        List<GoodsCategory> categoryList = new ArrayList<GoodsCategory>();
        for (int i = 0; i < categoryArray.length(); i++) {
            try {
                JSONObject categoryItem = categoryArray.getJSONObject(i);
                GoodsCategory category = new GoodsCategory();
                if (categoryItem.has("categoryId"))
                    category.setCategoryId(categoryItem.optString("categoryId"));
                if (categoryItem.has("categoryName"))
                    category.setCategoryName(categoryItem.optString("categoryName"));
                if (categoryItem.has("logo"))
                    category.setCategoryImg(categoryItem.optString("logo"));
                if (categoryItem.has("headerImg"))
                    category.setHeaderImg(categoryItem.optString("headerImg"));
                categoryList.add(category);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getGoodsCategory Excetion");
            }
        }
        return categoryList;
    }

    /**
     * 获取品牌分类
     *
     * @param brandArray
     * @return
     */
    public static List<GoodsBrand> getGoodsBrands(JSONArray brandArray) {
        List<GoodsBrand> brandList = new ArrayList<GoodsBrand>();
        for (int i = 0; i < brandArray.length(); i++) {
            try {
                JSONObject brandItem = brandArray.getJSONObject(i);
                GoodsBrand brand = new GoodsBrand();
                if (brandItem.has("markId"))
                    brand.setMarkId(brandItem.optString("markId"));
                if (brandItem.has("markName"))
                    brand.setMarkName(brandItem.optString("markName"));
                if (brandItem.has("markInitial"))
                    brand.setMarkInitial(brandItem.optString("markInitial"));
                if (brandItem.has("markInitial"))
                    brand.setSortLetters(brandItem.optString("markInitial"));
                if (brandItem.has("headerImg"))
                    brand.setHeaderImg(brandItem.optString("headerImg"));
                if (brandItem.has("logo"))
                    brand.setMarkImg(brandItem.optString("logo"));
                brandList.add(brand);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getGoodsCategory Excetion");
            }
        }
        return brandList;
    }

    /**
     * 获取精选活动分类
     *
     * @param typeArray
     * @return
     */
    public static List<HandpickType> getHandpickTypes(JSONArray typeArray) {
        List<HandpickType> typeList = new ArrayList<HandpickType>();
        for (int i = 0; i < typeArray.length(); i++) {
            try {
                JSONObject typeItem = typeArray.getJSONObject(i);
                HandpickType handpickType = new HandpickType();
                if (typeItem.has("sno"))
                    handpickType.setSno(typeItem.optString("sno"));
                if (typeItem.has("type"))
                    handpickType.setType(typeItem.optString("type"));
                if (typeItem.has("title"))
                    handpickType.setTitle(typeItem.optString("title"));
                if (typeItem.has("isMore"))
                    handpickType.setIsMore(typeItem.optString("isMore"));
                if (typeItem.has("type")) {
                    if ("1".equals(typeItem.optString("type"))) {
                        if (typeItem.has("dataInfo")) {
                            JSONArray dataInfo = typeItem.optJSONArray("dataInfo");
                            List<GoodsInfo> datas = getGoodsInfoList(dataInfo);
                            handpickType.setInfoList(datas);
                        }
                    } else if ("2".equals(typeItem.optString("type"))) {
                        if (typeItem.has("dataInfo")) {
                            JSONArray topGoodsList = typeItem.optJSONArray("dataInfo");
                            List<GoodsInfo> goodsList = getGoodsInfoList(topGoodsList);
                            handpickType.setInfoList(goodsList);
                        }
                    } else if ("3".equals(typeItem.optString("type"))) {
                        if (typeItem.has("dataInfo")) {
                            JSONArray activeArray = typeItem.optJSONArray("dataInfo");
                            JSONObject activeJson = activeArray.getJSONObject(0);
                            if (activeJson.has("activeId"))
                                handpickType.setActiveId(activeJson.optString("activeId"));
                            if (activeJson.has("activeName"))
                                handpickType.setActiveName(activeJson.optString("activeName"));
                            if (activeJson.has("activeImg"))
                                handpickType.setActiveImg(activeJson.optString("activeImg"));
                            if (activeJson.has("outerLayout"))
                                handpickType.setOuterLayout(activeJson.optString("outerLayout"));
                            if (activeJson.has("insideLayout"))
                                handpickType.setInsideLayout(activeJson.optString("insideLayout"));
                            if (activeJson.has("goodsList")) {
                                JSONArray goodsArray = activeJson.optJSONArray("goodsList");
                                List<GoodsInfo> goodsList = getGoodsInfoList(goodsArray);
                                handpickType.setInfoList(goodsList);
                            }
                        }
                    } else if ("4".equals(typeItem.optString("type"))) {
                        if (typeItem.has("dataInfo")) {
                            JSONArray activeArray = typeItem.optJSONArray("dataInfo");
                            JSONObject discoverJson = activeArray.getJSONObject(0);
                            handpickType.setDiscover(getDiscover(discoverJson));
                        }
                    }
                }
                typeList.add(handpickType);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getHandpickTypes Excetion");
            }
        }
        return typeList;
    }

    /**
     * 获取首页 type = 4 活动中的发现商品
     *
     * @param discoverJson
     * @return
     */
    public static Discover getDiscover(JSONObject discoverJson) {
        Discover discover = new Discover();
        try {
            if (discoverJson.has("discImg"))
                discover.setImgUrl(discoverJson.optString("discImg"));
            if (discoverJson.has("discName"))
                discover.setFindName(discoverJson.optString("discName"));
            if (discoverJson.has("outerLayout"))
                discover.setOuterLayout(discoverJson.optString("outerLayout"));
            if (discoverJson.has("squareImg"))
                discover.setSquareImg(discoverJson.optString("squareImg"));
            if (discoverJson.has("discId"))
                discover.setFindId(discoverJson.optString("discId"));
            if (discoverJson.has("groupList")) {
                List<SubFind> subfindList = new ArrayList<SubFind>();
                JSONArray subFinds = discoverJson.optJSONArray("groupList");
                for (int j = 0; j < subFinds.length(); j++) {
                    JSONObject subfindItem = subFinds.getJSONObject(j);
                    SubFind subfind = new SubFind();
                    if (subfindItem.has("groupId"))
                        subfind.setSubFindId(subfindItem.optString("groupId"));
                    if (subfindItem.has("groupName"))
                        subfind.setSubFindName(subfindItem.optString("groupName"));
                    if (subfindItem.has("squareImg"))
                        subfind.setSubSquareImg(subfindItem.optString("squareImg"));
                    if (subfindItem.has("end"))
                        subfind.setEnd(subfindItem.optString("end"));
                    subfindList.add(subfind);
                }
                discover.setSubFinds(subfindList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getDiscover Excetion");
        }
        return discover;
    }

    /**
     * 获取折扣专区列表
     *
     * @param discountArray
     * @return
     */
    public static List<DiscountInfo> getDiscountList(JSONArray discountArray) {
        List<DiscountInfo> discountList = new ArrayList<DiscountInfo>();
        for (int i = 0; i < discountArray.length(); i++) {
            try {
                JSONObject discountItem = discountArray.getJSONObject(i);
                DiscountInfo info = new DiscountInfo();
                if (discountItem.has("name"))
                    info.setName(discountItem.optString("name"));
                if (discountItem.has("beginTime"))
                    info.setBeginTime(discountItem.optString("beginTime"));
                if (discountItem.has("endTime"))
                    info.setEndTime(discountItem.optString("endTime"));
                if (discountItem.has("sno"))
                    info.setSno(discountItem.optString("sno"));
                if (discountItem.has("discount"))
                    info.setDiscount(discountItem.optString("discount"));
                if (discountItem.has("status"))
                    info.setStatus(discountItem.optString("status"));
                if (discountItem.has("memo"))
                    info.setDes(discountItem.optString("memo"));
                if (discountItem.has("privId"))
                    info.setPrivId(discountItem.optString("privId"));
                if (discountItem.has("privName"))
                    info.setPrivName(discountItem.optString("privName"));
                if (discountItem.has("privDesc"))
                    info.setPrivDesc(discountItem.optString("privDesc"));
                if (discountItem.has("goodsList"))
                    info.setGoodsList(getGoodsInfoList(discountItem.optJSONArray("goodsList")));
                discountList.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getDiscountList Excetion");
            }
        }
        return discountList;
    }


    /**
     * 获取活动(专场，专题，海报)商品列表
     *
     * @param json
     * @return
     */
    public static ActiveInfo getActiveGoodsList(JSONObject json) {
        ActiveInfo activeInfo = new ActiveInfo();
        if (json.has("activeId"))
            activeInfo.setActiveId(json.optString("activeId"));
        if (json.has("activeName"))
            activeInfo.setActiveName(json.optString("activeName"));
        if (json.has("startTime"))
            activeInfo.setStartTime(json.optString("startTime"));
        if (json.has("endTime"))
            activeInfo.setEndTime(json.optString("endTime"));
        if (json.has("headerImg"))
            activeInfo.setHeaderImg(json.optString("headerImg"));
        if (json.has("memo"))
            activeInfo.setMemo(json.optString("memo"));
        if (json.has("shareImage"))
            activeInfo.setShareImage(json.optString("shareImage"));
        if (json.has("shareText"))
            activeInfo.setShareText(json.optString("shareText"));
        if (json.has("shareHtml"))
            activeInfo.setShareHtml(json.optString("shareHtml"));
        if (json.has("goodsList")) {
            JSONArray jsonArray = json.optJSONArray("goodsList");
            activeInfo.setGoodsInfoList(getGoodsDetailList(jsonArray));
        }
        return activeInfo;
    }

    /**
     * 获取特权任务列表
     *
     * @param taskListArray
     * @return
     */

    public static List<TaskListInfo> getTaskListInfo(JSONArray taskListArray) {
        List<TaskListInfo> infoList = new ArrayList<>();
        try {
            for (int i = 0; i < taskListArray.length(); i++) {
                JSONObject jsonInfo = taskListArray.getJSONObject(i);
                TaskListInfo taskListInfo = new TaskListInfo();
                if (jsonInfo.has("id"))
                    taskListInfo.setId(jsonInfo.optString("id"));
                if (jsonInfo.has("spImg"))
                    taskListInfo.setSpImg(jsonInfo.optString("spImg"));
                if (jsonInfo.has("spName"))
                    taskListInfo.setSpName(jsonInfo.optString("spName"));
                if (jsonInfo.has("spDesc"))
                    taskListInfo.setSpDesc(jsonInfo.optString("spDesc"));
                if (jsonInfo.has("status"))
                    taskListInfo.setStatus(jsonInfo.optString("status"));
                if (jsonInfo.has("sno"))
                    taskListInfo.setSno(jsonInfo.optString("sno"));
                if (jsonInfo.has("condition")) {
                    List<TaskInfo> taskInfoList = new ArrayList<>();
                    JSONArray taskArray = jsonInfo.optJSONArray("condition");
                    for (int j = 0; j < taskArray.length(); j++) {
                        JSONObject taskJson = taskArray.getJSONObject(j);
                        TaskInfo info = new TaskInfo();
                        if (taskJson.has("condName"))
                            info.setCondName(taskJson.optString("condName"));
                        if (taskJson.has("condValue"))
                            info.setCondValue(taskJson.optString("condValue"));
                        if (taskJson.has("finishValue"))
                            info.setFinishValue(taskJson.optString("finishValue"));
                        if (taskJson.has("acType"))
                            info.setAcType(taskJson.optString("acType"));
                        if (taskJson.has("acParams"))
                            info.setAcParams(taskJson.optString("acParams"));
                        if (taskJson.has("acName"))
                            info.setAcName(taskJson.optString("acName"));
                        taskInfoList.add(info);
                    }
                    taskListInfo.setTaskInfoList(taskInfoList);
                }
                infoList.add(taskListInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("JSON", "JsonAnalysis getTaskListInfo Excetion");
        }
        return infoList;
    }

    /**
     * 获取消息中心列表
     *
     * @param msgArray
     * @return
     */
    public static List<MessageInfo> getMsgList(JSONArray msgArray) {
        List<MessageInfo> msgList = new ArrayList<MessageInfo>();
        for (int i = 0; i < msgArray.length(); i++) {
            try {
                JSONObject msgItem = msgArray.getJSONObject(i);
                MessageInfo info = new MessageInfo();
                if (msgItem.has("msgId"))
                    info.setMsgId(msgItem.optString("msgId"));
                if (msgItem.has("msgType"))
                    info.setMsgType(msgItem.optString("msgType"));
                if (msgItem.has("isReaded"))
                    info.setIsReaded(msgItem.optString("isReaded"));
                if (msgItem.has("msgContent"))
                    info.setMsgContent(msgItem.optString("msgContent"));
                if (msgItem.has("msgTitle"))
                    info.setMsgTitle(msgItem.optString("msgTitle"));
                if (msgItem.has("msgTime"))
                    info.setMsgTime(msgItem.optString("msgTime"));
                if (msgItem.has("readTime"))
                    info.setMsgTime(msgItem.optString("readTime"));
                if (msgItem.has("jumpType"))
                    info.setJumpType(msgItem.optString("jumpType"));
                if (msgItem.has("jumpParams"))
                    info.setJumpParams(msgItem.optString("jumpParams"));
                msgList.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("JSON", "JsonAnalysis getMsgList Excetion");
            }
        }
        return msgList;
    }
}

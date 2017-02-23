package com.aero.droid.dutyfree.talent.util.network;

import android.content.Context;
import android.text.TextUtils;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.util.AppUtil;
import com.aero.droid.dutyfree.talent.util.LogUtil;
import com.aero.droid.dutyfree.talent.util.TimeFormatUtil;
import com.aero.droid.dutyfree.talent.util.Verify;
import com.aero.droid.dutyfree.talent.util.encode.Encryption;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxp on 2015/11/21.
 * http请求封装类   使用okHttp框架
 */
public class OkHttpRequest {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    //post方式提交String
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * http  get请求
     *
     * @param context
     * @param murl
     * @param params
     * @param listener
     */
    public static void okHttpGet(final Context context, String murl, HashMap<String, String> params, final RespListener listener) {
        String url = creatGetUrl(context, murl, params);
        LogUtil.v("okHttp", "Url.okHttpGet = " + url);
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onRespError("1", context.getResources().getString(R.string.request_error));
                LogUtil.v("JSON", "request.onFailure()");
            }


            @Override
            public void onResponse(Response response) throws IOException {
                String msg = "";
                String code = "";
                JSONObject result = null;
                if (200 == response.code()){
                    String responseBody = response.body().string();
                    String respMsg = Encryption.getStringFormBase64(responseBody);
                    try {
                        JSONObject resultJson = new JSONObject(respMsg);
                        if (resultJson.has("JSON")) {
                            result = resultJson.optJSONObject("JSON");
                        } else {
                            code = "1";
                            msg = context.getResources().getString(R.string.request_error);
                        }
                        if (result.has("message")) {
                            msg = result.optString("message");
                        } else {
                            msg = "";
                        }
                        if (result.has("code")) {
                            code = result.optString("code");
                        } else {
                            code = "1";
                            if (TextUtils.isEmpty(msg))
                                msg = context.getResources().getString(R.string.request_error);
                        }
                        if ("0".equals(code)){
                            listener.onRespSucc(result, code, msg);
                        }else {
                            listener.onRespError(code,msg);
                            LogUtil.v("JSON", result.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    listener.onRespError("1", context.getResources().getString(R.string.request_error));
                    LogUtil.v("JSON", response.code() +"    " +response.message());
                }
            }
        });

    }

    /**
     * http  post请求
     *
     * @param context
     * @param murl
     * @param params
     * @param listener
     */
    public static void okHttpPost(final Context context, String murl, HashMap<String, String> params, final RespListener listener) {
        String url = creatGetUrl(context,murl,null);
        LogUtil.v("okHttp", "Url.okHttpPost = " + url);
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> iterable_element : params.entrySet()) {
                builder.add(iterable_element.getKey(),iterable_element.getValue());
            }
        }
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url).post(builder.build())
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onRespError("1", context.getResources().getString(R.string.request_error));
                LogUtil.v("JSON", "request.onFailure()");
            }


            @Override
            public void onResponse(Response response) throws IOException {
                String msg = "";
                String code = "";
                JSONObject result = null;
                if (200 == response.code()){
                    String responseBody = response.body().string();
                    String respMsg = Encryption.getStringFormBase64(responseBody);
                    try {
                        JSONObject resultJson = new JSONObject(respMsg);
                        if (resultJson.has("JSON")) {
                            result = resultJson.optJSONObject("JSON");
                        } else {
                            code = "1";
                            msg = context.getResources().getString(R.string.request_error);
                        }
                        if (result.has("message")) {
                            msg = result.optString("message");
                        } else {
                            msg = "";
                        }
                        if (result.has("code")) {
                            code = result.optString("code");
                        } else {
                            code = "1";
                            if (TextUtils.isEmpty(msg))
                                msg = context.getResources().getString(R.string.request_error);
                        }
                        if ("1".equals(code)){
                            listener.onRespError(code, msg);
                            LogUtil.v("JSON", result.toString());
                        }else {
                            listener.onRespSucc(result, code, msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    listener.onRespError("1", context.getResources().getString(R.string.request_error));
                    LogUtil.v("JSON", response.code() +"    " +response.message());
                }

            }
        });

    }
    /**
     * http  post 上传图片
     *
     * @param context
     * @param murl
     * @param params
     * @param listener
     */
    public static void okHttpPostUploadImg(final Context context, String murl, HashMap<String, String> params,String path, final RespListener listener) {
        String url = creatGetUrl(context,murl,null);
        LogUtil.v("okHttp", "Url.okHttpPost.uploadImg = " + url);
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBuilder uploadBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> iterable_element : params.entrySet()) {
                uploadBuilder.addFormDataPart(iterable_element.getKey(), iterable_element.getValue());
            }
        }
        uploadBuilder.addFormDataPart("Image", path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        //构建请求体
        RequestBody requestBody = uploadBuilder.build();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onRespError("1", context.getResources().getString(R.string.request_error));
                LogUtil.v("JSON", "request.onFailure()");
            }


            @Override
            public void onResponse(Response response) throws IOException {
                String msg = "";
                String code = "";
                JSONObject result = null;
                if (200 == response.code()){
                    String responseBody = response.body().string();
                    String respMsg = Encryption.getStringFormBase64(responseBody);
                    try {
                        JSONObject resultJson = new JSONObject(respMsg);
                        if (resultJson.has("JSON")) {
                            result = resultJson.optJSONObject("JSON");
                        } else {
                            code = "1";
                            msg = context.getResources().getString(R.string.request_error);
                        }
                        if (result.has("message")) {
                            msg = result.optString("message");
                        } else {
                            msg = "";
                        }
                        if (result.has("code")) {
                            code = result.optString("code");
                        } else {
                            code = "1";
                            if (TextUtils.isEmpty(msg))
                                msg = context.getResources().getString(R.string.request_error);
                        }
                        if ("0".equals(code)){
                            listener.onRespSucc(result, code, msg);
                        }else {
                            listener.onRespError(code,msg);
                            LogUtil.v("JSON", result.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    listener.onRespError("1", context.getResources().getString(R.string.request_error));
                    LogUtil.v("JSON", response.code() +"    " +response.message());
                }

            }
        });

    }

    /**
     * http  post 多图上传
     *
     * @param context
     * @param murl
     * @param params
     * @param listener
     */
    public static void okHttpPostUploadImgs(final Context context, String murl, HashMap<String, String> params,HashMap<String,String> imgParams, final RespListener listener) {
        String url = creatGetUrl(context,murl,null);
        LogUtil.v("okHttp", "Url.okHttpPost.uploadImgs = " + url);
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBuilder uploadBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> iterable_element : params.entrySet()) {
//                uploadBuilder.addFormDataPart(iterable_element.getKey(), iterable_element.getValue());
//                uploadBuilder.addPart(RequestBody.create(MEDIA_TYPE_MARKDOWN, iterable_element.getValue()));
                uploadBuilder.addFormDataPart(iterable_element.getKey(),null,RequestBody.create(MEDIA_TYPE_MARKDOWN,iterable_element.getValue()));
            }
        }
        if (imgParams != null && imgParams.size() > 0) {
            for (Map.Entry<String, String> img_element : imgParams.entrySet()) {
                uploadBuilder.addFormDataPart(img_element.getKey(), img_element.getValue(), RequestBody.create(MEDIA_TYPE_PNG, new File(img_element.getValue())));
            }
        }
        //构建请求体
        RequestBody requestBody = uploadBuilder.build();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onRespError("1", context.getResources().getString(R.string.request_error));
                LogUtil.v("JSON", "request.onFailure()");
            }


            @Override
            public void onResponse(Response response) throws IOException {
                String msg = "";
                String code = "";
                JSONObject result = null;
                if (200 == response.code()){
                    String responseBody = response.body().string();
                    String respMsg = Encryption.getStringFormBase64(responseBody);
                    try {
                        JSONObject resultJson = new JSONObject(respMsg);
                        if (resultJson.has("JSON")) {
                            result = resultJson.optJSONObject("JSON");
                        } else {
                            code = "1";
                            msg = context.getResources().getString(R.string.request_error);
                        }
                        if (result.has("message")) {
                            msg = result.optString("message");
                        } else {
                            msg = "";
                        }
                        if (result.has("code")) {
                            code = result.optString("code");
                        } else {
                            code = "1";
                            if (TextUtils.isEmpty(msg))
                                msg = context.getResources().getString(R.string.request_error);
                        }
                        if ("0".equals(code)){
                            listener.onRespSucc(result, code, msg);
                        }else {
                            listener.onRespError(code,msg);
                            LogUtil.v("JSON", result.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    listener.onRespError("1", context.getResources().getString(R.string.request_error));
                    LogUtil.v("JSON", response.code() +"    " +response.message());
                }

            }
        });

    }


    /**
     * get请求  拼接参数字符串
     *
     * @param context
     * @param url
     * @param params
     * @return
     */
    private static String creatGetUrl(Context context, String url, HashMap<String, String> params) {
        if (null == params)
            params = new HashMap<>();
        addNormalParamas(context, params);
        StringBuilder mUrl = new StringBuilder();
        mUrl.append(url);
        if (params != null && !params.isEmpty()) {
            boolean firstParam = true;
            for (Map.Entry<String, String> e : params.entrySet()) {
                if (firstParam) {
                    firstParam = false;
                    mUrl.append("?");
                } else {
                    mUrl.append("&");
                }

                mUrl.append(e.getKey()).append("=").append(e.getValue());
            }
        }
        return mUrl.toString();

    }


    /**
     * 添加请求固定参数：appId deviceNo ts(当前时间) token
     *
     * @param context
     * @param params
     * @return
     */
    //TODO 找时间去掉 请求里面的context  写在application里面
    private static HashMap<String, String> addNormalParamas(Context context, HashMap<String, String> params) {
        if (null != params) {
            params.put("appId", AppUtil.getPackageName(context));
            params.put("deviceNo", AppUtil.getDeviceId(context));
            try {
                params.put("ts", TimeFormatUtil.formatDate("yyMMddHHmmss"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("sdkVersion", AppUtil.getVersionName(context));
            params.put("token",
                    Verify.getToken(params, AppUtil.getDutyKey(context)));
        }
        return params;
    }
}

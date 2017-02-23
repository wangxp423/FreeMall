package com.aero.droid.dutyfree.talent.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.aero.droid.dutyfree.talent.app.Constants;
import com.aero.droid.dutyfree.talent.app.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Author : wangxp
 * Date : 2016/1/8
 * Desc : 文件管理 工具类
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();
    private static final String FILE_PATH = "/coogo/"; //文件目录
    private static final String AUDIO_PATH = "/audio/"; //音频目录
    private static final String IMAGE_PATH = "/image/"; //图片目录
    private static final String PHOTO_PATH = "/photo/"; //头像目录

    /**
     * 获取 目录地址
     *
     * @return
     */
    public static String getFileDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sdFile = Environment.getExternalStorageDirectory();
            sdFile = new File(
                    Environment.getExternalStorageDirectory(),
                    "Android/data/"
                            + MyApplication.getInstance().getApplicationInfo().packageName);

            if (!sdFile.exists()) {
                sdFile.mkdirs();
            }

            String path = sdFile.getAbsolutePath();
//            LogUtil.t("SD卡目录 = " + path);
            return path;
        } else {
            String strCacheDir = MyApplication.getInstance().getCacheDir()
                    .getAbsolutePath();
//            LogUtil.t("默认缓存目录 = " + strCacheDir);
            return strCacheDir + "/";
        }
    }

    /**
     * 获取文件路径
     *
     * @return
     */
    public static String getFilePath() {
        String path = getFileDir();
        path += FILE_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取音频文件路径
     *
     * @return
     */
    public static String getAudioPath() {
        String path = getFileDir();
        path += AUDIO_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取图片文件路径
     *
     * @return
     */
    public static String getImagePath() {
        String path = getFileDir();
        path += IMAGE_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取图片文件路径
     *
     * @return
     */
    public static String getPhotoPath() {
        String path = getFileDir();
        path += PHOTO_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 读取目录文件  并按照时间排序
     *
     * @param dirname 目录名称
     * @return list集合
     */
    public static List<String> getFiles(String dirname) throws Exception {
        List file_names = null;
        File dir = new File(dirname);
        if (dir.exists()) {
            file_names = new ArrayList();
            File[] files = dir.listFiles();
            //排序
            Arrays.sort(files, new CompratorByLastModified());
            for (int i = 0; i < files.length; i++) {
//                LogUtil.t("文件名字 = " + files[i].getName());
                file_names.add(files[i].getName());
            }
        } else {
            LogUtil.t("该目录没有任何文件信息！");
        }
        return file_names;
    }

    /**
     * 进行文件排序时间
     *
     * @author 谈情
     */
    private static class CompratorByLastModified implements Comparator<File> {
        public int compare(File f1, File f2) {
            long diff = f2.lastModified() - f1.lastModified();
            if (diff > 0)
                return 1;
            else if (diff == 0)
                return 0;
            else
                return -1;
        }
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
/****************************下面两个方法是更换头像方法********************************************/
        /**
         * 系统裁剪图片方法实现
         *
         * @param activity
         * @param uri
         */
        public static void startPhotoZoom(Activity activity, Uri uri) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, Constants.EVENT_CUT_PHOTO);
        }

        /**
         * 剪裁成功  将图片设置显示
         *
         * @param picData
         */
        public static void savePic(Intent picData) {
            Bitmap photo = picData.getExtras().getParcelable("data");

            File file = new File(getPhotoPath() + "photo_head.png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 0, baos);
            try {
                FileOutputStream streamLeng = new FileOutputStream(file);
                streamLeng.write(baos.toByteArray());
                streamLeng.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

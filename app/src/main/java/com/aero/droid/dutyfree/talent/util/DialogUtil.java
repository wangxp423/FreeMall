package com.aero.droid.dutyfree.talent.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aero.droid.dutyfree.talent.R;
import com.aero.droid.dutyfree.talent.app.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author : wangxp
 * Date : 2016/1/8
 * Desc : 各种dialog 工具类
 */
public class DialogUtil {
    public static Dialog getPhotoDialog(final Activity activity) {
        Dialog dialog = null;
        if (null == dialog){
            View view = View.inflate(activity, R.layout.photo_choose_dialog, null);
            dialog = new Dialog(activity,
                    R.style.transparentFrameWindowStyle);
            dialog.setContentView(view);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wmParams = window.getAttributes();
            wmParams.x = 0;
            wmParams.y = activity.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wmParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wmParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // 设置显示位置
            dialog.onWindowAttributesChanged(wmParams);
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            dialog.setCanceledOnTouchOutside(true);
            //拍照
            Button camera = (Button) view.findViewById(R.id.person_photo_btn);
            final Dialog finalDialog = dialog;
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(FileUtil.getImagePath(),TimeFormatUtil.getCameraPhotoName())));
                    activity.startActivityForResult(cameraIntent, Constants.EVENT_START_CAMERA_ACTIVITY);
                    finalDialog.dismiss();
                }
            });
            //相册
            Button photo = (Button) view.findViewById(R.id.person_data_photo_btn);
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
                    photoIntent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    activity.startActivityForResult(photoIntent, Constants.EVENT_START_PHOTO_ACTIVITY);
                    finalDialog.dismiss();
                }
            });
            //取消
            Button cancel = (Button) view.findViewById(R.id.person_photo_cancel_btn);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalDialog.dismiss();
                }
            });
        }
        dialog.show();
        return dialog;
    }
}

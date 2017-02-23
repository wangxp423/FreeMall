package com.aero.droid.dutyfree.talent.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 应用程序UI工具类：封装UI相关的一些操作
 * 
 */
public class UiUtil {
	private final static String TAG = "UiUtil";

	/**
	 * View的特殊显示
	 * @param activity
	 * @param newImg
	 * @param view
	 */
	public static void updateView(Activity activity, Bitmap newImg,
			ImageView view) {
		LogUtil.t("newImag 0 :" + newImg.getWidth() + "::"
				+ newImg.getHeight());
		int avalidHeight = getWindowHeight(activity)
				- getTitleHeight(activity);
		int avalidWidth = getWindowWidth(activity);

		Matrix matrix = new Matrix();

		float scaleX = newImg.getWidth() / avalidWidth;
		float scaleY = newImg.getHeight() / avalidHeight;

		// 判断资源文件的图片宽高比与屏幕宽高比，截取和屏幕相同宽高比，再等比例缩放与屏幕一样大
		if (scaleX > scaleY) {
			matrix.postScale(scaleY, scaleY); // 长和宽放大缩小的比例
			newImg = Bitmap.createBitmap(newImg, 0, 0,
					(int) (scaleY * avalidWidth), newImg.getHeight(), matrix,
					true);
		} else {
			matrix.postScale(scaleX, scaleX); // 长和宽放大缩小的比例
			newImg = Bitmap.createBitmap(newImg, 0, 0, newImg.getWidth(),
					(int) (scaleX * avalidHeight), matrix, true);
		}

		LogUtil.t("newImag 1 :" + newImg.getWidth() + "::"
				+ newImg.getHeight());

		view.setImageBitmap(newImg);
	}
	/**
	 * 设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0) {
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
			}
			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	/**
	 * 获取window的高
	 * @param context
	 * @return
	 */
	public static int getWindowHeight(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	/**
	 * 获取window的宽
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	/**
	 * 获取标题栏的高度
	 * @param context
	 * @return
	 */
	public static int getTitleHeight(Activity context){
		int statusHeight = 0;
		Rect localRect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight){
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
				statusHeight = context.getResources().getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}

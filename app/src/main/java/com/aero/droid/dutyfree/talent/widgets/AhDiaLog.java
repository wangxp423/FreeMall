package com.aero.droid.dutyfree.talent.widgets;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aero.droid.dutyfree.talent.R;


public class AhDiaLog extends Dialog {
	private View.OnClickListener onClickListener;
	private String title;
	private String content;
	private String negativeText;
	private String postiveText;
	private int negativeVisible = -1;
	private TextView titleView;
	private TextView contentView;
	private Button enterButton;
	private Button negativeButton;

	/**
	 * 弹出对话框
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            对话框内容
	 * @param onClickListener
	 *            确定监听
	 */
	public AhDiaLog(Context context, String content,
					View.OnClickListener onClickListener) {
		super(context, R.style.mydialog);
		this.content = content;
		this.onClickListener = onClickListener;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param onClickListener
	 *            确定监听
	 */
	public AhDiaLog(Context context, String title, String content,
					View.OnClickListener onClickListener) {
		this(context, content, onClickListener);
		// 如不需要改变title只需要传Null就可
		this.title = title;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param negativeText
	 *            取消键内容
	 * @param onClickListener
	 *            确定监听
	 */
	public AhDiaLog(Context context, String title, String content,
					String negativeText, View.OnClickListener onClickListener) {
		this(context, content, onClickListener);
		// 如不需要改变title只需要传Null就可
		this.title = title;
		// 如不需要改变取消按钮文字，只需要传Null就可
		this.negativeText = negativeText;
	}
	/**
	 * 弹出对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param negativeText
	 *            取消键内容
	 * @param postiveText
	 *            确定键键内容
	 * @param onClickListener
	 *            确定监听
	 */
	public AhDiaLog(Context context, String title, String content,
					String negativeText, String postiveText, View.OnClickListener onClickListener) {
		this(context, content, onClickListener);
		// 如不需要改变title只需要传Null就可
		this.title = title;
		// 如不需要改变取消按钮文字，只需要传Null就可
		this.negativeText = negativeText;
		this.postiveText = postiveText;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param context
	 *            上下文
	 * @param content
	 *            对话框内容
	 * @param negativeVisible
	 *            取消键隐藏
	 * @param onClickListener
	 *            确定监听
	 */
	public AhDiaLog(Context context, String content, int negativeVisible,
					View.OnClickListener onClickListener) {
		this(context, content, onClickListener);
		// 如不需要改变取消按钮隐藏，只需要传Null就可
		this.negativeVisible = negativeVisible;
	}

	private MyDiaLogListener listener = null;

	public interface MyDiaLogListener {
		void onCancelClick();

		void onResultIsUpdate(boolean isOk);

		void onException(boolean isEx);
	}

	public void setCancelClick(MyDiaLogListener listener) {
		this.listener = listener;
	}

	private void setNegativeVisible(int visible) {
		switch (visible) {
		case View.INVISIBLE:
			negativeButton.setVisibility(View.INVISIBLE);
			findViewById(R.id.ah_dialog_line).setVisibility(View.GONE);
			break;
		case View.GONE:
			negativeButton.setVisibility(View.GONE);
			findViewById(R.id.ah_dialog_line).setVisibility(View.GONE);
			break;
		case View.VISIBLE:
			negativeButton.setVisibility(View.VISIBLE);
			findViewById(R.id.ah_dialog_line).setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.my_dialog_base);
		this.setCancelable(false);
		enterButton = (Button) findViewById(R.id.ah_dialog_enter);
		negativeButton = (Button) findViewById(R.id.ah_dialog_cancel);
		titleView = (TextView) findViewById(R.id.ah_dialog_title_text);
		contentView = (TextView) findViewById(R.id.ah_dialog_body_text);
		if (!TextUtils.isEmpty(title)) {
			titleView.setText(title);
		}
		if (!TextUtils.isEmpty(negativeText)) {
			negativeButton.setText(negativeText);
		}
//		TODO
		if (!TextUtils.isEmpty(postiveText)) {
			enterButton.setText(postiveText);
		}
		setNegativeVisible(negativeVisible);
		contentView.setText(content);
		enterButton.setOnClickListener(onClickListener);
		negativeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onCancelClick();
				}
				AhDiaLog.this.dismiss();
			}
		});
	}
}
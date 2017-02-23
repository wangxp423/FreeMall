package com.aero.droid.dutyfree.talent.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {
    private OnMyListViewScrollListener listener;

	public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        t = getCurrentScrollY();
        if (null != listener)
        this.listener.onMyListViewScrollChange(l, t, oldl, oldt);
    }

    public void setMyScrollListener(OnMyListViewScrollListener mListener){
        this.listener = mListener;
    }

    public interface OnMyListViewScrollListener{
        public void onMyListViewScrollChange(int l, int t, int oldl, int oldt);
    }


    public int getCurrentScrollY() {
        View c = this.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = this.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }
}

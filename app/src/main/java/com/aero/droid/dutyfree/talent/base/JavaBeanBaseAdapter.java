package com.aero.droid.dutyfree.talent.base;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 描 述：adapter基类
 * 作 者：wangxp
 * 创 建：2015/11/02
 */
public abstract class JavaBeanBaseAdapter<T> extends BaseAdapter {

    private List<T> mObjects;

    private final Object mLock = new Object();

    private int mResource;

    private boolean mNotifyOnChange = true;

    protected Context mContext;

    private LayoutInflater mInflater;

    public JavaBeanBaseAdapter(Context context, int resource) {
        init(context, resource, new ArrayList<T>());
    }

    public JavaBeanBaseAdapter(Context context, int resource, T[] objects) {
        init(context, resource, Arrays.asList(objects));
    }

    public JavaBeanBaseAdapter(Context context, int resource, List<T> objects) {
        init(context, resource, objects);
    }

    public void refresh(List<T> mData){
        this.mObjects = mData;
        notifyDataSetChanged();
    }

    public void add(T object) {
        synchronized (mLock) {
            mObjects.add(object);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        synchronized (mLock) {
            mObjects.addAll(collection);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void addAll(T... items) {
        synchronized (mLock) {
            Collections.addAll(mObjects, items);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void insert(T object, int index) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void set(T object, int index) {
        synchronized (mLock) {
            mObjects.set(index, object);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void set(T oldObject, T newObject) {
        synchronized (mLock) {
            mObjects.set(mObjects.indexOf(oldObject), newObject);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void remove(T object) {
        synchronized (mLock) {
            mObjects.remove(object);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void remove(int index) {
        synchronized (mLock) {
            mObjects.remove(index);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public boolean contains(T object) {
        return mObjects.contains(object);
    }

    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(mObjects, comparator);
        }

        if (mNotifyOnChange) notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    /**
     * Control whether methods that change the list ({@link #add},
     * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
     * {@link #notifyDataSetChanged}.  If set to false, caller must
     * manually call notifyDataSetChanged() to have the changes
     * reflected in the attached view.
     * <p/>
     * The default is true, and calling notifyDataSetChanged()
     * resets the flag to true.
     *
     * @param notifyOnChange if true, modifications to the list will
     *                       automatically call {@link
     *                       #notifyDataSetChanged}
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    private void init(Context context, int resource, List<T> objects) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
        mObjects = objects;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public T getItem(int position) {
        return mObjects.get(position);
    }

    public int getPosition(T item) {
        return mObjects.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            (convertView = mInflater.inflate(mResource, parent, false))
                    .setTag(holder = new ViewHolder(convertView));
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            bindView(position, holder, getItem(position));
        }

        return convertView;
    }

    protected abstract void bindView(int position, ViewHolder holder, T object);

    public static abstract class BaseCursorAdapter extends CursorAdapter {
        private final LayoutInflater mInflater;
        private final int mResource;
        protected final Context mContext;

        protected abstract void bindView(ViewHolder viewHolder, Cursor cursor);

        public BaseCursorAdapter(Context context, int resource) {
            super(context, null, 0);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mResource = resource;
            mContext = context;
        }

        public BaseCursorAdapter(Context context, int resource, boolean autoRequery) {
            super(context, null, autoRequery);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mResource = resource;
            mContext = context;
        }

        public BaseCursorAdapter(Context context,Cursor cursor, int resource, boolean autoRequery) {
            super(context, cursor, autoRequery);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mResource = resource;
            mContext = context;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View convertView = mInflater.inflate(mResource, viewGroup, false);
            convertView.setTag(new ViewHolder(convertView));
            return convertView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (null != viewHolder)
                bindView(viewHolder, cursor);
        }
    }

    public static final class ViewHolder {
        private final View mConvertView;
        private final SparseArray<View> views;

        public ViewHolder(View convertView) {
            mConvertView = convertView;
            views = new SparseArray<View>();
        }

        public <T extends View> T getView(int viewId) {
            return retrieveView(viewId);
        }

        private <T extends View> T retrieveView(int viewId) {
            View view = views.get(viewId);
            if (null == view) {
                view = mConvertView.findViewById(viewId);
                views.append(viewId, view);
            }
            return (T) view;
        }

        public void setAlpha(int viewId, float alpha) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                retrieveView(viewId).setAlpha(alpha);
            } else {
                AlphaAnimation anim = new AlphaAnimation(alpha, alpha);
                anim.setDuration(0);
                anim.setFillAfter(true);
                retrieveView(viewId).startAnimation(anim);
            }
        }

        public void setVisibility(int viewId, int visibility) {
            retrieveView(viewId).setVisibility(visibility);
        }

        public View getConvertView() {
            return mConvertView;
        }

        public void removeView(int viewId) {
            views.remove(viewId);
        }
    }
}

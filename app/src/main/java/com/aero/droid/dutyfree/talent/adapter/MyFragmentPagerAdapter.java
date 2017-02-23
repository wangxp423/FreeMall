package com.aero.droid.dutyfree.talent.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Fragment adapter
 * @author wangxp
 *
 */
public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
	private Context context;
	private FragmentManager mFragmentManager;
	private List<Fragment> mFragmentList;

	public MyFragmentPagerAdapter(FragmentManager fm, Context con, List<Fragment> list) {
		super(fm);
		this.mFragmentManager = fm;
		this.context = con;
		this.mFragmentList = list;
	}
	public MyFragmentPagerAdapter(FragmentManager fm, Context con) {
		super(fm);
		this.mFragmentManager = fm;
		this.context = con;
	}

	@Override
	public Fragment getItem(int position) {
//		Fragment fragment = null;
//		if (position == 0)
//			fragment = new CategoryFragment1();
//		else if (position == 1)
//			fragment = new BrandFragment1();
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

//	public void setFragments(List<Fragment> fragments) {
//		if (this.mFragmentList != null) {
//			FragmentTransaction ft = mFragmentManager.beginTransaction();
//			for (Fragment f : this.mFragmentList) {
//				ft.remove(f);
//			}
//			ft.commit();
//			ft = null;
//			mFragmentManager.executePendingTransactions();
//		}
//		 this.mFragmentList = fragments;
//		 notifyDataSetChanged();
//	}

	@Override
	public Object instantiateItem(View container, int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

}

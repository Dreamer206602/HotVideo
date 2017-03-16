package com.booboomx.hotvideo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by booboomx on 17/3/16.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment>fragments) {
        super(fm);
        this.mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size()>0?mFragments.size():0;
    }
}

package com.zcf.bananavideohannan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    List<Fragment> fraList;

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fraList) {
        super(fm);
        this.fraList=fraList;
    }

    @Override
    public Fragment getItem(int i) {
        return fraList.get(i);
    }

    @Override
    public int getCount() {
        return fraList.size();
    }
}

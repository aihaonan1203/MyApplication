package com.zcf.bananavideohannan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zcf.bananavideohannan.bean.GuangGaoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanjin on 2017/7/10.
 * 图片展示adapter
 */
public class GTPictureDetailAdapter2 extends FragmentStatePagerAdapter {
    private List<GuangGaoBean> data;

    public GTPictureDetailAdapter2(FragmentManager fm, List<GuangGaoBean> photo_urls) {
        super(fm);
        if (photo_urls == null || photo_urls.size() <= 0) {
            photo_urls = new ArrayList<>();
        }
        this.data = photo_urls;
    }

    @Override
    public Fragment getItem(int position) {
//        return PictureChoiceDetailFragment.newInstance(data.get(position));
        return PictureViewPagerFragment2.newInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data.size();
    }
}

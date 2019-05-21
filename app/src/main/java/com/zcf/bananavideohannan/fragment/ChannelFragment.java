package com.zcf.bananavideohannan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.MainFragmentPagerAdapter;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.fragment.chanfgm.BqsxFragment;
import com.zcf.bananavideohannan.fragment.chanfgm.ZltjFragment;
import com.zcf.bananavideohannan.view.ViewPagerNoScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道
 */
public class ChannelFragment extends MyBaseFragment implements View.OnClickListener {
//    @ViewInject(R.id.chanfrgm)
//    private ViewPager chanfrgm;

    private ViewPagerNoScroll chanfrgm;

    private RadioGroup rg_channel_type;

    private RadioButton cb_zltj;

    private RadioButton cb_bjsx;

    private TextView tv_zltj_line;

    private TextView tv_bqsx_line;

    private MainFragmentPagerAdapter adapter;
    private Context mContext;
    private BvAndroidContent app;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_channel, null);
        mContext = getActivity();
        chanfrgm = view.findViewById(R.id.chanfrgm);
        rg_channel_type = view.findViewById(R.id.rg_channel_type);
        cb_zltj = view.findViewById(R.id.cb_zltj);
        cb_bjsx = view.findViewById(R.id.cb_bjsx);
        tv_zltj_line = view.findViewById(R.id.tv_zltj_line);
        tv_bqsx_line = view.findViewById(R.id.tv_bqsx_line);

        app = (BvAndroidContent) mContext.getApplicationContext();
        if (app.getParam(Consts.USER_UID) != null) {
            uid = app.getParam(Consts.USER_UID).toString();
        }


        chanfrgm.setNoScroll(false);
        initFragment();

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ZltjFragment());
        fragments.add(new BqsxFragment());
        adapter = new MainFragmentPagerAdapter(getActivity().getSupportFragmentManager(), chanfrgm, fragments);
        chanfrgm.setAdapter(adapter);

        setListeners();

    }

    private void setListeners() {
        cb_zltj.setOnClickListener(this);
        cb_bjsx.setOnClickListener(this);

        chanfrgm.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        cb_zltj.setChecked(true);
                        tv_zltj_line.setVisibility(View.VISIBLE);
                        tv_bqsx_line.setVisibility(View.GONE);
                        break;
                    case 1:
                        cb_bjsx.setChecked(true);
                        tv_zltj_line.setVisibility(View.GONE);
                        tv_bqsx_line.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_zltj:
                tv_zltj_line.setVisibility(View.VISIBLE);
                tv_bqsx_line.setVisibility(View.GONE);
                chanfrgm.setCurrentItem(0);
                break;
            case R.id.cb_bjsx:
                tv_zltj_line.setVisibility(View.GONE);
                tv_bqsx_line.setVisibility(View.VISIBLE);
                chanfrgm.setCurrentItem(1);
        }
    }
}

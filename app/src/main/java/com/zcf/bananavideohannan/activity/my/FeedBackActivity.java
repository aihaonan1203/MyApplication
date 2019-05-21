package com.zcf.bananavideohannan.activity.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.adapter.FragmentPagerAdapter;
import com.zcf.bananavideohannan.fragment.feekbackfragment.MyFeedBackFragment;
import com.zcf.bananavideohannan.fragment.feekbackfragment.CommonQuestionFragment;
import com.zcf.bananavideohannan.fragment.feekbackfragment.PublishFeekBackFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends MyBaseActivity implements View.OnClickListener {
    private List<Fragment> fraList;
    private LinearLayout mLlytWenti;
    private TextView mLineWenti;
    private LinearLayout mLlytYijian;
    private TextView mLineYijian;
    private LinearLayout mLlytFankui;
    private TextView mLineFankui;
    private ViewPager mViewpager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
    }

    private void init() {
        fraList = new ArrayList<>();
        fraList.add(new CommonQuestionFragment());
        fraList.add(new PublishFeekBackFragment());
        fraList.add(new MyFeedBackFragment());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fraList);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mLineWenti.setVisibility(View.VISIBLE);
                        mLineYijian.setVisibility(View.GONE);
                        mLineFankui.setVisibility(View.GONE);
                        break;
                    case 1:
                        mLineWenti.setVisibility(View.GONE);
                        mLineYijian.setVisibility(View.VISIBLE);
                        mLineFankui.setVisibility(View.GONE);
                        break;
                    case 2:
                        mLineWenti.setVisibility(View.GONE);
                        mLineYijian.setVisibility(View.GONE);
                        mLineFankui.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        mLlytWenti = findViewById(R.id.llyt_wenti);
        mLineWenti = findViewById(R.id.line_wenti);
        mLlytYijian = findViewById(R.id.llyt_yijian);
        mLineYijian = findViewById(R.id.line_yijian);
        mLlytFankui = findViewById(R.id.llyt_fankui);
        mLineFankui = findViewById(R.id.line_fankui);
        mViewpager = findViewById(R.id.idea_viewpager);
        mLlytWenti.setOnClickListener(this);
        mLlytYijian.setOnClickListener(this);
        mLlytFankui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llyt_wenti:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.llyt_yijian:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.llyt_fankui:
                mViewpager.setCurrentItem(2);
                break;
        }
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }
}

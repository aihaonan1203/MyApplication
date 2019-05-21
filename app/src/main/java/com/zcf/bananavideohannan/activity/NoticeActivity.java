package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.MainFragmentPagerAdapter;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.NoticeBean;
import com.zcf.bananavideohannan.fragment.notice.GongGFragment;
import com.zcf.bananavideohannan.fragment.notice.MsgFragment;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.ViewPagerNoScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道
 */
public class NoticeActivity extends MyBaseActivity implements View.OnClickListener {
//    @ViewInject(R.id.chanfrgm)
//    private ViewPager chanfrgm;

    private ViewPagerNoScroll chanfrgm;

    private RadioGroup rg_channel_type;

    private RadioButton cb_zltj;

    private RadioButton cb_bjsx;

    private TextView tv_zltj_line;

    private TextView tv_bqsx_line;
    private ImageView iv_back;

    private MainFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        chanfrgm = findViewById(R.id.chanfrgm);
        rg_channel_type = findViewById(R.id.rg_channel_type);
        cb_zltj = findViewById(R.id.cb_zltj);
        cb_bjsx = findViewById(R.id.cb_bjsx);
        tv_zltj_line = findViewById(R.id.tv_zltj_line);
        tv_bqsx_line = findViewById(R.id.tv_bqsx_line);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        chanfrgm.setNoScroll(false);
        initFragment();
//        getData();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void getData() {
        OkGo.<String>post(Consts.ACTION_URL_GET_NOTICE_LIST).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            if (!TextUtils.isEmpty(response.body())) {
                                NoticeBean bean = GsonUtil.parseJsonWithGson(response.body(), NoticeBean.class);
                                if (RESULT_CODE_SUCCESS == bean.getCode()) {
                                    List<NoticeBean.NoticeDomain> data = bean.getData();
                                    if (!listIsNull(data)) {

                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new GongGFragment());
        fragments.add(new MsgFragment());
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), chanfrgm, fragments);
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

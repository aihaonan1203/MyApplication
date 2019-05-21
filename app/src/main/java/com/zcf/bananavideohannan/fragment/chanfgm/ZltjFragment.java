package com.zcf.bananavideohannan.fragment.chanfgm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.AvListActivity;
import com.zcf.bananavideohannan.activity.SpecialListActivity;
import com.zcf.bananavideohannan.adapter.GTPictureDetailAdapter2;
import com.zcf.bananavideohannan.adapter.RevyZltjAdapter;
import com.zcf.bananavideohannan.adapter.ZhuanlanGridAdapter;
import com.zcf.bananavideohannan.adapter.ZhuanlanLvAdapter;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.AVBean;
import com.zcf.bananavideohannan.bean.GuangGaoBean;
import com.zcf.bananavideohannan.bean.zltj.ZltjTjResultBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.view.MyListviewNoScroll;
import com.zcf.bananavideohannan.view.ScrollRecyclerView;
import com.zcf.bananavideohannan.view.ViewPagerCustom;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 专栏推荐
 */
@ContentView(R.layout.fragment_main_channel_zttj)
public class ZltjFragment extends MyBaseFragment {

    @ViewInject(R.id.scrollView)
    private PullToRefreshScrollView scrollView;

    @ViewInject(R.id.zltj_horizon_listview)
    private ScrollRecyclerView mZltjHorizonListview;

    @ViewInject(R.id.zltj_viewpager)
    private ViewPagerCustom zltj_viewpager;

    @ViewInject(R.id.gv_rmzt)
    private GridView gv_rmzt;

    @ViewInject(R.id.rlyt_more)
    private RelativeLayout rlyt_more;

    @ViewInject(R.id.rlyt_remen_more)
    private RelativeLayout rlyt_remen_more;

    @ViewInject(R.id.lv_nvyou)
    private MyListviewNoScroll mZltjLv;

    RevyZltjAdapter tjAdapter;
    ZhuanlanGridAdapter ztAdapter;
    ZhuanlanLvAdapter nyAdapter;

    /**
     * radioGroup滑动point
     */
    @ViewInject(R.id.zltj_point)
    private RadioGroup zltj_point;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshComplete();
            switch (msg.what) {
                case 1:
                    initTjAdapter();
                    break;
                case 2:
                    initZtAdapter();
                    break;
                case 3:
                    initNyAdapter();
                    break;
            }
        }
    };

    public void refreshComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.onRefreshComplete();
            }
        }, 500);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllData();

        rlyt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AvListActivity.class));
            }
        });

        rlyt_remen_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SpecialListActivity.class));
            }
        });

        //下拉内容不偏移
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout headLables = scrollView.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel("下拉刷新");
        headLables.setRefreshingLabel("哎呀,在等我一会嘛,不要急撒");
        headLables.setReleaseLabel("有一种爱叫做放手,放开我就刷新了");
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getAllData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });
    }

    private void getAllData() {
        getChannel();
        getTjData();
        getZtData();
        getNyData();
    }

    private void getChannel() {
        Disposable d=Network.getVideoApi().getChannel("channel")
                .compose(this.<CommonlyBean<GuangGaoBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<GuangGaoBean>>() {
                    @Override
                    public void accept(CommonlyBean<GuangGaoBean> listCommonlyBean) throws Exception {
                        ad_data=new ArrayList<GuangGaoBean>(listCommonlyBean.getData());
                        if (!listIsNull(ad_data)) {
                            initAdApter();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private List<GuangGaoBean> ad_data;
    private ZltjTjResultBean tj_data;
    private ZltjTjResultBean zt_data;
    private List<AVBean> ny_data;

    private void initAdApter() {
        GTPictureDetailAdapter2 adapter = new GTPictureDetailAdapter2(getActivity().getSupportFragmentManager(), ad_data);
        zltj_viewpager.setAdapter(adapter);
        initGroup();
        zltj_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton rbtn = (RadioButton) zltj_point.getChildAt(i);
                rbtn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initTjAdapter() {
        tjAdapter = new RevyZltjAdapter(getActivity(), tj_data.getData());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mZltjHorizonListview.setLayoutManager(manager);
        mZltjHorizonListview.setAdapter(tjAdapter);
    }

    private void initZtAdapter() {
        if (zt_data.getData()!=null&&zt_data.getData().size()>8){
            for (int i =  zt_data.getData().size(); i > 8; i--) {
                zt_data.getData().remove(i-1);
            }
        }
        ztAdapter = new ZhuanlanGridAdapter(getActivity(), zt_data.getData());
        gv_rmzt.setAdapter(ztAdapter);

    }

    private void initNyAdapter() {
        nyAdapter = new ZhuanlanLvAdapter(getActivity(), ny_data);
        mZltjLv.setAdapter(nyAdapter);
    }

    /**
     * 动态显示radioGroup点
     */
    private void initGroup() {
        zltj_point.removeAllViews();
        for (int i = 0; i < ad_data.size(); i++) {
            RadioButton rbtn = new RadioButton(getActivity());
            rbtn.setButtonDrawable(null);
            rbtn.setBackgroundResource(R.drawable.radiobutton_ischecked);
            rbtn.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            rbtn.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            zltj_point.addView(rbtn);
        }
        RadioButton rbtn = (RadioButton) zltj_point.getChildAt(0);
        rbtn.setChecked(true);
    }

    private void getTjData() {
        Disposable d=Network.getVideoApi().special_index("recommend","1")
                .compose(this.<ZltjTjResultBean>applySchedulers())
                .subscribe(new Consumer<ZltjTjResultBean>() {
                    @Override
                    public void accept(ZltjTjResultBean resultBean) throws Exception {
                        Log.e("accept: ","" );
                        tj_data=resultBean;
                        handler.sendEmptyMessage(1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });
    }

    private void getZtData() {
        Disposable d=Network.getVideoApi().special_index("hot","1")
                .compose(this.<ZltjTjResultBean>applySchedulers())
                .subscribe(new Consumer<ZltjTjResultBean>() {
                    @Override
                    public void accept(ZltjTjResultBean resultBean) throws Exception {
                        Log.e("accept: ","" );
                        zt_data=resultBean;
                        handler.sendEmptyMessage(2);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });
    }


    private void getNyData() {
        Disposable d=Network.getVideoApi().actress_index("","1","1")
                .compose(this.<CommonlyBean<AVBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<AVBean>>() {
                    @Override
                    public void accept(CommonlyBean<AVBean> commonlyBean) throws Exception {
                        if (commonlyBean.getCode()==200){
                            ny_data=commonlyBean.getData();
                            handler.sendEmptyMessage(3);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });
    }

}

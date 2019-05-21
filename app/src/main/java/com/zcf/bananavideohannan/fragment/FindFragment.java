package com.zcf.bananavideohannan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.gaotai.baselib.DcAndroidContext;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.SearchActivity;
import com.zcf.bananavideohannan.adapter.FindlistviewAdapter;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.zcf.bananavideohannan.base.Consts.USER_UID;

/**
 * 发现
 */
@ContentView(R.layout.fragment_main_find)
public class FindFragment extends MyBaseFragment {


    @ViewInject(R.id.mListview)
    PullToRefreshListView mListview;

    private BvAndroidContent app;
    private FindlistviewAdapter adapter;
    private String uid;
    private Disposable disposable;
    private int page=1;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        app = (BvAndroidContent) getActivity().getApplicationContext();
        if (app.getParam(USER_UID) != null) {
            uid = app.getParam(USER_UID).toString();
        }

        loadData(page);

        //下拉内容不偏移
        mListview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout headLables = mListview.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel("下拉刷新");
        headLables.setRefreshingLabel("哎呀，再等一会吗，不要急撒");
        headLables.setReleaseLabel("有一种爱叫做放手，松开我就刷新了");

        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void loadData(int page) {
        disposable = Network.getVideoApi().getFind(DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString(),page)
                .compose(MyBaseFragment.<CommonlyBean<VideoBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<VideoBean>>() {
                    @Override
                    public void accept(CommonlyBean<VideoBean> commonlyBean) throws Exception {
                        List<VideoBean> list=commonlyBean.getData();
                        if (list!=null&&list.size()>0){
                            initAdapter(list);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshComplete();
                    }
                });
    }


    public void refreshComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListview.onRefreshComplete();
            }
        }, 500);
    }

    private void initAdapter(List<VideoBean> data) {
        refreshComplete();
        if (adapter == null) {
            if (userinfo.getVIP()==0){
                adapter = new FindlistviewAdapter(getActivity(), data,false);
            }else {
                adapter = new FindlistviewAdapter(getActivity(), data,true);
            }
            mListview.setAdapter(adapter);
        } else {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }

    }

    private LoginBean.DataBean.UserinfoBean userinfo;
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }

    @Event(R.id.iv_search)
    private void gotosearhc(View view) {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void onPause() {
        fm.jiecao.jcvideoplayer_lib.jcvideoplayer_two.JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

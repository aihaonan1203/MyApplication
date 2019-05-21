package com.zcf.bananavideohannan.fragment.chanfgm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.BqsxSearchAdapter;
import com.zcf.bananavideohannan.adapter.ChannelBqGridAdapter1;
import com.zcf.bananavideohannan.adapter.ChannelBqGridAdapter2;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.LabelBean;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.network.Network;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.gaotai.baselib.util.AndroidUtil.dip2px;

/**
 * 标签筛选
 */
@ContentView(R.layout.fragment_main_channel_bqsx)
public class BqsxFragment extends MyBaseFragment {
    @ViewInject(R.id.grid_bq1)
    private GridView grid_bq1;

    @ViewInject(R.id.grid_bq2)
    private GridView grid_bq2;

    @ViewInject(R.id.lv_selected_search)
    private PullToRefreshListView lv_selected_search;

    @ViewInject(R.id.rlyt_bottom)
    private RelativeLayout rlyt_bottom;


    private List<LabelBean> data1=new ArrayList<>();
    private List<LabelBean.ChildrenBean> data2=new ArrayList<>();

    private ChannelBqGridAdapter1 adapter1;
    private ChannelBqGridAdapter2 adapter2;

    private BqsxSearchAdapter adapter;

    HashMap<Integer, List<LabelBean.ChildrenBean>> mp_click = new HashMap<>();


    private HashMap<Integer, String> mp_parent = new HashMap<>();
    private HashMap<Integer, Integer> mp_child = new HashMap<>();
    private HashMap<Integer, String> mp_selected = new HashMap<>();
    private List<VideoBean> search_data;
    private HashMap<Object, String> label;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_selected_search.setVisibility(View.GONE);
        threadGetVideoDetail();
        lv_selected_search.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout mStartLabelse = lv_selected_search.getLoadingLayoutProxy(true, false);
        mStartLabelse.setRefreshingLabel("正在加载中,请耐心等待...");
        // 下拉刷新事件
        lv_selected_search.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    getByLabels(labels);
                }
            }
        });

        loadAllTypeData();
    }

    private void threadGetVideoDetail() {
        label = (HashMap<Object, String>) DcAndroidContext.getInstance().getParam("label");
        if (label == null) {
            label = new HashMap<>();
            Network.getVideoApi().getLabel_index()
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<CommonlyBean<LabelBean>, Publisher<LabelBean>>() {
                        @Override
                        public Publisher<LabelBean> apply(CommonlyBean<LabelBean> labelBeanCommonlyBean) throws Exception {
                            List<LabelBean> data = labelBeanCommonlyBean.getData();
                            return Flowable.fromIterable(data);
                        }
                    }).flatMap(new Function<LabelBean, Publisher<LabelBean.ChildrenBean>>() {
                @Override
                public Publisher<LabelBean.ChildrenBean> apply(LabelBean labelBean) throws Exception {
                    return Flowable.fromIterable(labelBean.getChildren());
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LabelBean.ChildrenBean>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(Long.MAX_VALUE);
                        }

                        @Override
                        public void onNext(LabelBean.ChildrenBean bean) {
                            label.put(bean.getId(), bean.getName());
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.e("onError: ","" );
                        }

                        @Override
                        public void onComplete() {
                            Log.e("onNext: ", "结束--->" + label.size());
                            DcAndroidContext.getInstance().setParam("label", label);
                        }
                    });
        }
    }

    private void loadAllTypeData() {
        Disposable d=Network.getVideoApi().getLabel_index()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommonlyBean<LabelBean>>() {
                    @Override
                    public void accept(CommonlyBean<LabelBean> labelBeanCommonlyBean) throws Exception {
                        data1.addAll(labelBeanCommonlyBean.getData());
                        if (!listIsNull(data1)) {
                            initAdapter();
                            for (int i = 0; i < data1.size(); i++) {
                                LabelBean parentBean = data1.get(i);
                                if (parentBean != null) {
                                    mp_click.put(parentBean.getId(), parentBean.getChildren());
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("accept: ","" );
                    }
                });

//        OkGo.<String>post(ACTION_URL_GET_BQSX_DATA).tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            Bqsxbean bean = GsonUtil.parseJsonWithGson(response.body(), Bqsxbean.class);
//                            if (bean != null) {
//                                data1 = bean.getData();
//                                if (!listIsNull(data1)) {
//                                    initAdapter();
//
//                                    for (int i = 0; i < data1.size(); i++) {
//                                        BqsxparentBean parentBean = data1.get(i);
//                                        if (parentBean != null) {
//                                            mp_click.put(parentBean.getLabel_id(), parentBean.getChildren());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });
    }

    private void initSearchAdapter() {
        adapter = new BqsxSearchAdapter(getActivity(), search_data);
        lv_selected_search.setAdapter(adapter);

        if (lv_selected_search.isRefreshing()) {
            lv_selected_search.onRefreshComplete();
        }
        ProgressDialogUtil.dismiss();
    }

    /**
     * 设置高度
     */
    public static void setHeight(Context context, RecyclerView listView, List<?> list, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listView.getLayoutParams();
        params.height = dip2px(context, height) * list.size();
        listView.setLayoutParams(params);
    }

    private void initAdapter() {
        if (data1 != null && data1.size() > 0) {
            LabelBean newbean = new LabelBean();
            newbean.setName("重置");
            newbean.setId(-1);
            data1.add(newbean);
        }

        adapter1 = new ChannelBqGridAdapter1(getActivity(), data1);

        // 默认选中
        LabelBean moren_select = data1.get(0);

        if (moren_select != null && moren_select.getId() != -1) {
            adapter1.setSelected_id(data1.get(0).getId());
            grid_bq1.setAdapter(adapter1);

            data2 = moren_select.getChildren();
            adapter2 = new ChannelBqGridAdapter2(getActivity(), data2);
            grid_bq2.setAdapter(adapter2);
        }
        adapter1.setItemSelectClick(new ChannelBqGridAdapter1.itemClickSelcted() {
            @Override
            public void onItemSelectedClick(LabelBean bean) {
                if (bean != null) {
                    if ("重置".equals(bean.getName())) {
                        grid_bq2.setVisibility(View.VISIBLE);
                        lv_selected_search.setVisibility(View.GONE);
                        // 默认选中
                        LabelBean moren_select = data1.get(0);
                        if (moren_select != null && moren_select.getId() != -1) {
                            int label_id = data1.get(0).getId();
                            adapter1.setSelected_id(data1.get(0).getId());
                            data2 = mp_click.get(label_id);
                        }
                        mp_selected = new HashMap<>();

                        mp_parent = new HashMap<>();
                        mp_child = new HashMap<>();

                        adapter1.setMp_parent(mp_parent);
                        adapter1.notifyDataSetChanged();

                        adapter2.setMp_child(mp_child);
                        adapter2.setData(data2);
                        adapter2.notifyDataSetChanged();
                        rlyt_bottom.setVisibility(View.GONE);
                    } else {
                        adapter1.setSelected_id(bean.getId());
                        adapter1.notifyDataSetChanged();

                        data2 = mp_click.get(bean.getId());
                        adapter2.setData(data2);
                        adapter2.notifyDataSetChanged();
                    }
                }
            }
        });

        adapter2 = new ChannelBqGridAdapter2(getActivity(), data2);
        grid_bq2.setAdapter(adapter2);

        adapter2.setChildClickSelcted(new ChannelBqGridAdapter2.childClickSelcted() {
            @Override
            public void onChildSelectedClick(LabelBean.ChildrenBean bean) {
                if (!mp_child.containsKey(bean.getId())) {
                    mp_child.put(bean.getId(), bean.getId());
                    mp_selected.put(bean.getId(), bean.getName());
                    if (!mp_parent.containsKey(bean.getPid())) {
                        mp_parent.put(bean.getPid(), "");
                    }
                } else {
                    mp_selected.remove(bean.getId());
                    mp_child.remove(bean.getId());
                    int pid = bean.getPid();
                    if (!isChildHasPid(mp_child, pid)) {
                        if (mp_parent.containsKey(pid)) {
                            mp_parent.remove(pid);
                        }
                    }
                }

                adapter1.setMp_parent(mp_parent);
                adapter1.notifyDataSetChanged();

                adapter2.setMp_child(mp_child);
                adapter2.notifyDataSetChanged();

                if (mp_child != null && mp_child.size() > 0) {
                    rlyt_bottom.setVisibility(View.VISIBLE);
                } else {
                    rlyt_bottom.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean isChildHasPid(HashMap<Integer, Integer> mpdata, int pid) {
        if (mpdata == null || mpdata.size() <= 0) {
            return false;
        } else {
            for (Integer id : mpdata.keySet()) {
                int valuepid = mpdata.get(id);
                if (valuepid == pid) {
                    return true;
                }
            }
        }
        return false;
    }

    private String labels;

    @Event(R.id.btn_send_labels)
    private void sendLabels(View view) {
        ProgressDialogUtil.showDialog(getActivity(), "", true);
        if (mp_selected != null && mp_selected.size() > 0) {
            StringBuffer strsb = new StringBuffer();
            for (Integer key : mp_selected.keySet()) {
                strsb.append(key).append(",");
            }
            labels = strsb.toString();
            if (!TextUtils.isEmpty(labels)) {
                labels = labels.substring(0, labels.length() - 1);
            }
            getByLabels(labels);
        }
    }

    private void getByLabels(String labels) {
        Disposable d=Network.getVideoApi().label_video(labels)
                .compose(MyBaseFragment.<CommonlyBean<VideoBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<VideoBean>>() {
                    @Override
                    public void accept(CommonlyBean<VideoBean> commonlyBean) throws Exception {
                        ProgressDialogUtil.dismiss();
                        search_data= commonlyBean.getData();
                        if (search_data != null) {
                            if (!listIsNull(search_data)) {
                                grid_bq2.setVisibility(View.GONE);
                                rlyt_bottom.setVisibility(View.GONE);
                                lv_selected_search.setVisibility(View.VISIBLE);
                                initSearchAdapter();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismiss();
                    }
                });

//        OkGo.<String>post(ACTION_URL_GET_BY_BQSX).tag(this)
//                .params("labels", labels)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        if (200 == response.code()) {
//                            if (response.body() != null) {
//                                BqsxSearchBean bean = GsonUtil.parseJsonWithGson(response.body(), BqsxSearchBean.class);
//                                if (bean != null) {
//                                    search_data = bean.getData();
//                                    if (!listIsNull(search_data)) {
//                                        grid_bq2.setVisibility(View.GONE);
//                                        rlyt_bottom.setVisibility(View.GONE);
//                                        lv_selected_search.setVisibility(View.VISIBLE);
//                                        initSearchAdapter();
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });
    }

}

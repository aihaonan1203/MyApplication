package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.FilmAdapter;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LabelBean;
import com.zcf.bananavideohannan.bean.SearchDataBean;
import com.zcf.bananavideohannan.bean.VideoBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.AutoLinefeedLayout;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 搜索界面
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends MyBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.rlyt_seasrch)
    private RelativeLayout rlyt_seasrch;

    @ViewInject(R.id.edt_search_input)
    private EditText edt_search_input;

    @ViewInject(R.id.iv_delete_input)
    private ImageView iv_delete_input;

    @ViewInject(R.id.tv_cancle)
    private TextView tv_cancle;

    @ViewInject(R.id.lv_search_data)
    private ListView lv_search_data;

    @ViewInject(R.id.afl_search_jl)
    private AutoLinefeedLayout afl_search_jl;

    @ViewInject(R.id.afl_search_bq)
    private AutoLinefeedLayout afl_search_bq;

    @ViewInject(R.id.rlyt_lishi)
    private RelativeLayout rlyt_lishi;

    @ViewInject(R.id.llyt_search)
    private LinearLayout llyt_search;

    @ViewInject(R.id.rlyt_rem)
    private RelativeLayout rlyt_rem;

    private FilmAdapter adapter;
    private List<SearchDataBean.DataBean> data;
    private HashMap<Object, String> label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        threadGetVideoDetail();
        freshLsJlAdapter();

        setListeners();

        edt_search_input.requestFocus();

//        if (app != null && app.getParam("search_type_data") != null) {
//            List<SearchTypeBean.SearchType> data = (List<SearchTypeBean.SearchType>) app.getParam("search_type_data");
//            if (!listIsNull(data)) {
//                rlyt_rem.setVisibility(View.VISIBLE);
//                initBqAdapter(data);
//            }
//        }
        getHotSearch();
    }

    private void freshLsJlAdapter() {
        HashMap<String, String> mpBq = app.getMpBq();
        if (mpBq != null && mpBq.size() > 0) {
            rlyt_lishi.setVisibility(View.VISIBLE);
            afl_search_jl.setVisibility(View.VISIBLE);
            initBqAdapterJL(mpBq);
        } else {
            afl_search_jl.setVisibility(View.GONE);
            rlyt_lishi.setVisibility(View.GONE);
        }
    }

    private void getHotSearch() {
        OkGo.<String>post(ACTION_URL_SEARCH_HOT).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            SearchDataBean bean = GsonUtil.parseJsonWithGson(response.body(), SearchDataBean.class);
                            if (bean != null) {
                                data = bean.getData();
                                if (!listIsNull(data)) {
                                    rlyt_rem.setVisibility(View.VISIBLE);
                                    initBqAdapter(data);
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


    private void setListeners() {
        rlyt_seasrch.setOnClickListener(this);
        iv_delete_input.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        edt_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String inputtest = edt_search_input.getText().toString().trim();
                    if (TextUtils.isEmpty(inputtest)) {
                        ToastUtil.toastShort(mContext, "请输入搜索内容");
                    } else {
                        if (!app.getMpBq().containsKey(inputtest)) {
                            app.getMpBq().put(inputtest, inputtest);
                        }
                        loadBySearch(inputtest);
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlyt_seasrch:
                if (TextUtils.isEmpty(edt_search_input.getText().toString().trim())) {
                    ToastUtil.toastShort(mContext, "请输入搜索内容");
                    return;
                }
                loadBySearch(edt_search_input.getText().toString());
                break;
            case R.id.iv_delete_input:
                if (!TextUtils.isEmpty(edt_search_input.getText())) {
                    edt_search_input.setText("");
                }
                break;
            case R.id.tv_cancle:
                finish();
                break;
        }
    }

    /**
     * 根据搜索内容
     */
    private void loadBySearch(String inputText) {
        ProgressDialogUtil.showDialog(mContext, "搜索中...", false);
        Disposable disposable = Network.getVideoApi().keyword_video(inputText, "1")
                .compose(this.<CommonlyBean<VideoBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<VideoBean>>() {
                    @Override
                    public void accept(CommonlyBean<VideoBean> commonlyBean) throws Exception {
                        if (200 == commonlyBean.getCode()) {
                            List<VideoBean> bean = commonlyBean.getData();
                            if (bean != null) {
                                if (!listIsNull(bean)) {
                                    llyt_search.setVisibility(View.GONE);
                                    lv_search_data.setVisibility(View.VISIBLE);
                                    initSearchAdapter(bean);
                                }
                            }
                        }
                        ProgressDialogUtil.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismiss();
                        ToastUtil.toastShort(mContext, "换个姿势再试一次");
                    }
                });
    }

    private void initBqAdapter(List<SearchDataBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            final String label = data.get(i).getName();
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.childview_film_label, null);
            textView.setText(label);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!app.getMpBq().containsKey(label)) {
                        app.getMpBq().put(label, label);
                    }
                    loadBySearch(label);
                }
            });
            afl_search_bq.addView(textView);
        }
    }

    private void initBqAdapterJL(HashMap<String, String> mpdata) {
        for (final String label : mpdata.keySet()) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.childview_film_label, null);
            textView.setText(label);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!app.getMpBq().containsKey(label)) {
                        app.getMpBq().put(label, label);
                    }
                    loadBySearch(label);
                }
            });
            afl_search_jl.addView(textView);
        }
    }


    private void threadGetVideoDetail() {
        label = (HashMap<Object, String>) app.getParam("label");
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
                            app.setParam("label", label);
                        }
                    });
        }
    }


    private void initSearchAdapter(List<VideoBean> data) {
        if (adapter == null) {
            adapter = new FilmAdapter(mContext, data);
            lv_search_data.setAdapter(adapter);
        } else {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Event(R.id.iv_del_jl)
    private void deleteSearchJl(View view) {
        app.setMpBq(new HashMap<String, String>());
        freshLsJlAdapter();
    }

//    private void initAdapter() {
//        // TODO: 2018/12/3
//        List<String> labels = new ArrayList<>();
//        labels.add("测试");
//        labels.add("测试数");
//        labels.add("测试数据");
//        labels.add("测试");
//        labels.add("测试数");
//        labels.add("测试");
//        labels.add("测试数");
//        labels.add("测试数据");
//        labels.add("测试");
//        labels.add("测试数");
//        labels.add("测试");
//        labels.add("测试数");
//        labels.add("测试数据");
//        labels.add("测试");
//        labels.add("测试数");
//
//        data = new ArrayList<>();
//        data.add(new FilmDBModel("测试数据", labels, "", "9.3", 3869, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "8.3", 15820000, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "7.5", 45842512, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "5.6", 345213, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "4.3", 1232134, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "3.8", 11233, ""));
//        data.add(new FilmDBModel("测试数据", labels, "", "7.7", 1542345, ""));
//        if (adapter == null) {
//            adapter = new FilmAdapter(mContext, data);
//            lv_search_data.setAdapter(adapter);
//        } else {
//            adapter.setData(data);
//            adapter.notifyDataSetChanged();
//        }
//    }

}

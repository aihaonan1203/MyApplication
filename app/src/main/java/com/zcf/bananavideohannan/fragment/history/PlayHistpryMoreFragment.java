package com.zcf.bananavideohannan.fragment.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.adapter.MyHistoryAdapter;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.MyBaseFragment;
import com.zcf.bananavideohannan.bean.LishiBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_playhistory_more)
public class PlayHistpryMoreFragment extends MyBaseFragment {
    @ViewInject(R.id.lv_ph_more)
    private ListView lv_ph_more;
    private Context mContext;
    private MyHistoryAdapter adapter;
    public static Handler handler;
    private List<LishiBean> data;

    @SuppressLint("ValidFragment")
    public PlayHistpryMoreFragment(List<LishiBean> data) {
        this.data = data;
    }

    public static final int HANDLER_SHOW_CHECKBOX = 1;
    public static final int HANDLER_HIDE_CHECKBOX = 2;
    public static final int HANDLER_SELECT_ALL_CHECKBOX = 3;
    public static final int HANDLER_SCANCLE_ELECT_ALL_CHECKBOX = 4;
    public static final int HANDLER_DELETE_ALL_CHECKBOX = 5;
    private BvAndroidContent app;

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mContext = getActivity();
        app = (BvAndroidContent) mContext.getApplicationContext();
        initAdapter();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HANDLER_SHOW_CHECKBOX:
                        if (adapter != null) {
                            adapter.setShowCheck(true);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case HANDLER_HIDE_CHECKBOX:
                        if (adapter != null) {
                            adapter.setShowCheck(false);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case HANDLER_SELECT_ALL_CHECKBOX:
                        Observable.fromIterable(data)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<LishiBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LishiBean lishiBean) {
                                        lishiBean.setIscheck(true);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        break;
                    case HANDLER_DELETE_ALL_CHECKBOX:
                        break;
                    case HANDLER_SCANCLE_ELECT_ALL_CHECKBOX:
                        Observable.fromIterable(data)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<LishiBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LishiBean lishiBean) {
                                        lishiBean.setIscheck(false);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        break;
                }
            }
        };
    }

    private void initAdapter() {
        adapter=new MyHistoryAdapter(getActivity(),data);
        lv_ph_more.setAdapter(adapter);
    }
}

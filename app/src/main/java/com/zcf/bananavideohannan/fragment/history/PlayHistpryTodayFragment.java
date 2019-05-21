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

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.zcf.bananavideohannan.base.Consts.USER_UID;

@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_playhistory_today)
public class PlayHistpryTodayFragment extends MyBaseFragment {
    @ViewInject(R.id.lv_ph_today)
    private ListView lv_ph_today;
    private Context mContext;
    private MyHistoryAdapter adapter;
    private List<LishiBean> data,myData;

    @SuppressLint("ValidFragment")
    public PlayHistpryTodayFragment(List<LishiBean> data) {
        this.data = data;
        myData=data;
    }



    public static Handler handler;

    public static final int HANDLER_SHOW_CHECKBOX = 1;
    public static final int HANDLER_HIDE_CHECKBOX = 2;
    public static final int HANDLER_SELECT_ALL_CHECKBOX = 3;
    public static final int HANDLER_SCANCLE_ELECT_ALL_CHECKBOX = 4;
    public static final int HANDLER_DELETE_ALL_CHECKBOX = 5;
    private BvAndroidContent app;
    private String uid;

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mContext = getActivity();
        app = (BvAndroidContent) mContext.getApplicationContext();
        initAdapter();
        if (app != null && app.getParam(USER_UID) != null) {
            uid = app.getParam(USER_UID).toString();
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HANDLER_SHOW_CHECKBOX:
                        adapter.setShowCheck(true);
                        adapter.notifyDataSetChanged();
                        break;
                    case HANDLER_HIDE_CHECKBOX:
                        adapter.setShowCheck(false);
                        adapter.setMpdata_select(new HashMap<String, String>());
                        adapter.notifyDataSetChanged();
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
        }

        ;
    }

    private void initAdapter() {
        adapter=new MyHistoryAdapter(Objects.requireNonNull(getActivity()),data);
        lv_ph_today.setAdapter(adapter);
    }
}

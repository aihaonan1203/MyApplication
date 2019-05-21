package com.zcf.bananavideohannan.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.gaotai.baselib.util.CompleteQuit;
import com.zcf.bananavideohannan.activity.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.xutils.x;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyBaseActivity extends AppCompatActivity implements Consts {
    public Context mContext;
    public BvAndroidContent app;
    public boolean isLogined = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        }catch (EventBusException e){

        }
        CompleteQuit.getInstance().addActivity(this);
        x.view().inject(this);
        mContext = this;
        app = (BvAndroidContent) getApplicationContext();
    }



    /**
     * 验证是否登录过
     */
    public boolean checkLogin() {
        String uid = ContextProperties.readRemember(mContext, ContextProperties.REM_UID);
        String password = ContextProperties.readRemember(mContext, ContextProperties.REM_PASSWORD);
        String account = ContextProperties.readRemember(mContext, ContextProperties.REM_PASSWORD);
        String token = ContextProperties.readRemember(mContext, ContextProperties.REM_TOKEN);
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(account)&& !TextUtils.isEmpty(token)) {
            isLogined = true;

            return true;
        } else {
            isLogined = false;
            return false;
        }
    }

    public void gotoTargetActivity(Class<?> mClass) {
        if (checkLogin()) {
            startActivity(new Intent(mContext, mClass));
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        CompleteQuit.getInstance().removeActivity(this);
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public boolean listIsNull(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    public static   <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}


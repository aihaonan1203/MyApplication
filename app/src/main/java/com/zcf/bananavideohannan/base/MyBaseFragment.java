package com.zcf.bananavideohannan.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcf.bananavideohannan.activity.login.LoginActivity;

import org.xutils.x;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 基础Fragment
 */
public class MyBaseFragment extends android.support.v4.app.Fragment {
    private boolean injected = false;
    private boolean isLogined = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }

        checkLogin();
    }

    public boolean listIsNull(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }


    /**
     * 验证是否登录过
     */
    public boolean checkLogin() {
        String uid = ContextProperties.readRemember(getActivity(), ContextProperties.REM_UID);
        String password = ContextProperties.readRemember(getActivity(), ContextProperties.REM_PASSWORD);
        String account = ContextProperties.readRemember(getActivity(), ContextProperties.REM_PASSWORD);
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(account)) {
            isLogined = true;
            return true;
        } else {
            isLogined = false;
            return false;
        }
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

    public void gotoTargetActivity(Class<?> mClass) {
        if (checkLogin()) {
            startActivity(new Intent(getActivity(), mClass));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
}

package com.gaotai.baselib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import java.util.List;

/**
 * 基础Fragment
 */
public class BaseFragment extends android.support.v4.app.Fragment {
    private boolean injected = false;

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
    }

    public boolean listIsNull(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }
}

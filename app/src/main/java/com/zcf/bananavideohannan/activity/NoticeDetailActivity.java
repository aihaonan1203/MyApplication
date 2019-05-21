package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_notice_detail)
public class NoticeDetailActivity extends MyBaseActivity {
    @ViewInject(R.id.webview)
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }
}

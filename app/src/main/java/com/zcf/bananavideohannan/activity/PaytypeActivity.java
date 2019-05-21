package com.zcf.bananavideohannan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_paytype)
public class PaytypeActivity extends MyBaseActivity {
    @ViewInject(R.id.llyt_weixin)
    private LinearLayout llyt_weixin;

    @ViewInject(R.id.llyt_zhifb)
    private LinearLayout llyt_zhifb;

    @ViewInject(R.id.rb_pay_weixin)
    private CheckBox rb_pay_weixin;

    @ViewInject(R.id.rb_pay_zhifubao)
    private CheckBox rb_pay_zhifubao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    @Event(R.id.llyt_weixin)
    private void chooseweixn(View view) {
        rb_pay_weixin.setChecked(true);
        rb_pay_zhifubao.setChecked(false);
    }

    @Event(R.id.llyt_zhifb)
    private void choosepay(View view) {
        rb_pay_weixin.setChecked(false);
        rb_pay_zhifubao.setChecked(true);
    }
}

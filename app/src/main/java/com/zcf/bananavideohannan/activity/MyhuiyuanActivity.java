package com.zcf.bananavideohannan.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.ToastView;
import com.zcf.bananavideohannan.view.XCRoundImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_myhuiyuan)
public class MyhuiyuanActivity extends MyBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.iv_head)
    private XCRoundImageView iv_head;

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    @ViewInject(R.id.tv_date)
    private TextView tv_date;

    @ViewInject(R.id.llyt_onemonth)
    private LinearLayout llyt_onemonth;

    @ViewInject(R.id.llyt_baoji)
    private LinearLayout llyt_baoji;

    @ViewInject(R.id.llyt_lxbaoyue)
    private LinearLayout llyt_lxbaoyue;

    @ViewInject(R.id.btn_pay)
    private Button btn_pay;

    @ViewInject(R.id.iv_cz_erweima)
    private ImageView iv_cz_erweima;

    @ViewInject(R.id.tv_cz_vx)
    private TextView tv_cz_vx;

    @ViewInject(R.id.llyt_textarea)
    private LinearLayout llyt_textarea;

    @ViewInject(R.id.tv_cz_content)
    private TextView tv_cz_content;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app != null) {
            if (app.getParam(Consts.USER_MOBILE) != null) {
                String phone = Utils.getPhone(app.getParam(Consts.USER_MOBILE).toString());
                tv_phone.setText(phone);
            }
            if (app.getParam(Consts.USER_HEADIMG) != null) {
                LoadImageUtil.loadImg(app.getParam(Consts.USER_HEADIMG).toString(), iv_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            }

            if (app.getParam(Consts.USER_VX_EWM) != null) {
                LoadImageUtil.loadImg(app.getParam(Consts.USER_VX_EWM).toString(), iv_cz_erweima, LoadImageUtil.getImageOptions(R.drawable.white), R.drawable.white);
            }
            String account = null;
            if (app.getParam(Consts.USER_VX_ACCOUNT) != null) {
                tv_name.setVisibility(View.VISIBLE);
                account = app.getParam(Consts.USER_VX_ACCOUNT).toString();
                tv_cz_vx.setText(account);
            }

            String content = null;
            if (app.getParam(Consts.USER_VX_CONTENT) != null) {
                content = app.getParam(Consts.USER_VX_CONTENT).toString();
                tv_cz_content.setText(content);
            }
        }

        setListeners();
    }

    private void setListeners() {
        llyt_onemonth.setOnClickListener(this);
        llyt_baoji.setOnClickListener(this);
        llyt_lxbaoyue.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        tv_cz_vx.setOnClickListener(this);
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_onemonth:
                llyt_onemonth.setBackgroundResource(R.drawable.bg_vip_cq_bg);
                llyt_baoji.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                llyt_lxbaoyue.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                break;
            case R.id.llyt_baoji:
                llyt_onemonth.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                llyt_baoji.setBackgroundResource(R.drawable.bg_vip_cq_bg);
                llyt_lxbaoyue.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                break;
            case R.id.llyt_lxbaoyue:
                llyt_onemonth.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                llyt_baoji.setBackgroundResource(R.drawable.bg_vip_cq_bg1);
                llyt_lxbaoyue.setBackgroundResource(R.drawable.bg_vip_cq_bg);
                break;
            case R.id.btn_pay:
                startActivity(new Intent(mContext, PaytypeActivity.class));
                break;
            case R.id.tv_cz_vx:
                if (!TextUtils.isEmpty(tv_cz_vx.getText().toString().trim())) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(tv_cz_vx.getText().toString());
                    ToastView.createToastConfig().showMessage(mContext, "已复制到粘贴板");
                }
                break;
        }
    }
}

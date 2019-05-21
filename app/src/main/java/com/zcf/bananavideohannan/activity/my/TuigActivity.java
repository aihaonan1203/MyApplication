package com.zcf.bananavideohannan.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gaotai.baselib.util.LoadImageUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.ErweimActivity;
import com.zcf.bananavideohannan.base.BvAndroidContent;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.XCRoundImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_tuig)
public class TuigActivity extends MyBaseActivity {

    @ViewInject(R.id.iv_head)
    private XCRoundImageView iv_head;

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    @ViewInject(R.id.tv_yaoqingma)
    private TextView tv_yaoqingma;

    @ViewInject(R.id.tv_lessd)
    private TextView tv_lessd;

    @ViewInject(R.id.tv_level_num)
    private TextView tv_level_num;

    @ViewInject(R.id.tv_level_name)
    private TextView tv_level_name;

    @ViewInject(R.id.tv_look_max_num)
    private TextView tv_look_max_num;

    @ViewInject(R.id.tv_lesslook_num)
    private TextView tv_lesslook_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app == null) {
            app = (BvAndroidContent) mContext.getApplicationContext();
        }
        LoadImageUtil.loadImg(userinfo.getAvatar(), iv_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
        tv_phone.setText(Utils.getPhone(userinfo.getMobile()));
        tv_yaoqingma.setText("我的邀请码：" + userinfo.getInvite());
        tv_lessd.setText("距离下一等级还差" + userinfo.getBlv() + "人");
        tv_level_num.setText("Lv" + userinfo.getLevel());
        tv_look_max_num.setText(userinfo.getTimesupper());
        if (app.getParam("times") != null) {
            tv_lesslook_num.setText(app.getParam("times").toString());
        }else {
            tv_lesslook_num.setText(String.valueOf(userinfo.getTimes()));
        }
    }

    @Event(R.id.btn_tuig)
    private void gototg(View view) {
        startActivity(new Intent(mContext, ErweimActivity.class));
    }

    @Event(R.id.rlyt_erweima)
    private void gototg1(View view) {
        startActivity(new Intent(mContext, ErweimActivity.class));
    }

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    private LoginBean.DataBean.UserinfoBean userinfo;
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }
}

package com.zcf.bananavideohannan.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.ErweimActivity;
import com.zcf.bananavideohannan.activity.topup.TopUpActivity;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.view.XCRoundImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TuiGuangActivity extends MyBaseActivity implements View.OnClickListener {
    public static final int MYVIP=999;
    private ImageView mIvBack;
    private RelativeLayout mMyPLay;
    private XCRoundImageView mIvHead;
    /**
     * 姓名
     */
    private TextView mTvPhone;
    /**
     * 开通会员获取邀请码
     */
    private TextView mTvYaoqingma;
    private LinearLayout mRlytErweima;
    /**
     * 0
     */
    private TextView mTvMoney;
    private ImageView mIvGrade;
    /**
     * 普通会员
     */
    private TextView mTvLevelName;
    private LinearLayout mLlytVip1;
    private LinearLayout mLlytVip2;
    private LinearLayout mLlytVip3;
    /**
     * 90天所有视频无限制免费观看
     */
    private TextView mTvVipLook;
    /**
     * 永久享受推广特权。分别享受1至4级代理\n10%,10%,10%,10%,的奖励金
     */
    private TextView mTvVipTq;
    private LinearLayout mFlytContent;
    /**
     * 立即推广
     */
    private Button mBtnTuig;
    private TextView mTvZw1;
    private TextView mTvZw2;
    private TextView mTvZw3;

    private boolean isVip=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang);
        initView();
        initData();
        changeText(1);
    }

    private void initData() {
        if (userinfo!=null){
            mTvPhone.setText(userinfo.getNickname());
        }
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mMyPLay = (RelativeLayout) findViewById(R.id.my_p_lay);
        mIvHead = (XCRoundImageView) findViewById(R.id.iv_head);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvYaoqingma = (TextView) findViewById(R.id.tv_yaoqingma);
        mRlytErweima = (LinearLayout) findViewById(R.id.rlyt_erweima);
        mRlytErweima.setOnClickListener(this);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mIvGrade = (ImageView) findViewById(R.id.iv_grade);
        mTvLevelName = (TextView) findViewById(R.id.tv_level_name);
        mLlytVip1 = (LinearLayout) findViewById(R.id.llyt_vip1);
        mLlytVip1.setOnClickListener(this);
        mLlytVip2 = (LinearLayout) findViewById(R.id.llyt_vip2);
        mLlytVip2.setOnClickListener(this);
        mLlytVip3 = (LinearLayout) findViewById(R.id.llyt_vip3);
        mLlytVip3.setOnClickListener(this);
        mTvVipLook = (TextView) findViewById(R.id.tv_vip_look);
        mTvVipTq = (TextView) findViewById(R.id.tv_vip_tq);
        mFlytContent = (LinearLayout) findViewById(R.id.flyt_content);
        mBtnTuig = (Button) findViewById(R.id.btn_tuig);
        mBtnTuig.setOnClickListener(this);
        mTvZw1 = (TextView) findViewById(R.id.tv_zw_1);
        mTvZw2 = (TextView) findViewById(R.id.tv_zw_2);
        mTvZw3 = (TextView) findViewById(R.id.tv_zw_3);
        switch (userinfo.getVIP()){
            case 1:
                mBtnTuig.setText("您已经是尊贵的VIP1了");
                break;
            case 2:
                mBtnTuig.setText("您已经是尊贵的VIP2了");
                break;
            case 3:
                mBtnTuig.setText("您已经是尊贵的VIP代理了");
                break;
        }
    }

    private int money=298;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rlyt_erweima:
                if (userinfo.getVIP()==0){
                    ToastUtil.toastLong(this,"您还不是VIP哦，需要开通才能获取推广码赚佣金呦！");
                    return;
                }
                startActivity(new Intent(mContext, ErweimActivity.class));
                break;
            case R.id.llyt_vip1:
                money=298;
                changeText(1);
                break;
            case R.id.llyt_vip2:
                money=1000;
                changeText(2);
                break;
            case R.id.llyt_vip3:
                money=50000;
                changeText(3);
                break;
            case R.id.btn_tuig:
                if (userinfo.getVIP()==0){
                    Intent intent=new Intent(TuiGuangActivity.this,TopUpActivity.class);
                    intent.putExtra("money",money);
                    startActivityForResult(intent,MYVIP);
                }else if(money!=298&&userinfo.getVIP()==1){
                    Intent intent=new Intent(TuiGuangActivity.this,TopUpActivity.class);
                    intent.putExtra("money",money);
                    startActivityForResult(intent,MYVIP);
                }else if (money!=298&&money!=1000&&userinfo.getVIP()==2){
                    Intent intent=new Intent(TuiGuangActivity.this,TopUpActivity.class);
                    intent.putExtra("money",money);
                    startActivityForResult(intent,MYVIP);
                }else{
                    ToastUtil.toastShort(this,"您已经是VIP了,不需要再次充值了！");
                }
                break;
        }
    }

    private LoginBean.DataBean.UserinfoBean userinfo;
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }


    private void changeText(int index) {
        mTvZw1.setBackgroundColor(getResources().getColor(R.color.huise));
        mTvZw2.setBackgroundColor(getResources().getColor(R.color.huise));
        mTvZw3.setBackgroundColor(getResources().getColor(R.color.huise));
        switch (index) {
            case 1:
                mTvZw1.setBackgroundColor(getResources().getColor(R.color.white));
                setSpannableString(mTvVipLook,"90天所有视频无限制免费观看",0,3);
                setSpannableStringTq(mTvVipTq,"永久享受推广特权。分别享受1至4级代理\n10%,5%,3%,2%的奖励金",0,2,19,32);
                break;
            case 2:
                mTvZw2.setBackgroundColor(getResources().getColor(R.color.white));
                setSpannableString(mTvVipLook,"永久所有视频无限制免费观看",0,2);
                setSpannableStringTq(mTvVipTq,"永久享受推广特权。分别享受1至5级代理\n30%,10%,5%,3%,2%的奖励金",0,2,19,36);
                break;
            case 3:
                mTvZw3.setBackgroundColor(getResources().getColor(R.color.white));
                setSpannableString(mTvVipLook,"永久所有视频无限制免费观看",0,3);
                setSpannableStringTq(mTvVipTq,"永久享受推广特权。伞下无限代新增业绩10%奖金+推广佣金30%,10%,5%,3%,2%(代理不拿代理伞下奖金)",0,2,18,21,28,44);
                break;
        }
        if (index>userinfo.getVIP()){
            mBtnTuig.setText("开通VIP推广赚取推广金");
        }else {
            mBtnTuig.setText("您已经是VIP了,不需要再次充值了");
        }
    }

    private void setSpannableString(TextView textView, String string, int i1, int i2) {
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        spannableString.setSpan(colorSpan, i1, i2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void setSpannableStringTq(TextView textView, String string, int i1, int i2,int i3,int i4) {
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        spannableString.setSpan(colorSpan, i1, i2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan2, i3, i4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void setSpannableStringTq(TextView textView, String string, int i1, int i2,int i3,int i4,int i5,int i6) {
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.main_bq_color));
        spannableString.setSpan(colorSpan, i1, i2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan2, i3, i4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan3, i5, i6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}

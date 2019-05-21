package com.zcf.bananavideohannan.activity.topup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.CommonlyBean;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.ObligationBean;
import com.zcf.bananavideohannan.bean.ZhifuBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.util.RxTimerUtil;
import com.zcf.bananavideohannan.util.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class ObligationActivity extends MyBaseActivity implements View.OnClickListener {

    private RelativeLayout mRlytBack;
    /**
     * 29:30
     */
    private TextView mTvTime;
    /**
     * 当前有一笔订单,需要您付款，总金额￥10000
     */
    private TextView mTvHint;
    /**
     * 金额：  5000元
     */
    private TextView mTvMoney;
    /**
     * 待付款
     */
    private TextView mTvState;
    /**
     * 3606221997110458282
     */
    private TextView mTvAccountNumber;
    /**
     * 联系收款方
     */
    private Button mBtnCallPhone;
    /**
     * 开户行：招商银行
     */
    private TextView mTvType;
    private TextView tv_erweima;
    /**
     * 账户名：张三
     */
    private TextView mTvName;
    /**
     * 我已付款
     */
    private Button mBtnSubmit;
    private long time;
    private ObligationBean bean;
    private ZhifuBean zhifubean;
    private LoginBean.DataBean.UserinfoBean userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligation);
        initView();
        String data = getIntent().getStringExtra("data");
        if (data!=null){
            bean = GsonUtil.parseJsonWithGson(data, ObligationBean.class);
            setStart_time(System.currentTimeMillis());
            mTvHint.setText("当前有一笔订单需要您付款，总金额￥"+bean.getMoney());
            if (!bean.getSupportpay().equals("3")){
                mTvType.setText("付款方式");
                if (bean.getSupportpay().equals("1")){
                    mTvAccountNumber.setText("微信");
                }else if (bean.getSupportpay().equals("2")){
                    mTvAccountNumber.setText("支付宝");
                }else {
                    mTvAccountNumber.setText("银行卡");
                }
            }
            if (bean.getOrder_status().equals("0")) {
                mTvState.setText("待付款");
            }else {
                mTvState.setText("待确认");
                mBtnSubmit.setVisibility(View.INVISIBLE);
                mTvState.setTextColor(getResources().getColor(R.color.green));
            }
            mTvMoney.setText("金额：  "+bean.getMoney()+"元");
        }else {
            getData();
        }

    }

    private boolean isOk=false;
    private void getData() {
        ToastUtils.show(this,"正在加载..");
        Disposable disposable=Network.getTopUpApi().getTopUp(userinfo.getToken())
                .compose(this.<CommonlyBean<ZhifuBean>>applySchedulers())
                .subscribe(new Consumer<CommonlyBean<ZhifuBean>>() {
                    @Override
                    public void accept(CommonlyBean<ZhifuBean> zhifuBeanCommonlyBean) throws Exception {
                        ToastUtils.dismiss();
                        if (zhifuBeanCommonlyBean.getCode()==200){
                            for (int i = 0; i < zhifuBeanCommonlyBean.getData().size(); i++) {
                                ZhifuBean zhifuBean = zhifuBeanCommonlyBean.getData().get(i);
                                if (zhifuBean.getOrder_status().equals("0")|zhifuBean.getOrder_status().equals("1")) {
                                    isOk=true;
                                    zhifubean=zhifuBean;
                                    setStart_time2(System.currentTimeMillis());
                                    initData();
                                }
                            }
                        }
                        if (!isOk){{
                            new AlertDialog.Builder(ObligationActivity.this)
                                    .setTitle("提示")
                                    .setMessage("暂无有效订单")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        }}
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void initData() {
        mTvHint.setText("当前有一笔订单需要您付款，总金额￥"+zhifubean.getMoney());
        if (!zhifubean.getSupportpay().equals("3")){
            mTvType.setText("付款方式");
            if (zhifubean.getSupportpay().equals("1")){
                mTvAccountNumber.setText("微信");
            }else if (zhifubean.getSupportpay().equals("2")){
                mTvAccountNumber.setText("支付宝");
            }else {
                mTvAccountNumber.setText("银行卡");
            }
        }
        if (zhifubean.getOrder_status().equals("0")) {
            mTvState.setText("待付款");
        }else {
            mTvState.setText("待确认");
            mBtnSubmit.setVisibility(View.INVISIBLE);
            mTvState.setTextColor(getResources().getColor(R.color.green));
        }
        mTvMoney.setText("金额：  "+zhifubean.getMoney()+"元");
    }

    private void initView() {
        mRlytBack = (RelativeLayout) findViewById(R.id.rlyt_back);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvHint = (TextView) findViewById(R.id.tv_hint);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mTvAccountNumber = (TextView) findViewById(R.id.tv_account_number);
        mBtnCallPhone = (Button) findViewById(R.id.btn_call_phone);
        mBtnCallPhone.setOnClickListener(this);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvName = (TextView) findViewById(R.id.tv_name);
        tv_erweima = (TextView) findViewById(R.id.tv_erweima);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mRlytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="";
                if (bean!=null){
                    url = bean.getPayurl();
                }else {
                    url=zhifubean.getPayurl();
                }
                ImageView imageView=new ImageView(ObligationActivity.this);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(500,500);
//                imageView.setLayoutParams(params);
                Glide.with(ObligationActivity.this).load(url).apply(new RequestOptions().placeholder(R.drawable.logo)).into(imageView);
                new AlertDialog.Builder(ObligationActivity.this)
                        .setTitle("支付码")
                        .setView(imageView)
                        .show();
            }
        });
    }

    private void setStart_time(final long timeMillis) {
        time = timeMillis/1000;
        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                long t=(time-bean.getCreate_time()+number)*1000;
                Date date = new Date(1800000-t);
                String format = simpleDateFormat.format(date);
                mTvTime.setText(format);
            }

        });
    }

    private void setStart_time2(final long timeMillis) {
        time = timeMillis/1000;
        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                long t=(time-zhifubean.getCreate_time()+number)*1000;
                Date date = new Date(1800000-t);
                String format = simpleDateFormat.format(date);
                mTvTime.setText(format);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call_phone:
                break;
            case R.id.btn_submit:
                final EditText editText=new EditText(this);
                new AlertDialog.Builder(this)
                        .setView(editText)
                        .setTitle("香蕉视频")
                        .setMessage("请输入支付账号姓名末位账号：")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=editText.getText().toString();
                                if (name.isEmpty()) {
                                    ToastUtil.toastShort(ObligationActivity.this,"请输入姓名...");
                                    return;
                                }
                                String preid="";
                                if (bean!=null) {
                                    preid=bean.getPreId();
                                }else {
                                    preid=zhifubean.getPreId();
                                }
                                Disposable disposable=Network.getTopUpApi().affirm(userinfo.getToken(),preid,name)
                                        .compose(MyBaseActivity.<ResponseBody>applySchedulers())
                                        .subscribe(new Consumer<ResponseBody>() {
                                            @Override
                                            public void accept(ResponseBody responseBody) throws Exception {
                                                JSONObject jsonObject=new JSONObject(responseBody.string());
                                                if (jsonObject.getInt("code")==200) {
                                                    ToastUtil.toastShort(ObligationActivity.this,"请求成功！");
                                                    mTvState.setText("待确认");
                                                    mTvState.setTextColor(getResources().getColor(R.color.green));
                                                    mBtnSubmit.setVisibility(View.INVISIBLE);
                                                }
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {

                                            }
                                        });
                            }
                        }).setNegativeButton("取消",null)
                        .show();
                break;
        }
    }
}

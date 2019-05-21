package com.zcf.bananavideohannan.activity.topup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.activity.my.TuiGuangActivity;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bean.TopUpBean;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class TopUpActivity extends MyBaseActivity implements View.OnClickListener {

    private RelativeLayout mRlytBack;
    /**
     * 充值记录
     */
    private TextView mTvRight;
    private EditText mEtMoney;
    private EditText mEtNumber;
    /**
     * 微信
     */
    private RadioButton mRbWx;
    /**
     * 支付宝
     */
    private RadioButton mRbAli;
    /**
     * 银行卡
     */
    private RadioButton mRbBankCard;
    /**
     * 充值
     */
    private Button mBtnSubmit;
    /**
     * 联系电话仅用于充值转账过程中沟通
     */
    private TextView mTvHint;
    private int money;
    private LoginBean.DataBean.UserinfoBean userinfo;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        money = getIntent().getIntExtra("money", 298);
        setContentView(R.layout.activity_top_up);
        initView();
    }

    private void initView() {
        mRlytBack = (RelativeLayout) findViewById(R.id.rlyt_back);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mEtMoney = (EditText) findViewById(R.id.et_money);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mRbWx = (RadioButton) findViewById(R.id.rb_wx);
        mRbAli = (RadioButton) findViewById(R.id.rb_ali);
        mRbBankCard = (RadioButton) findViewById(R.id.rb_bank_card);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mRlytBack.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
        mEtMoney.setOnClickListener(this);
        mEtNumber.setOnClickListener(this);
        mTvHint = (TextView) findViewById(R.id.tv_hint);
        mRbWx.setOnClickListener(this);
        mRbAli.setOnClickListener(this);
        mRbBankCard.setOnClickListener(this);
        mEtMoney.setText(String.valueOf(money));
        setSpannableString(mTvHint,mTvHint.getText().toString(),0,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_back:
                finish();
                break;
            case R.id.btn_submit:
                String phone=mEtNumber.getText().toString().trim();
                if (!Utils.isMobileNO(phone)|phone.isEmpty()){
                    ToastUtil.toastShort(this,"请输入正确的手机号！！");
                    return;
                }
                topup(phone);
                break;
            case R.id.tv_right:
                startActivity(new Intent(TopUpActivity.this,ObligationActivity.class));
                break;
        }
    }

    private void topup(String phone) {
        String type;
        if (mRbWx.isChecked()){
            type="1";
        }else if (mRbAli.isChecked()){
            type="2";
        }else {
            type="3";
        }
        disposable = Network.getTopUpApi().topup(userinfo.getToken(),String.valueOf(money),type,phone)
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        final JSONObject jsonObject=new JSONObject(responseBody.string());
                        int code = jsonObject.getInt("code");
                        if (code==200){
                            startActivity(new Intent(TopUpActivity.this,ObligationActivity.class));
                        }else if (code==100){
                            new AlertDialog.Builder(TopUpActivity.this)
                                    .setTitle("发起支付失败")
                                    .setMessage(jsonObject.getString("msg"))
                                    .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(TopUpActivity.this, ObligationActivity.class);
                                            try {
                                                intent.putExtra("data",jsonObject.getJSONObject("data").toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消支付",null)
                                    .show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(LoginBean.DataBean.UserinfoBean userinfo) {
        this.userinfo=userinfo;
    }


    private void setSpannableString(TextView textView, String string, int i1, int i2) {
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.red));
        spannableString.setSpan(colorSpan, i1, i2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.zcf.bananavideohannan.activity.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.util.LoadImageUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.network.Network;
import com.zcf.bananavideohannan.util.Utils;
import com.zcf.bananavideohannan.view.XCRoundImageView;

import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class VipActivity extends MyBaseActivity {
    private XCRoundImageView iv_head;
    private TextView tv_date;
    private ImageView iv_back;
    private EditText yzm;
    private EditText yqm;
    private TextView yzm_textA;
    private int randomsj;
    private Button duihuana;
    private Context context;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_vip);
        iv_head = findViewById(R.id.iv_head);
        tv_date = findViewById(R.id.tv_date);
        iv_back = findViewById(R.id.iv_back);
        yzm = findViewById(R.id.yzm);
        yqm = findViewById(R.id.yqm);
        yzm_textA = findViewById(R.id.yzm_text);
        duihuana = findViewById(R.id.duihuan);
        initviewa();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Random rand = new Random();
        randomsj = rand.nextInt(100);
        yzm_textA.setText(String.valueOf(randomsj));
        RxView.clicks(duihuana)
                .throttleFirst(3, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (yzm.getText().toString().isEmpty()) {
                            Toast.makeText(context, "请输入右侧验证码", Toast.LENGTH_SHORT).show();
                        } else if (yqm.getText().toString().isEmpty()) {
                            Toast.makeText(context, "请输入激活码", Toast.LENGTH_SHORT).show();
                        } else if (!yzm.getText().toString().equals(String.valueOf(randomsj))) {
                            Toast.makeText(context, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                        } else {
                            jhmget(yqm.getText().toString());
                        }
                    }
                });
        yqm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("zc", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("zc", "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("zc", "afterTextChanged: ");
                if(s.length()>=12){
                    duihuana.setBackground(getResources().getDrawable(R.drawable.btn_login2));
                }else {
                    duihuana.setBackground(getResources().getDrawable(R.drawable.btn_login));
                }
            }
        });
    }

    private void initviewa() {
        if (app != null) {
            if (app.getParam(ContextProperties.REM_USERMOBILE) != null) {
                String phone = Utils.getPhone(app.getParam(ContextProperties.REM_USERMOBILE).toString());
                tv_date.setText(phone);
            }
            if (app.getParam(ContextProperties.REM_HEAD_IMG) != null) {
                LoadImageUtil.loadImg(app.getParam(ContextProperties.REM_HEAD_IMG).toString(), iv_head, LoadImageUtil.getImageOptions(R.drawable.moren_headimg), R.drawable.moren_headimg);
            }
        }
    }

    private void jhmget(final String jhm) {
        if (jhm.length()<12){
            Toast.makeText(context, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
            return;
        }
        String token = DcAndroidContext.getInstance().getParam(ContextProperties.REM_TOKEN).toString();
        Disposable d=Network.getVideoApi().vip(token,jhm)
                .compose(this.<ResponseBody>applySchedulers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject=new JSONObject(responseBody.string());
                        if (jsonObject.getInt("code")==200){
                            Toast.makeText(context, "激活成功!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, "请检查网络连接，激活失败!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

package com.zcf.bananavideohannan.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.MLoginBean;
import com.zcf.bananavideohannan.bean.ResultSuccessBean;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.ToastViewDialog;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends MyBaseActivity {

    @ViewInject(R.id.edt_reg_name)
    private EditText edt_reg_name;

    @ViewInject(R.id.edt_reg_pwd)
    private EditText edt_reg_pwd;

    @ViewInject(R.id.edt_phone_code)
    private EditText edt_phone_code;

    @ViewInject(R.id.edt_wel_code)
    private EditText edt_wel_code;

    @ViewInject(R.id.tv_send_code)
    private TextView tv_send_code;

    private boolean isCodeing = false;// 是否验证码冷却中
    ToastViewDialog toastViewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastViewDialog = new ToastViewDialog(mContext, "请稍等...");
        tv_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });
    }

    String mobile;

    @Event(R.id.btn_register)
    private void register(View v) {
        mobile = edt_reg_name.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.toastShort(mContext, "请输入手机号");
            return;
        }
        String pwd = edt_phone_code.getText().toString().trim();
        if (TextUtils.isEmpty(edt_phone_code.getText().toString().trim())) {
            ToastUtil.toastShort(mContext, "请输入密码");
            return;
        }
//        String code = edt_reg_pwd.getText().toString().trim();
//        if (TextUtils.isEmpty(edt_reg_pwd.getText().toString().trim())) {
//            ToastUtil.toastShort(mContext, "请输入验证码");
//            return;
//        }
        final String refer = edt_wel_code.getText().toString().trim();
        toastViewDialog.show();
        OkGo.<String>post(ACTION_URL_REGISTER).tag(this).isMultipart(true)
                .params("mobile", mobile)
                .params("password", pwd)
//                .params("yzm", code)
                .params("refer", refer)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MLoginBean mLoginBean = GsonUtil.parseJsonWithGson(response.body(), MLoginBean.class);
                        if (mLoginBean.getCode()==200) {
                            ToastUtil.toastShort(mContext, "注册成功");
                            finish();
                        }else {
                            ToastUtil.toastShort(mContext, "注册失败--"+mLoginBean.getMsg());
                        }
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                        if (response != null) {
                            ToastUtil.toastShort(mContext, response.toString());
                        }
                        super.onError(response);
                    }
                });
    }

    private void sendCode() {
        if (isCodeing) {
            ToastUtil.toastShort(mContext, "请稍等再发送验证码");
            return;
        }

        String mobile = edt_reg_name.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.toastShort(mContext, "请输入手机号");
            return;
        }

        toastViewDialog.show();
        OkGo.<String>post(ACTION_URL_SMSCODE).tag(this).isMultipart(true)
                .params("mobile", mobile)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            ResultSuccessBean bean = GsonUtil.parseJsonWithGson(response.body(), ResultSuccessBean.class);
                            if (bean != null) {
                                if (RESULT_CODE_SUCCESS == bean.getCode()) {
                                    ToastUtil.toastShort(mContext, "发送成功");
                                    isCodeing = true;
                                    startSendCodeThread();
                                } else {
                                    if (!TextUtils.isEmpty(bean.getMsg())) {
                                        ToastUtil.toastShort(mContext, bean.getMsg());
                                    }
                                }
                            } else {
                                ToastUtil.toastShort(mContext, "发送失败");
                            }
                        }
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                        if (response != null) {
                            ToastUtil.toastShort(mContext, "发送失败");
                        }
                        super.onError(response);
                    }
                });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv_send_code.setText(String.valueOf(current_time) + "s");
            } else if (msg.what == 2) {
                tv_send_code.setText("获取验证码");
            }
        }
    };

    private int lessTime = 59;

    private int current_time;

    private void startSendCodeThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isCodeing) {
                    for (int i = lessTime; i > 0; i--) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (i <= 0) {
                            isCodeing = false;
                            handler.sendEmptyMessage(2);
                        }else {
                            current_time = i;
                            handler.sendEmptyMessage(1);
                        }

                    }
                }
            }
        });
        thread.start();
        thread = null;
    }

    @Override
    protected void onDestroy() {
        isCodeing = false;
        super.onDestroy();
    }
}

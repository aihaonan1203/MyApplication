package com.zcf.bananavideohannan.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaotai.baselib.util.CompleteQuit;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.ResultSuccessBean;
import com.zcf.bananavideohannan.domain.JsonResultObject;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.ToastViewDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_edit_pwd)
public class EditPwdActivity extends MyBaseActivity {

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    @ViewInject(R.id.edt_code1)
    private EditText edt_code1;

    @ViewInject(R.id.edt_code2)
    private EditText edt_code2;

    @ViewInject(R.id.edt_code3)
    private EditText edt_code3;

    @ViewInject(R.id.edt_code4)
    private EditText edt_code4;

    @ViewInject(R.id.edt_newpwd)
    private EditText edt_newpwd;

    @ViewInject(R.id.llyt_pwd)
    private LinearLayout llyt_pwd;

    @ViewInject(R.id.tv_send_code)
    private TextView tv_send_code;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastViewDialog = new ToastViewDialog(mContext, "请稍候...");
        if (app != null && app.getParam(ContextProperties.REM_USERMOBILE) != null) {
            phone = app.getParam(ContextProperties.REM_USERMOBILE).toString();
            tv_phone.setText("已向"+phone+"发送验证码");
        }
        llyt_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_code1.requestFocus();
            }
        });

        edt_code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edt_code2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edt_code3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edt_code4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    ToastViewDialog toastViewDialog;

    @Event(R.id.iv_back)
    private void goback(View view) {
        finish();
    }

    @Event(R.id.btn_enter)
    private void update(View view) {
        if (TextUtils.isEmpty(edt_newpwd.getText().toString().trim())) {
            ToastUtil.toastShort(mContext, "请输入新密码");
            return;
        }

        String code = edt_code1.getText().toString() + edt_code2.getText().toString() + edt_code3.getText().toString() + edt_code4.getText().toString();

        if (TextUtils.isEmpty(code) && code.length() != 4) {
            ToastUtil.toastShort(mContext, "验证码错误");
            return;
        }
        toastViewDialog.show();

        OkGo.<String>post(ACTION_URL_GET_CHANGE_PWD).tag(this).isMultipart(true)
                .params("mobile", phone)
                .params("mobilecode", code)
                .params("password", edt_newpwd.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            JsonResultObject bean = GsonUtil.parseJsonWithGson(response.body(), JsonResultObject.class);
                            if (bean != null) {
                                if (RESULT_CODE_SUCCESS == bean.getCode()) {
                                    ToastUtil.toastShort(mContext, "修改成功");
                                    isCodeing = true;

                                    ContextProperties.writeRemember(mContext, ContextProperties.REM_UID, "");
                                    ContextProperties.writeRemember(mContext, ContextProperties.REM_ACCOUNUT, "");
                                    ContextProperties.writeRemember(mContext, ContextProperties.REM_PASSWORD, "");
                                    ContextProperties.writeRemember(mContext, ContextProperties.REM_IS_LOGINED, ContextProperties.IS_LOGIN_TRUE_NO);

                                    CompleteQuit.getInstance().clearActivity();
                                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                }
                            } else {
                                ToastUtil.toastShort(mContext, "修改失败");
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
                            ToastUtil.toastShort(mContext, "修改失败");
                        }
                        super.onError(response);
                    }
                });
    }

    @Event(R.id.tv_send_code)
    private void sendCode(View view) {
        if (isCodeing) {
            ToastUtil.toastShort(mContext, "请稍等再发送验证码");
            return;
        }
        toastViewDialog.show();

        OkGo.<String>post(ACTION_URL_SMSCODE).tag(this).isMultipart(true)
                .params("mobile", phone)
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

    private boolean isCodeing = false;// 是否验证码冷却中

    private int lessTime = 59;

    private int current_time;

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
                        }
                        current_time = i;
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
        thread.start();
        thread = null;
    }
}

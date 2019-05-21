package com.zcf.bananavideohannan.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.MainActivity;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.ContextProperties;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.bean.LoginBean;
import com.zcf.bananavideohannan.bll.LoginBll;
import com.zcf.bananavideohannan.util.GsonUtil;
import com.zcf.bananavideohannan.view.ToastViewDialog;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login)
public class LoginActivity extends MyBaseActivity
//        implements Consts, View.OnClickListener
{
    @ViewInject(R.id.edt_name)
    private EditText edt_name;

    @ViewInject(R.id.edt_pwd)
    private EditText edt_pwd;

//    @ViewInject(R.id.tv_register)
//    private TextView tv_register;

    @ViewInject(R.id.btn_login)
    private Button btn_login;

//    @ViewInject(R.id.rb_userlogin)// 账号登录
//    private RadioButton rb_userlogin;

//    @ViewInject(R.id.rb_phoneLogin)// 手机号登录
//    private RadioButton rb_phoneLogin;

    @ViewInject(R.id.llyt_user_login)// 账号登录区
    private LinearLayout llyt_user_login;

    @ViewInject(R.id.llyt_smscode_login)// 手机登录区
    private LinearLayout llyt_smscode_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastViewDialog = new ToastViewDialog(mContext, "登录中...");
    }

    private void setListeners() {
//        rb_userlogin.setOnClickListener(this);
//        rb_phoneLogin.setOnClickListener(this);
    }

    @Event(R.id.btn_login)
    private void gotologin(View v) {
        login();
    }

    private void login() {
        String userName = edt_name.getText().toString();
        String pwd = edt_pwd.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.toastShort(mContext, "请输入账号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.toastShort(mContext, "请输入密码");
            return;
        }

        httpLogin(userName, pwd);
    }
    ToastViewDialog toastViewDialog;
    private void httpLogin(final String account, final String pwd) {
        toastViewDialog.show();
        OkGo.<String>post(Consts.ACTION_URL_LOGIN).tag(this).isMultipart(true)
                .params("account", account)
                .params("password", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (200 == response.code()) {
                                LoginBean bean = GsonUtil.parseJsonWithGson(response.body(), LoginBean.class);
                                if (bean != null) {
                                    if (Consts.RESULT_CODE_SUCCESS == bean.getCode()) {
                                        // 登录成功
                                        ContextProperties.remeberCache(mContext, account, pwd, String.valueOf(bean.getData().getUserinfo().getId()));
//                                        getPerInfo(String.valueOf(bean.getData().getUserinfo().getId()));
                                        app.setParam(USER_ISREFRESH_PERSONINFO, USER_FRESH_NO);
                                        LoginBean.DataBean.UserinfoBean userinfo = bean.getData().getUserinfo();
                                        if (userinfo != null) {
                                            ContextProperties.writeProperties(mContext, userinfo);
                                            EventBus.getDefault().postSticky(userinfo);
                                        }
                                        startActivity(new Intent(mContext, MainActivity.class));
                                        finish();
                                    } else {
                                        if (!TextUtils.isEmpty(bean.getMsg())) {
                                            ToastUtil.toastShort(mContext, bean.getMsg());
                                        }
                                        ToastUtil.toastShort(mContext, "用户名或密码错误");
                                        ContextProperties.clearRem(mContext);
                                    }
                                }
                            } else {
                                ToastUtil.toastShort(mContext, "用户名或密码错误");
                            }
                        } catch (Exception e) {
                            ToastUtil.toastShort(mContext, "用户名或密码错误");
                            ContextProperties.clearRem(mContext);
                            ProgressDialogUtil.dismiss();
                            e.printStackTrace();
                        }
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtil.toastShort(mContext, "请检查您的网络是否可用");
                        ContextProperties.clearRem(mContext);
                        if (toastViewDialog != null) {
                            toastViewDialog.dismiss();
                        }
                        super.onError(response);
                    }
                });
    }

    private void getPerInfo(final String uid) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginBll loginBll = new LoginBll(mContext);
                    loginBll.getPersonInfo(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread = null;
    }

    @Event(R.id.btn_goto_register)
    private void gotoRegister(View v) {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }

    @Event(R.id.tv_find_pwd)
    private void gotofindpwd(View v) {
        startActivity(new Intent(mContext, FindPwdActivity.class));
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.rb_phoneLogin:
//                llyt_user_login.setVisibility(View.VISIBLE);
//                llyt_smscode_login.setVisibility(View.GONE);
//                break;
//            case R.id.rb_userlogin:
//                llyt_user_login.setVisibility(View.GONE);
//                llyt_smscode_login.setVisibility(View.VISIBLE);
//                break;
//        }
//    }
}

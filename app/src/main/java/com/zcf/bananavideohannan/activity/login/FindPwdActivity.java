package com.zcf.bananavideohannan.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.Consts;
import com.zcf.bananavideohannan.base.MyBaseActivity;
import com.zcf.bananavideohannan.domain.JsonResultObject;
import com.zcf.bananavideohannan.util.GsonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 找回密码
 */
@ContentView(R.layout.activity_find_pwd)
public class FindPwdActivity extends MyBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.llyt_input_phone)
    private LinearLayout llyt_input_phone;

    @ViewInject(R.id.llyt_input_sms_newpwd)
    private LinearLayout llyt_input_sms_newpwd;

    @ViewInject(R.id.edt_find_phone)
    private EditText edt_find_phone;

    @ViewInject(R.id.edt_find_smsCode)
    private EditText edt_find_smsCode;

    @ViewInject(R.id.edt_find_newpwd)
    private EditText edt_find_newpwd;

    @ViewInject(R.id.btn_next)
    private Button btn_next;

    @ViewInject(R.id.rlyt_get_smscode)
    private RelativeLayout rlyt_get_smscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListeners();
    }

    private void setListeners() {
        btn_next.setOnClickListener(this);
        rlyt_get_smscode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (llyt_input_phone.getVisibility() == View.VISIBLE && llyt_input_sms_newpwd.getVisibility() == View.GONE) {
                    llyt_input_phone.setVisibility(View.GONE);
                    llyt_input_sms_newpwd.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rlyt_get_smscode:
                // TODO: 2018/12/5  发送验证码
                requestSendSmsCode();

                break;
        }
    }

    /**
     * 发送验证码
     */
    private void requestSendSmsCode() {
        String phone = edt_find_phone.getText().toString();
        OkGo.<String>post(Consts.ACTION_URL_SMSCODE).tag(this).isMultipart(true)
                .params("mobile", phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (200 == response.code()) {
                            JsonResultObject jsonObj = GsonUtil.parseJsonWithGson(response.body(), JsonResultObject.class);
                            if (RESULT_CODE_SUCCESS == jsonObj.getCode()) {
                                // 发送成功 UI倒计时

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}

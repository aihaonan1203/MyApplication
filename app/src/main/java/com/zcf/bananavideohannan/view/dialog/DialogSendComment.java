package com.zcf.bananavideohannan.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;

public class DialogSendComment extends AlertDialog {
    private ImageView iv_close;
    private Button btn_send;
    private EditText edt_input;
    private TextView tv_input_num;
    private Context mContext;
    private int height;// 设置上边距
    private LinearLayout llyt_main;
    private final int BOND = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BOND:
                    InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
            }
        }

    };

    public DialogSendComment(Context context, int height) {
        super(context);
        this.mContext = context;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_send_comment);

        iv_close = findViewById(R.id.iv_close);
        btn_send = findViewById(R.id.btn_send);
        edt_input = findViewById(R.id.edt_input);
        llyt_main = findViewById(R.id.llyt_main);
        tv_input_num = findViewById(R.id.tv_input_num);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        edt_input.requestFocus();
        TextWatcher edtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                int length = 0;
                if (!TextUtils.isEmpty(input) && input.length() > 0) {
                    length = input.length();
                    if (length >= 50) {
                        ToastUtil.toastShort(mContext, "已达到最大字数输入限制");
                    }
                }
                tv_input_num.setText("" + length + "/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        edt_input.addTextChangedListener(edtWatcher);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_input.getText().toString().trim())) {
                    ToastUtil.toastShort(mContext, "评论内容不能为空");
                    return;
                }

                if (iSendComment != null) {
                    dismiss();
                    iSendComment.onClickSendComment(edt_input.getText().toString());
                }
            }
        });


        Window window = getWindow();
        window.setGravity(Gravity.CENTER);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llyt_main.getLayoutParams();
        params.setMargins(0, height, 0, 0);//4个参数按顺序分别是左上右下
        llyt_main.setLayoutParams(params);

        showSoftInputView();
    }

    private ISendComment iSendComment;

    public void setiSendComment(ISendComment iSendComment) {
        this.iSendComment = iSendComment;
    }

    public interface ISendComment {
        void onClickSendComment(String comment);
    }


    /**
     * 显示软键盘
     */
    private void showSoftInputView() {
        handler.sendEmptyMessageDelayed(BOND, 100);
    }
}

package com.zcf.bananavideohannan.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;

public class ToastViewDialog extends AlertDialog {
    private Context mContext;
    String text;
    private TextView tvmsg;

    public ToastViewDialog(Context context, String text) {
        super(context, R.style.DialogStyle1);
        this.mContext = context;
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        this.setCanceledOnTouchOutside(false);
        this.setView(new EditText(mContext));
        tvmsg = (TextView) findViewById(R.id.tv_dialog_message);
        tvmsg.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            tvmsg.setVisibility(View.VISIBLE);
            tvmsg.setText(text);
        }
    }
}

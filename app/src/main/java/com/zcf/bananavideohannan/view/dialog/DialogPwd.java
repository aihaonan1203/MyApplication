package com.zcf.bananavideohannan.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;

/**
 * 删除-底部弹出dialog
 */
public class DialogPwd extends AlertDialog implements View.OnClickListener {
    private Activity mContext;

    public DialogPwd(Activity context) {
        super(context);
        this.mContext = context;
    }

    private EditText edt_nickname;
    private TextView tv_cancle;
    private TextView tv_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_pwd);

        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        edt_nickname = findViewById(R.id.edt_nickname);
        tv_cancle = findViewById(R.id.tv_cancle);
        tv_enter = findViewById(R.id.tv_enter);
        tv_cancle.setOnClickListener(this);
        tv_enter.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
//        /**
////         * 设置宽度全屏，要设置在show的后面
////         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity=Gravity.CENTER;
        layoutParams.width= LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height= LinearLayout.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
              dismiss();
                if (onEditName != null) {
                    onEditName.onCancle(edt_nickname.getText().toString());
                }
                break;
            case R.id.tv_enter:
                if (onEditName != null) {
                    onEditName.onAffirm(edt_nickname.getText().toString());
                }
                break;
        }
    }

    onClick onEditName;

    public void setOnClick(onClick onEditName) {
        this.onEditName = onEditName;
    }

    public interface onClick {
        void onAffirm(String name);
        void onCancle(String name);
    }

}

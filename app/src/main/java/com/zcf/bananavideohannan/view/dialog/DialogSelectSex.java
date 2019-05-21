package com.zcf.bananavideohannan.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zcf.bananavideohannan.R;

/**
 * 删除-底部弹出dialog
 */
public class DialogSelectSex extends AlertDialog implements View.OnClickListener {
    private Activity mContext;

    public DialogSelectSex(Activity context) {
        super(context);
        this.mContext = context;
    }

    private TextView tv_nan;
    private TextView tv_nv;
    private Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_sex);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        tv_nan = findViewById(R.id.tv_nan);
        tv_nv = findViewById(R.id.tv_nv);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(this);
        tv_nan.setOnClickListener(this);
        tv_nv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nan:
                if (clickOpenPicSelect != null) {
                    clickOpenPicSelect.onSelectSex(tv_nan.getText().toString());
                }
                break;
            case R.id.tv_nv:
                if (clickOpenPicSelect != null) {
                    clickOpenPicSelect.onSelectSex(tv_nv.getText().toString());
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public void setClickOpenPicSelect(onClickOpenPicSelect clickOpenPicSelect) {
        this.clickOpenPicSelect = clickOpenPicSelect;
    }

    onClickOpenPicSelect clickOpenPicSelect;

    public interface onClickOpenPicSelect {
        void onSelectSex(String sex);
    }

}

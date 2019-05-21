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
public class DialogSelectPic extends AlertDialog implements View.OnClickListener {
    private Activity mContext;

    public DialogSelectPic(Activity context) {
        super(context);
        this.mContext = context;
    }

    private TextView tv_camera;
    private TextView tv_picselect;
    private Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_pic);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);// 设置dialog显示的位置
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setDimAmount(0);

        tv_camera = findViewById(R.id.tv_camera);
        tv_picselect = findViewById(R.id.tv_picselect);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(this);
        tv_picselect.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_camera:
                if (clickOpenPicSelect != null) {
                    clickOpenPicSelect.openCamera();
                }
                break;
            case R.id.tv_picselect:
                if (clickOpenPicSelect != null) {
                    clickOpenPicSelect.openPicSelect();
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
        void openPicSelect();

        void openCamera();
    }

}

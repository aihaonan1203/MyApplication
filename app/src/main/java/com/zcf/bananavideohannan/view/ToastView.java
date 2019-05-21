package com.zcf.bananavideohannan.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zcf.bananavideohannan.R;

public class ToastView {
    private static ToastView toastview;

    private Toast toast;

    private ToastView() {

    }

    public static ToastView createToastConfig() {
        if (toastview == null) {
            toastview = new ToastView();
        }
        return toastview;
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param message 内容
     */
    public void showMessage(Context context, String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toastview, null);
        layout.getBackground().setAlpha(150);
        TextView text = (TextView) layout.findViewById(R.id.tv_message);
        text.setText(message);
        //text.setTextColor(R.color.aqua);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}

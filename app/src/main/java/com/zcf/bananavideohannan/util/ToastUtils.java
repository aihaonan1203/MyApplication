package com.zcf.bananavideohannan.util;

import android.content.Context;

import com.zcf.bananavideohannan.view.ToastViewDialog;

public class ToastUtils {
    private static ToastViewDialog toastViewDialog;

    /**
     * 显示
     *
     * @param context
     * @param message    内容
     */
    public static void show(Context context, String message) {
        if (toastViewDialog != null)
            toastViewDialog.dismiss();

        toastViewDialog = new ToastViewDialog(context, message);
        toastViewDialog.show();
    }

    /**
     * 关闭显示
     * <p>The dismiss</p>
     */
    public static void dismiss() {
        try {
            if (toastViewDialog != null) {
                toastViewDialog.dismiss();
                toastViewDialog = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

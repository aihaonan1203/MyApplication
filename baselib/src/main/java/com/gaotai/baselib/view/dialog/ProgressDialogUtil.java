package com.gaotai.baselib.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * ProgressDialog 显示
 *
 * @author mengliang
 * @version 1.0
 */
public class ProgressDialogUtil {
    private static ProgressDialog progressDialog;

    /**
     * 显示
     *
     * @param context
     * @param message    内容
     * @param cancelable 设置是否可以通过点击Back键取消
     */
    public static void show(Context context, String message, boolean cancelable) {
        if (progressDialog != null)
            ProgressDialogUtil.dismiss();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static ProgressDialog showDialog(Context context, String message, boolean cancelable) {
        if (progressDialog != null)
            ProgressDialogUtil.dismiss();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 更改显示内容
     *
     * @param message 内容文字
     */
    public static void setMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    /**
     * 关闭显示
     * <p>The dismiss</p>
     */
    public static void dismiss() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

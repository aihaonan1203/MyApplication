package com.zcf.bananavideohannan.activity.my;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaotai.baselib.DcAndroidContext;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;
import com.gaotai.baselib.view.dialog.ToastUtil;
import com.zcf.bananavideohannan.R;
import com.zcf.bananavideohannan.base.MyBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import static com.gaotai.baselib.util.AndroidUtil.getFormatSize;

@ContentView(R.layout.activity_settins)
public class SettinsActivity extends MyBaseActivity {
    @ViewInject(R.id.tv_chache_mb)
    private TextView tv_chache_mb;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 8:
                    ProgressDialogUtil.dismiss();
                    ToastUtil.toastShort(mContext, "缓存清理成功！");
                    tv_chache_mb.setText(getFormatSize(0.0));
                    break;
                case 9:
                    ProgressDialogUtil.dismiss();
                    ToastUtil.toastShort(mContext, "清理异常，请稍候重试！");
                    break;
            }
        }

        ;
    };
    private RelativeLayout rlytCallWe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        showCacheSize();
        rlytCallWe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=DcAndroidContext.getInstance().getParam("contact").toString();
                if (phone.isEmpty()){
                    return;
                }
                AlertDialog.Builder dialog=new AlertDialog.Builder(SettinsActivity.this);
                dialog.setMessage("电话号码:"+phone);
                dialog.setTitle("联系我们");
                dialog.setPositiveButton("复制到剪贴板", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        //创建ClipData对象
                        ClipData clipData = ClipData.newPlainText("tahome text copy",DcAndroidContext.getInstance().getParam("contact").toString());
                        //添加ClipData对象到剪切板中
                        clipboardManager.setPrimaryClip(clipData);
                        ToastUtil.toastShort(mContext, "已复制");
                    }
                });
                dialog.show();
            }
        });
    }

    private void showCacheSize() {
        double cacheSize = 0.0;
        cacheSize += getFolderSize(mContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(mContext.getExternalCacheDir());
        }
        cacheSize += getFolderSize(new File("/data/data/com.zcf.bananavideo/webcache"));
        cacheSize += getFolderSize(new File("/data/data/com.zcf.bananavideo/webviewCache"));
        String cacheSizeTxt = getFormatSize(cacheSize);
        tv_chache_mb.setText(cacheSizeTxt);
    }

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList == null) {
                return size;
            }
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    @Event(R.id.iv_back)
    private void finish(View v) {
        finish();
    }

    @Event(R.id.rlyt_accountManager)
    private void gotoUserManager(View v) {
        startActivity(new Intent(mContext, AccountManagerActivity.class));
    }

    @Event(R.id.rlyt_clear_cache)
    private void clearCache(View v) {
        ProgressDialogUtil.show(mContext, "清理中,请稍候...", false);
        new Thread() {
            public void run() {
                try {
                    File file = mContext.getCacheDir();
                    //清除本应用内部缓存
                    deleteFilesByDirectory(file);
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        deleteFilesByDirectory(mContext.getExternalCacheDir());
                    }

                    File appCacheDir = new File("/data/data/com.zcf.bananavideo/webcache");
                    File webviewCacheDir = new File("/data/data/com.zcf.bananavideo/webviewCache");
                    //删除webview 缓存目录
                    if (webviewCacheDir.exists()) {
                        deleteFilesByDirectory(webviewCacheDir);
                    }
                    //删除webview 缓存 缓存目录
                    if (appCacheDir.exists()) {
                        deleteFilesByDirectory(appCacheDir);
                    }
                    handler.sendEmptyMessage(8);
                } catch (Exception ex) {
                    handler.sendEmptyMessage(9);
                }
            }
        }.start();
    }

    @Event(R.id.rlyt_check_update)
    private void checkUpdate(View v) {
        ToastUtil.toastShort(mContext, "检查更新");
    }

    @Event(R.id.rlyt_user_info)
    private void userInfo(View v) {
        ToastUtil.toastShort(mContext, "用户协议");
    }


    /**
     * 删除缓存文件
     *
     * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        try {
            if (directory != null && directory.exists() && directory.isDirectory()) {
                for (File item : directory.listFiles()) {
                    item.delete();
                }
            }
        } catch (Exception ex) {
        }
    }

    @Event(R.id.rlyt_back)
    private void goback(View view) {
        finish();
    }

    private void initView() {
        rlytCallWe = findViewById(R.id.rlyt_call_we);
    }
}

package com.gaotai.baselib.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.download.DownloadFileManager;
import com.gaotai.baselib.download.Request;
import com.gaotai.baselib.download.TaskDBManager;
import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.smack.XmppUtil;
import com.gaotai.baselib.util.AndroidUtil;
import com.gaotai.baselib.util.InstallApkUtils;
import com.gaotai.baselib.util.MD5Generator;
import com.gaotai.baselib.view.UpdateVersion;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

/**
 * 自动更新并 静默安装
 */
public class XutilsUpdateVersionInstallUtil {

    /**
     * 下载进度条
     */
    ProgressDialog progressDialog;
    /**
     * APK 服务器URL 路径
     */
    private String apkPathServer = "";
    /**
     * APK 版本号
     */
    private String lastestVersion = "";
    /**
     * APK 本地文件路径
     */
    private String apkFilePath = "";
    /**
     * 说明
     */
    private String versionDesc = "";
    /**
     * 更新eHandler
     */
    private UpdateHandler mHandler;

    private Activity myactivity;
    /**
     * 下载日志tag
     */
    private static final String TAG = "zhxy download";
    /**
     * 版本获取地址
     */
    private String versionServletAddress = "";
    /**
     * apk下载地址
     */
    public String apkAddress = "";

    /**
     * @param _myactivity
     * @param versionServletAddress 版本获取地址
     * @param apkAddress            apk下载地址 前缀
     */
    public XutilsUpdateVersionInstallUtil(Activity _myactivity, String versionServletAddress, String apkAddress) {
        this.myactivity = _myactivity;
        TaskDBManager.init(this.myactivity, false, "task.db");
        this.versionServletAddress = versionServletAddress;
        this.apkAddress = apkAddress;
    }

    UpdateTaskListener updateTaskListener;

    /**
     * 设置更新状态监听
     */
    public void registUpdateListener(UpdateTaskListener updateTaskListener) {
        this.updateTaskListener = updateTaskListener;
    }

    /**
     * 如是最新版是否显示文字提示
     */
    private boolean isShowText = false;

    /**
     * 获取更新并显示下载弹出框
     *
     * @param isshow 如是最新版是否显示文字提示
     */
    public void getUpdateActiviy(boolean isshow) {

        if (AndroidUtil.isHasNetWork(myactivity)) {
            isShowText = isshow;
            //创建消息循环
            mHandler = new UpdateHandler();
            //获取当前安装包版本
            String version = AndroidUtil.getVerName(myactivity);
            //启动获取版本文件线程
            UpdateVersion updateVersion = new UpdateVersion(mHandler, version, versionServletAddress);
            updateVersion.start();
        } else {
            if (isShowText) {
                Toast.makeText(myactivity, "请先打开网络,否则无法正常使用软件", Toast.LENGTH_LONG).show();
            }
            if (updateTaskListener != null) {
                updateTaskListener.onNoUpdate();
            }
        }
    }

    class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (myactivity.isFinishing()) {// 如果已结束,则不再弹更新对话框进行升级
                return;
            }
            Bundle data = msg.getData();

            switch (msg.what) {
                case DcAndroidConsts.down_file:
                    String errorMsg = data.getString("errorMsg");
                    if (!TextUtils.isEmpty(errorMsg)) {
                        Toast.makeText(myactivity, errorMsg,
                                Toast.LENGTH_LONG).show();
                        if (updateTaskListener != null) {
                            updateTaskListener.onNoUpdate();
                        }
                        break;
                    }

                    apkPathServer = data.getString("apkUrl");
                    lastestVersion = data.getString("lastestVersion");
                    versionDesc = data.getString("versionDesc");
                    if (versionDesc.equals("")) {
                        versionDesc = "发现新版本，请更新！";
                    }

                    progressDialog = new ProgressDialog(myactivity); // 创建进度对话框
                    progressDialog.setMax(100); // 进度最大值
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setTitle("软件更新"); // 设置标题
                    progressDialog.setCancelable(false); // 进度条不能用回退按钮关闭
                    progressDialog.incrementProgressBy(-progressDialog.getProgress());
                    progressDialog.show();

                    //下载地址
                    String url = apkAddress;
                    apkPathServer = apkPathServer.replace("\\", "/");
                    url += apkPathServer;

                    //创建目录
                    File file = new File(DcAndroidConsts.APK_PATH);
                    if (!file.exists())
                        file.mkdirs();
                    apkFilePath = DcAndroidConsts.APK_PATH + lastestVersion + ".apk";

                    downFile(url, apkFilePath);
                    break;

                case DcAndroidConsts.down_error:
                    Toast.makeText(myactivity, "系统更新异常，未能自动更新",
                            Toast.LENGTH_LONG).show();
                    try {
                        progressDialog.dismiss();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (updateTaskListener != null) {
                        updateTaskListener.onNoUpdate();
                    }
                case DcAndroidConsts.down_cancle:
                    if (updateTaskListener != null) {
                        updateTaskListener.onNoUpdate();
                    }
                    if (isShowText) {
                        Toast.makeText(myactivity, "当前是最新版本，无需更新", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * 下载文件
     */
    public void downFile(final String downUrl, final String filePath) {
        final RequestParams requestParams = new RequestParams(downUrl);
        requestParams.setSaveFilePath(filePath);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LOG.i("下载APK", "totao==>" + total + ",current==>" + current);
                Log.d(TAG, String.format("onTaskRunning() [%d,%d]", current, total));
                progressDialog.setMax((int) total / 1000);
                int progress = (int) (current / total);
                progressDialog.setProgress((int) current / 1000);
                progressDialog.incrementProgressBy(progress);
            }

            @Override
            public void onSuccess(File result) {
                Log.d(TAG, "onTaskFinished()");
                try {
                    progressDialog.dismiss();
                    if (!AndroidUtil.isHasSDCard()) {
                        chmodRw(apkFilePath);
                    }
                    if (updateTaskListener != null) {
                        updateTaskListener.onUpdate(apkFilePath);
                    }
                    LOG.i("下载APK", "安装中，请稍后！");
                    Toast.makeText(myactivity, "安装中，请稍后！", Toast.LENGTH_LONG).show();
                    InstallApkUtils.installAndStartApk(myactivity, apkFilePath);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (updateTaskListener != null) {
                        updateTaskListener.onUpdateError();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onTaskError()");
                Toast.makeText(myactivity, "自动更新失败,请稍后重试！",
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                if (updateTaskListener != null) {
                    updateTaskListener.onUpdateError();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * android 系统文件
     *
     * @param url 文件路径
     */
    private void chmodRw(String url) {
        try {
            // [文件夹705:drwx---r-x]
            String[] args1 = {
                    "chmod",
                    "705", DcAndroidConsts.APK_PATH};
            Runtime.getRuntime().exec(args1);
            // [文件604:-rw----r--]
            String[] args2 = {"chmod", "604", url};
            Runtime.getRuntime().exec(args2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装更新监听
     */
    public interface UpdateTaskListener {
        /**
         * 开始更新安装
         */
        void onUpdate(String apkPath);

        /**
         * 无需更新 或者 没有进行更新操作
         */
        void onNoUpdate();

        /**
         * 更新失败
         */
        void onUpdateError();
    }

}

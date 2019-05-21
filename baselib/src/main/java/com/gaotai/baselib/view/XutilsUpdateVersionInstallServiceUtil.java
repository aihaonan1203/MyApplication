package com.gaotai.baselib.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.download.DownloadFileManager;
import com.gaotai.baselib.download.Request;
import com.gaotai.baselib.download.TaskDBManager;
import com.gaotai.baselib.util.AndroidUtil;
import com.gaotai.baselib.util.DcDateUtils;
import com.gaotai.baselib.util.InstallApkUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

/**
 * 自动更新并 静默安装
 * 可设置 UpdateTaskListener  进行监听状态
 */
public class XutilsUpdateVersionInstallServiceUtil {
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

    private Context context;
    /**
     * 下载日志tag
     */
    private static final String TAG = "dzbp service download";

    /**
     * 版本获取地址
     */
    private String versionServletAddress = "";
    /**
     * apk下载地址
     */
    public String apkAddress = "";

    /**
     * @param context
     * @param versionServletAddress 版本获取地址
     * @param apkAddress            apk下载地址 前缀
     */
    public XutilsUpdateVersionInstallServiceUtil(Context context, String versionServletAddress, String apkAddress) {
        this.context = context.getApplicationContext();
        TaskDBManager.init(context, false, "task.db");
        this.versionServletAddress = versionServletAddress;
        this.apkAddress = apkAddress;
    }

    XutilsUpdateVersionInstallUtil.UpdateTaskListener updateTaskListener;

    /**
     * 获取更新 后台自动下载安装
     */
    public void getUpdateActiviy() {
        try {
            if (AndroidUtil.isHasNetWork(context)) {
                //创建消息循环
                mHandler = new UpdateHandler();
                //获取当前安装包版本
                String version = AndroidUtil.getVerName(context);
                //启动获取版本文件线程
                UpdateVersion updateVersion = new UpdateVersion(mHandler, version, versionServletAddress);
                updateVersion.start();
            } else {
                if (updateTaskListener != null) {
                    updateTaskListener.onNoUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置更新状态监听
     */
    public void registUpdateListener(XutilsUpdateVersionInstallUtil.UpdateTaskListener updateTaskListener) {
        this.updateTaskListener = updateTaskListener;
    }

    class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            try {
                Bundle data = msg.getData();

                switch (msg.what) {
                    case DcAndroidConsts.down_file:
                        apkPathServer = data.getString("apkUrl");
                        lastestVersion = data.getString("lastestVersion");
                        versionDesc = data.getString("versionDesc");
                        if (versionDesc.equals("")) {
                            versionDesc = "发现新版本，请更新！";
                        }

                        //下载地址
                        String url = apkAddress;
                        apkPathServer = apkPathServer.replace("\\", "/");
                        url += apkPathServer;

                        //创建目录
                        File file = new File(DcAndroidConsts.APK_PATH);
                        if (!file.exists())
                            file.mkdirs();
                        apkFilePath = DcAndroidConsts.APK_PATH + lastestVersion + DcDateUtils.toString(DcDateUtils.now(), DcDateUtils.FORMAT_YMD_3) + ".apk";

                        Request request = new Request();
                        request.requestUrl = url;
                        request.dstFilePath = apkFilePath;

                        downFile(url, apkFilePath);

                        break;

                    case DcAndroidConsts.down_error:
                        if (updateTaskListener != null) {
                            updateTaskListener.onNoUpdate();
                        }
                    case DcAndroidConsts.down_cancle:
                        if (updateTaskListener != null) {
                            updateTaskListener.onNoUpdate();
                        }
                        break;
                    default:
                        return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
                Log.d(TAG, String.format("onLoading() [%d,%d]", current, total));
            }

            @Override
            public void onSuccess(File result) {
                Log.d(TAG, "onSuccess()");
                try {
                    if (!AndroidUtil.isHasSDCard()) {
                        chmodRw(apkFilePath);
                    }
                    if (updateTaskListener != null) {
                        updateTaskListener.onUpdate(apkFilePath);
                    }
                    InstallApkUtils.installAndStartApk(context, apkFilePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (updateTaskListener != null) {
                        updateTaskListener.onUpdateError();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError()");
                if (updateTaskListener != null) {
                    updateTaskListener.onUpdateError();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (updateTaskListener != null) {
                    updateTaskListener.onUpdateError();
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
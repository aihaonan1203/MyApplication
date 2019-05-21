package com.gaotai.baselib.version;

import org.xutils.http.RequestParams;
import org.xutils.x;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.gaotai.baselib.DcAndroidConsts;

import fastjson.JSON;
import fastjson.JSONObject;

public class UpdateVersionApkPatch extends Thread {
    private Handler mHandler;

    /**
     * 当前版本versionName
     */
    private static String mLocalVersionName;

    /**
     * 当前版本MD5
     */
    private static String mCurrentApkMd5;

    /**
     * 版本获取地址
     */
    private String versionServletAddress = "";
    /**
     * 版本类别
     */
    private String appType = "";

    public UpdateVersionApkPatch(Handler handler, String localVersionName, String currentApkMd5, String appType, String versionServletAddress) {
        mHandler = handler;
        this.mLocalVersionName = localVersionName;
        this.mCurrentApkMd5 = currentApkMd5;
        this.versionServletAddress = versionServletAddress;
        this.appType = appType;
    }


    @Override
    public void run() {
        try {
            getVersion();
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
        }
    }

    void getVersion() throws Exception {
        String lastestVersion = "";// 最新版本号
        String apkPathServer = ""; // apk下载地址
        String versionDesc = "";// 版本更新说明
        String newFileMd5 = "";// 最新版本apk MD5校验值
        String patchPath = "";// 差分包下载地址
        String isupdateapk = "1";

        UpdateVersionDomain updateVersionDomain = getLastestVersion();
        if (updateVersionDomain != null) {
            apkPathServer = updateVersionDomain.getFilePath();
            lastestVersion = updateVersionDomain.getVersion();
            versionDesc = updateVersionDomain.getVersionDesc();
            newFileMd5 = updateVersionDomain.getVersionCode();
            patchPath = updateVersionDomain.getFilesPatch();
            isupdateapk = updateVersionDomain.getIsupdateapk();

            //如果为空 跳过更新
            if (lastestVersion.equals("null") || lastestVersion.equals("")) {
                mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
                return;
            }

            boolean isdown = true;
            if (lastestVersion.trim().equals(mLocalVersionName.trim())) {
                isdown = false;
            } else {
                try {
                    //验证高低版本
                    String[] strlast = lastestVersion.split("\\.");
                    String[] strmLocal = mLocalVersionName.split("\\.");
                    if (strlast.length == 3 && strlast.length == strmLocal.length) {
                        int local0 = Integer.parseInt(strmLocal[0].toString());
                        int local1 = Integer.parseInt(strmLocal[1].toString());
                        int local2 = Integer.parseInt(strmLocal[2].toString());

                        int strlast0 = Integer.parseInt(strlast[0].toString());
                        int strlast1 = Integer.parseInt(strlast[1].toString());
                        int strlast2 = Integer.parseInt(strlast[2].toString());
                        if (local0 > strlast0) {
                            isdown = false;
                        } else if (local0 == strlast0 && local1 > strlast1) {
                            isdown = false;
                        } else if (local0 == strlast0 && local1 == strlast1
                                && local2 > strlast2) {
                            isdown = false;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (isdown) {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("apkUrl", apkPathServer);
                data.putString("lastestVersion", lastestVersion);
                data.putString("versionDesc", versionDesc);
                data.putString("isupdateapk", isupdateapk);
                data.putString("apkMd5", newFileMd5);
                data.putString("patchPath", patchPath);
                msg.setData(data);
                msg.what = DcAndroidConsts.down_file;
                mHandler.sendMessage(msg);
            } else {
                mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
            }

        } else {
            mHandler.sendEmptyMessage(DcAndroidConsts.down_error);
        }
    }

    /**
     * 向服务器发送请求，获取最新版本信息
     */
    public UpdateVersionDomain getLastestVersion() throws Exception {
        RequestParams params = new RequestParams(versionServletAddress);
        params.addBodyParameter("appType", appType);
        params.addBodyParameter("oldVersionNum", getmLocalVersionName());
        params.addBodyParameter("versionCode", getmCurrentApkMd5());
        try {
            String responseStr = x.http().postSync(params, String.class);
            if (!TextUtils.isEmpty(responseStr)) {
                return JSON.parseObject(responseStr, UpdateVersionDomain.class);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getmLocalVersionName() {
        return mLocalVersionName;
    }

    public static String getmCurrentApkMd5() {
        return mCurrentApkMd5;
    }

}

package com.zcf.bananavideohannan.activity.servcice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class AutoLoadDataService extends Service {
    private Runnable taskAutoTopType = null;//后台任务:自动上报设备在线状态
    private Runnable taskAutoBestNew = null; //后台任务:自动上报互动操作
    private Runnable taskAutoReBo = null;  //后台任务:自动上报留言回复
    private Runnable taskAutoLikes = null; //后台任务:自动下载文件


    private Handler handler_taskAutoTopType = new Handler();
    private Handler handler_taskAutoBestNew = new Handler();
    private Handler handler_taskAutoReBo = new Handler();
    private Handler handler_taskAutoLikes = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private void startAutoReportMarkerThread() {
        taskAutoTopType = new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    public void run() {
                        try {
                            // 执行业务类上报方法

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                handler_taskAutoTopType.postDelayed(taskAutoTopType, 1000 * 5);
            }
        };
        handler_taskAutoTopType.postDelayed(taskAutoTopType, 1000 * 5);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

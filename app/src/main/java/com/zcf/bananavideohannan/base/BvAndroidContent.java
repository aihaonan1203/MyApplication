package com.zcf.bananavideohannan.base;

import android.content.pm.ActivityInfo;
import android.util.Log;

import com.gaotai.baselib.DcAndroidContext;
import com.lzy.okgo.OkGo;
import com.zcf.bananavideohannan.bean.OfflineCacheBean.OffCacheBean;

import org.xutils.DbManager;

import java.util.HashMap;
import java.util.List;

public class BvAndroidContent extends DcAndroidContext {
    /**
     * 数据库名称
     */
    private static final String DB_NAME = "yvideo.db";

    /**
     * 数据库版本号
     */
    private static final int DB_VERSIN = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);

//        UMConfigure.init(this, "5be8ed1fb465f55c660000d8", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        PlatformConfig.setWeixin(Consts.WXAPP_ID, Consts.WXAPP_SECRET);

        daoConfig = new DbManager.DaoConfig().setDbName(DB_NAME).setDbVersion(DB_VERSIN)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        Log.e("db", String.valueOf(newVersion));
                        int upgradeVersion = oldVersion;
                        if (upgradeVersion == 1) {
                            try {
//                                db.addColumn(GTUserGroupMembersModel.class, "usertype");
//                                db.addColumn(GTMessageGroupModel.class, "tbgzRead");
//                                db.addColumn(GTMessageGroupModel.class, "tbgzDel");
                            } catch (Exception e) {
                                Log.e("db", " add GTUserGroupMembersModel usertype error ");
                            }
                            upgradeVersion = 2;
                        }
                    }
                });
//        SDKInitializer.initialize(this);
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }


    private HashMap<String, String> mpBq = new HashMap<>();

    public HashMap<String, String> getMpBq() {
        return mpBq;
    }

    public void setMpBq(HashMap<String, String> mpBq) {
        this.mpBq = mpBq;
    }


    public static final String APP_TODAY = "alldata";
    public static final String APP_ALL_HISTORY_DATA = "today";
    public static final String APP_SWVEN = "seven";
    public static final String APP_MORE = "more";
    public static final String APP_LIKES = "likes";

    private HashMap<String, List<OffCacheBean.DataBeanX.DataBean>> historyData = new HashMap<>();

    public HashMap<String, List<OffCacheBean.DataBeanX.DataBean>> getHistoryData() {
        return historyData;
    }

    public void setHistoryData(HashMap<String, List<OffCacheBean.DataBeanX.DataBean>> historyData) {
        this.historyData = historyData;
    }


    // 已选择的观看记录
    public HashMap<String, String> mp_selected = new HashMap<>();

    public void setMp_selected(HashMap<String, String> mp_selected) {
        this.mp_selected = mp_selected;
    }

    public HashMap<String, String> getMp_selected() {
        return mp_selected;
    }


    // 下载中的任务
    public HashMap<String,String> mpDownData = new HashMap<>();

    public HashMap<String, String> getMpDownData() {
        return mpDownData;
    }

    public void setMpDownData(HashMap<String, String> mpDownData) {
        this.mpDownData = mpDownData;
    }

    public int orentation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

}

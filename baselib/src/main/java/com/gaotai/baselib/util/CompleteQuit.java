package com.gaotai.baselib.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * 完全退出程序
 * 每个Activity onCreate 调用 addActivity
 * onDestroy 调用 removeActivity
 *
 * @author MengLiang
 */
public class CompleteQuit extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();

    private static CompleteQuit instance;

    private CompleteQuit() {
    }


    /**
     * 单例模式中获取唯一的CompleteQuit实例
     *
     * @return
     */
    public static CompleteQuit getInstance() {
        if (null == instance) {
            instance = new CompleteQuit();
        }
        return instance;

    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);

    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityList.remove(activity);

    }

    /**
     * 遍历所有Activity并finish 退出程序
     */
    public void exit() {
        clearActivity();
        System.exit(0);
    }

    /**
     * 遍历清除所有Activity
     */
    public void clearActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void clearActivityOtherMain() {
        for (int i = 0; i < activityList.size(); i++) {
            if (i != activityList.size() - 1) {
                activityList.get(i).finish();
            }
        }
    }

    /**
     * 遍历清除Activity
     *
     * @param activityMain 保留的Activity
     */
    public void clearOtherActivity(Activity activityMain) {
        for (Activity activity : activityList) {
            if (activity != activityMain) {
                activity.finish();
            }
        }
    }

    /**
     * 清除最后一个 打开的Activity
     */
    public void clearLastActivity() {
        //最后一个索引
        int cleadindex = activityList.size();
        int index = 0;
        for (Activity activity : activityList) {
            index++;
            if (cleadindex == index) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前显示的Activity
     */
    public Activity getLasActivity() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    /**
     * 获取当前的显示的所有Activity
     *
     * @return
     */
    public List<Activity> getActivityList() {
        return activityList;
    }

}
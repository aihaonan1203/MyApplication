package com.gaotai.baselib.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gaotai.baselib.log.LogFile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * android系统的基础数据操作
 *
 * @author mengliang
 * @version 1.0
 */
public class AndroidUtil {
    private static String ESN = "";
    private static String IMSI = "";

    /**
     * 获取手机ESN
     *
     * @param context
     * @return
     */
    public static String getESN(Context context) {
        if (ESN == null || ESN.equals("") || ESN.length() == 0) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                ESN = telephonyManager.getDeviceId();
            } catch (Exception ex) {

            }
        }
        return ESN;
    }

    /**
     * 获取手机IMSI
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        if (IMSI == null || IMSI.equals("") || IMSI.length() == 0) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                IMSI = telephonyManager.getSubscriberId();
                if (TextUtils.isEmpty(IMSI)) {
                    IMSI = getMtkDoubleImsi(context);
                    if (TextUtils.isEmpty(IMSI)) {
                        IMSI = getQualcommDoubleSim(context);
                    }
                }
            } catch (Exception ex) {

            }
        }
        return IMSI;
    }

    /**
     * 获取mtk平台 双卡平台  imsi_
     *
     * @param context
     * @return
     */
    private static String getMtkDoubleImsi(Context context) {
        String defaultImsi = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            java.lang.reflect.Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            Integer simId_1 = (Integer) fields1.get(null);
            java.lang.reflect.Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            Integer simId_2 = (Integer) fields2.get(null);

            Method m = TelephonyManager.class.getDeclaredMethod(
                    "getSubscriberIdGemini", int.class);
            String imsi_1 = (String) m.invoke(tm, simId_1);
            String imsi_2 = (String) m.invoke(tm, simId_2);

			/*
			Method m1 = TelephonyManager.class.getDeclaredMethod(
					"getDeviceIdGemini", int.class);
			String imei_1 = (String) m1.invoke(tm, simId_1);
			String imei_2 = (String) m1.invoke(tm, simId_2);

			Method mx = TelephonyManager.class.getDeclaredMethod(
					"getPhoneTypeGemini", int.class);
			String phoneType_1 = (Integer) mx.invoke(tm, simId_1);
			String phoneType_2 = (Integer) mx.invoke(tm, simId_2);
			*/

            if (!TextUtils.isEmpty(imsi_1)) {
                defaultImsi = imsi_1;
            } else if (!TextUtils.isEmpty(imsi_2)) {
                defaultImsi = imsi_2;
            }
            if (defaultImsi == null) {
                Method mx = TelephonyManager.class.getMethod("getDefault",
                        int.class);
                TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
                TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

                imsi_1 = tm1.getSubscriberId();
                imsi_2 = tm2.getSubscriberId();

                if (!TextUtils.isEmpty(imsi_1)) {
                    defaultImsi = imsi_1;
                } else if (!TextUtils.isEmpty(imsi_2)) {
                    defaultImsi = imsi_2;
                }
            }
        } catch (Exception e) {
        }
        return defaultImsi;
    }

    /**
     * 高通平台获取imsi
     *
     * @param context
     * @return
     */
    public static String getQualcommDoubleSim(Context context) {
        String defaultImsi = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
            Object obj = context.getSystemService("phone_msim");
            Integer simId_1 = 0;
            Integer simId_2 = 1;
            //Method mx = cx.getMethod("getDataState");
            // int stateimei_1 = (Integer) mx.invoke(cx.newInstance());
            //int stateimei_2 = tm.getDataState();
            //Method mde = cx.getMethod("getDefault");
            //Method md = cx.getMethod("getDeviceId", int.class);
            Method ms = cx.getMethod("getSubscriberId", int.class);
            //Method mp = cx.getMethod("getPhoneType");

            // Object obj = mde.invoke(cx);

            //String imei_1 = (String) md.invoke(obj, simId_1);
            //String imei_2 = (String) md.invoke(obj, simId_2);

            String imsi_1 = (String) ms.invoke(obj, simId_1);
            String imsi_2 = (String) ms.invoke(obj, simId_2);
            if (!TextUtils.isEmpty(imsi_1)) {
                defaultImsi = imsi_1;
            } else if (!TextUtils.isEmpty(imsi_2)) {
                defaultImsi = imsi_2;
            }
            //int statephoneType_1 = tm.getDataState();
            //int statephoneType_2 = (Integer) mx.invoke(obj);

        } catch (Exception e) {

        }
        return defaultImsi;
    }

    /**
     * 根据手机imsi号获取移动网络运营商
     */
    public static String getMobileOperatorTypeByImsi(String imsi) {
        if (imsi.equals("")) {
            return "";
        }
        if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
            return "CHINAMOBILE";//中国移动
        } else if (imsi.startsWith("46001") || imsi.startsWith("46005")) {
            return "CHINAUNICOM";//中国联通
        } else if (imsi.startsWith("46003") || imsi.startsWith("46006")) {
            return "CHINATELECOM";//中国电信
        } else if (imsi.startsWith("46020")) {
            return "CHINATIETONG";//中国铁通
        }
        return "";
    }

    /**
     * 是否有SD卡
     *
     * @return
     */
    public static boolean isHasSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isHasNetWork(Context context) {
        try {
            ConnectivityManager conMan = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            //android.net.NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            android.net.NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            //mobile == android.net.NetworkInfo.State.CONNECTED	||
            if (wifi == android.net.NetworkInfo.State.CONNECTED) {
                return true;
            } else {
                // 获得当前网络信息
                NetworkInfo info = conMan.getActiveNetworkInfo();
                // 判断是否连接
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * <p>
     * 将图片转为字节数组.
     * </P>
     *
     * @param src
     * @param format
     * @param quality
     * @return
     */
    public static byte[] photoToByte(Bitmap src, Bitmap.CompressFormat format,
                                     int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);
        return os.toByteArray();
    }

    /**
     * <p>
     * 将字节数组转为图片.
     * </P>
     *
     * @param array
     * @return
     */
    public static Bitmap byteToPhoto(byte[] array) {
        if (array == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * 获取当前应用的版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "1.0.0";
        }
        return verName;
    }

    /**
     * <p>
     * 获取正在运行的程序包名列表.
     * </P>
     *
     * @param context
     * @return
     */
    public static List<String> getRuningPackageName(Context context) {
        List<String> result = new ArrayList<String>();
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcessInfo : list) {
            result.add(appProcessInfo.processName);
        }
        return result;
    }

    /**
     * <p>
     * 获取正在运行的程序名称列表.
     * </P>
     *
     * @param context
     * @return
     */
    public static List<String> getRuningLableName(Context context) {
        List<String> result = new ArrayList<String>();
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcessInfo : list) {
            try {
                PackageInfo pkg = pm.getPackageInfo(appProcessInfo.processName,
                        0);
                result.add(pkg.applicationInfo.loadLabel(pm).toString());
            } catch (NameNotFoundException e) {
                result.add(appProcessInfo.processName);
            }
        }
        return result;
    }

    /**
     * 获取当前应用的标签名称
     *
     * @param context
     * @return
     */
    public static String getLableSelf(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pkg = pm.getPackageInfo(context.getPackageName(), 0);
            return pkg.applicationInfo.loadLabel(pm).toString();
        } catch (NameNotFoundException e) {
            return context.getPackageName();
        }
    }

    /**
     * 获取本机手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 卸载应用程序
     */
    public static void deleteApp(Context context, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent it = new Intent(Intent.ACTION_DELETE, uri);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(it);
    }

    /**
     * 查询手机内安装的应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllInstallApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            // 判断是否为非系统预装的应用程序
            // if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM)
            // <= 0) {
            // customs applications
            apps.add(pak);
            // }
        }
        return apps;
    }

    /**
     * 查询手机内安装的应用的包名
     *
     * @param context
     * @return
     */
    public static List<String> getAllInstallAppsPackageName(Context context) {
        List<String> packageNames = new ArrayList<String>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            // 判断是否为非系统预装的应用程序
            // if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM)
            // <= 0) {
            // customs applications
            packageNames.add(pak.packageName);
            // }
        }
        return packageNames;
    }

    /**
     * 杀掉系统程序。
     * <p>The killPackage</p>
     *
     * @param context
     * @param packageName
     */
    public static void killPackage(Context context, String packageName) {
        ActivityManager actMag = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        // 杀掉系统程序。
        if (android.os.Build.VERSION.SDK_INT < 8) {
            actMag.restartPackage(packageName);
        } else {
            actMag.restartPackage(packageName);
            // actMag.killBackgroundProcesses(packageName);
        }
    }

    /**
     * 获取手机的ip地址
     *
     * @return
     */
    public static String getHostIp(Context context) {

        String hostIp = "";
        try {
	    	
	    	/*//本地局域网ip
	        Enumeration nis = NetworkInterface.getNetworkInterfaces();
	        InetAddress ia = null;
	        while (nis.hasMoreElements()) {
	        	NetworkInterface ni = (NetworkInterface) nis.nextElement();
	        	Enumeration<InetAddress> ias = ni.getInetAddresses();
	        	while (ias.hasMoreElements()) {
	        		ia = ias.nextElement();
	        		if (ia instanceof Inet6Address) {
	        			continue;// skip ipv6
	        		}
	        		String ip = ia.getHostAddress();
	        		if (!"127.0.0.1".equals(ip)) {
	        			hostIp = ia.getHostAddress();
	        			break;
	        		}
	        	}
	        }*/
	    	/*//局域网ip
	    	WifiManager wifiManager = (WifiManager) context .getSystemService(Context.WIFI_SERVICE);  
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();  
            int i = wifiInfo.getIpAddress();  
            hostIp =  int2ip(i);*/

            //用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }

        } catch (Exception e) {
        }
        return hostIp;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 判断进程是否运行
     *
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        //KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        if (lists != null) {
            for (RunningAppProcessInfo info : lists) {
                if (info.processName.equals(proessName)) {
                    //boolean isBackground = info.importance != IMPORTANCE_FOREGROUND && info.importance != IMPORTANCE_VISIBLE;
                    //boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                    //if (isBackground || isLockedState)
                    //{
                    isRunning = true;
                    //}
                }
            }
        }
        return isRunning;
    }

    /**
     * 重启机器
     */
    public static void reStart() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream out = new DataOutputStream(
                    process.getOutputStream());
            out.writeBytes("reboot \n");
            out.writeBytes("exit\n");
            out.flush();
        } catch (IOException e) {
            LogFile.writeLog("重启失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭机器
     */
    public static void shutdown() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream out = new DataOutputStream(
                    process.getOutputStream());
            out.writeBytes("reboot -p\n");
            out.writeBytes("exit\n");
            out.flush();
        } catch (IOException e) {
            LogFile.writeLog("关机失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭Android导航栏，实现全屏  需要root权限
     */
    public static void closeBar() {
        try {
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
            ArrayList<String> envlist = new ArrayList<String>();
            Map<String, String> env = System.getenv();
            for (String envName : env.keySet()) {
                envlist.add(envName + "=" + env.get(envName));
            }
            String[] envp = envlist.toArray(new String[0]);
            Process proc = Runtime.getRuntime().exec(
                    new String[]{"su", "-c", command}, envp);
            proc.waitFor();
        } catch (Exception ex) {
            // Toast.makeText(getApplicationContext(), ex.getMessage(),
            // Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示导航栏 需要root权限
     */
    public static void showBar() {
        try {
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib am startservice -n com.android.systemui/.SystemUIService";
            ArrayList<String> envlist = new ArrayList<String>();
            Map<String, String> env = System.getenv();
            for (String envName : env.keySet()) {
                envlist.add(envName + "=" + env.get(envName));
            }
            String[] envp = envlist.toArray(new String[0]);
            Process proc = Runtime.getRuntime().exec(
                    new String[]{"su", "-c", command}, envp);
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取当前版本apk的MD5校验值
     */
    public static String getCurrentApkMd5(Context context) {
        String oldApksource = ApkUtils.getSourceApkPath(context, context.getPackageName());
        File file = new File(oldApksource);
        String md5ByFile = ApkSignUtils.getMd5ByFile(file);
        return md5ByFile;
    }

    /**
     * 设置高度
     */
    public static void setHeight(Context context, ListView listView, List<?> list, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.height = dip2px(context, height) * list.size();
        listView.setLayoutParams(params);
    }

    /**
     * 设置高度
     */
//    public static void setHeight(Context context, pull listView, List<?> list, int height) {
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
//        params.height = dip2px(context, height) * list.size();
//        listView.setLayoutParams(params);
//    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    // 跳转到浏览器
    public static void openBrowser(Context mContext, String linkUrl) {
        if (linkUrl.indexOf("http://") < 0 || linkUrl.indexOf("https://") < 0) {
            linkUrl = "http://" + linkUrl;
        }

        Uri uri = Uri.parse(linkUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

}

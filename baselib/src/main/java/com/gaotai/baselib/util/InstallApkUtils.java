package com.gaotai.baselib.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.gaotai.baselib.view.dialog.ToastUtil;

/**
 * 静默安装
 * 需要root权限
 */
public class InstallApkUtils {

    /**
     * 根据包名 查找应用的起始页  启动应用  ----待验证
     *
     * @return
     */
    public static void startAppApk(Context context, String packageName) {
        try {
            List<ResolveInfo> matches = findActivitiesForPackage(context, packageName);
            if ((matches != null) && (matches.size() > 0)) {
                ResolveInfo resolveInfo = matches.get(0);
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                startApk(activityInfo.packageName, activityInfo.name);

                Log.i("InstallApkUtils", "start " + packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("InstallApkUtils", "error:start " + packageName);
        }
    }

    /**
     * 获取是否安装了同样版本的 客户端
     *
     * @return
     */
    public static boolean isInstallApp(final Context context, String packageName, String version) {
        String oaversion = null, myversion = null;
        try {
            //获取 当前应用的版本名称
            oaversion = context
                    .getPackageManager()
                    .getPackageInfo(packageName, 0).versionName;
            myversion = version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (oaversion == null || myversion == null) {
            return false;
        }
        if (!oaversion.equals(myversion)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 安装assets 文件下 的客户端
     *
     * @param apkname  客户端名称 zhxyoa.apk
     * @param filename 客户端文件 zhxyoa.mp3
     */
    public static void intallApp(final Context context, final String apkname, final String filename) {
        new Thread() {
            public void run() {
                try {
                    String path = context.getFilesDir().getAbsolutePath()
                            + "/" + apkname; // 从assets中解压到这个目录

                    File f = new File(path);
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    InputStream is = (InputStream) context.getAssets().open(filename);// assets里的文件在应用安装后仍然存在于apk文件中
                    inputStreamToFile(is, f);
                    String cmd = "chmod 777 " + f.getAbsolutePath();
                    Runtime.getRuntime().exec(cmd);
                    cmd = "chmod 777 " + f.getParent();
                    Runtime.getRuntime().exec(cmd);
                    // 尝试提升上2级的父文件夹权限，在阅读插件下载到手机存储时，刚好用到了2级目录
                    // /data/data/packagename/files/这个目录下面所有的层级目录都需要提升权限，才可安装apk，弹出安装界面
                    cmd = "chmod 777 " + new File(f.getParent()).getParent();
                    Runtime.getRuntime().exec(cmd);

                    installAndStartApk(context, path);

                    Log.i("InstallApkUtils", "intall " + apkname);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    /**
     * 流写入到文件
     * <p>The inputStreamToFile</p>
     *
     * @param inputStream
     * @param file
     */
    private static void inputStreamToFile(InputStream inputStream, File file) {
        // /InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // read this file into InputStream
            // inputStream = new FileInputStream("test.txt");
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安装APK
     *
     * @param context
     * @param apkPath
     */
    public static void installAndStartApk(final Context context, final String apkPath) {
        if ((apkPath == null) || (context == null)) {
            return;
        }

        File file = new File(apkPath);
        if (file.exists() == false) {
            return;
        }

        new Thread() {
            public void run() {
                try {
                    String packageName = getUninstallApkPackageName(context, apkPath);
                    if (silentInstall(context,apkPath)) {
                        List<ResolveInfo> matches = findActivitiesForPackage(context, packageName);
                        if ((matches != null) && (matches.size() > 0)) {
                            ResolveInfo resolveInfo = matches.get(0);
                            ActivityInfo activityInfo = resolveInfo.activityInfo;
                            startApk(activityInfo.packageName, activityInfo.name);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // LogFile.writeLog("apk inatall " + ex.toString());
                }
            }

            ;
        }.start();

    }

    /**
     * 获取APK 包名
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getUninstallApkPackageName(Context context, String apkPath) {
        String packageName = null;
        if (apkPath == null) {
            return packageName;
        }

        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info == null) {
            return packageName;
        }

        packageName = info.packageName;
        return packageName;
    }

    /**
     * 获取APP 的起始页相关信息
     * <p>The findActivitiesForPackage</p>
     *
     * @param context
     * @param packageName
     * @return
     */
    public static List<ResolveInfo> findActivitiesForPackage(Context context, String packageName) {
        final PackageManager pm = context.getPackageManager();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setPackage(packageName);

        final List<ResolveInfo> apps = pm.queryIntentActivities(mainIntent, 0);
        return apps != null ? apps : new ArrayList<ResolveInfo>();
    }

    /**
     * 静默安装APK
     *
     * @param apkPath
     * @return
     */
    public static boolean silentInstall(Context mContext,String apkPath) {
        String cmd1 = "chmod 777 " + apkPath + " \n";
        String cmd2 = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + apkPath + " \n";
        if (Build.VERSION.SDK_INT >= 25) { //7.0及以上
            return installSlient(mContext, apkPath);
        }
        return execWithSID(cmd1, cmd2);
    }

    private static boolean execWithSID(String... args) {
        boolean isSuccess = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);

            for (String tmp : args) {
                dataOutputStream.writeBytes(tmp);
            }

            dataOutputStream.flush(); // 提交命令
            dataOutputStream.close(); // 关闭流操作
            out.close();

            isSuccess = waitForProcess(process);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    /**
     * 启动APK
     *
     * @param packageName
     * @param activityName
     * @return
     */
    public static boolean startApk(String packageName, String activityName) {
        boolean isSuccess = false;

        String cmd = "am start -n " + packageName + "/" + activityName + " \n";
        try {
            Process process = Runtime.getRuntime().exec(cmd);

            isSuccess = waitForProcess(process);
        } catch (IOException e) {
            //NLog.i(TAG, e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    private static boolean waitForProcess(Process p) {
        boolean isSuccess = false;
        int returnCode;
        try {
            returnCode = p.waitFor();
            switch (returnCode) {
                case 0:
                    isSuccess = true;
                    break;

                case 1:
                    break;

                default:
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static boolean installSlient(Context mContext, String apkPath) {
        String cmd = "pm install -r " + apkPath;
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            //静默安装需要root权限
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.writeBytes("exit\n");
            os.flush();
            //执行命令
            process.waitFor();
            //获取返回结果
//            successMsg = new StringBuilder();
//            errorMsg = new StringBuilder();
//            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String s;
//            while ((s = successResult.readLine()) != null) {
//                successMsg.append(s);
//            }
//            while ((s = errorResult.readLine()) != null) {
//                errorMsg.append(s);
//            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
//        String sstr = successMsg.toString();
//        String estr = errorMsg.toString();
        //显示结果
//        ToastUtil.toastShort(mContext, "成功消息：" + successMsg.toString() + "\n" + "错误消息: " + errorMsg.toString());
    }
}
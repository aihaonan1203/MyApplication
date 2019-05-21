package com.gaotai.baselib.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.gaotai.baselib.DcAndroidConsts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author MengLiang
 */
public class FileUtils {
    private static String SDPATH;

    private static final String LOG_TAG = "FileUtils";

    private static int FILESIZE = 4 * 1024;

    private static final String _DATA = "_data";

    public static String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        // 得到当前外部存储设备的目录( /SDCARD )
        SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
    }

    /**
     * 缓存文件到本地
     *
     * @param data
     * @param filePath 文件 全路径
     * @param dir      文件夹路径
     */
    public static void saveFile(byte[] data, String filePath, String dir) {
        File filesDir = new File(dir);
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        // 文件
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = null;
            try {
                is = byteTOInputStream(data);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            int len;

            while ((len = is.read(data)) != -1) {
                os.write(data, 0, len);
                os.flush();
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将byte数组转换成InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception {

        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    /**
     * 按照指定路径创建文件
     *
     * @param filePath
     * @return
     */
    public static File makeFile(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public static File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName 不包含SD
     * @return
     */
    public static boolean isSDFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 全路径
     * @return
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.delete();
    }

    /**
     * @param absolutePath
     * @return 创建时间：2011-5-16 创建人：wangbing 方法描述：根据传入的绝对路径删除文件 （参数含义说明如下）
     */
    public boolean deleteFileWithPath(String absolutePath) {
        File file = new File(absolutePath);
        return file.delete();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param fileName
     * @param input
     * @return
     */
    public static File write2SDFromInput(String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            int lastSeperator = fileName.lastIndexOf("/");
            if (lastSeperator != -1) {
                String path = fileName.substring(0, lastSeperator);
                createSDDir(path);
            }
            file = createSDFile(fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @param fileName
     * @param bytes
     * @return 创建时间：2011-5-16 创建人：wangbing 方法描述：把byte数组写入到文件 位于SDCARD根目录下
     * （参数含义说明如下） 文件名，字节数组(建议不能太大，容易内存溢出)
     */
    public static String writeFile2SD(String fileName, byte[] bytes) {
        File file = null;
        OutputStream output = null;
        try {
            int lastSeperator = fileName.lastIndexOf("/");
            if (lastSeperator != -1) {
                String path = fileName.substring(0, lastSeperator);
                createSDDir(path);
            }
            file = createSDFile(fileName);
            output = new FileOutputStream(file);
            output.write(bytes);
            output.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    // 建立一个MIME类型与文件后缀名的匹配表
    private static final String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"},
            {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"},
            {".doc", "application/msword"}, {".exe", "application/octet-stream"}, {".gif", "image/gif"},
            {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"},
            {".html", "text/html"}, {".jar", "application/java-archive"}, {".java", "text/plain"}, {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"},
            {".png", "image/png"}, {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"}, {".rar", "application/x-rar-compressed"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"}, {".sh", "text/plain"},
            {".tar", "application/x-tar"}, {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/xml"}, {".xml", "text/plain"},
            {".z", "application/x-compress"}, {".zip", "application/zip"}, {"", "*/*"}};

    /**
     * 打开文件
     *
     * @param file
     */
    public void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 获取文件file的MIME类型
        String type = getMIMEType(file);
        // 设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        context.startActivity(intent);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();

        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;

        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 返回指定URI字符串的真实路径。如果给定URI对象代表一个content://的URI，真实路径则从存储中进行检索。
     *
     * @param uri      audio/image/video的URI对象
     * @param activity Activity
     * @return String 文件路径
     */
    public static String getRealPath(Uri uri, Activity activity) {
        return getRealPath(uri.toString(), activity);
    }

    /**
     * 从URI字符串中获取InputStream.
     *
     * @param uriString 从该URI字符串获取InputStream
     * @param activity  Activity
     * @return InputStream 返回InputStream,如果指定URI无效则返回null
     * @throws IOException
     */
    public static InputStream getInputStreamFromUriString(String uriString, Activity activity) throws IOException {
        if (uriString.startsWith("content")) {
            Uri uri = Uri.parse(uriString);
            return activity.getContentResolver().openInputStream(uri);
        } else if (uriString.startsWith("file://")) {
            int question = uriString.indexOf("?");
            if (question > -1) {
                uriString = uriString.substring(0, question);
            }
            if (uriString.startsWith("file:///android_asset/")) {
                Uri uri = Uri.parse(uriString);
                String relativePath = uri.getPath().substring(15);
                return activity.getAssets().open(relativePath);
            } else {
                return new FileInputStream(getRealPath(uriString, activity));
            }
        } else {
            return new FileInputStream(getRealPath(uriString, activity));
        }
    }

    /**
     * 复制单个文件
     *
     * @param srcFile      待复制的文件
     * @param destFileName 目标文件名
     * @param overlay      如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(File srcFile, String destFileName, boolean overlay) {
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }
        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        //return dir.delete();
        return true;
    }

    /**
     * 删除文件或文件目录
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    /**
     * 返回指定URI字符串的真实路径。如果给定URI字符串代表一个content://的URI，真实路径则从存储中进行检索。
     *
     * @param uriString audio/image/video的URI字符串
     * @param activity  Activity
     * @return String 文件路径
     */
    public static String getRealPath(String uriString, Activity activity) {
        String realPath = null;

        if (uriString.startsWith("content://")) {
            String[] proj = {_DATA};
            Cursor cursor = activity.managedQuery(Uri.parse(uriString), proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(_DATA);
            cursor.moveToFirst();
            realPath = cursor.getString(column_index);
            if (realPath == null) {
                Log.e(LOG_TAG, "Could get real path for URI string " + uriString);
            }
        } else if (uriString.startsWith("file://")) {
            realPath = uriString.substring(7);
            if (realPath.startsWith("/android_asset/")) {
                Log.e(LOG_TAG, "Cannot get real path for URI string " + uriString
                        + " because it is a file:///android_asset/ URI.");
                realPath = null;
            }
        } else {
            realPath = uriString;
        }

        return realPath;
    }

    /**
     * 获取应用程序的文件路径
     *
     * @param context
     * @return
     */
    public static String getAblePath(Context context) {
        if (context == null) {
            return DcAndroidConsts.basePath;
        } else {
            boolean hasSD = AndroidUtil.isHasSDCard();
            if (hasSD) {
                return DcAndroidConsts.basePath;
            } else {
                return context.getFilesDir().getAbsolutePath();
            }
        }
    }

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                result.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 读取文件内容
     */
    public static String readFileOnLine(File file) throws IOException {// 输入文件路径
        StringBuffer sBuffer = new StringBuffer();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
        BufferedReader reader = new BufferedReader(read);
        String strLine;
        while ((strLine = reader.readLine()) != null) {// 通过readline按行读取
            sBuffer.append(strLine + "\r\n");// strLine就是一行的内容
        }
        reader.close();
        return sBuffer.toString();
    }

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String readAllContent(File file) {
        StringBuilder result = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            String s = "";
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                result.append(s);//将读取的字符串添加换行符后累加存放在缓存中
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    public static String getResourcesUri(Context mContext, @DrawableRes int id) {
        Resources resources = mContext.getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    public static String getFileName(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }
}

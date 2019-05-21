
package com.gaotai.baselib.log;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.util.DcDateUtils;

/**
 * 将错误信息写入文件中
 *
 * @author MengLiang
 */
public class LogFile {

    /**
     * 错误信息写入程序日志文件
     *
     * @param loninfo
     */
    public static void writeErrLog(String loninfo) {
        try {
            //创建日志文件地址
            File directory = new File(DcAndroidConsts.LOG_PATH);
            if (!directory.exists()) {//判断是否存在文件夹
                directory.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filename = "Err" + DcDateUtils.toString(DcDateUtils.now(), DcDateUtils.FORMAT_YMD_1) + ".txt";
            //错误日志 写入文件
            FileWriter fileWriter = new FileWriter(DcAndroidConsts.LOG_PATH + filename, true);
            fileWriter.write(loninfo + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 信息写入程序日志文件
     *
     * @param loninfo
     */
    public static void writeLog(String loninfo) {
        try {
            //创建日志文件地址
            File directory = new File(DcAndroidConsts.LOG_PATH);
            if (!directory.exists()) {//判断是否存在文件夹
                directory.mkdirs();
            }

            Log.d("LogFile", loninfo);//写入控制台日志
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filename = DcDateUtils.toString(DcDateUtils.now(), DcDateUtils.FORMAT_YMD_1) + ".txt";
            //错误日志 写入文件
            FileWriter fileWriter = new FileWriter(DcAndroidConsts.LOG_PATH + filename, true);
            fileWriter.write(DcDateUtils.getCurrentTimeAsString1() + " :  " + loninfo + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 详细的错误堆栈信息写入程序日志文件
     *
     * @param loginfo 错误信息
     * @param ex      错误堆栈
     */
    public static void writeLog(String loginfo, Throwable ex) {
        try {
            //创建日志文件地址
            File directory = new File(DcAndroidConsts.LOG_PATH);
            if (!directory.exists()) {//判断是否存在文件夹
                directory.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filename = DcDateUtils.toString(DcDateUtils.now(), DcDateUtils.FORMAT_YMD_1) + ".txt";

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();

            //错误日志 写入文件
            FileWriter fileWriter = new FileWriter(DcAndroidConsts.LOG_PATH + filename, true);
            fileWriter.write(DcDateUtils.getCurrentTimeAsString1() + " :  " + loginfo + "\r\n");
            fileWriter.write(DcDateUtils.getCurrentTimeAsString1() + " :  " + result + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 详细的错误堆栈信息写入程序日志文件
     *
     * @param loginfo 错误信息
     * @param ex      错误堆栈
     */
    public static void writeLog(String loginfo, Exception ex) {
        try {
            //创建日志文件地址
            File directory = new File(DcAndroidConsts.LOG_PATH);
            if (!directory.exists()) {//判断是否存在文件夹
                directory.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filename = DcDateUtils.toString(DcDateUtils.now(), DcDateUtils.FORMAT_YMD_1) + ".txt";

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();

            //错误日志 写入文件
            FileWriter fileWriter = new FileWriter(DcAndroidConsts.LOG_PATH + filename, true);
            fileWriter.write(DcDateUtils.getCurrentTimeAsString1() + " :  " + loginfo + "\r\n");
            fileWriter.write(DcDateUtils.getCurrentTimeAsString1() + " :  " + result + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLogToFile(String loninfo, String filePath) {
        try {
            //创建日志文件地址
            File directory = new File(DcAndroidConsts.LOG_PATH);
            if (!directory.exists()) {//判断是否存在文件夹
                directory.mkdirs();
            }

            Log.d("LogFile", loninfo);//写入控制台日志
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //错误日志 写入文件
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(loninfo);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

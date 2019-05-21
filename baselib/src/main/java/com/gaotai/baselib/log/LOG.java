package com.gaotai.baselib.log;

import android.util.Log;

/**
 * 日志输出
 * @author MengLiang
 */
public class LOG {

	/**
	 * VERBOSE级别
	 */
    public static final int VERBOSE = Log.VERBOSE;
    /**
	 * DEBUG级别
	 */
    public static final int DEBUG = Log.DEBUG;
    /**
	 * INFO级别
	 */
    public static final int INFO = Log.INFO;
    /**
  	 * WARN级别
  	 */
    public static final int WARN = Log.WARN;
    /**
  	 * ERROR级别
  	 */
    public static final int ERROR = Log.ERROR;

   /**
    * 默认级别
    */
    public static int LOGLEVEL = Log.DEBUG;

    /**
     * 设置当前日志级别
     *
     * @param logLevel 日志级别
     */
    public static void setLogLevel(int logLevel) {
        LOGLEVEL = logLevel;
        Log.i("FrameWorkLog", "Changing log level to " + logLevel);
    }

    /**
     * 设置当前日志级别
     *
     * @param logLevel 日志级别名称(可选值:VERBOSE|DEBUG|INFO|WARN|ERROR)
     */
    public static void setLogLevel(String logLevel) {
    	logLevel  =logLevel.toUpperCase();
        if ("VERBOSE".equals(logLevel)) LOGLEVEL = VERBOSE;
        else if ("DEBUG".equals(logLevel)) LOGLEVEL = DEBUG;
        else if ("INFO".equals(logLevel)) LOGLEVEL = INFO;
        else if ("WARN".equals(logLevel)) LOGLEVEL = WARN;
        else if ("ERROR".equals(logLevel)) LOGLEVEL = ERROR;
        Log.i("FrameWorkLog", "Changing log level to " + logLevel + "(" + LOGLEVEL + ")");
    }

    /**
     * 判断指定日志级别是否可以有效输出
     *
     * @param logLevel
     * @return boolean true:有效 false:无效
     */
    public static boolean isLoggable(int logLevel) {
        return (logLevel >= LOGLEVEL);
    }

    /**
     * 输出Verbose级别消息日志.
     *
     * @param tag 标签
     * @param s 消息内容
     */
    public static void v(String tag, String s) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, s);
    }

    /**
     * 输出Debug级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     */
    public static void d(String tag, String s) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, s);
    }

    /**
     * 输出Info级别消息日志.
     *
     * @param tag 标签
     * @param s 消息内容
     */
    public static void i(String tag, String s) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, s);
    }

    /**
     * 输出Warning级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     */
    public static void w(String tag, String s) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, s);
    }

    /**
     * 输出Error级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     */
    public static void e(String tag, String s) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, s);
    }

    /**
     * 输出Verbose级别消息日志.
     *
     * @param tag 标签
     * @param s 消息内容
     * @param e 抛出的异常
     */
    public static void v(String tag, String s, Throwable e) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, s, e);
    }

    /**
     * 输出Debug级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param e 抛出的异常
     */
    public static void d(String tag, String s, Throwable e) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, s, e);
    }

    /**
     * 输出Info级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param e 抛出的异常
     */
    public static void i(String tag, String s, Throwable e) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, s, e);
    }

    /**
     * 输出Warning级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param e 抛出的异常
     */
    public static void w(String tag, String s, Throwable e) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, s, e);
    }

    /**
     * 输出Error级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param e 抛出的异常
     */
    public static void e(String tag, String s, Throwable e) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, s, e);
    }

    /**
     * 输出Verbose级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param args 带输出格式的参数对象
     */
    public static void v(String tag, String s, Object... args) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, String.format(s, args));
    }

    /**
     * 输出Debug级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param args 带输出格式的参数对象
     */
    public static void d(String tag, String s, Object... args) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, String.format(s, args));
    }

    /**
     * 输出Info级别消息日志.
     *
     * @param tag 标签
     * @param s 消息内容
     * @param args 带输出格式的参数对象
     */
    public static void i(String tag, String s, Object... args) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, String.format(s, args));
    }

    /**
     * 输出Warning级别消息日志.
     *
    * @param tag 标签
     * @param s 消息内容
     * @param args 带输出格式的参数对象
     */
    public static void w(String tag, String s, Object... args) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, String.format(s, args));
    }

    /**
     * 输出Error级别消息日志.
     *
     * @param tag 标签
     * @param s 消息内容
     * @param args 带输出格式的参数对象
     */
    public static void e(String tag, String s, Object... args) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, String.format(s, args));
    }

}

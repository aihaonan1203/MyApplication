package com.gaotai.baselib.handler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.gaotai.baselib.log.LogFile;
import com.gaotai.baselib.util.CompleteQuit;
import com.gaotai.baselib.util.DcDateUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控未知的程序错误信息，并写入日志
 * @author MengLiang
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	/**
	 * 用来存储设备信息和异常信息
	 */
	private Map<String, String> infos = new HashMap<String, String>();

	private static CrashHandler instance; // 单例引用

	private CrashHandler()
	{}

	public synchronized static CrashHandler getInstance()
	{
		// 同步方法，以免单例多线程环境下出现异常
		if(instance == null)
		{
			instance = new CrashHandler();
		}
		return instance;
	}

	private Context context;

	public void init(Context ctx)
	{
		// 初始化，把当前对象设置成UncaughtExceptionHandler处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		context = ctx;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		// 当有未处理的异常发生时，就会来到这里。。
		String threadName = thread.getName();
		Log.d("zhxy", "Exception, thread: " + thread + " name: " + threadName + " id: " + thread.getId()
				+ " exception: " + ex);
		try
		{		
			LogFile.writeErrLog("time:" + DcDateUtils.toString(DcDateUtils.now(),DcDateUtils.FORMAT_DATE_TIME_3));
			LogFile.writeErrLog("errtype:" + ex);
			saveCatchInfoFile(ex);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		CompleteQuit.getInstance().clearLastActivity();
		
	}

	/**
	 * 收集设备参数信息
	 */
	public void collectDeviceInfo()
	{
		infos = new HashMap<String, String>();
		try
		{
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if(pi != null)
			{
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		}
		catch (NameNotFoundException e)
		{
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields)
		{
			try
			{
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			}
			catch (Exception e)
			{
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存详细的错误堆栈信息到文件中
	 * @param ex
	 */
	private void saveCatchInfoFile(Throwable ex)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : infos.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key + "=" + value + "\r\n");
			}
	
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			Throwable cause = ex.getCause();
			while (cause != null)
			{
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String result = writer.toString();
			sb.append(result);

			LogFile.writeErrLog("errinfo:" +sb.toString());
			Log.e("zhxy", "errinfo, " + sb.toString());
		}
		catch (Exception e)
		{
			Log.e(TAG, "an error occured while writing file...", e);
		}
	}
}
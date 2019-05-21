package com.gaotai.baselib.version;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.download.DownloadFileManager;
import com.gaotai.baselib.download.Request;
import com.gaotai.baselib.download.TaskDBManager;
import com.gaotai.baselib.util.AndroidUtil;
import com.gaotai.baselib.util.ApkSignUtils;
import com.gaotai.baselib.util.ApkUtils;
import com.gaotai.baselib.util.FileUtils;
import com.gaotai.baselib.util.PatchUtils;
import com.gaotai.baselib.view.dialog.ProgressDialogUtil;

/**
 * 更新版本
 * 带差分包更新功能
 */
public class UpdateVersionApkPatchUtil
{

	/**
	 * 下载进度
	 */
	final int PROGRESS_DIALOG = 0; // 进度条对话框ID

	/**
	 * 下载进度条
	 */
	ProgressDialog progressDialog;

	/**
	 * APK 服务器URL 路径
	 */
	private String apkPathServer = "";

	/**
	 * APK 版本号
	 */
	private String lastestVersion = "";

	/**
	 * APK 本地文件路径
	 */
	private String apkFilePath = "";

	/**
	 * 说明
	 */
	private String versionDesc = "";

	/** 最新版本MD5值 */
	private String newApkMd5;

	/** 获取的差分包patch下载地址 */
	private String patchUrl = "";

	/** 下载的类型：1.apk 2.patch */
	private String downType;

	/** 当前版本MD5值 */
	private String currentApkMd5;

	/** 成功 */
	private static final int WHAT_SUCCESS = 1;

	/** 本地安装的微博MD5不正确 */
	private static final int WHAT_FAIL_OLD_MD5 = -1;

	/** 新生成的微博MD5不正确 */
	private static final int WHAT_FAIL_GEN_MD5 = -2;

	/** 合成失败 */
	private static final int WHAT_FAIL_PATCH = -3;

	/** 获取源文件失败 */
	private static final int WHAT_FAIL_GET_SOURCE = -4;

	/** 未知错误 */
	private static final int WHAT_FAIL_UNKNOWN = -5;

	/**
	 * 更新eHandler
	 */
	private UpdateHandler mHandler;

	/**
	 * 显示弹出
	 */
	Dialog m_dialog;

	private Activity myactivity;

	/**
	 * 下载日志tag
	 */
	private static final String TAG = "zhxy download";

	/**
	 * 下载任务监听
	 */
	private DownloadTaskListener mDownloadListener = new DownloadTaskListener();

	/**
	 *
	 * @param _myactivity
	 * @param appType 版本类别
	 * @param versionServletAddress 版本获取地址
     */
	public UpdateVersionApkPatchUtil(Activity _myactivity, String appType, String versionServletAddress)
	{
		this.myactivity = _myactivity;
		this.versionServletAddress = versionServletAddress;
		this.appType = appType;
		TaskDBManager.init(this.myactivity, false, "task.db");
	}

	/**
	 * 如是最新版是否显示文字提示
	 */
	private boolean isShowText = false;
	/**
	 * 版本获取地址
	 */
	private String versionServletAddress = "";
	/**
	 * 版本类别
	 */
	private String appType = "";
	/**
	 * 合并后新的版本路径名称
	 */
	private String newApkPathName = DcAndroidConsts.APK_PATH + "zhxyOldtoNew.apk";

	/**
	 * 获取更新并显示下载弹出框
	 * 
	 * @param isshow
	 *            如是最新版是否显示文字提示
	 */
	public void getUpdateActiviy(boolean isshow)
	{
		if(AndroidUtil.isHasNetWork(myactivity))
		{
			isShowText = isshow;
			//创建消息循环
			mHandler = new UpdateHandler();
			//获取当前安装包版本 
			String versionName = AndroidUtil.getVerName(myactivity);
			//当前版本对应的MD5校验值
			currentApkMd5 = AndroidUtil.getCurrentApkMd5(myactivity);
			//启动获取版本文件线程
			UpdateVersionApkPatch updateVersion = new UpdateVersionApkPatch(mHandler, versionName, currentApkMd5,appType,versionServletAddress);
			updateVersion.start();
		}
		else
		{
			if(isShowText)
			{
				Toast.makeText(myactivity, "请先打开网络,否则无法正常使用软件", Toast.LENGTH_LONG).show();
			}
		}
	}

	class UpdateHandler extends Handler
	{
		int fileSize = 0;

		@Override
		public void handleMessage(Message msg)
		{
			if(myactivity.isFinishing())
			{// 如果已结束,则不再弹更新对话框进行升级
				return;
			}
			Bundle data = msg.getData();

			switch (msg.what)
			{
				case DcAndroidConsts.down_file:
					apkPathServer = data.getString("apkUrl");
					lastestVersion = data.getString("lastestVersion");
					versionDesc = data.getString("versionDesc");
					newApkMd5 = data.getString("apkMd5");
					patchUrl = data.getString("patchPath");

					if(TextUtils.isEmpty(versionDesc))
					{
						versionDesc = "发现新版本，请更新！";
					}
					if(!TextUtils.isEmpty(lastestVersion))
					{
						newApkPathName = DcAndroidConsts.APK_PATH + lastestVersion+ ".apk";
					}

					DownloadFileManager.getInstance();
					m_dialog = new AlertDialog.Builder(myactivity).setTitle("发现新版本")
							.setMessage(versionDesc.replace("@", "\n"))
							// 设置内容
							.setPositiveButton("立即更新",// 设置确定按钮
								new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										m_dialog.cancel();
										progressDialog = new ProgressDialog(myactivity); // 创建进度对话框
										progressDialog.setMax(100); // 进度最大值
										progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
										progressDialog.setTitle("软件更新"); // 设置标题
										progressDialog.setCancelable(false); // 进度条不能用回退按钮关闭										
										progressDialog.incrementProgressBy(-progressDialog.getProgress());
										progressDialog.show();

										//创建目录
										File file = new File(DcAndroidConsts.APK_PATH);
										if(!file.exists())
										{
											file.mkdirs();
										}
										String url = "";
										// 如果差分包下载地址为空 下载最新完整版本
										if(TextUtils.isEmpty(patchUrl) || TextUtils.isEmpty(newApkMd5))
										{
											apkFilePath = DcAndroidConsts.APK_PATH + lastestVersion + ".apk";
											apkPathServer = apkPathServer.replace("\\", "/");
											url = apkPathServer;
											downType = DcAndroidConsts.DOWN_FILE_TYPE_APK;
										}
										// 差分包地址和新版本md5值不为空，则下载差分包
										else if(!TextUtils.isEmpty(patchUrl) && !TextUtils.isEmpty(newApkMd5))
										{
											patchUrl = patchUrl.replace("\\", "/");
											url = patchUrl;
											apkFilePath = DcAndroidConsts.APK_PATH + "zhxy" + ".patch";
											downType = DcAndroidConsts.DOWN_FILE_TYPE_PATCH;
										}
										// 执行下载文件(apk or patch)
										Request request = new Request();
										request.requestUrl = url;
										request.dstFilePath = apkFilePath;

										DownloadFileManager.getInstance().registDownloadTaskListener(url,
											mDownloadListener);
										DownloadFileManager.getInstance().downloadFile(request);
									}
								}).setNegativeButton("稍后再说", new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int whichButton)
								{
									// 点击"取消"按钮之后退出程序
									dialog.cancel();
								}
							}).create();// 创建
					m_dialog.setCancelable(false);
					m_dialog.show();
					break;

				case DcAndroidConsts.down_error:
					Toast.makeText(myactivity, "系统更新异常，未能自动更新", Toast.LENGTH_LONG).show();
					try {
						progressDialog.dismiss();
					}
					catch (Exception ex){
						ex.printStackTrace();
					}
				case DcAndroidConsts.down_cancle:
					if(isShowText)
					{
						Toast.makeText(myactivity, "当前是最新版本，无需更新", Toast.LENGTH_LONG).show();
					}
					break;
				default:
					return;
			}
		}
	}

	private class DownloadTaskListener implements com.gaotai.baselib.download.DownloadTaskListener
	{

		@Override
		public void onTaskAdded()
		{
			Log.d(TAG, "onTaskAdded()");

		}

		@Override
		public void onTaskWaiting()
		{
			Log.d(TAG, "onTaskWaiting()");

		}

		@Override
		public void onTaskStart()
		{
			Log.d(TAG, "onTaskStart()");

		}

		@Override
		public void onTaskRunning(long curSize, long totalSize)
		{
			Log.d(TAG, String.format("onTaskRunning() [%d,%d]", curSize, totalSize));
			progressDialog.setMax((int) totalSize / 1000);
			int progress = (int) (curSize / totalSize);
			progressDialog.setProgress((int) curSize / 1000);
			progressDialog.incrementProgressBy(progress);
			//DownloadTestActivity.this.mProgressBar0.setProgress(progress);
		}

		@Override
		public void onTaskStop()
		{
			Log.d(TAG, "onTaskStop()");
			progressDialog.dismiss();
			Toast.makeText(myactivity, "已停止更新任务！", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onTaskFinished(String filepath)
		{
			Log.d(TAG, "onTaskFinished()");
			progressDialog.dismiss();
			if(downType.equals(DcAndroidConsts.DOWN_FILE_TYPE_APK))
			{
				if(!reboot(apkFilePath))
				//下载APK完毕 安装
					reboot(apkFilePath);
			}
			else if(downType.equals(DcAndroidConsts.DOWN_FILE_TYPE_PATCH))// 下载的是差分包 执行合并
			{
				File file = new File(apkFilePath);
				Log.i("info", "file是否存在：" + file.exists());

				if(!ApkUtils.isInstalled(myactivity, myactivity.getPackageName()))//获取当前包名
				{
					Toast.makeText(myactivity, "未发现应用", Toast.LENGTH_SHORT).show();
				}
				else if(!file.exists())
				{
					Toast.makeText(myactivity, "差异包不存在", Toast.LENGTH_SHORT).show();
				}
				else
				{
					// 开始合成apk
					ProgressDialogUtil.show(myactivity, "正在升级...请稍等", false);
					new PatchApkTask().execute();
				}
			}
		}

		@Override
		public void onTaskError(int code, String msg)
		{
			Log.d(TAG, "onTaskError()");
			Toast.makeText(myactivity, "自动更新失败,请稍后重试！", Toast.LENGTH_LONG).show();
			progressDialog.dismiss();
		}
	}

	/**
	 * 打开安装包
	 * 
	 * @param url
	 *            文件路径
	 * @return
	 */
	public boolean reboot(String url)
	{
		try
		{
			if(!AndroidUtil.isHasSDCard())
			{
				chmodRw(url);
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setDataAndType(Uri.parse("file://" + url), "application/vnd.android.package-archive");
			myactivity.startActivity(i);
		}
		catch (Exception ex)
		{
			Toast.makeText(myactivity, "程序出错，无法重新安装主程序,请卸载后重新安装.", Toast.LENGTH_LONG).show();
			Log.e("error", ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * android 系统文件
	 * 
	 * @param url
	 *            文件路径
	 */
	private void chmodRw(String url)
	{
		try
		{
			// [文件夹705:drwx---r-x]
			String[] args1 = {"chmod", "705", DcAndroidConsts.APK_PATH };
			Runtime.getRuntime().exec(args1);
			// [文件604:-rw----r--]
			String[] args2 = {"chmod", "604", url};
			Runtime.getRuntime().exec(args2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private class PatchApkTask extends AsyncTask<String, Void, Integer>
	{
		// 执行合成(旧版apk+差分包)
		@Override
		protected Integer doInBackground(String... params)
		{
			// 获取当前应用信息
			PackageInfo packageInfo = ApkUtils.getInstalledApkPackageInfo(myactivity,myactivity.getPackageName());
			if(packageInfo != null)
			{
				// 获取旧版本apk资源
				String oldApksource = ApkUtils.getSourceApkPath(myactivity, myactivity.getPackageName());
				if(!TextUtils.isEmpty(oldApksource))
				{
					// 检查当前版本apk是否与MD5一致
					if(ApkSignUtils.checkMd5(oldApksource, currentApkMd5))
					{
						// 执行合成 旧版apk资源+差分包patch=new Apk
						int patchResult = PatchUtils.patch(oldApksource, newApkPathName, apkFilePath);
						if(patchResult == 0)// 成功
						{
							// 检查合并的新apk是否与服务器获取的MD5值一致
							if(ApkSignUtils.checkMd5(newApkPathName, newApkMd5))
							{
								return WHAT_SUCCESS;// 成功
							}
							else return WHAT_FAIL_GEN_MD5;// 合成的apk与服务器给的MD5值不匹配
						}
						else return WHAT_FAIL_PATCH;// 合成失败
					}
					else return WHAT_FAIL_OLD_MD5;//  当前版本md5值不匹配
				}
				else return WHAT_FAIL_GET_SOURCE;// 旧版本apk资源为空
			}
			else return WHAT_FAIL_UNKNOWN;// 当前应用信息为空
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			String text = null;
			ProgressDialogUtil.dismiss();
			switch (result)
			{
				case WHAT_SUCCESS:
					showToast("合成成功");
					reboot(newApkPathName);
					break;
				case WHAT_FAIL_OLD_MD5:
					text = "现在安装的版本不对！正下载最新版本...";
					goToDownApk(text);
					break;
				case WHAT_FAIL_GEN_MD5:
					text = "合成完毕,但与最新版本不一致！正下载最新版本...";
					goToDownApk(text);
					break;
				case WHAT_FAIL_PATCH:
					text = "合并失败！正下载最新版本...";
					goToDownApk(text);
					break;
				case WHAT_FAIL_GET_SOURCE:
					text = "无法获取客户端的源apk文件，正下载最新版本...";
					goToDownApk(text);
					break;
				default:
					break;
			}
		}
	}

	/** 提示信息、并重新下载apk */
	private void goToDownApk(String text)
	{
		showToast(text);
		// 重新设置下载文件存储路径
		apkFilePath = DcAndroidConsts.APK_PATH + lastestVersion + ".apk";
		// 开始下载完整APK
		startDownApk(apkPathServer, apkFilePath);
	}

	private void startDownApk(String url, String path)
	{
		downType = DcAndroidConsts.DOWN_FILE_TYPE_APK;
		progressDialog.show();
		Request request = new Request();
		request.requestUrl = url;
		request.dstFilePath = path;

		DownloadFileManager.getInstance().registDownloadTaskListener(url, mDownloadListener);
		DownloadFileManager.getInstance().downloadFile(request);
	}

	static
	{
		System.out.println("加载库时搜索的路径列表:\n" + System.getProperty("java.library.path"));
		//ClassLoader classLoader = new ClassLoader();
		//dalvik.system.PathClassLoader[dexPath=/data/app/com.qrcode.qrcode-1.apk,libraryPath=/data/app-lib/com.qrcode.qrcode-1
		//Runtime.getRuntime().loadLibrary("ApkPatchLibrary", VMStack.getCallingClassLoader());

		System.loadLibrary("ApkPatchLibrary");
		/*
		 * if ( ENABLE_ANDROID_INTEGRATION ) {
		 * System.load("/system/lib/libfoobar.so"); } else {
		 * System.loadLibrary("ApkPatchLibrary"); }
		 */
	}

	private void showToast(final String text)
	{
		Toast.makeText(myactivity, text, Toast.LENGTH_LONG).show();
	}
}

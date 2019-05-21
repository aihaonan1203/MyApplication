package com.gaotai.baselib.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.gaotai.baselib.util.FileUtils;
import com.gaotai.baselib.view.UpdateVersion;

/**
 * 
 * <p>
 * Description:升级基础activity
 * </p>
 * 
 * @author MengLiang
 * @version 1.0
 */
public abstract class BaseUpdateActivity extends BaseActivity {
	/**
	 * 当自动升级完成，或升级过程被终止时调用此函数
	 */
	public abstract void updateFinish();

	/** Called when the activity is first created. */
	Dialog m_dialog;
	ProgressDialog progressDialog;
	final int PROGRESS_DIALOG = 0; // 进度条对话框ID
	
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
	/**
	 * 更新eHandler
	 */
	private UpdateHandler mHandler;		
	
	/**
	 * 是否必须更新安装
	 */
	private String isupdateapk = "1";

	/**
	 * 下载日志tag
	 */
	private static final String TAG = "download";
	/**
	 * 下载任务监听
	 */
	private DownloadTaskListener mDownloadListener = new DownloadTaskListener();
	/**
	 * apk下载地址
	 */
	public final String apkAddress = "http://www.jseduinfo.com/static";
	/**
	 *  版本获取地址
	 */
	public final String versionServletAddress = "http://www.jseduinfo.com/zhxy-mobile/client/public/upgradedVersion";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TaskDBManager.init(BaseUpdateActivity.this, false, "task.db");
		
		if (AndroidUtil.isHasNetWork(this)) {
			 //创建消息循环
			mHandler = new UpdateHandler();
			 //获取当前安装包版本 
			String version = AndroidUtil.getVerName(this);
			 //启动获取版本文件线程
			UpdateVersion updateVersion = new UpdateVersion(mHandler, version,versionServletAddress);
			updateVersion.start();
		} else {
			Toast.makeText(this, "请保持手机联网,否则无法正常使用软件", Toast.LENGTH_LONG)
					.show();
			updateFinish();
		}
	}

	class UpdateHandler extends Handler {
		int fileSize = 0;

		@Override
		public void handleMessage(Message msg) {
			if (BaseUpdateActivity.this.isFinishing()) {// 如果已结束,则不再弹更新对话框进行升级
				return;
			}
			Bundle data = msg.getData();

			switch (msg.what) {
			case DcAndroidConsts.down_file:
				String errorMsg = data.getString("errorMsg");
				if (!TextUtils.isEmpty(errorMsg)) {
					Toast.makeText(BaseUpdateActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
					updateFinish();
					break;
				}

				apkPathServer = data.getString("apkUrl");
				lastestVersion = data.getString("lastestVersion");
				versionDesc  = data.getString("versionDesc");
				if(versionDesc.equals(""))
				{
					versionDesc = "发现新版本，请更新！";
				}
				
				if(data.containsKey("isupdateapk"))
				{
					isupdateapk  = data.getString("isupdateapk");
					if(isupdateapk.equals(""))
					{
						isupdateapk = "1";
					}
				}
				DownloadFileManager.getInstance();
				m_dialog = new AlertDialog.Builder(BaseUpdateActivity.this)
						.setTitle("发现新版本")
						.setMessage(versionDesc.replace("@", "\n"))
						// 设置内容
						.setPositiveButton("立即更新",// 设置确定按钮
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {										
										m_dialog.cancel();
										//创建下载进度条
										progressDialog = new ProgressDialog(BaseUpdateActivity.this); // 创建进度对话框
										progressDialog.setMax(100); // 进度最大值
										progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
										progressDialog.setTitle("软件更新"); // 设置标题
										progressDialog.setCancelable(false); // 进度条不能用回退按钮关闭										
										progressDialog.incrementProgressBy(-progressDialog.getProgress());
										progressDialog.show();
										
										//下载地址
										String url = apkAddress;
										apkPathServer = apkPathServer.replace("\\", "/");
										url += apkPathServer;
										//创建目录
										File file = new File(DcAndroidConsts.APK_PATH);
										if (!file.exists())
											file.mkdirs();								
										apkFilePath = DcAndroidConsts.APK_PATH + lastestVersion + ".apk";
										Request request = new Request();
										request.requestUrl = url;
										request.dstFilePath = apkFilePath;
										//注册监听执行下载任务
										DownloadFileManager.getInstance().registDownloadTaskListener(url, mDownloadListener);
										DownloadFileManager.getInstance().downloadFile(request);
									
									}
								})
						.setNegativeButton("稍后再说",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 点击"取消"按钮之后退出程序
										dialog.cancel();
										try {
											if(isupdateapk.equals("1"))
											{
												BaseUpdateActivity.this.finish();
											}
											else
											{
												updateFinish();
											}
										} catch (Throwable e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
									}
								}).create();// 创建
				m_dialog.setCancelable(false);
				m_dialog.show();
				break;

			case DcAndroidConsts.down_error:
				//失败
				Toast.makeText(BaseUpdateActivity.this, "系统更新异常，未能自动更新",
						Toast.LENGTH_LONG).show();
				progressDialog.dismiss();
			case DcAndroidConsts.down_cancle:
				updateFinish();
				break;
			default:
				return;
			}
		}
	}
	
	

	private class DownloadTaskListener implements com.gaotai.baselib.download.DownloadTaskListener{

		@Override
		public void onTaskAdded() {
			Log.d(TAG, "onTaskAdded()");
			
		}

		@Override
		public void onTaskWaiting() {
			Log.d(TAG, "onTaskWaiting()");
			
		}

		@Override
		public void onTaskStart() {
			Log.d(TAG, "onTaskStart()");
			
		}

		@Override
		public void onTaskRunning(long curSize, long totalSize) {
			//任务进行中    已下载文件大小   文件总计大小
			Log.d(TAG, String.format("onTaskRunning() [%d,%d]", curSize,totalSize));
			progressDialog.setMax((int) totalSize/1000);
			int progress = (int)(curSize / totalSize);
			progressDialog.setProgress((int)curSize/1000);			
			progressDialog.incrementProgressBy(progress);
			//DownloadTestActivity.this.mProgressBar0.setProgress(progress);
		}

		@Override
		public void onTaskStop() {
			Log.d(TAG, "onTaskStop()");
			progressDialog.dismiss();
			Toast.makeText(BaseUpdateActivity.this, "已停止更新任务！",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onTaskFinished(String filepath) {
			Log.d(TAG, "onTaskFinished()");
			progressDialog.dismiss();
			if (!reboot(apkFilePath))
				//下载APK完毕 安装
				reboot(apkFilePath);
			BaseUpdateActivity.this.finish();
		}

		@Override
		public void onTaskError(int code, String msg) {
			Log.d(TAG, "onTaskError()");
			Toast.makeText(BaseUpdateActivity.this, "自动更新失败,请稍后重试！",
					Toast.LENGTH_LONG).show();
			progressDialog.dismiss();
			updateFinish();
		}
	}
	
	/**
	 * 打开安装包
	 * @param url  文件路径
	 * @return
	 */
	public boolean reboot(String url) {
		try {
			if (!AndroidUtil.isHasSDCard()) {
				chmodRw(url);
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.parse("file://" + url),
					"application/vnd.android.package-archive");
			BaseUpdateActivity.this.startActivity(i);
			//context.finish();
		} catch (Exception ex) {
			Toast.makeText(BaseUpdateActivity.this, "程序出错，无法重新安装主程序", Toast.LENGTH_LONG).show();
			Log.e("error", ex.getMessage());
			return false;
		}		
		return true;
	}

	/**
	 * android 系统文件
	 * @param url 文件路径
	 */
	private void chmodRw(String url) {
		try {
			// [文件夹705:drwx---r-x]
			String[] args1 = {
					"chmod",
					"705",DcAndroidConsts.APK_PATH };
			Runtime.getRuntime().exec(args1);
			// [文件604:-rw----r--]
			String[] args2 = { "chmod", "604", url };
			Runtime.getRuntime().exec(args2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
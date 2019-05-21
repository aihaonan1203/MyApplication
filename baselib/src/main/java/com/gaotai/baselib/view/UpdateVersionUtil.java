package com.gaotai.baselib.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import java.io.File;
import java.io.IOException;

public class UpdateVersionUtil {

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
	 *  版本获取地址
	 */
	private String versionServletAddress = "";
	/**
	 * apk下载地址
	 */
	public String apkAddress = "";

	/**
	 *
	 * @param _myactivity
	 * @param versionServletAddress 版本获取地址
	 * @param apkAddress  apk下载地址 前缀
	 */
	public UpdateVersionUtil(Activity _myactivity,String versionServletAddress,String apkAddress){
		this.myactivity = _myactivity;	
		TaskDBManager.init(this.myactivity, false, "task.db");
		this.versionServletAddress = versionServletAddress;
		this.apkAddress = apkAddress;
	}
	/**
	 *  如是最新版是否显示文字提示
	 */
	private boolean isShowText = false;
	/**
	 * 获取更新并显示下载弹出框
	 * @param isshow   如是最新版是否显示文字提示
	 */
	public void getUpdateActiviy(boolean isshow) {
		isShowText = isshow;
		if (AndroidUtil.isHasNetWork(myactivity)) {		
			 //创建消息循环
			mHandler = new UpdateHandler();
			 //获取当前安装包版本 
			String version = AndroidUtil.getVerName(myactivity);
			 //启动获取版本文件线程
			UpdateVersion updateVersion = new UpdateVersion(mHandler, version,versionServletAddress);
			updateVersion.start();
		}else {
			if(isShowText)
			{
				Toast.makeText(myactivity, "请先打开网络,否则无法正常使用软件", Toast.LENGTH_LONG)
				.show();
			}
		}
	}	
	
	
	class UpdateHandler extends Handler {
		int fileSize = 0;

		@Override
		public void handleMessage(Message msg) {				
			if (myactivity.isFinishing()) {// 如果已结束,则不再弹更新对话框进行升级
				return;
			}
			Bundle data = msg.getData();

			switch (msg.what) {
			case DcAndroidConsts.down_file:
				String errorMsg = data.getString("errorMsg");
				if (!TextUtils.isEmpty(errorMsg)) {
					Toast.makeText(myactivity, errorMsg,
							Toast.LENGTH_LONG).show();				
					break;
				}

				apkPathServer = data.getString("apkUrl");
				lastestVersion = data.getString("lastestVersion");
				versionDesc  = data.getString("versionDesc");
				if(versionDesc.equals(""))
				{
					versionDesc = "发现新版本，请更新！";
				}
				DownloadFileManager.getInstance();
				m_dialog = new AlertDialog.Builder(myactivity)
						.setTitle("发现新版本")
						.setMessage(versionDesc.replace("@", "\n"))
						// 设置内容
						.setPositiveButton("立即更新",// 设置确定按钮
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {										
										m_dialog.cancel();											
										progressDialog = new ProgressDialog(myactivity); // 创建进度对话框
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
										
										DownloadFileManager.getInstance().registDownloadTaskListener(url, mDownloadListener);
										DownloadFileManager.getInstance().downloadFile(request);
										/*
										UpdateDownload down = new UpdateDownload(mHandler,
												apkPathServer, lastestVersion, myactivity);
										down.start();
										*/
									}

									private void Activity(Context myactivity) {
										// TODO Auto-generated method stub
									}
								})
						.setNegativeButton("稍后再说",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 点击"取消"按钮之后退出程序
										dialog.cancel();										
									}
								}).create();// 创建
				m_dialog.setCancelable(false);
				m_dialog.show();
				break;

			case DcAndroidConsts.down_error:
				Toast.makeText(myactivity, "系统更新异常，未能自动更新",
						Toast.LENGTH_LONG).show();
				try {
					progressDialog.dismiss();
				}
				catch (Exception ex){
					ex.printStackTrace();
				}
			case DcAndroidConsts.down_cancle:
				if(isShowText)
				{
					Toast.makeText(myactivity, "当前是最新版本，无需更新", Toast.LENGTH_LONG)
					.show();
				}
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
			Toast.makeText(myactivity, "已停止更新任务！",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onTaskFinished(String filepath) {
			Log.d(TAG, "onTaskFinished()");
			progressDialog.dismiss();
			if (!reboot(apkFilePath))
				//下载APK完毕 安装
				reboot(apkFilePath);
		}

		@Override
		public void onTaskError(int code, String msg) {
			Log.d(TAG, "onTaskError()");
			Toast.makeText(myactivity, "自动更新失败,请稍后重试！",
					Toast.LENGTH_LONG).show();
			progressDialog.dismiss();
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
			myactivity.startActivity(i);
			//context.finish();
		} catch (Exception ex) {
			Toast.makeText(myactivity, "程序出错，无法重新安装主程序", Toast.LENGTH_LONG).show();
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

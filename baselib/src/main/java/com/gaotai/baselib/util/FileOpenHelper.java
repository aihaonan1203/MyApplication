package com.gaotai.baselib.util;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

/**
 * 文件打开相关操作
 * @author MengLiang
 */
public class FileOpenHelper {

	private static FileOpenHelper fo;
	private String path = "";

	private FileOpenHelper() {

	}

	public static FileOpenHelper getInstance() {
		if (fo == null) {
			return new FileOpenHelper();
		}
		return fo;

	}

	public Intent open(String type, String path) {
		if (type.equals("doc") || type.equals("docx")) {
			return getWordFileIntent(path);
		} else if (type.equals("xls") || type.equals("xlsx")) {
			return getExcelFileIntent(path);
		} else if (type.equals("ppt") || type.equals("pptx")) {
			return getPptFileIntent(path);
		} else if (type.equals("txt")) {
			return getTextFileIntent(path);
		} else if ((type.contains("jpg") || type.contains("jpeg") || type.contains("png") || type.contains("gif") || type.contains("bmp"))) {
			return getPhotoFileIntent(path);
		} else if ((type.contains("zip") || type.contains("rar") || type.contains("cab") || type.contains("7z"))) {
			return null;
		} else if ((type.contains("ra") || type.contains("wma") || type.contains("mp3") || type.contains("mpg") || type.contains("mpeg") || type.contains("avi")
				|| type.contains("wmv") || type.contains("mp4") || type.contains("rm") || type.contains("rmvb") || type.contains("3gp") || type.contains("asf") || type
					.contains("rm"))) {
			return getVideoFileIntent(path);
		} else if (type.contains("swf") || type.contains("flv")) {
			return getVideoFileIntent(path);
		} else if (type.contains("pdf")) {
			return getPdfFileIntent(path);
		} else {
			return null;
		}
	}

	/**
	 * 获取一个用于打开Word文件的intent
	 * @param param
	 * @return
	 */
	private Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	/**
	 * 获取一个用于打开Excel文件的intent
	 * @param param
	 * @return
	 */
	private Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;

	}

	/**
	 * 获取一个用于打开PPT文件的intent
	 * @param param
	 * @return
	 */
	private Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	/**
	 * android获取一个用于打开文本文件的intent
	 * @param param
	 * @return
	 */
	public static Intent getTextFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri2 = Uri.fromFile(new File(param));
		intent.setDataAndType(uri2, "text/plain");
		return intent;
	}

	/**
	 * 打开本地图片
	 * 
	 * @param param
	 * @return
	 */
	private Intent getPhotoFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	/**
	 * 打开本地视频文件
	 * 
	 * @param param
	 * @return
	 */
	private Intent getVideoFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	/**
	 * 打开本地FLASH文件
	 * 
	 * @param param
	 * @return
	 */
	private Intent getFlashFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "flash/*");
		return intent;
	}

	/**
	 * 打开本地PDF文件
	 */
	private Intent getPdfFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

}

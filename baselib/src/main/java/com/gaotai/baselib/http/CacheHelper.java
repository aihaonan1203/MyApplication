package com.gaotai.baselib.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.gaotai.baselib.DcAndroidConsts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class CacheHelper {
	private final String TAG = "IMAGECACHE";
	private final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;// 要求SD卡至少需要剩余的空间(MB)
	private final int CACHE_SIZE = 9; // 缓存大小(MB)
	private final double MB = 1024 * 1024;
	private final long mTimeDiff = 1000 * 3600 * 24;
	private final String FILE_PATH = DcAndroidConsts.CLIENET_FILEPATH_PROPERTY;
	private final String WHOLESALE_CONV = ".png";

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 给出文件名 检查文件是否存在，存在则加载，不存在则
	 * 
	 */
	public Boolean GetBoolImage(String filename) {
		try {
			String filepath = FILE_PATH + "/" + filename;
			File file = new File(filepath);
			if (file.exists()) {
				return true;
			}
		} catch (OutOfMemoryError err) {
		}
		return false;
	}

	/**
	 * 给出文件名 检查文件是否存在，存在则加载，不存在则
	 * 
	 */
	public Bitmap GetCacheImage(String filename) {
		Bitmap result = null;
		try {
			String filepath = FILE_PATH + "/" + filename;
			File file = new File(filepath);
			if (file.exists()) {
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(filepath, opts);
				// result = BitmapFactory.decodeFile(filepath);
				
				//压缩 
				//opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
				opts.inJustDecodeBounds = false;

				result = BitmapFactory.decodeFile(filepath, opts);

			}
		} catch (OutOfMemoryError err) {
		}

		return result;
	}

	/**
	 * 保存图片到SD卡上
	 * 
	 * @param bm
	 *            图片源
	 * @param dir
	 *            图片保存的目录
	 * @param filename
	 *            图片保存的文件名
	 */
	public String saveBmpToSd(Bitmap bm, String filename) {
		if (bm == null) {
			Log.w(TAG, " trying to savenull bitmap");
			return "";
		}
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			Log.w(TAG, "Low free space onsd, do not cache");
			return "";
		}
		
		//判断是否存在 不存在创建目录
		File dir = new File(FILE_PATH);
		if (! dir.exists() || !dir.isDirectory()) {
		    dir.mkdirs();
		}
		
		File file = new File(FILE_PATH + "/" + filename);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			Log.i(TAG, "Image " + filename + " saved");

			// removeCache();
			return filename;
		} catch (FileNotFoundException e) {
			Log.w(TAG, "FileNotFoundException");
		} catch (IOException e) {
			Log.w(TAG, "IOException" + e.getMessage());
		}
		return "";
	}

	/**
	 * 计算sdcard上的剩余空间
	 * 
	 * @return
	 **/
	public int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 修改文件的最后修改时间
	 * 
	 * @param dir
	 * @param fileName
	 **/
	private void updateFileTime(String fileName) {
		File file = new File(FILE_PATH, fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * 删除废物图片文件
	 * 
	 * @param filenames
	 */
	public void removeWasterCache(HashMap<String, Object> map_image) {
		File dir = new File(FILE_PATH);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(WHOLESALE_CONV)) {
				if (map_image.containsKey(files[i].getName())) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 计算存储目录下的文件大小，
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件 *
	 * 
	 * @param dirPath
	 * @param filename
	 */
	private void removeCache() {
		File dir = new File(FILE_PATH);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(WHOLESALE_CONV)) {
				dirSize += files[i].length();
			}
		}
		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Comparator<File> filelastmodifsort = (Comparator<File>) new FileLastModifSort();
			Arrays.sort(files, filelastmodifsort);
			Log.i(TAG, "Clear some expiredcache files ");
			for (int i = 0; i < removeFactor; i++) {
				if (files[i].getName().contains(WHOLESALE_CONV)) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除过期文件 * @param dirPath
	 * 
	 * @param filename
	 */
	private void removeExpiredCache(String filename) {
		File file = new File(FILE_PATH, filename);
		if (System.currentTimeMillis() - file.lastModified() > mTimeDiff) {
			Log.i(TAG, "Clear some expiredcache files ");
			file.delete();
		}
	}
}

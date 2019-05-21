package com.gaotai.baselib.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.text.TextUtils;

import com.gaotai.baselib.util.FileUtils;

/**
 * 
 * <p>
 * 提供通过Http协议下载的各种接口
 * </p>
 * 
 * @version 1.0
 * @create 2011-7-1
 */
public class HttpDownloader {

	private URL url = null;

	/**
	 * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容 1.创建一个URL对象
	 * 2.通过URL对象,创建一个HttpURLConnection对象 3.得到InputStream 4.从InputStream当中读取数据
	 * 
	 * @param urlStr
	 *            下载文件的地址
	 * @return 服务器返回的结果字符串
	 */
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param urlStr
	 *            下载文件的服务器地址
	 * @param fileName
	 *            本地需要保存的文件名称（包含全路径）
	 * @param cookieStr
	 *            拼接好的cookie串
	 * @return -1:文件下载出错 0:文件下载成功 1:文件已经存在
	 */
	public int downFile(String urlStr, String fileName, String cookieStr) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();

			if (fileUtils.isFileExist(fileName)) {
				return 1;
			}

			inputStream = getInputStreamFromURL(urlStr, cookieStr);
			File resultFile = fileUtils
					.write2SDFromInput(fileName, inputStream);
			if (resultFile == null) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 *            服务器地址
	 * @return 返回一个InputStream输入流
	 */
	public InputStream getInputStreamFromURL(String urlStr, String cookieStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			if (!TextUtils.isEmpty(cookieStr)) {
				urlConn.setRequestProperty("Cookie", cookieStr);
			}
			inputStream = urlConn.getInputStream();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputStream;
	}

	// 从网络上取数据方法
	public byte[] loadImageByteFromUrl(String imageUrl) {
		try {
			// 测试时，模拟网络延时，实际时这行代码不能有
			// SystemClock.sleep(2000);
			// Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter)
			URL url = new URL(imageUrl);

			// URLConection conn=url.openConnection();

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(6 * 1000); // 设置链接超时时间6s

			conn.setRequestMethod("GET");

			// conn.connect();

			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();

				byte[] buffer = new byte[1024];
				int len = -1;
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				while ((len = inputStream.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
				inputStream.close();
				byteArrayOutputStream.close();

				byte[] byteArray = byteArrayOutputStream.toByteArray();

				return byteArray;
			}

		} catch (Exception e) {
			// throw new RuntimeException(e);
		}
		return null;
	}
}

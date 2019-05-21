package com.gaotai.baselib.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;

public class AsyncImageLoader {
	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	public Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	private ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务
	private final Handler handler = new Handler();

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Bitmap loadDrawable(final String imageUrl,final String appid,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap bitmap = loadImageFromUrl(imageUrl); 
						
					imageCache.put(imageUrl, new SoftReference<Bitmap>(
							bitmap));

					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(bitmap);
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}
	
	
	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Bitmap loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap bitmap = loadImageFromUrl(imageUrl);

					imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));

					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(bitmap);
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}

	// 从网络上取数据方法
	public Bitmap loadImageFromUrl(String imageUrl) {
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

				return BitmapFactory.decodeByteArray(byteArray, 0,
						byteArray.length);
			}
			return null;

			// return Drawable.createFromStream(new
			// URL(imageUrl).openStream(),"image.png");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
				//throw new RuntimeException(e);
		}
		return null;		
	}
		
	// 对外界开放的回调接口
	public interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Bitmap bitmap);
	}
	
	// 对外界开放的回调接口
	public interface ImageUrlCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Bitmap bitmap,String imageUrl,String appId);
	}

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Bitmap loadDrawable(final String imageUrl,final String appid,
			final ImageUrlCallback callback) {
		// 如果缓存过就从缓存中取出数据		
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap bitmap = loadImageFromUrl(imageUrl); 
						
					imageCache.put(imageUrl, new SoftReference<Bitmap>(
							bitmap));

					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(bitmap,imageUrl,appid);
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}
	
}

package com.gaotai.baselib;


import java.util.HashMap;
import java.util.Map;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.Map.Entry;
import java.util.Set;

import com.gaotai.baselib.download.DownloadFileManager;
import com.gaotai.baselib.download.TaskDBManager;
import com.gaotai.baselib.handler.CrashHandler;
import com.gaotai.baselib.listener.ParamChangeListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Application;

/**
 * 全局上下文 提供初始化全局信息
 * 
 * @author MengLiang
 */
public class DcAndroidContext extends Application
{
	private Map<String, Object> context;

	private static DcAndroidContext instance;

	public static DcAndroidContext getInstance()
	{
		return instance;
	}

	/**
	 * 数据库配置
	 */
	public DbManager.DaoConfig daoConfig;

	public DcAndroidContext()
	{
		context = new HashMap<String, Object>();
	}


	private Map<String, ParamChangeListener> listeners = new HashMap<String, ParamChangeListener>();


	/**
	 * 添加属性值变化监听器。同名监听器只保留最后添加的一个
	 * @param listenerName 监听器名称
	 * @param listener 监听器
	 */
	public void addListener(String listenerName, ParamChangeListener listener){
		listeners.put(listenerName, listener);
	}

	/**
	 * 删除属性值变化监听器
	 * @param listenerName
	 */
	public void removeListener(String listenerName){
		listeners.remove(listenerName);
	}

	/**
	 * 获取上下文中的key值
	 *
	 * @param key
	 * @return
	 */
	public Object getParam(String key) {
		return context.get(key);
	}


	/**
	 * 设置key值
	 *
	 * @param key
	 * @return
	 */
	public void setParam(String key, Object value) {
		Object oldValue = this.context.put(key, value);
		Set<Entry<String, ParamChangeListener>> entrys = listeners.entrySet();
		for(Entry<String, ParamChangeListener> listener : entrys){
			listener.getValue().onParamChanged(key, value, oldValue);
		}
	}

	/**
	 * 移除key
	 *
	 * @param key
	 * @return
	 */
	public void removeParam(String key) {
		Object oldValue = this.context.remove(key);
		Set<Entry<String, ParamChangeListener>> entrys = listeners.entrySet();
		for(Entry<String, ParamChangeListener> listener : entrys){
			listener.getValue().onParamChanged(key, null, oldValue);
		}
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		x.Ext.init(this);
	    //x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
		instance = this;

		//在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext());

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽  
				//.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个  
				.threadPoolSize(3)
				//线程池内加载的数量  
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				//.memoryCache(new LruMemoryCache(10 * 1024 * 1024))
				//建议内存设在5-10M,可以有比较好的表现
				//.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现  
				.memoryCacheSize(5 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				//将保存的时候的URI名称用MD5 加密  
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 设置图片下载和显示的工作队列排序
				.discCacheFileCount(2000)
				//缓存的文件数量  
				//.discCache(new DiskCache(cacheDir))//自定义缓存路径  
				//.discCache(new UnlimitedDiscCache(cacheDir)) 
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间  
				//.imageDecoder(new BaseImageDecoder())
				.writeDebugLogs() // Remove for release app  
				.build();//开始构建  

		ImageLoader.getInstance().init(config);
			
		//初始下载任务
		TaskDBManager.init(this, false, DcAndroidConsts.DB_TASK_NAME);
		DownloadFileManager.getInstance();
	}

}

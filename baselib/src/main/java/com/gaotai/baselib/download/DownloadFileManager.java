package  com.gaotai.baselib.download;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

// @formatter:off
/**
 * @author Ji.Li
 * @Create at 2015-6-30
 * @Version 1.0
 * <p><strong>Features draft description.主要功能介绍
 *            1、注册任务监听器
 *            2、提供操作任务的接口，向Engine转发客户的请求
 *            3、根据客户端的请求，初始化Engine及任务
 *            4、根据任务返回的信息，通知UI及更新数据库
 *            </strong></p>
 */
// @formatter:on
public class DownloadFileManager {

    // ===========================================================
    // Constants
    // ===========================================================

	private static final String TAG = DownloadFileManager.class.getSimpleName();
    
    private static final int                      MSG_TASK_START    = 1;
	private static final int                      MSG_TASK_STOP     = 2;
	private static final int                      MSG_TASK_FINISH   = 3;
	private static final int                      MSG_TASK_ERROR    = 4;
	private static final int                      MSG_TASK_PROGRESS = 5;
    
    // ===========================================================
    // Fields
    // ===========================================================

	//volatile关键字，在双重校验中避免产生多个实例
	private static volatile DownloadFileManager   sInstance = null;
	private final ReadWriteLock                   LISTENER_RW_LOCK;
	private final Lock                            LISTENER_R_LOCK;
	private final Lock                            LISTENER_W_LOCK;
	private HashMap<String, DownloadTaskListener> mNotifyListeners;
	private Configuration                         mConfiguration;
	private volatile Handler                      mHandler;
	private NotifyUITask                          mNotifyUITask;
	private DownloadFileEngine                    mEngine;
	
	// ===========================================================
    // Constructors
    // ===========================================================
    
    private DownloadFileManager(){
        this.mNotifyListeners = new HashMap<String,DownloadTaskListener>();
        this.mNotifyUITask = new NotifyUITask();
        this.mConfiguration = new Configuration();
        this.mEngine = new DownloadFileEngine(this, mConfiguration);
        this.LISTENER_RW_LOCK = new ReentrantReadWriteLock(false);
        this.LISTENER_R_LOCK = this.LISTENER_RW_LOCK.readLock();
        this.LISTENER_W_LOCK = this.LISTENER_RW_LOCK.writeLock();
        prepareHandler();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

	public static DownloadFileManager getInstance() {
		if (sInstance == null) {
			synchronized (DownloadFileManager.class) {
				if (sInstance == null) {
					sInstance = new DownloadFileManager();
				}
			}
		}
		return sInstance;
	}
	
    public void registDownloadTaskListener(String url,DownloadTaskListener listener){
    	//显然，对Listener的Map Read操作要远多余Write 操作
    	Log.d(TAG, "registDownloadTaskListener() url = "+ url);
    	LISTENER_W_LOCK.lock();
    	try{
    		this.mNotifyListeners.put(url, listener);
    	}finally{
    		LISTENER_W_LOCK.unlock();
    	}
    }
    
    public void unregistDownloadTaskListener(String url){
    	LISTENER_W_LOCK.lock();
    	try{
    		this.mNotifyListeners.remove(url);
    	}finally{
    		LISTENER_W_LOCK.unlock();
    	}
    }
    
    /**
     * 下载文件 放入队列
     * @param request
     */
    public void downloadFile(Request request){
    	Log.d(TAG, "downloadFile() url = "+ request.requestUrl);
    	this.mEngine.enqueue(request);
    }
    
    /**
     * 停止下载
     * @param url
     */
    public void stopDownload(String url){
    	Log.d(TAG, "stopDownload() url = "+ url);
    	this.mEngine.stopTask(url);
    }
    
    /**
     * 删除任务和文件
     * @param url
     * @return
     */
    public boolean deleteTaskAndFileIfExist(String url){
    	return this.mEngine.deleteTaskAndFile(url);
    }
    
    private void prepareHandler(){
    	this.mHandler = new Handler(Looper.getMainLooper(), this.mNotifyUITask);
    }
    
    /**
     * 任务开始
     * @param url
     */
    void notifyTaskStart(String url){
    	Log.d(TAG, "notifyTaskStart() url = " + url);
    	//Handler 多线程访问,会不会出现问题
    	if (this.mHandler != null){
    		Message msg = Message.obtain();
    		msg.what = MSG_TASK_START;
    		msg.obj = url;
    		this.mHandler.sendMessage(msg);
    	}else{
    		DownloadTaskListener listener = getTaskListener(url);
        	if (listener != null){
        		listener.onTaskStart();
        	}
    	}
    }
    
    /**
     * 任务完成
     * @param url
     */
    void notifyTaskFinished(String url){
    	Log.d(TAG, "notifyTaskFinished() url = " + url);
    	if (this.mHandler != null){
    		Message msg = Message.obtain();
    		msg.what = MSG_TASK_FINISH;
    		msg.obj = url;
    		this.mHandler.sendMessage(msg);
    	}else{
    		DownloadTaskListener listener = getTaskListener(url);
        	if (listener != null){
        		listener.onTaskFinished(url);
        	}
    	}
    }
    /**
     * 下载任务异常
     * @param url  地址
     * @param errorCode  错误代码
     * @param errorMsg   错误说明
     */
	void notifyTaskError(String url, int errorCode, String errorMsg) {
		Log.d(TAG, String.format("{url,errorCode,errorMsg} = ", url,errorCode,errorMsg));
		if (this.mHandler != null) {
			Message msg = Message.obtain();
			msg.what = MSG_TASK_ERROR;
			StringBuilder sb = new StringBuilder();
			sb.append(url).append(",").append(String.valueOf(errorCode)).append(",").append(errorMsg);
			msg.obj = sb.toString();
			this.mHandler.sendMessage(msg);
		} else {
			DownloadTaskListener listener = getTaskListener(url);
			if (listener != null) {
				listener.onTaskError(errorCode, errorMsg);
			}
		}
	}
	
    /**
     * 下载成功通知
     * @param url
     * @param curBytes
     * @param totalBytes
     */
    void notifyTaskProgress(String url,long curBytes,long totalBytes){
    	Log.d(TAG, String.format("{url,curBytes,totalBytes} = ", url,curBytes,totalBytes));
    	if (this.mHandler != null) {
			Message msg = Message.obtain();
			msg.what = MSG_TASK_PROGRESS;
			StringBuilder sb = new StringBuilder();
			sb.append(url).append(",").append(String.valueOf(curBytes)).append(",").append(String.valueOf(totalBytes));
			msg.obj = sb.toString();
			this.mHandler.sendMessage(msg);
		} else {
			DownloadTaskListener listener = getTaskListener(url);
	    	if (listener != null){
	    		listener.onTaskRunning(curBytes, totalBytes);
	    	}
		}
    }
    
    /**
     * 通知任务停止
     * @param url
     */
    void notifyTaskStop(String url){
    	Log.d(TAG, "notifyTaskStop() url = "+ url);
    	if (this.mHandler != null){
    		Message msg = Message.obtain();
    		msg.what = MSG_TASK_STOP;
    		msg.obj = url;
    		this.mHandler.sendMessage(msg);
    	}else{
    		DownloadTaskListener listener = getTaskListener(url);
        	if (listener != null){
        		listener.onTaskStop();
        	}
    	}
    }
    
    /**
     * 更新任务
     * @param taskID
     * @param task
     */
    void updateTaskInfo(int taskID,Task task){
    	/**
    	 * 现在是单线程访问,Task可能存在脏数据;Task可能被多个线程写吗，多线程读不会出现问题
    	 */
    	//Log.d(TAG, "updateTaskInfo() taskID = "+ taskID);
    	TaskDBManager manager = TaskDBManager.getInstance();
    	manager.insertOrUpdateTask(task);
    }
    
    private DownloadTaskListener getTaskListener(String url){
    	if (url == null){
    		return null;
    	}
    	LISTENER_R_LOCK.lock();
    	try{
    		return this.mNotifyListeners.get(url);
    	}finally{
    		LISTENER_R_LOCK.unlock();
    	}
    }
    
    // ===========================================================
    // Native Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    public static class Configuration{
    	/**
    	 * 链接超时秒数
    	 */
    	public static final int DEFAULT_CONNECT_TIMEOUT   = 3000;
    	/**
    	 * 读取超时
    	 */
    	public static final int DEFAULT_READ_TIMEOUT      = 3000;
    	/**
    	 * 线程更新毫秒数   Million seconds
    	 */
    	public static final int DEFAULT_NOTIFY_INTERVALUE = 1000;
    	/**
    	 * 超时秒数
    	 */
    	public int     connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    	/**
    	 * 读取超时
    	 */
    	public int     readTimeout    = DEFAULT_READ_TIMEOUT;
    	/**
    	 * 线程更新毫秒数   Million seconds
    	 */
    	public int     notifyProgressInterVal = 1000; 
    	/**
    	 * 每个任务的线程数
    	 */
    	public static final int DEFAULT_THREAD_NUM = 1;
    }
    
    public class NotifyUITask implements Handler.Callback{

		@Override
		public boolean handleMessage(Message msg) {
			String content = (String)msg.obj;
			//url,aram1,param2
			String key;
			String[] arr = new String[3];
			if (content != null){
				arr = content.split(",");
				if (arr == null || arr.length == 0){
					return true;
				}
				key = arr[0];
			}else{
				key = null;
			}
			Log.d(TAG, "handleMessage() content = " + content);
			DownloadTaskListener listener = getTaskListener(key);
			if (listener == null){
				return true;
			}
			int what = msg.what;
			switch(what){
			case MSG_TASK_PROGRESS:
				long curSize = Long.parseLong(arr[1]);
				long totalSize = Long.parseLong(arr[2]);
				listener.onTaskRunning(curSize, totalSize);
				break;
			case MSG_TASK_START:
			    listener.onTaskStart();
				break;
			case MSG_TASK_STOP:
				listener.onTaskStop();
				break;
			case MSG_TASK_FINISH:
				String filepath = "";
	    		TaskDBManager dbManager = TaskDBManager.getInstance();
	    		List<Task> tasks = dbManager.queryTask(key);
	        	if (tasks != null && tasks.size() > 0){
	        		for (Task task:tasks){
	        			filepath = task.filePath;
	        		}
	        	}
				listener.onTaskFinished(filepath);
				break;
			case MSG_TASK_ERROR:
				int code = Integer.parseInt(arr[1]);
				String errormsg = arr[2];
				listener.onTaskError(code, errormsg);
				break;
		    default:;
			}
			return true;
		}
    }
}

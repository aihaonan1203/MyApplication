package  com.gaotai.baselib.download;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.gaotai.baselib.download.FileOperateUtil;
import com.gaotai.baselib.download.DownloadFileManager.Configuration;

class DownloadFileEngine {

	private static final String TAG = DownloadFileEngine.class.getSimpleName();
	
	public static final int MSG_QUERY_DB_TASK = 0x101;
	
	private ExecutorService mTasksExcutor;
	/**
	 * 同时运行的最大任务数
	 */
	private int mMaxThreadSize = 4;
	private DownloadFileManager mDownloadManager;
	private Map<String, DownloadFileTask> mEngineTasks;  
	private Configuration mConfiguration;
	
	private final AtomicBoolean mStarted = new AtomicBoolean(false);
	
	//请求队列,用于在线程的Looper未prepared时候,缓存请求的数据
	private boolean             mIsPendingRequestsNotExecuted = true;
	private List<Request>       mPendingRequests;
	private HandlerThread       mQueryTaskThread;
	private Handler             mQueryTaskHandler;
	
	public DownloadFileEngine(DownloadFileManager manager,Configuration configuration) {
		this.mQueryTaskThread = new QueryTaskThread("QueryTaskThread");
	    this.mQueryTaskThread.start();
	    Log.d(TAG, " mQueryTaskThread start");
		this.mEngineTasks = Collections.synchronizedMap(new HashMap<String,DownloadFileTask>());
        this.mTasksExcutor = Executors.newFixedThreadPool(mMaxThreadSize,new TaskThreadFactory());
        this.mConfiguration = configuration;
        this.mDownloadManager = manager;
        this.mPendingRequests = Collections.synchronizedList(new ArrayList<Request>());     
	}
	
    void enqueue(Request req){
    	if (req == null) {
            return;
        }
    	Log.d(TAG, "enqueue url = " + req.requestUrl);
        if (!URLUtil.isValidUrl(req.requestUrl)) {
        	Log.d(TAG, "enqueue Request url is not valid ");
            throw new IllegalArgumentException("Request url is not valid");
        }
        if (this.mQueryTaskHandler != null){
        	Log.d(TAG, "enqueue mQueryTaskHandler sendMessage");
        	Message msg = Message.obtain();
        	msg.what = MSG_QUERY_DB_TASK;
        	msg.obj = req;
        	this.mQueryTaskHandler.sendMessage(msg);
        }else{
        	Log.d(TAG, "enqueue mPendingRequests add req ");
        	this.mPendingRequests.add(req);
        }
    }
    
    /**
     * 停止任务
     * @param url
     */
    void stopTask(String url){
    	Log.d(TAG, "enter stopTask() url = " + url);
    	DownloadFileTask task = this.mEngineTasks.get(url);
    	if (task != null) {
    		Log.d(TAG, "stopTask() url = " + url);
    		task.cancel();
    		this.mEngineTasks.remove(url);
    	}
    }
    
    /**
     * 开始
     * @param isStartLaskTasks
     */
    void start(boolean isStartLaskTasks){
    	this.mStarted.set(true);
    }
    
	/**
	 * 停止任务
	 */
    void stop(){
    	stopAllRunningTask();
    	this.mStarted.set(true);
    }
    
    /**
     * 停止所有运行的任务
     */
    private void stopAllRunningTask(){
    	Map<String, DownloadFileTask> tasks = this.mEngineTasks;
    	Collection<DownloadFileTask> collection = tasks.values();
    	for (DownloadFileTask task:collection){
    		task.cancel();
    	}
    	tasks.clear();
    }
    
    /**
     * 根据网络地址删除下载任务及下载的文件
     * @param url
     * @return
     */
    public boolean deleteTaskAndFile(String url){
    	if (url == null){
    		return false;
    	}
    	TaskDBManager dbManager = TaskDBManager.getInstance();
    	List<Task> tasks = dbManager.queryTask(url);
    	if (tasks != null && tasks.size() > 0){
    		for (Task task:tasks){
    			dbManager.deleteTaskIfExist(task.url);
    			String filePath = task.filePath;
    			FileOperateUtil.delete(filePath);
    		}
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 任务查询线程  
     */
    private class QueryTaskThread extends HandlerThread{

		public QueryTaskThread(String name) {
			super(name);
		}

		@Override
		protected void onLooperPrepared() {
			DownloadFileEngine.this.mQueryTaskHandler = new Handler(new QueryDBTask());
			Log.d(TAG, " mQueryTaskThread end");
		}
    }
    
    private static class TaskThreadFactory implements java.util.concurrent.ThreadFactory{

    	private static final String THREAD_NAME_PREFIX = "DownloadFileThread-";
    	
    	private AtomicInteger mThreadCount = new AtomicInteger(0);
    	
		@Override
		public Thread newThread(Runnable runnable) {
			Thread th = new Thread(runnable, THREAD_NAME_PREFIX + mThreadCount.get());
			th.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			this.mThreadCount.incrementAndGet();
			return th;
		}
    	
    }
    
    private class QueryDBTask implements Handler.Callback{

		@Override
		public boolean handleMessage(Message msg) {
			Log.d(TAG, "QueryDBTask === ");
			int what = msg.what;
			Object obj = msg.obj;
			if (obj == null){
				return true;
			}			
			//检查下是否在线程未启动之前,是否有未执行的请求
			DownloadFileEngine engine = DownloadFileEngine.this;
			if (engine.mIsPendingRequestsNotExecuted){
				for (Request req:engine.mPendingRequests){
					queryAndDownload(req);
				}
				//以原子的方式设置为false,set(newValue) volatile关键字并不能bao zheng
				engine.mIsPendingRequestsNotExecuted = false;
			}
			if (what == MSG_QUERY_DB_TASK){
				Request req = (Request)obj;
				queryAndDownload(req);
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * 查询任务并且下载
		 * @param req
		 */
		private void queryAndDownload(Request req){
			//1、客户端调用此方法,queryTask()在客户端线程执行
	        //2、queryTask对于sqlite读操作,updateTaskInfo是对sqlite多线程写操作
	        //3、客户端线程存在写数据库的操作,insertTask()
			Log.d(TAG, "queryAndDownload url = " + req.requestUrl);
			
			DownloadFileEngine engine = DownloadFileEngine.this;
	    	TaskDBManager dbManager = TaskDBManager.getInstance();
	    	String url = req.requestUrl;
	    	String filepath = req.dstFilePath;
	    	List<Task> tasks = dbManager.queryTask(url,filepath);
	    	
	    	//验证文件是否存在  没有被物理删除
	    	boolean isReload = false; //是否重新下载
	    	if(tasks != null  && tasks.size() > 0)
	    	{
	    		if(!DownloadFileTask.isExistsFile(tasks.get(0)))
	    		{
	    			isReload = true;
	    		}
	    	}
	    	
	    	if (tasks != null && tasks.size() > 0 && isReload == false){
	    		// task is already exist
	    		Task task = tasks.get(0);
	    		int status = task.status;
	    		DownloadFileTask downloadTask;
	    		switch(status){
	    		case Task.STATUS_INIT:
	    			 downloadTask = new DownloadFileTask(engine.mDownloadManager,task,mConfiguration);
		    		 engine.mEngineTasks.put(url, downloadTask);
		    		 engine.mTasksExcutor.execute(downloadTask);
	    			 break;
	    		case Task.STATUS_RUNNABLE:
	    		case Task.STATUS_STARTED:
	    			//为了处理异常退出，而引起的数据库状态不对的问题，还需要查询当前正在运行的任务列表
	    			Set<String> set = engine.mEngineTasks.keySet();
	    			for (String s:set){
	    			    if (TextUtils.equals(url, s)){
	    			    	break;
	    			    }
	    			} 
	    			//可能是上次异常退出引起db脏数据
	    			task.status = Task.STATUS_STOPPED; 
	    			downloadTask = new DownloadFileTask(engine.mDownloadManager, task , mConfiguration);
	    			engine.mEngineTasks.put(url, downloadTask);
	    			engine.mTasksExcutor.execute(downloadTask);
	    			break;
	    		case Task.STATUS_STOPPED:
	    			// restart task
	    			downloadTask = new DownloadFileTask(engine.mDownloadManager, task , mConfiguration);
	    			engine.mEngineTasks.put(url, downloadTask);
	    			engine.mTasksExcutor.execute(downloadTask);
	    			break;
	    		case Task.STATUS_ERROR:
	    			// delete and restart task
	    			//engine.mDownloadManager.deleteTaskAndFileIfExist(task.url);
	    			
	    			break;
	    		case Task.STATUS_FINISHED:
	    			//通知下载完成
	    			engine.mDownloadManager.notifyTaskFinished(task.url);
	    			break;
	    	    default:;
	    		}
	    	}else{
	    		//重新下载
	    		if(isReload)
	    		{
	    			//删除之前的任务
	    			dbManager.deleteTaskIfExist(url);
	    		}
	    		//新增下载任务 并执行下载
	    		Task task = Task.buildNewTask(req);
	    		Log.d(TAG, "insertTask()");
	    		dbManager.insertTask(task);
	    		task.ID = dbManager.queryTaskID(url,task.filePath);
	    		DownloadFileTask downloadTask = new DownloadFileTask(engine.mDownloadManager,task,mConfiguration);
	    		engine.mEngineTasks.put(url, downloadTask);
	    		engine.mTasksExcutor.execute(downloadTask);
	    	}
		}
    }
}

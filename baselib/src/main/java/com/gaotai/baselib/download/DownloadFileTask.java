package  com.gaotai.baselib.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

import com.gaotai.baselib.download.FileOperateUtil;
import com.gaotai.baselib.download.DownloadFileManager.Configuration;
/**
 * 文件下载任务控制
 */
public class DownloadFileTask implements Runnable{

    private static final String TAG = DownloadFileTask.class.getSimpleName();
   // private static final int BUFFER_SIZE = 4 * 1024; 
    
    //final 关键字声明的成员变量，可以在构造方法里面初始化
    private final DownloadFileManager  mDLManager;
    //private final byte[] mBuffer;
    private final Task   mTask;
    /**
	 * 链接超时秒数
	 */
    private final int    mConnectTimeoutMills;
    /**
	 * 读取超时
	 */
    private final int    mReadTimeoutMills;
    /**
	 * 线程更新毫秒数   Million seconds
	 */
    private final int    mNotifyProgressInterVal;
    /**
    * 是否停止线程
    */
    private volatile boolean mCancelled = false;
    /**
     * 开启的线程数  
     */
    private int threadNum;
    /**
     * 每一个线程的下载量  
     */
    private int blockSize;    
    
    /**
     * 下载大小分隔符
     */
    private String sizeSplit = ";"; 
    /**
     * 下载线程
     */
    private FileDownloadThread[] threads;
    
    public DownloadFileTask(DownloadFileManager manager,Task task,Configuration config){
    	this.mTask = task;
    	//this.mBuffer = new byte[BUFFER_SIZE];
    	this.mDLManager = manager;
    	if (config != null){
    		this.mReadTimeoutMills = config.readTimeout;
    		this.mConnectTimeoutMills = config.connectTimeout;
    		this.mNotifyProgressInterVal = config.notifyProgressInterVal;
    	}else{
    		this.mReadTimeoutMills = Configuration.DEFAULT_READ_TIMEOUT;
    		this.mConnectTimeoutMills = Configuration.DEFAULT_CONNECT_TIMEOUT;
    		this.mNotifyProgressInterVal = Configuration.DEFAULT_NOTIFY_INTERVALUE;
    	}
    	this.threadNum = Configuration.DEFAULT_THREAD_NUM;
    }
    
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		Log.d(TAG, name + "run() start");
		
		Task task = mTask;
		String urlStr = task.url;
		DownloadFileManager manager = mDLManager;
		
		threads = new FileDownloadThread[threadNum];  
				
        try {    
        	URL url = new URL(urlStr);  
            URLConnection conn = url.openConnection();  
             // 读取下载文件总大小  
             int fileSize = conn.getContentLength();  
             if (fileSize <= 0) {  
                 System.out.println("读取网络文件长度失败");                  
            	 throw new Exception("读取网络文件长度失败");
             }
             if(1024*500 > fileSize)
             {
            	 //小于500KB 只起一个线程
            	 threadNum =1;
            	 threads = new FileDownloadThread[threadNum];  
             }
             if(task.fileSize > 0)
             {
            	 //判断是否和之前的文件一致
            	 if(task.fileSize != fileSize)
            	 {
            		 //不一致 重新下载 不使用进度
            		 task.downloadedSize = "0";
            	 }
             }
             
             task.fileSize = fileSize;
             // 计算每条线程下载的数据长度  
             blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum  
                     : fileSize / threadNum + 1;  

             Log.d(TAG, "fileSize:" + fileSize + "  blockSize:");  
             int[] loadsize = new int[threadNum];
             //初始化下载值
             for (int i = 0; i < threadNum; i++) {  
            	 loadsize[i] = 0;
             }
             if(task.downloadedSize != null)
             {
	             //读取已经下载的文件大小
	             String[] strloadsize = task.downloadedSize.split(sizeSplit);
	             if(strloadsize.length >= threadNum)
	             {
	            	 for (int i = 0; i < threadNum; i++) {
	                	 loadsize[i] = Integer.parseInt(strloadsize[i]);
	                 }
	             }
             }
             //创建空文件
             File file = new File(task.filePath);  
             for (int i = 0; i < threads.length; i++) {  
                 // 启动线程，分别下载每个线程需要下载的部分  
                 threads[i] = new FileDownloadThread(url, file, blockSize,  
                         (i + 1),loadsize[i],mConnectTimeoutMills,mReadTimeoutMills);  
                 threads[i].setName("Thread:" + i);  
                 threads[i].start();  
             }  

             boolean isfinished = false;  
             int downloadedAllSize = 0;  
             while (!isfinished && !mCancelled) {  
                 isfinished = true;  
                 // 当前所有线程下载总量  
                 downloadedAllSize = 0;  
                 
                 boolean isException = false;// 是否异常退出
                 
                 String strdownloadedSize = "";//已下载大小的字符组合
                 for (int i = 0; i < threads.length; i++) {  
                     downloadedAllSize += threads[i].getDownloadLength();  
                     if (!threads[i].isCompleted()) {  
                         isfinished = false;  
                     }  
                     if (threads[i].isException()) {  
                    	 isException = true;  
                     } 
                     strdownloadedSize += String.valueOf(threads[i].getDownloadLength()) + sizeSplit;
                 }
                 
                 task.downloadedSize = strdownloadedSize;			
                 manager.notifyTaskProgress(task.url, downloadedAllSize, task.fileSize);
                 if(isfinished)
                 {
                	 //下载完毕
                	 task.status = Task.STATUS_FINISHED;
 					 manager.notifyTaskFinished(task.url);
                 }
                 //更新数据库
            	 manager.updateTaskInfo(task.ID, task);
            	 //异常退出
            	 if(isException)
            	 {
            		 manager.notifyTaskError(urlStr, HttpResponseCode.INTERNAL_SERVER_ERROR,"error");
            		 cancel();
            	 }
                 try {
					Thread.sleep(mNotifyProgressInterVal);
				 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }// 休息1秒后再读取下载进度  
             }  
             
             Log.d(TAG, " all of downloadSize:" + downloadedAllSize);  
         } catch (MalformedURLException e) {  
        	 manager.notifyTaskError(urlStr, HttpResponseCode.INTERNAL_SERVER_ERROR,"error");
         } catch (Exception e) {  
        	 manager.notifyTaskError(urlStr, HttpResponseCode.INTERNAL_SERVER_ERROR,"error");
         }         
         
        /*
		URL url = null;
		HttpURLConnection conn = null;
		RandomAccessFile raf;
		byte[] buf = mBuffer;
		
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Range", "bytes=" + task.downloadedSize + "-");
			conn.setConnectTimeout(this.mConnectTimeoutMills);
			conn.setReadTimeout(this.mReadTimeoutMills);
			int responseCode = conn.getResponseCode();
			Log.d(TAG, "responseCode = " + responseCode);
			if (responseCode == HttpResponseCode.OK || responseCode == HttpResponseCode.PARTIAL_CONTENT){				
				if (task.fileSize == Task.NO_INIT_SIZE){
					//有面试官说，通过获取文件的大小 contentLengh,有问题
					task.fileSize =  conn.getContentLength(); 
				}
				task.status = Task.STATUS_RUNNABLE;
				manager.updateTaskInfo(task.ID, task);
				manager.notifyTaskStart(task.url);
				
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				int len;
				raf = getRandomAccessFile(task);
				long last = System.currentTimeMillis();
				long current = 0L;
				while ((len = bis.read(buf)) != -1 && !mCancelled){
					raf.write(buf, 0, len);
					task.downloadedSize += len;
					manager.updateTaskInfo(task.ID, task);
					current = System.currentTimeMillis();
					if (current - last >= this.mNotifyProgressInterVal){
						manager.notifyTaskProgress(task.url, task.downloadedSize, task.fileSize);
					    last = current;
					}
				}
				
				if (mCancelled && task.downloadedSize != task.fileSize){
					task.status = Task.STATUS_STOPPED;
					manager.updateTaskInfo(task.ID, task);
					manager.notifyTaskStop(task.url);
					return;
				}
				
				if (task.downloadedSize == task.fileSize){
					task.status = Task.STATUS_FINISHED;
					manager.updateTaskInfo(task.ID, task);
					manager.notifyTaskFinished(task.url);
					return;
				}
				
				
			}else if (responseCode == HttpResponseCode.INTERNAL_SERVER_ERROR){
				mDLManager.notifyTaskError(task.url,task.errorCode,task.errorMsg);
			}
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			manager.notifyTaskError(urlStr, HttpResponseCode.INTERNAL_SERVER_ERROR,"error");
		} catch (IOException e) {
			//e.printStackTrace();
			manager.notifyTaskError(urlStr, HttpResponseCode.INTERNAL_SERVER_ERROR,"error");
		}finally{
			//Calls to disconnect() may return the socket to a pool of connected sockets
			if (conn != null){
			    conn.disconnect();
			}
		} 
		Log.d(TAG, name + "run() end");*/
	}
	
	/**
	 * 获取任务的文件
	 * @param task
	 * @return
	 * @throws IOException
	 *//*
	private static final RandomAccessFile getRandomAccessFile(Task task) throws IOException{
		File file = FileOperateUtil.createFile(task.filePath);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		if (task.downloadedSize >= 0L){
			raf.seek(task.downloadedSize);
		}
		return raf;
	}*/
	
	
	/**
	 * 验证文件是否存在
	 * @param task
	 * @return
	 */
	public static final boolean isExistsFile(Task task){
		try{
              File f=new File(task.filePath);
              if(!f.exists()){
                 return false;
              }               
	     }catch (Exception e) {
	         // TODO: handle exception
	         return false;
	     }
         return true;
	}
	
	/**
     * 终止线程
     */
	public void cancel() {
		this.mCancelled = true;
		if(threads != null)
		{
			for (int i = 0; i < threads.length; i++) {  
				if(threads[i] !=null)
				{
					threads[i].cancel();
				}
			}
		}
	}
}

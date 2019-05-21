package com.gaotai.baselib.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;
/**
 * 下载线程
 */
public class FileDownloadThread  extends Thread {  
  
    private static final String TAG = FileDownloadThread.class.getSimpleName();  
  
    /** 当前下载是否完成 */  
    private boolean isCompleted = false;  
    /** 当前下载文件长度 */  
    private int downloadLength = 0;  
    /** 文件保存路径 */  
    private File file;  
    /** 文件下载路径 */  
    private URL downloadUrl;  
    /** 当前下载线程ID */  
    private int threadId;  
    /** 线程下载数据长度 */  
    private int blockSize;  
    /** 已下载的长度  */  
    private int loadSize;  
    /**
     * 线程文件大小
     */
    private static final int BUFFER_SIZE = 2 * 1024; 
    /**
     * 文件存储
     */
    private final byte[] mBuffer;
    /**
     * 链接超时时间
     */
    private int    mConnectTimeoutMills;
    /**
     * 读取超时时间
     */
    private int    mReadTimeoutMills;
    /**
     * 是否终止线程
     */
    private volatile boolean mCancelled = false;
    
    /**
     * 是否异常退出
     */
    private boolean isException = false;
    
    /** 
     *  
     * @param url:文件下载地址 
     * @param file:文件保存路径 
     * @param blocksize:下载数据长度 
     * @param threadId:线程ID 
     * @param loadSize:已下载的文件大小
     * @param mConnectTimeoutMills:链接超时时间
     * @param mReadTimeoutMills:读取超时时间
     */  
    public FileDownloadThread(URL downloadUrl, File file, int blocksize,  
            int threadId,int loadSize,int mConnectTimeoutMills,int mReadTimeoutMills) {  
        this.downloadUrl = downloadUrl;  
        this.file = file;  
        this.threadId = threadId;  
        this.blockSize = blocksize;  
        this.loadSize = loadSize;        
        this.mBuffer = new byte[BUFFER_SIZE];        
        this.mReadTimeoutMills = mReadTimeoutMills;
		this.mConnectTimeoutMills = mConnectTimeoutMills;
    }  
  
    @Override  
    public void run() {  
  
        BufferedInputStream bis = null;  
        RandomAccessFile raf = null;  
        isException = false;
        try {  
            URLConnection conn = downloadUrl.openConnection();  
            conn.setAllowUserInteraction(true);  
            conn.setConnectTimeout(this.mConnectTimeoutMills);
			conn.setReadTimeout(this.mReadTimeoutMills);
            int startPos = blockSize * (threadId - 1) + loadSize;//开始位置  
            downloadLength = loadSize;
            int endPos = blockSize * threadId - 1;//结束位置  
            if(endPos < startPos)
            {
            	//下载完毕
            	isCompleted = true; 
            	Log.d(TAG, "current thread task has finished,all size:"  
                          + downloadLength);  
            	return;
            }
            //设置当前线程下载的起点、终点  
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);  
            System.out.println(Thread.currentThread().getName() + "  bytes="  
                    + startPos + "-" + endPos);  
  
            byte[] buffer = mBuffer;
            bis = new BufferedInputStream(conn.getInputStream());  
  
            raf = new RandomAccessFile(file, "rwd");  
            raf.seek(startPos);  
            int len;  
            //while ((len = bis.read(buffer, 0, 1024)) != -1) {  
            while ((len = bis.read(buffer)) != -1 && !mCancelled){
                raf.write(buffer, 0, len);  
                downloadLength += len;  
            }
            isCompleted = true;  
            Log.d(TAG, "current thread task has finished,all size:"  
                    + downloadLength);  
  
        } catch (Exception e) {  
        	isException = true;
            e.printStackTrace();  
        } finally {  
            if (bis != null) {  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (raf != null) {  
                try {  
                    raf.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * 线程文件是否下载完毕 
     */  
    public boolean isCompleted() {  
        return isCompleted;  
    }  
  
    /** 
     * 线程下载文件长度 
     */  
    public int getDownloadLength() {  
        return downloadLength;  
    }  
    

    /** 
     * 是否异常退出
     */  
    public boolean isException() {  
        return isException;  
    }  
    
    
    /**
     * 终止线程
     */
    public void cancel() {
		this.mCancelled = true;
	}
  
}  

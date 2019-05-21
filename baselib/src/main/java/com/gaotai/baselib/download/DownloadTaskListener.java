package  com.gaotai.baselib.download;

public interface DownloadTaskListener {
	public void onTaskAdded();
    public void onTaskWaiting();    	
	public void onTaskStart();
	public void onTaskRunning(long curSize,long totalSize);
	public void onTaskStop();
	public void onTaskFinished(String filepath);
	public void onTaskError(int code,String msg);
}

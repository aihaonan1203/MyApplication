package  com.gaotai.baselib.download;

class Task {

	public static final int STATUS_INIT         = 0;
	public static final int STATUS_STOPPED      = 1;
	public static final int STATUS_STARTED      = 2;
	public static final int STATUS_RUNNABLE     = 3;
	public static final int STATUS_FINISHED     = 4;
	public static final int STATUS_ERROR        = 5;
    
    public static final int NETWORK_TYPE_WIFI   = 1;
    public static final int NETWORK_TYPE_MOBILE = 2;
    
    public static final int NO_INIT_SIZE = -1;

    public int              ID;                // 任务 ID
    public int              status;            // 任务状态
    public String           fileName;          // 文件名
    public String           filePath;          // 保存路径
    public long             fileSize;          // 文件总大小
    public String           downloadedSize;    // 已下载的大小  多个线程用+隔开
    public int              supportNetworkType; // 支持网络类型
    public String           url;               // 下载地址
    public int              type;              // 下载文件类型
    public long             createTime;        // 任务创建时间戳
    public int              errorCode;         // 任务错误码
    public String           errorMsg;          // 任务错误消息
    public String           MD5;               // MD5 checksum
    public String           SHA1;              // SHA-1 checksum
    /**
     * 初始化Task
     * @return
     */
    public static Task buildNewTask() {
        Task task = new Task();
        task.status = Task.STATUS_INIT;
        task.fileName = "";
        task.filePath = "";
        task.fileSize = NO_INIT_SIZE;
        task.supportNetworkType = NETWORK_TYPE_WIFI;
        task.MD5 = "";
        task.SHA1 = "";
        task.url = "";
        return task;
    }
    
    /**
     * 新增 task
     * @param req
     * @return
     */
    public static Task buildNewTask(Request req){
    	if (req == null){
    		return buildNewTask();
    	}
    	Task task = buildNewTask();
    	task.createTime = System.currentTimeMillis();
		task.filePath = req.dstFilePath;
		task.url = req.requestUrl;
		task.MD5 = req.MD5;
		task.SHA1 = req.SHA1;
		return task;
    }
}

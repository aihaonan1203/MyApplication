package  com.gaotai.baselib.download;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * 下载任务数据库表操作类
 *
 */
public class TaskDBManager {
	
	// ===========================================================
    // Constants
    // +==========================================================
	
	private static final String TAG = TaskDBManager.class.getSimpleName();
	
	/**
     * Column Index
     */
    public static final int     COLUMN_ID_INDEX                 = 0;
    public static final int     COLUMN_STATE_INDEX              = 1;
    public static final int     COLUMN_FILENAME_INDEX           = 2;
    public static final int     COLUMN_FILEPATH_INDEX           = 3;
    public static final int     COLUMN_FILESIZE_INDEX           = 4;
    public static final int     COLUMN_DOWNLOADEDSIZE_INDEX     = 5;
    public static final int     COLUMN_SUPPORTNETWORKTYPE_INDEX = 6;
    public static final int     COLUMN_URL_INDEX                = 7;
    public static final int     COLUMN_TYPE_INDEX               = 8;
    public static final int     COLUMN_CREATETIME_INDEX         = 9;
    public static final int     COLUMN_ERRORCODE_INDEX          = 10;
    public static final int     COLUMN_MD5_INDEX                = 11;
    public static final int     COLUMN_SHA1_INDEX               = 12;
    
    
    public static final int     DATABASE_VERSION                = 1;
    public static final String  TABLE_FILE_NAME                 = "task.db";
    public static final String  TABLE_NAME                      = "task";
	
    // -==========================================================

    // ===========================================================
    // Fields
    // +==========================================================

	private static volatile TaskDBManager sInstance = null;
	private static TaskDBHelper sDBHelper = null;

    // -==========================================================

    // ===========================================================
    // Constructors
    // +==========================================================
    
	private TaskDBManager() {
		
	}
	
	// -==========================================================

    // ===========================================================
    // Getter & Setter
    // +==========================================================

    //TODO 代码在+-号之间编写

    // -==========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // +==========================================================
	
	
	// -==========================================================

    // ===========================================================
    // Methods
    // +==========================================================
	
	public static TaskDBManager getInstance(){
		if (sInstance == null){
			synchronized (TaskDBManager.class) {
				if (sInstance == null){
					sInstance = new TaskDBManager();
				}
			}
		}
		return sInstance;
	}
	
	public static synchronized void init(Context context,boolean isSdcard,String dbName){
		sDBHelper = new TaskDBHelper(context, isSdcard, dbName,DATABASE_VERSION);
	}
	/**
	 * 增加或更新一条新的任务
	 * @param task
	 */
	public final void insertOrUpdateTask(Task task){
        if (task != null){
        	//Log.d(TAG, "insertOrUpdateTask()");
            StringBuilder sb = new StringBuilder(170);
            sb.append("INSERT OR REPLACE INTO ").append(TABLE_NAME)
              .append("(id,status,filename,filepath,filesize,downloadedsize,supporttype,url,type,createtime,errorcode,md5,sha1) ")
              .append("values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            SQLiteDatabase db = sDBHelper.getWritableDatabase();
            db.execSQL(sb.toString(), new Object[]{task.ID,task.status,task.fileName,task.filePath,task.fileSize,task.downloadedSize
                ,task.supportNetworkType,task.url,task.type,task.createTime,task.errorCode,task.MD5,task.SHA1});
        }
    }
	/**
	 * 增加一条新的任务
	 * @param task
	 */
    public final void insertTask(Task task){
        if (task != null){
        	Log.d(TAG, "insertTask()");
            StringBuilder sb = new StringBuilder(170);
            sb.append("INSERT INTO ").append(TABLE_NAME)
              .append("(id,status,filename,filepath,filesize,downloadedsize,supporttype,url,type,createtime,errorcode,md5,sha1) ")
              .append("values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            SQLiteDatabase db = sDBHelper.getWritableDatabase();
            db.execSQL(sb.toString(), new Object[]{null,task.status,task.fileName,task.filePath,task.fileSize,task.downloadedSize
                ,task.supportNetworkType,task.url,task.type,task.createTime,task.errorCode,task.MD5,task.SHA1});
        }
    }
    
    /**
     * 查询下载任务
     * @param url 网络URL
     * @param filePath  本地存储路径
     * @return
     */
    public final List<Task> queryTask(String url,String filePath) {
        if (url == null){
            return null;
        }
        List<Task> list = new ArrayList<Task>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id,status,filename,filepath,filesize,downloadedsize,supporttype,url,type,createtime,errorcode,md5,sha1")
          .append(" FROM ").append(TABLE_NAME)
          .append(" where url = ? and filepath=?");
        
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = null;
        Task task;
        try{
        	cursor = db.rawQuery(sb.toString(), new String[]{url,filePath});
        	while (cursor.moveToNext()){
                task = new Task();
                task.ID = cursor.getInt(COLUMN_ID_INDEX);
                task.status = cursor.getInt(COLUMN_STATE_INDEX);
                task.fileName = cursor.getString(COLUMN_FILENAME_INDEX);
                task.filePath = cursor.getString(COLUMN_FILEPATH_INDEX);
                task.fileSize = cursor.getLong(COLUMN_FILESIZE_INDEX);
                task.downloadedSize = cursor.getString(COLUMN_DOWNLOADEDSIZE_INDEX);
                task.supportNetworkType = cursor.getInt(COLUMN_SUPPORTNETWORKTYPE_INDEX);
                task.url = cursor.getString(COLUMN_URL_INDEX);
                task.type = cursor.getInt(COLUMN_TYPE_INDEX);
                task.createTime = cursor.getLong(COLUMN_CREATETIME_INDEX);
                task.errorCode = cursor.getInt(COLUMN_ERRORCODE_INDEX);
                task.MD5 = cursor.getString(COLUMN_MD5_INDEX);
                task.SHA1 = cursor.getString(COLUMN_SHA1_INDEX);
                list.add(task);
            }
        }finally{
        	if (cursor != null){
        		cursor.close();
        	}
        }
        return list;
    }
    
    
    /**
     * 查询下载任务
     * @param url 网络URL
     * @return
     */
    public final List<Task> queryTask(String url) {
        if (url == null){
            return null;
        }
        List<Task> list = new ArrayList<Task>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id,status,filename,filepath,filesize,downloadedsize,supporttype,url,type,createtime,errorcode,md5,sha1")
          .append(" FROM ").append(TABLE_NAME)
          .append(" where url = ? ");
        
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = null;
        Task task;
        try{
        	cursor = db.rawQuery(sb.toString(), new String[]{url});
        	while (cursor.moveToNext()){
                task = new Task();
                task.ID = cursor.getInt(COLUMN_ID_INDEX);
                task.status = cursor.getInt(COLUMN_STATE_INDEX);
                task.fileName = cursor.getString(COLUMN_FILENAME_INDEX);
                task.filePath = cursor.getString(COLUMN_FILEPATH_INDEX);
                task.fileSize = cursor.getLong(COLUMN_FILESIZE_INDEX);
                task.downloadedSize = cursor.getString(COLUMN_DOWNLOADEDSIZE_INDEX);
                task.supportNetworkType = cursor.getInt(COLUMN_SUPPORTNETWORKTYPE_INDEX);
                task.url = cursor.getString(COLUMN_URL_INDEX);
                task.type = cursor.getInt(COLUMN_TYPE_INDEX);
                task.createTime = cursor.getLong(COLUMN_CREATETIME_INDEX);
                task.errorCode = cursor.getInt(COLUMN_ERRORCODE_INDEX);
                task.MD5 = cursor.getString(COLUMN_MD5_INDEX);
                task.SHA1 = cursor.getString(COLUMN_SHA1_INDEX);
                list.add(task);
            }
        }finally{
        	if (cursor != null){
        		cursor.close();
        	}
        }
        return list;
    }
    
    /**
     * 查询下载任务ID
     * @param url  文件网络地址
     * @param filePath  本地存储地址
     * @return
     */
    public final int queryTaskID(String url,String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id")
          .append(" FROM ").append(TABLE_NAME)
          .append(" where url = ? and filepath=?");
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = null;
        int taskID = -1;
        try{
        	cursor = db.rawQuery(sb.toString(), new String[]{url,filePath});
            if (cursor.moveToNext()){
            	taskID = cursor.getInt(COLUMN_ID_INDEX);
            }
        }finally{
        	if (cursor != null){
        		cursor.close();
        	}
        }
        return taskID;
    }
    
    /**
     * 查询任务列表
     * @param taskState 任务状态
     * @return
     */
    public final List<Task> queryTasks(int taskState){
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT id,status,filename,filepath,filesize,downloadedsize,supporttype,url,type,createtime,errorcode,md5,sha1")
          .append(" FROM ").append(TABLE_NAME)
          .append(" status  = ?");
    	SQLiteDatabase db = sDBHelper.getReadableDatabase();
    	List<Task> list = new ArrayList<Task>();
    	Task task;
    	Cursor cursor = null;
		try {
			cursor = db.rawQuery(sb.toString(), new String[] { String.valueOf(taskState) });
			while (cursor.moveToNext()) {
				task = new Task();
				task.ID = cursor.getInt(COLUMN_ID_INDEX);
				task.status = cursor.getInt(COLUMN_STATE_INDEX);
				task.fileName = cursor.getString(COLUMN_FILENAME_INDEX);
				task.filePath = cursor.getString(COLUMN_FILEPATH_INDEX);
				task.fileSize = cursor.getLong(COLUMN_FILESIZE_INDEX);
				task.downloadedSize = cursor.getString(COLUMN_DOWNLOADEDSIZE_INDEX);
				task.supportNetworkType = cursor.getInt(COLUMN_SUPPORTNETWORKTYPE_INDEX);
				task.url = cursor.getString(COLUMN_URL_INDEX);
				task.type = cursor.getInt(COLUMN_TYPE_INDEX);
				task.createTime = cursor.getLong(COLUMN_CREATETIME_INDEX);
				task.errorCode = cursor.getInt(COLUMN_ERRORCODE_INDEX);
				task.MD5 = cursor.getString(COLUMN_MD5_INDEX);
				task.SHA1 = cursor.getString(COLUMN_SHA1_INDEX);
				list.add(task);
			}
		} finally {
            if (cursor != null){
            	cursor.close();
            }
		}
    	return list;
    }
    
    /**
     * 根据url删除任务表中的数据项
     * @param url
     */
    public final void deleteTaskIfExist(String url){
        if (url == null){
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(TABLE_NAME)
          .append(" where url = ?");
        SQLiteDatabase db = sDBHelper.getWritableDatabase();
        db.execSQL(sb.toString(), new Object[]{url});
    }
    
    /**
     * 根据任务id删除任务表中的数据项
     * @param url
     */
    public final void deleteTaskIfExist(int taskID){
        StringBuilder sb = new StringBuilder(30);
        sb.append("delete from ").append(TABLE_NAME)
          .append(" where id = ?");
        SQLiteDatabase db = sDBHelper.getWritableDatabase();
        db.execSQL(sb.toString(), new Object[]{taskID});
    }
}

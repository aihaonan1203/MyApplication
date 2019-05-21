package  com.gaotai.baselib.download;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

//@formatter:off
/**
* @author Ji.Li
* @Create at 2015-9-7
* @Version 1.0
* <p><strong>Features draft description.主要功能介绍</strong></p>
*/
//@formatter:on
public class TaskDBHelper extends SQLiteOpenHelper{

    // ===========================================================
    // Constants
    // +==========================================================

    private static final String TAG                             = TaskDBHelper.class.getSimpleName();

    // -==========================================================

    // ===========================================================
    // Fields
    // +==========================================================

    //TODO 代码在+-号之间编写

    // -==========================================================

    // ===========================================================
    // Constructors
    // +==========================================================

    /**
     * TaskDBHelper构造方法
     * @param context
     * @param isUseSdcard 是否是使用sdcard上的数据库文件
     * @param name        如果是使用sdcard,name应该是文件的全路径;否则为数据库name
     **/
    public TaskDBHelper(Context context, boolean isUseSdcard, String name,int version) {
        super(isUseSdcard?new LocalContext(context):context, name, null, version);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() {oldVersion,newVersion} = {" + oldVersion + "," + newVersion +"}");
        upgradeTo(db, oldVersion, newVersion);
    }
    
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d(TAG, "onOpen()");
        super.onOpen(db);
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade()");
        super.onDowngrade(db, oldVersion, newVersion);
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onConfigure(SQLiteDatabase db) {
        Log.d(TAG, "onConfigure()");
        super.onConfigure(db);
    }

    // -==========================================================

    // ===========================================================
    // Methods
    // +==========================================================

    private static final void createTable(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type = ? AND name = ?", new String[] { "table", "task" });
            //查询task表是否存在
            //DROP TABLE IF EXISTS task
            if (!cursor.moveToNext()) {
                StringBuilder create = new StringBuilder(500);
                //提升效率，预定义缓存大小
                create.append("CREATE TABLE task (");
                create.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");         //task ID
                create.append("status INTEGER NOT NULL DEFAULT 0,");            //任务状态
                create.append("filename VARCHAR(50) NOT NULL DEFAULT '',");     //文件名
                create.append("filepath VARCHAR(250) NOT NULL DEFAULT '',");    //文件的存储路径
                create.append("filesize INTEGER NOT NULL DEFAULT 0,");          //文件的总大小
                create.append("downloadedsize VARCHAR(500) DEFAULT '0',");    //已下载的文件大小
                create.append("supporttype INTEGER NOT NULL DEFAULT 0,");       //支持的网络类型
                create.append("url VARCHAR(250) NOT NULL DEFAULT '',");         //下载链接url
                create.append("type INTEGER NOT NULL DEFAULT 0,");              //下载文件的类型
                create.append("createtime INTEGER NOT NULL DEFAULT 0,");        //创建时间戳
                create.append("errorcode INTEGER NOT NULL DEFAULT 0,");         //错误码
                create.append("md5 VARCHAR(32) DEFAULT '',");                   //MD5 checksum
                create.append("sha1 VARCHAR(40) DEFAULT '')");                  //SHA-1 checksum
                db.execSQL(create.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static final void upgradeTo(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库逐个版本升级
        for (int i = oldVersion; i < newVersion; i++) {
            onUpgrade(db, i);
        }
    }
    
    private static final void onUpgrade(SQLiteDatabase db,int version){
        
    }
    
    // -==========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // +==========================================================

    /**
     * 如果是在其他路径下创建的db文件，必须传入自己定义的 ContextWrapper,因为SQLiteOpenHelper在创建数据库会调用openOrCreateDatabase()
     *
     **/
    private static class LocalContext extends ContextWrapper {

        public LocalContext(Context base) {
            super(base);
            Log.d(TAG, "LocalContext()");
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
            Log.d(TAG, "openOrCreateDatabase1() {name,mode,factory} = {" + name + "," + mode + "}");
            File dbFile = new File(name);
            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getPath(), factory, SQLiteDatabase.CREATE_IF_NECESSARY
                    | SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            return db;
        }

        @SuppressLint("NewApi")
        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory,
                                                   DatabaseErrorHandler errorHandler) {
            Log.d(TAG, "openOrCreateDatabase2() {name,mode,factory} = {" + name + "," + mode + "}");
            File dbFile = new File(name);
            SQLiteDatabase db;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                db =
                    super.openOrCreateDatabase(dbFile.getAbsolutePath(), Context.MODE_ENABLE_WRITE_AHEAD_LOGGING | SQLiteDatabase.CREATE_IF_NECESSARY
                            | SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS, null);
            } else {
                db =
                    SQLiteDatabase.openDatabase(dbFile.getPath(), factory, SQLiteDatabase.CREATE_IF_NECESSARY
                        | SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            }
            return db;
        }
    }
    // -==========================================================
}

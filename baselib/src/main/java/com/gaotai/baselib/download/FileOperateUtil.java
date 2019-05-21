package com.gaotai.baselib.download;

import java.io.File;

// @formatter:off
/**
 * @author Ji.Li
 * @Create at 2015-8-19
 * @Version 1.0
 * <p><strong>Features draft description.主要功能介绍</strong></p>
 */
// @formatter:on
public class FileOperateUtil {

    // ===========================================================
    // Constants
    // ===========================================================
    
    @SuppressWarnings("unused")
	private static final String TAG = FileOperateUtil.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * 删除指定文件，如果为文件，仅删除指定文件；若为文件夹，递归删除
     * @param File root
     * @return 如果删除成功，则返回true;失败则为false
     */
    public static final boolean delete(File root) {
        // 如果file不存在
        if (root == null || !root.exists()) {
            return false;
        }
        // 如果是文件
        if (root.isFile()) {
            return root.delete();
        } else {
            // 如果是文件夹，递归删除
            File[] files = root.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f);
                }
            }
            return root.delete();
        }
    }
    
    public static final boolean delete(String filePath) {
        File f = new File(filePath);
        return delete(f);
    }
    
    public static final File createFile(String filePath){
    	if (filePath == null){
    		return null;
    	}
    	File file = new File(filePath);
        File parentFile = file.getParentFile();
    	if (!parentFile.exists()){
    		parentFile.mkdirs();
    	}
        return file;
    }
    
    // ===========================================================
    // Native Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}

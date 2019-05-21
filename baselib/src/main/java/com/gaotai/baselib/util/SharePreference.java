package com.gaotai.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharedPreferences 存储相关操作
 * 
 * @author MengLiang
 */
public class SharePreference {

	/**
	 * 设置Activity 值
	 * <p>对象只能在该Activity中使用</p>
	 * @param activity 页面
	 * @param key  键值
	 * @param value 内容
	 */
	public void setSharedPreference(Activity activity,String key,String value){
		SharedPreferences pre = PreferenceManager
				.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor edit = pre.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	/**
	 * 获取 Activity 值
	 *  <p>对象只能在该Activity中使用</p>
	 * @param activity
	 * @param key
	 * @return
	 */
	public String getSharedPreference(Activity activity,String key){
		SharedPreferences pre = PreferenceManager
				.getDefaultSharedPreferences(activity);
		String SharedInfo = pre.getString(key,null);
		return SharedInfo;
	}
	
	/**
	 * 删除Activity 值
	 *  <p>对象只能在该Activity中使用</p>
	 * @param activity
	 * @param key
	 */
	public void deleteSharedPreference(Activity activity,String key){
		SharedPreferences pre = PreferenceManager
				.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor edit = pre.edit();
		edit.remove(key);
		edit.commit();
	}
	
	/**
	 * 读取全局存储的键值
	 * @param context
	 * @param configname 配置文件名
	 * @param key 键值
	 * @return
	 */
	public String readConfig(Context context,String configname, String key) {
		String data = null;
		try {
			SharedPreferences sharedata = context.getSharedPreferences(configname, 0);
			data = sharedata.getString(key, null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 设置全局存储的键值
	 * @param myactivity
	 * @param configname 配置文件名
	 * @param key 键
	 * @param value 值
	 */
	public void writeConfig(Context myactivity,String configname, String key, String value) {
		Editor sharedata = myactivity.getSharedPreferences(configname, 0).edit();
		sharedata.putString(key, value);
		sharedata.commit();
	}
}

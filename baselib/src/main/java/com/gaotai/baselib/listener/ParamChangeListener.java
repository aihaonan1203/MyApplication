package com.gaotai.baselib.listener;

/**
 * 参数值变更监听器。
 * @author JinL
 */
public interface ParamChangeListener {
	/**
	 * 参数值变化时，调用此方法
	 * @param key 参数名
	 * @param newValue 新值
	 * @param oldValue 旧值
	 */
	public void onParamChanged(String key, Object newValue, Object oldValue);
}

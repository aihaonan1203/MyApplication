package com.gaotai.baselib.smack.listener;

/**
 * IQ 消息 提供接收
 * @author mengliang
 */
public interface MessageIQProviderListener {
	/**
	 * 接收到新的IQ
	 * @param iqtype 类别
	 * @param object 处理后的消息数据
	 */
	public void onParseIQ(int iqtype,Object object);
}

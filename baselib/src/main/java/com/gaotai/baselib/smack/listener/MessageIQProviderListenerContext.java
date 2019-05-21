package com.gaotai.baselib.smack.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/***
 * 消息iq监听上下文     
 * 有数据时通知监听
 */
public class MessageIQProviderListenerContext {
	
	private static MessageIQProviderListenerContext uniqueInstance;
	  
	public static synchronized MessageIQProviderListenerContext getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MessageIQProviderListenerContext();
		}
		return uniqueInstance;
	}
	
	private MessageIQProviderListenerContext()
	{
		
	}
	
	private Map<String, MessageIQProviderListener> listeners = new HashMap<String, MessageIQProviderListener>();
	
	/**
	 * 添加属性值变化监听器。同名监听器只保留最后添加的一个
	 * @param listenerName 监听器名称
	 * @param listener 监听器
	 */
	public void addListener(String listenerName, MessageIQProviderListener listener){
		listeners.put(listenerName, listener);
	}
	
	/**
	 * 删除属性值变化监听器
	 * @param listenerName
	 */
	public void removeListener(String listenerName){
		listeners.remove(listenerName);
	}
	
	/**
	 * 接收到新消息通知监听器
	 * @param msgType 消息类别
	 * @param  obj
	 */
	public void callParseIQListener(int msgType, Object obj)
	{
		Set<Entry<String, MessageIQProviderListener>> entrys = listeners.entrySet();
		for(Entry<String, MessageIQProviderListener> listener : entrys){
			listener.getValue().onParseIQ(msgType,obj);
		}
	}	
}

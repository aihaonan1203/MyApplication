package com.gaotai.baselib.smack.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.gaotai.baselib.smack.domain.MessagePacketDomain;

/***
 * 消息信息监听上下文     
 * 有数据时通知监听
 */
public class MessageListenerContext {
	
	private static MessageListenerContext uniqueInstance;
	  
	public static synchronized MessageListenerContext getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MessageListenerContext();
		}
		return uniqueInstance;
	}
	
	private MessageListenerContext()
	{
		
	}
	
	private Map<String, MessageListener> listeners = new HashMap<String, MessageListener>();
	
	/**
	 * 添加属性值变化监听器。同名监听器只保留最后添加的一个
	 * @param listenerName 监听器名称
	 * @param listener 监听器
	 */
	public void addListener(String listenerName, MessageListener listener){
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
	 * @param messagePacketDomain
	 */
	public void callMsgAddListener(int msgType, MessagePacketDomain messagePacketDomain)
	{
		Set<Entry<String, MessageListener>> entrys = listeners.entrySet();
		for(Entry<String, MessageListener> listener : entrys){
			listener.getValue().onMsgAdd(msgType,messagePacketDomain);
		}
	}	
}

package com.gaotai.baselib.smack.listener;

import com.gaotai.baselib.smack.domain.MessagePacketDomain;


/**
 * 即时消息通知Listener
 * @author mengliang
 */
public interface MessageListener {
	/**
	 * 接收到新消息
	 * @param msgType 消息类别
	 * @param messagePacketDomain 处理后的消息数据
	 */
	public void onMsgAdd(int msgType,MessagePacketDomain messagePacketDomain);	
}

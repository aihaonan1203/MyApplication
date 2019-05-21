package com.gaotai.baselib.smack.listener;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.XmppConnectionManager;
import com.gaotai.baselib.smack.XmppUtil;
import com.gaotai.baselib.smack.domain.MessagePacketDomain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

/**
 * 通知类消息监听
 * 处理Packet消息封装到消息体中
 */
public class HeadLineListener extends BaseListener implements PacketListener{
		
	@Override
	public void processPacket(Packet packet) {		
		Message message = (Message) packet;
		if (message != null) {			
			try {
				MessagePacketDomain messagePacketDomain = new MessagePacketDomain();
				
				String from = message.getFrom().split("@")[0];
				messagePacketDomain.setSender(Long.parseLong(from));
				
				dealMessage(message,messagePacketDomain);
				// 通知类型
				if (message.getSubject() != null) {
					messagePacketDomain.setBusinesstype(message.getSubject());
				}
				messagePacketDomain.setIsNoticeMsg(true);
				
				try {
					XmppUtil.sendMessageBack(XmppConnectionManager.getInstance().getConnection(), messagePacketDomain.getChatid(), message.getFrom());
				} catch (Exception e) {
					Log.e("zhxychat",messagePacketDomain.getChatid() + "回执失败" + e.getMessage());
				}
				
				MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_HEADLINE,messagePacketDomain);
				// 获取消息后停顿一下 200毫秒
				Thread.sleep(100);
			} catch (Exception ex) {
				Log.e("zhxychat", "消息处理失败" + ex.getMessage());
			}
		}
	}
}

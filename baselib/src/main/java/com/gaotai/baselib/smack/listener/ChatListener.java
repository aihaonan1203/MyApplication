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
 * 即时消息监听
 * 处理Packet消息封装到消息体中
 */
public class ChatListener extends BaseListener implements PacketListener{
	
	@Override
	public void processPacket(Packet packet) {		
		Message message = (Message) packet;
		if (message != null) {			
			try {
				MessagePacketDomain messagePacketDomain = new MessagePacketDomain();
				if (message.getBody() == null) {
					try {
						// 获取回执ID
						// 我发送的消息回执
						PacketExtension pakext = message.getExtension("sendok", DcAndroidConsts.MSG_KEY_NAME_SPACE);
						if (pakext == null) {
							Log.e("zhxychat", "未处理：" + message.toString());
							return;
						}
						String from = message.getFrom().split("@")[0];
						messagePacketDomain.setSendreceipt(true);
						messagePacketDomain.setChatid(message.getPacketID().toString());
						messagePacketDomain.setSender(Long.parseLong(from));
						
					} catch (Exception ex) {
						Log.e("zhxychat", "接收回执处理失败:" + ex.getMessage());
						return;
					}
				}
				else
				{
					String from = message.getFrom().split("@")[0];
					messagePacketDomain.setSender(Long.parseLong(from));
					
					PacketExtension pakext = message.getExtension("sent", DcAndroidConsts.MSG_KEY_CARBONS_SPACE);
					if (pakext != null) {
						//自己的消息
						//获取to
						String touser = message.getTo().split("@")[0];
						messagePacketDomain.setTouser(Long.parseLong(touser));
					}
					dealMessage(message,messagePacketDomain);
					
					try {
						XmppUtil.sendMessageBack(XmppConnectionManager.getInstance().getConnection(), messagePacketDomain.getChatid(), message.getFrom());
					} catch (Exception e) {
						Log.e("zhxychat",messagePacketDomain.getChatid() + "回执失败" + e.getMessage());
					}
				}
				MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_TYPE_DEF,messagePacketDomain);	
				// 获取消息后停顿一下 200毫秒
				Thread.sleep(100);
			} catch (Exception ex) {
				Log.e("zhxychat", "消息处理失败" + ex.getMessage());
			}
		}
	}
}

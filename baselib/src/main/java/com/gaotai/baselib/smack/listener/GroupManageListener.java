package com.gaotai.baselib.smack.listener;

import java.io.StringReader;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.smack.domain.MessagePacketDomain;


/***
 * 群组消息操作监听包括 移除 退出 踢人 解散
 *
 */
public class GroupManageListener implements PacketListener {

	@Override
	public void processPacket(Packet arg0) {
		try {
			// 群组操作
			if (arg0.getFrom() != null && arg0.getFrom().indexOf("oproom") == 0) {
	
				String groupid = arg0.getFrom().split("@")[0];
				XmlPullParser parser = null;
				XmlPullParserFactory pullParserFactory;
				try {
					pullParserFactory = XmlPullParserFactory.newInstance();
					// 获取XmlPullParser的实例
					parser = pullParserFactory.newPullParser();
					// 设置输入流 xml文件
					parser.setInput(new StringReader(arg0.toXML()));
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					int type = 0;
					// 解析 判断是什么操作
					int eventType = parser.getEventType();				
					String jid = "";
					String optype = "";
					if (arg0 instanceof Presence) {
						Presence pre = (Presence) arg0;
						optype = pre.getType().toString();
					}
					// 名称
					String nick = "";
					// 是否被移除群
					boolean iskick = false;
					String affiliation = "";
					while (eventType != XmlPullParser.END_DOCUMENT) {
						switch (eventType) {
						case XmlPullParser.START_TAG:
							String strparname = parser.getName();
							if ("destroy".equals(strparname)) {
								type = 2;
							}
							if ("item".equals(strparname)) {
								try {
									affiliation = parser.getAttributeValue("",
											"affiliation");
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (optype.equals("unavailable")) {
									jid = parser.getAttributeValue("", "jid");
									if (jid != null) {
										jid = jid.split("@")[0];
										type = 1;
									}
	
									nick = parser.getAttributeValue("", "nick");
								}
							}
	
							if ("actor".equals(strparname)) {
								if (affiliation.equals("none")) {
									iskick = true;
								}
							}
							break;
						case XmlPullParser.END_TAG:
							// Log.e("zhxychat", "group destroy:" +
							// parser.getName());
							break;
						}
						eventType = parser.next();
					}
	
					MessagePacketDomain messagePacketDomain = new MessagePacketDomain();
					messagePacketDomain.setGroupid(groupid);
					
					// 踢人、退出
					if (type == 1) {
						messagePacketDomain.setIskick(iskick);
						messagePacketDomain.setSender(Long.parseLong(jid));
						messagePacketDomain.setSendername(nick);
						MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_KICKGROUPUSER, messagePacketDomain);
						LOG.d("zhxy.GroupManageListener", "群组(踢人、退出)" + groupid + ",jid:" + jid);
					}
	
					// 解散群
					if (type == 2) {
						MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_DISSOLUTIONGROUP, messagePacketDomain);					
						LOG.d("zhxy.GroupManageListener", "解散群组" + groupid);
					}
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

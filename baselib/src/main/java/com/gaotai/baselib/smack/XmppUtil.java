package com.gaotai.baselib.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;







import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.domain.ChatSendDomain;
import com.gaotai.baselib.smack.extension.MessagePacketExtension;
import com.gaotai.baselib.smack.extension.MessageTypePacketExtension;
import com.gaotai.baselib.smack.extension.MessageURLPacketExtension;
import com.gaotai.baselib.util.DcDateUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
/**
 * XMPP 常用操作 ： 发送消息 发送群聊消息等
 */
public class XmppUtil {

	/**
	 * 注册
	 * 
	 * @param account
	 *            注册帐号
	 * @param password
	 *            注册密码
	 * @return 1、注册成功 0、服务器没有返回结果2、这个账号已经存在3、注册失败
	 */
	/*
	 * public static int register(XMPPConnection mXMPPConnection,String account,
	 * String password) { Registration reg = new Registration();
	 * reg.setType(IQ.Type.SET); reg.setTo(mXMPPConnection.getServiceName()); //
	 * 注意这里createAccount注册时，参数是UserName，不是jid，是"@"前面的部分。
	 * reg.setUsername(account); reg.setPassword(password); //
	 * 这边addAttribute不能为空，否则出错。所以做个标志是android手机创建的吧！！！！！
	 * reg.addAttribute("android", "geolo_createUser_android"); PacketFilter
	 * filter = new AndFilter(new PacketIDFilter(reg.getPacketID()), new
	 * PacketTypeFilter(IQ.class)); PacketCollector collector
	 * =mXMPPConnection.createPacketCollector(filter);
	 * mXMPPConnection.sendPacket(reg); IQ result = (IQ)
	 * collector.nextResult(SmackConfiguration.getPacketReplyTimeout()); // Stop
	 * queuing results停止请求results（是否成功的结果） collector.cancel(); if (result ==
	 * null) { return 0; } else if (result.getType() == IQ.Type.RESULT) { return
	 * 1; } else { if
	 * (result.getError().toString().equalsIgnoreCase("conflict(409)")) { return
	 * 2; } else { return 3; } } }
	 */

	/**
	 * 查询用户
	 * 
	 * @param userName
	 * @return
	 * @throws XMPPException
	 */
	/*
	 * public static List<Session> searchUsers(XMPPConnection
	 * mXMPPConnection,String userName) { List<Session> listUser=new
	 * ArrayList<Session>(); try{ UserSearchManager search = new
	 * UserSearchManager(mXMPPConnection); //此处一定要加上 search. Form searchForm =
	 * search.getSearchForm("search."+mXMPPConnection.getServiceName()); Form
	 * answerForm = searchForm.createAnswerForm();
	 * answerForm.setAnswer("Username", true); answerForm.setAnswer("search",
	 * userName); ReportedData data =
	 * search.getSearchResults(answerForm,"search."
	 * +mXMPPConnection.getServiceName()); Iterator<Row> it = data.getRows();
	 * Row row=null; while(it.hasNext()){ row=it.next(); Session session=new
	 * Session(); session.setFrom(row.getValues("Username").next().toString());
	 * listUser.add(session); } }catch(Exception e){
	 * 
	 * } return listUser; }
	 */

	/**
	 * 更改用户状态
	 */
	public static void setPresence(Context context, XMPPConnection con, int code) {
		if (con == null)
			return;
		Presence presence = null;
		switch (code) {
		case 0:
			presence = new Presence(Presence.Type.available); // 在线
			break;
		case 1:
			presence = new Presence(Presence.Type.available); // 设置Q我吧
			presence.setMode(Presence.Mode.chat);
			break;
		case 2:
			/*
			 * //隐身 Roster roster = con.getRoster(); Collection<RosterEntry>
			 * entries = roster.getEntries(); for (RosterEntry entry : entries)
			 * { presence = new Presence(Presence.Type.unavailable);
			 * presence.setPacketID(Packet.ID_NOT_AVAILABLE);
			 * presence.setFrom(con.getUser()); presence.setTo(entry.getUser());
			 * }
			 */
			// 向同一用户的其他客户端发送隐身状态
			presence = new Presence(Presence.Type.unavailable);
			presence.setPacketID(Packet.ID_NOT_AVAILABLE);
			presence.setFrom(con.getUser());
			presence.setTo(StringUtils.parseBareAddress(con.getUser()));
			break;
		case 3:
			presence = new Presence(Presence.Type.available); // 设置忙碌
			presence.setMode(Presence.Mode.dnd);
			break;
		case 4:
			presence = new Presence(Presence.Type.available); // 设置离开
			presence.setMode(Presence.Mode.away);
			break;
		case 5:
			presence = new Presence(Presence.Type.unavailable); // 离线
			break;
		default:
			break;
		}
		if (presence != null) {
			// presence.setStatus(PreferencesUtils.getSharePreStr(context,
			// "sign"));
			// con.sendPacket(presence);
		}
	}

	/**
	 * 删除当前用户
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean deleteAccount(XMPPConnection connection) {
		try {
			connection.getAccountManager().deleteAccount();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回所有组信息 <RosterGroup>
	 * 
	 * @return List(RosterGroup)
	 */
	/*
	 * public static List<RosterGroup> getGroups(Roster roster) {
	 * List<RosterGroup> groupsList = new ArrayList<RosterGroup>();
	 * Collection<RosterGroup> rosterGroup = roster.getGroups();
	 * Iterator<RosterGroup> i = rosterGroup.iterator(); while (i.hasNext())
	 * groupsList.add(i.next()); return groupsList; }
	 */

	/**
	 * 返回相应(groupName)组里的所有用户<RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	/*
	 * public static List<RosterEntry> getEntriesByGroup(Roster roster, String
	 * groupName) { List<RosterEntry> EntriesList = new
	 * ArrayList<RosterEntry>(); RosterGroup rosterGroup =
	 * roster.getGroup(groupName); Collection<RosterEntry> rosterEntry =
	 * rosterGroup.getEntries(); Iterator<RosterEntry> i =
	 * rosterEntry.iterator(); while (i.hasNext()) EntriesList.add(i.next());
	 * return EntriesList; }
	 */

	/**
	 * 返回所有用户信息 <RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	/*
	 * public static List<RosterEntry> getAllEntries(Roster roster) {
	 * List<RosterEntry> EntriesList = new ArrayList<RosterEntry>();
	 * Collection<RosterEntry> rosterEntry = roster.getEntries();
	 * Iterator<RosterEntry> i = rosterEntry.iterator(); while (i.hasNext()){
	 * RosterEntry rosterentry= (RosterEntry) i.next(); Log.e("jj",
	 * "好友："+rosterentry
	 * .getUser()+","+rosterentry.getName()+","+rosterentry.getType().name());
	 * EntriesList.add(rosterentry); } return EntriesList; }
	 */

	/**
	 * 创建一个组
	 */
	/*
	 * public static boolean addGroup(Roster roster,String groupName) { try {
	 * roster.createGroup(groupName); return true; } catch (Exception e) {
	 * e.printStackTrace(); Log.e("jj", "创建分组异常："+e.getMessage()); return false;
	 * } }
	 * 
	 * public static boolean removeGroup(Roster roster,String groupName) {
	 * return false; }
	 * 
	 * // 添加一个好友 无分组
	 * 
	 * public static boolean addUser(Roster roster,String userName,String name)
	 * { try { roster.createEntry(userName, name, null); return true; } catch
	 * (Exception e) { e.printStackTrace(); return false; }
	 * 
	 * }
	 */
	/**
	 * 添加一个好友到分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	/*
	 * public static boolean addUsers(Roster roster,String userName,String
	 * name,String groupName) { try { roster.createEntry(userName, name,new
	 * String[]{ groupName}); return true; } catch (Exception e) {
	 * e.printStackTrace(); Log.e("jj", "添加好友异常："+e.getMessage()); return false;
	 * }
	 * 
	 * }
	 */

	/**
	 * 删除一个好友
	 * 
	 * @param roster
	 * @param userJid
	 * @return
	 */
	/*
	 * public static boolean removeUser(Roster roster,String userJid) { try {
	 * RosterEntry entry = roster.getEntry(userJid); roster.removeEntry(entry);
	 * return true; } catch (Exception e) { e.printStackTrace(); return false; }
	 * }
	 */

	/**
	 * 把一个好友添加到一个组中
	 * 
	 * @param userJid
	 * @param groupName
	 */
	/*
	 * public static void addUserToGroup(final String userJid, final String
	 * groupName, final XMPPConnection connection) { RosterGroup group =
	 * connection.getRoster().getGroup(groupName); // 这个组已经存在就添加到这个组，不存在创建一个组
	 * RosterEntry entry = connection.getRoster().getEntry(userJid); try { if
	 * (group != null) { if (entry != null) group.addEntry(entry); } else {
	 * RosterGroup newGroup = connection.getRoster().createGroup("我的好友"); if
	 * (entry != null) newGroup.addEntry(entry); } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * 把一个好友从组中删除
	 * 
	 * @param userJid
	 * @param groupName
	 */
	/*
	 * public static void removeUserFromGroup(final String userJid,final String
	 * groupName, final XMPPConnection connection) { RosterGroup group =
	 * connection.getRoster().getGroup(groupName); if (group != null) { try {
	 * RosterEntry entry = connection.getRoster().getEntry(userJid); if (entry
	 * != null) group.removeEntry(entry); } catch (XMPPException e) {
	 * e.printStackTrace(); } } }
	 */

	/**
	 * 发送消息
	 * 
	 * @param mXMPPConnection
	 * @param chatSendDomain 消息体
	 * @return 消息的ID
	 */
	public static String sendMessage(XMPPConnection mXMPPConnection,ChatSendDomain chatSendDomain)
			throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		ChatManager chatmanager = mXMPPConnection.getChatManager();
		
		Chat chat = chatmanager.createChat(chatSendDomain.getTouser() + "@"
				+ mXMPPConnection.getServiceName(), null);
		String msgid = "";
		if (chat != null) {
			Message message = new Message();
			//时间
			message.setProperty(DcAndroidConsts.MSG_KEY_TIME, chatSendDomain.getCreatetime());		
			message.setBody(chatSendDomain.getContent());
			//增加消息类型
			MessageTypePacketExtension msgpakext = new MessageTypePacketExtension(chatSendDomain.getMsgtype());
			message.addExtension(msgpakext);
			
			if(chatSendDomain.getShareinfo() != null && !chatSendDomain.getShareinfo().equals(""))
			{
				//增加第三方分享链接
				MessageURLPacketExtension msgurlpakext = new MessageURLPacketExtension(chatSendDomain.getShareinfo());
				message.addExtension(msgurlpakext);
			}
			msgid = message.getPacketID();
			chat.sendMessage(message);
			Log.e("zhxychat", "发送成功");
		}
		return msgid;
	}

	/**
	 * 重新发送消息
	 * @param mXMPPConnection
	 * @param chatSendDomain 消息体
	 * @throws XMPPException
	 */
	public static void reSendMessage(XMPPConnection mXMPPConnection,ChatSendDomain chatSendDomain)
			throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		ChatManager chatmanager = mXMPPConnection.getChatManager();

		Chat chat = chatmanager.createChat(chatSendDomain.getTouser()  + "@"
				+ mXMPPConnection.getServiceName(), null);

		if (chat != null) {
			Message message = new Message();
			message.setProperty(DcAndroidConsts.MSG_KEY_TIME, DcDateUtils.getCurrentTimeAsString());
			message.setBody(chatSendDomain.getContent());
			//增加消息类型
			MessageTypePacketExtension msgpakext = new MessageTypePacketExtension(chatSendDomain.getMsgtype());
			message.addExtension(msgpakext);
			
			if(chatSendDomain.getShareinfo() != null && !chatSendDomain.getShareinfo().equals(""))
			{
				//增加第三方分享链接
				MessageURLPacketExtension msgurlpakext = new MessageURLPacketExtension(chatSendDomain.getShareinfo());
				message.addExtension(msgurlpakext);
			}
			message.setPacketID(chatSendDomain.getChatid());
			chat.sendMessage(message);
			Log.e("zhxychat", "发送成功");
		}
	}

	/**
	 * 发送消息回执
	 * 
	 * @param mXMPPConnection
	 * @param id
	 * @param touser
	 * @throws XMPPException
	 */
	public void sendMessageBack1(XMPPConnection mXMPPConnection,
			String id, String touser) throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		ChatManager chatmanager = mXMPPConnection.getChatManager();
		Chat chat = chatmanager.createChat(touser + "@"
				+ mXMPPConnection.getServiceName(), null);
		if (chat != null) {
			Message message = new Message();

			// 回执一个空消息，并添加子节点 recvok，命名空间 com.gaotai.cn.recvflag
			MessagePacketExtension msgpakext = new MessagePacketExtension(
					id);
			message.addExtension(msgpakext);
			message.setPacketID(id);
			chat.sendMessage(message);
			Log.e("zhxychat", "发送回执成功");
		}
	}

	/**
	 * 发送群组消息回执
	 * 
	 * @param mXMPPConnection
	 * @param id
	 * @param touser
	 * @throws XMPPException
	 */
	public static void sendMessageBack(XMPPConnection mXMPPConnection,
			String id, String touser) throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		ChatManager chatmanager = mXMPPConnection.getChatManager();
		Chat chat = chatmanager.createChat(touser, null);
		if (chat != null) {
			Message message = new Message();

			// 回执一个空消息，并添加子节点 recvok，命名空间 com.gaotai.cn.recvflag
			MessagePacketExtension msgpakext = new MessagePacketExtension(
					id);
			message.addExtension(msgpakext);
			message.setPacketID(id);
			chat.sendMessage(message);
			Log.e("zhxychat", "发送回执成功");
		}
	}
	
	/**
	 * 发送群组消息
	 * 
	 * @param mXMPPConnection 当前连接 
	 * @param muc  群组聊天
	 * @param groupid 聊天ID
	 * @param chatSendDomain 消息体内容
	 * @throws XMPPException
	 */
	public static String sendGroupMessage(XMPPConnection mXMPPConnection,MultiUserChat muc,String groupid, ChatSendDomain chatSendDomain) throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		String msgid = "";
		if (muc != null) {
			muc.getRoom();
			Message message = new Message(
				groupid + DcAndroidConsts.XMPP_SERVICE_CONFERENCE
							+ mXMPPConnection.getServiceName(), Message.Type.groupchat);
			message.setProperty(DcAndroidConsts.MSG_KEY_TIME, DcDateUtils.getCurrentTimeAsString());
			message.setBody(chatSendDomain.getContent());
			
			//增加消息类型
			MessageTypePacketExtension msgpakext = new MessageTypePacketExtension(chatSendDomain.getMsgtype());
			message.addExtension(msgpakext);
			
			if(!TextUtils.isEmpty(chatSendDomain.getShareinfo()))
			{
				//增加第三方分享链接
				MessageURLPacketExtension msgurlpakext = new MessageURLPacketExtension(chatSendDomain.getShareinfo());
				message.addExtension(msgurlpakext);
			}			
			msgid = message.getPacketID();
			muc.sendMessage(message);
			Log.e("zhxychat", "发送群组消息成功");
		}
	
		/*
		 * if(mXMPPConnection==null||!mXMPPConnection.isConnected()){ throw new
		 * XMPPException(); } String msgid = "";
		 * 
		 * ChatManager chatmanager = mXMPPConnection.getChatManager(); Chat chat
		 * =chatmanager.createChat(touser + "@" + mXMPPConnection.getServiceName(),
		 * null); if (chat != null) { Message message = new Message(touser + "@"
		 * + mXMPPConnection.getServiceName(),Type.groupchat);
		 * message.setProperty(DcAndroidConsts.MSG_KEY_TIME, createtime);
		 * message.setBody(content); chat.sendMessage(message); msgid =
		 * message.getPacketID(); Log.e("zhxychat", "发送群组消息成功"); }
		 */

		return msgid;
	}

	/**
	 * 群组消息重新发送
	 * 
	 * @param mXMPPConnection 当前连接 
	 * @param muc  群组聊天
	 * @param groupid 聊天ID
	 * @param chatSendDomain 消息体内容
	 * @throws XMPPException
	 */
	public static void reSendGroupMessage(XMPPConnection mXMPPConnection,MultiUserChat muc,String groupid, ChatSendDomain chatSendDomain)
			throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		if (muc != null) {
			Message message = new Message(
				groupid + DcAndroidConsts.XMPP_SERVICE_CONFERENCE
							+ mXMPPConnection.getServiceName(), Message.Type.groupchat);

			message.setProperty(DcAndroidConsts.MSG_KEY_TIME, DcDateUtils.getCurrentTimeAsString());
			message.setBody(chatSendDomain.getContent());
			
			//增加消息类型
			MessageTypePacketExtension msgpakext = new MessageTypePacketExtension(chatSendDomain.getMsgtype());
			message.addExtension(msgpakext);
			
			if(!TextUtils.isEmpty(chatSendDomain.getShareinfo()))
			{
				//增加第三方分享链接
				MessageURLPacketExtension msgurlpakext = new MessageURLPacketExtension(chatSendDomain.getShareinfo());
				message.addExtension(msgurlpakext);
			}			
			message.setPacketID(chatSendDomain.getChatid());			
			muc.sendMessage(message);

			Log.e("zhxychat", "重发群组消息成功");
		}
	}

	/**
	 * 发送消息回执
	 * 
	 * @param mXMPPConnection
	 * @param id
	 * @param touser
	 * @throws XMPPException
	 */
	public static void sendGroupMessageBack(XMPPConnection mXMPPConnection,
			String id, String touser) throws XMPPException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			throw new XMPPException();
		}
		ChatManager chatmanager = mXMPPConnection.getChatManager();
		Chat chat = chatmanager.createChat(touser, null);
		if (chat != null) {
			Message message = new Message();
			MessagePacketExtension msgpakext = new MessagePacketExtension(
					id);
			message.addExtension(msgpakext);
			message.setPacketID(id);
			chat.sendMessage(message);
			Log.e("zhxychat", "发送回执成功:" + id);
		}
	}

	/**
	 * 发送iq请求
	 * 
	 * @throws XMPPException
	 */
	public static void sendGroupMemberIQ(XMPPConnection mXMPPConnection,
			String groupid, String form) throws XMPPException {
		mXMPPConnection.sendPacket(getGroupMemberXml(mXMPPConnection,groupid, form));
	}

	/***
	 * 设置请求的 iq
	 * 
	 * @param groupid
	 * @param form
	 * @return
	 */
	public static IQ getGroupMemberXml(XMPPConnection mXMPPConnection,String groupid, String form) {
		IQ iq = new IQ() {
			public String getChildElementXML() {
				StringBuilder buf = new StringBuilder();
				buf.append("<mucquery xmlns='com.gaotai.cn.muc.members'></mucquery>");
				return buf.toString();
			}
		};
		iq.setTo(groupid + DcAndroidConsts.XMPP_SERVICE_CONFERENCE  + mXMPPConnection.getServiceName());
		iq.setType(IQ.Type.GET);
		// 方法如名，这里是设置这份报文来至那个JID,后边的/smack是这段信息来至哪个端，如spark端就是/spark，android就是/Smack
		iq.setFrom(form + "@" + mXMPPConnection.getServiceName());
		return iq;
	}

		
	/***
	 * 设置请求的 iq
	 * 
	 * @param groupid
	 * @param form
	 * @return
	 */
	public static IQ getcreatMultiRoomXml(final XMPPConnection mXMPPConnection,final List<String> memberlist,final String groupid,final String name,final String form) {
		IQ iq = new IQ() {
			public String getChildElementXML() {
				StringBuilder buf = new StringBuilder();				
				buf.append("<query xmlns=\"http://jabber.org/protocol/muc#owner\">");
				buf.append("<x xmlns=\"jabber:x:data\" type=\"submit\">");
				buf.append("<field var=\"FORM_TYPE\" type=\"hidden\"><value>http://jabber.org/protocol/muc#roomconfig</value></field>");
				buf.append("<field var=\"muc#roomconfig_roomname\" type=\"text-single\"><value>" + groupid+"</value></field>");
				buf.append("<field var=\"muc#roomconfig_roomdesc\" type=\"text-single\"><value>" + name +"</value></field>");
				buf.append("<field var=\"muc#roomconfig_persistentroom\" type=\"boolean\"><value>1</value></field>");
				buf.append("<field var=\"muc#roomconfig_roomowners\" type=\"jid-multi\"><value>" + form + "@" + mXMPPConnection.getServiceName() + "</value></field>");
				buf.append("<field var=\"muc#roomconfig_members\" type=\"jid-multi\">");
				
				for(int i=0;i< memberlist.size();i++)
				{
					buf.append("<value>" + memberlist.get(i) + "@" + mXMPPConnection.getServiceName() +"</value>");
				}
				
				buf.append("</field>");
				buf.append("</x>");
				buf.append("</query>");
				return buf.toString();
			}
		};
		iq.setTo(groupid + "@" + mXMPPConnection.getServiceName());
		iq.setType(IQ.Type.SET);
		// 方法如名，这里是设置这份报文来至那个JID,后边的/smack是这段信息来至哪个端，如spark端就是/spark，android就是/Smack
		iq.setFrom(form + "@" + mXMPPConnection.getServiceName());
		return iq;
	}
	
	/**
	 * 创建聊天室 群组
	 * @param mXMPPConnection
	 * @param groupid
	 * @param name
	 * @param myuid
	 */
	public static boolean creatMultiRoom(XMPPConnection mXMPPConnection,
			List<String> memberlist,String groupid, String name,String myuid) {
		try
		{
			//mXMPPConnection.sendPacket(getcreatMultiRoomXml(memberlist,groupid,name,myuid));
			
			
			// 使用XMPPConnection创建一个MultiUserChat
			MultiUserChat muc = new MultiUserChat(mXMPPConnection, groupid
					+ DcAndroidConsts.XMPP_SERVICE_CONFERENCE + mXMPPConnection.getServiceName());

			// 创建聊天室
			muc.create(groupid);

			// 获得聊天室的配置表单
			Form form = muc.getConfigurationForm();
			// 根据原始表单创建一个要提交的新表单。
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复
			for (Iterator fields = form.getFields(); fields.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType())
						&& field.getVariable() != null) {
					// 设置默认值作为答复
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}

			// 设置描述
			submitForm.setAnswer("muc#roomconfig_roomdesc", name);

			// 设置聊天室的新拥有者
			List owners = new ArrayList();
			owners.add(myuid + "@" + mXMPPConnection.getServiceName() );
			
			//owners.add("liaonaibo1\\40slook.cc");
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);

			// 设置聊天室是持久聊天室，即将要被保存下来
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			// 房间仅对成员开放
			submitForm.setAnswer("muc#roomconfig_membersonly", false);
			// 允许占有者邀请其他人
			submitForm.setAnswer("muc#roomconfig_allowinvites", true);
			// 能够发现占有者真实 JID 的角色
			// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
			// 登录房间对话
			submitForm.setAnswer("muc#roomconfig_enablelogging", true);
			// 仅允许注册的昵称登录
			submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
			// 允许使用者修改昵称
			submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
			// 允许用户注册房间
			submitForm.setAnswer("x-muc#roomconfig_registration", false);

			// 聊天室成员
			submitForm.setAnswer("muc#roomconfig_members", memberlist);

			// 发送已完成的表单（有默认值）到服务器来配置聊天室
			muc.sendConfigurationForm(submitForm);
		
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 离开聊天室 群组 -- 旧版
	 * @param mXMPPConnection
	 * @param groupid
	 */
	public static boolean leaveMultiRoom(XMPPConnection mXMPPConnection,String groupid) {
		try
		{			
			// 使用XMPPConnection创建一个MultiUserChat
			MultiUserChat muc = new MultiUserChat(mXMPPConnection, groupid
					+ DcAndroidConsts.XMPP_SERVICE_CONFERENCE + mXMPPConnection.getServiceName());
			muc.leave();
			
			return true;
		} catch (Exception e) {
				e.printStackTrace();
		}
		return false;
	}

	/**
	 * 离开聊天室 群组
	 * @param mXMPPConnection
	 * @param userid
	 * @param groupid
	 */
	public static boolean leaveMultiLinkRoom(XMPPConnection mXMPPConnection,String userid,String groupid) {
		try
		{
			MultiUserChat muc = XmppConnectionManager.getInstance().linkRoom(userid, "123456",String.valueOf(groupid));
			if (muc != null) {
				muc.join(userid,"123456");
				muc.leave();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解散聊天室 群组
	 * @param mXMPPConnection
	 * @param userid
	 * @param groupid
	 */
	public static boolean destroyMultiLinkRoom(XMPPConnection mXMPPConnection,String userid,String groupid) {
		try
		{
			MultiUserChat muc = XmppConnectionManager.getInstance().linkRoom(userid, "123456",String.valueOf(groupid));
			if (muc != null) {
				muc.join(userid,"123456");
				muc.destroy("Test", null);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解散聊天室 群组 -- 旧版
	 * @param mXMPPConnection
	 * @param groupid
	 */
	public static boolean destroyMultiRoom(XMPPConnection mXMPPConnection,String groupid) {
		try
		{			
			// 使用XMPPConnection创建一个MultiUserChat
			MultiUserChat muc = new MultiUserChat(mXMPPConnection, groupid
					+ DcAndroidConsts.XMPP_SERVICE_CONFERENCE + mXMPPConnection.getServiceName());
			
			muc.destroy("Test", null);
			return true;
		} catch (Exception e) {
				e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 聊天室踢人
	 * @param mXMPPConnection
	 * @param groupid
	 */
	public static boolean kickParticipantMultiRoom(XMPPConnection mXMPPConnection,String groupid,String username) {
		try
		{			
			// 使用XMPPConnection创建一个MultiUserChat
			MultiUserChat muc = new MultiUserChat(mXMPPConnection, groupid
					+ DcAndroidConsts.XMPP_SERVICE_CONFERENCE + mXMPPConnection.getServiceName());
			//muc.kickParticipant(username, "看你不爽就 踢了你"); 
			muc.banUser(username, "看你不爽就踢了你");
			//Collection<String> list = new ArrayList<String>();
			//list.add(username);
			//muc.banUsers(list);
			return true;
		} catch (Exception e) {
				e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 聊天室踢人
	 * @param mXMPPConnection
	 * @param groupid
	 */
	public static boolean kickParticipantMultiRoom(XMPPConnection mXMPPConnection,String groupid,Collection<String> userlist) {
		try
		{
			// 使用XMPPConnection创建一个MultiUserChat
			MultiUserChat muc = new MultiUserChat(mXMPPConnection, groupid
					+ DcAndroidConsts.XMPP_SERVICE_CONFERENCE + mXMPPConnection.getServiceName());
			//muc.kickParticipant(username, "看你不爽就 踢了你"); 
			//muc.banUser(username, "看你不爽就踢了你");
			//Collection<String> list = new ArrayList<String>();
			//list.add(username);
			muc.banUsers(userlist);
			return true;
		} catch (Exception e) {
				e.printStackTrace();
		}
		return false;
	}
	
}

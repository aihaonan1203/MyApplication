package com.gaotai.baselib.smack;

import java.util.Date;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.provider.GroupCreateIQProvider;
import com.gaotai.baselib.smack.provider.GroupMemberIQProvider;
import com.gaotai.baselib.smack.provider.MUCGroupListIQProvider;

import android.util.Log;

/**
 * 
 * XMPP服务器连接工具类.
 * 
 */
public class XmppConnectionManager {
	private XMPPConnection connection;
	private static ConnectionConfiguration connectionConfig;
	private static XmppConnectionManager xmppConnectionManager;


	/**
	 *  即时聊天地址
	 */
	private String  xmpp_host = "";
	/**
	 *  即时聊天端口
	 */
	private int xmpp_port = 5222;
	/**
	 * 即时聊天服务地址
	 */
	private String  xmpp_service_name = "";
	
	
	private XmppConnectionManager() {

	}

	public static XmppConnectionManager getInstance() {
		if (xmppConnectionManager == null) {
			xmppConnectionManager = new XmppConnectionManager();
		}
		return xmppConnectionManager;
	}

	/**
	 * 初始化即时消息
	 */
	public XMPPConnection init(String xmpp_host,int xmpp_port,String xmpp_service_name) {
		this.xmpp_host = xmpp_host;
		this.xmpp_port = xmpp_port;
		this.xmpp_service_name = xmpp_service_name;
		
		
		Connection.DEBUG_ENABLED = false;
		ProviderManager pm = ProviderManager.getInstance();
		configure(pm);

		connectionConfig = new ConnectionConfiguration(xmpp_host, xmpp_port, xmpp_service_name);
		connectionConfig.setSASLAuthenticationEnabled(false);// 不使用SASL验证，设置为false
		connectionConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		// 允许自动连接
		connectionConfig.setReconnectionAllowed(false);
		// 允许登陆成功后更新在线状态
		//connectionConfig.setSendPresence(true);
		connectionConfig.setSendPresence(false); 
		//roster 
		connectionConfig.setRosterLoadedAtLogin(false);
		// 收到好友邀请后manual表示需要经过同意,accept_all表示不经同意自动为好友
		//Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
		connection = new XMPPConnection(connectionConfig);
		
	
	
		return connection;
	}

	/**
	 * 
	 * 返回一个有效的xmpp连接,如果无效则返回空.
	 * 
	 * @return
	 */
	public XMPPConnection getConnection() {
		if (connection == null) {
			throw new RuntimeException("请先初始化XMPPConnection连接");
		}
		return connection;
	}

	/**
	 * 
	 * 销毁xmpp连接.
	 * 
	 */
	public void disconnect() {
		if (connection != null) {
			connection.disconnect();
		}
	}

	public void configure(ProviderManager pm) {

		// Private Data Storage
		pm.addIQProvider("query", "jabber:iq:private",
				new PrivateDataManager.PrivateDataIQProvider());

		// Time
		try {
			pm.addIQProvider("query", "jabber:iq:time",
					Class.forName("org.jivesoftware.smackx.packet.Time"));
		} catch (ClassNotFoundException e) {
		}

		// XHTML
		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
				new XHTMLExtensionProvider());

		// Roster Exchange
		//pm.addExtensionProvider("x", "jabber:x:roster",new RosterExchangeProvider());
		
		// Message Events
		pm.addExtensionProvider("x", "jabber:x:event",
				new MessageEventProvider());
		// Chat State
		pm.addExtensionProvider("active",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("composing",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("paused",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("inactive",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("gone",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());

		// FileTransfer
		pm.addIQProvider("si", "http://jabber.org/protocol/si",
				new StreamInitiationProvider());

		// Group Chat Invitations
		pm.addExtensionProvider("x", "jabber:x:conference",
				new GroupChatInvitation.Provider());
		// Service Discovery # Items
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
				new DiscoverItemsProvider());
		// Service Discovery # Info
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
				new DiscoverInfoProvider());
		// Data Forms
		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
		// MUC User
		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
				new MUCUserProvider());
		// MUC Admin
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
				new MUCAdminProvider());
		// MUC Owner
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
				new MUCOwnerProvider());
		// Delayed Delivery
		pm.addExtensionProvider("x", "jabber:x:delay",
				new DelayInformationProvider());
		// Version
		try {
			pm.addIQProvider("query", "jabber:iq:version",
					Class.forName("org.jivesoftware.smackx.packet.Version"));
		} catch (ClassNotFoundException e) {
		}
		// VCard
		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
		// Offline Message Requests
		pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
				new OfflineMessageRequest.Provider());
		// Offline Message Indicator
		pm.addExtensionProvider("offline",
				"http://jabber.org/protocol/offline",
				new OfflineMessageInfo.Provider());
		// Last Activity
		//pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
		
		// User Search
		//pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
		
		// SharedGroupsInfo
		//pm.addIQProvider("sharedgroup","http://www.jivesoftware.org/protocol/sharedgroup",
		//		new SharedGroupsInfo.Provider());
		
		// JEP-33: Extended Stanza Addressing
		pm.addExtensionProvider("addresses",
				"http://jabber.org/protocol/address",
				new MultipleAddressesProvider());

		try
		{
			//每当遇到标签为muc同时命名空间为YANG的IQ包时，交由MUCPacketExtensionProvider处理
			pm.addIQProvider("muc", DcAndroidConsts.MSG_ROOMS_NAME_SPACE, new MUCGroupListIQProvider());  
			
			
			// 群成员查看处理
			pm.addIQProvider("mucquery", DcAndroidConsts.MSG_MUCMEMBERS_NAME_SPACE, new GroupMemberIQProvider());
			
			// 群组创建等处理
			pm.addIQProvider("muc", "com.gaotai.cn.rooms.create", new GroupCreateIQProvider());
			
			//ping的等待时间
			SmackConfiguration.setPacketReplyTimeout(10000);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*
	private MultiUserChat muc;

	public MultiUserChat getMuc() {
		return muc;
	}

	public void setMuc(MultiUserChat muc) {
		this.muc = muc;
	}*/
	
	/**
	 * 加入聊天室
	 * 
	 * @param user
	 * @param pwd
	 *            会议室密码
	 * @param roomName
	 * @return
	 */
	public MultiUserChat linkRoom(String user, String pwd, String roomName) {
		MultiUserChat muc = new MultiUserChat(connection,roomName + DcAndroidConsts.XMPP_SERVICE_CONFERENCE + xmpp_service_name);
		return muc;
	}
		
	/**
	 * 加入聊天室
	 * 
	 * @param user
	 * @param pwd
	 *            会议室密码
	 * @param roomName
	 * @return
	 */
	public MultiUserChat joinRoom(String user, String pwd, String roomName) {
		MultiUserChat muc = new MultiUserChat(connection,roomName + DcAndroidConsts.XMPP_SERVICE_CONFERENCE + xmpp_service_name);
		DiscussionHistory history = new DiscussionHistory();
		history.setMaxStanzas(20);
		history.setSince(new Date(2015, 04, 31));
		// history.setSince(new Date());
		try {
			muc.join(user, pwd, history,
					SmackConfiguration.getPacketReplyTimeout());
			//Message msg = muc.nextMessage();
			//if (null != msg)
			//{
			//	SLog.i(tag, msg.toXML());
			//}
			//Message msg2 = muc.nextMessage(100);
			//if (null != msg2)
			//{
				//SLog.i(tag, msg2.toXML());
			//}
		} catch (XMPPException e) {
			//SLog.e(tag, " 加入 聊天室 出错");
			//SLog.e(tag, Log.getStackTraceString(e));
			return null;
		}
		return muc;
	}
}

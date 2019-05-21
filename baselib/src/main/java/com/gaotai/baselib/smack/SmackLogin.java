package com.gaotai.baselib.smack;

import com.gaotai.baselib.DcAndroidConsts;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.provider.ProviderManager;

/**
 * 即时聊天登录
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@gaotai.cn">作者中文名</a>
 * @version $Revision$
 */
public class SmackLogin {
	/**
	 * 登录提示 成功
	 */
	public static final int LOGIN_SECCESS = 0;
	public static final int HAS_NEW_VERSION = 1;// 发现新版本
	public static final int IS_NEW_VERSION = 2;// 当前版本为最新
	public static final int LOGIN_ERROR_ACCOUNT_PASS = 3;// 账号或者密码错误
	public static final int SERVER_UNAVAILABLE = 4;// 无法连接到服务器
	public static final int LOGIN_ERROR = 5;// 连接失败

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param xmpp_host
	 * @param xmpp_port
	 * @param xmpp_service_name
	 * @return
	 */
	public Integer login(String username, String password,String xmpp_host,int xmpp_port,String xmpp_service_name) {
		// 初始化xmpp配置
		XmppConnectionManager.getInstance().init(xmpp_host,xmpp_port,xmpp_service_name);

		try {
			XMPPConnection connection = XmppConnectionManager.getInstance()
					.getConnection();

			connection.connect();
			
				
			if(!connection.isAuthenticated())
			{
				connection.login(username, password, DcAndroidConsts.XMPP_SERVICE_RESOURCE); // 登录
			}
			// OfflineMsgManager.getInstance(activitySupport).dealOfflineMsg(connection);//处理离线消息
			// connection.sendPacket(new Presence(Presence.Type.available));
			/*
			 * if (loginConfig.isNovisible()) {// 隐身登录 Presence presence = new
			 * Presence(Presence.Type.unavailable); Collection<RosterEntry>
			 * rosters = connection.getRoster() .getEntries(); for (RosterEntry
			 * rosterEntry : rosters) { presence.setTo(rosterEntry.getUser());
			 * connection.sendPacket(presence); } }
			 * loginConfig.setUsername(username); if (loginConfig.isRemember())
			 * {// 保存密码 loginConfig.setPassword(password); } else {
			 * loginConfig.setPassword(""); }
			 */
			// loginConfig.setOnline(true);
			
			
			
			return LOGIN_SECCESS;
		} catch (Exception xee) {
			if (xee instanceof XMPPException) {
				XMPPException xe = (XMPPException) xee;
				final XMPPError error = xe.getXMPPError();
				int errorCode = 0;
				if (error != null) {
					errorCode = error.getCode();
				}
				if (errorCode == 401) {
					return LOGIN_ERROR_ACCOUNT_PASS;
				} else if (errorCode == 403) {
					return LOGIN_ERROR_ACCOUNT_PASS;
				} else {
					return SERVER_UNAVAILABLE;
				}
			} else {
				return LOGIN_ERROR;
			}
		}
	}

	/**
	 * 重登录
	 * @param username
	 * @param password
	 * @return
	 */
	public Integer relogin(String username, String password,String xmpp_host,int xmpp_port,String xmpp_service_name,
			XMPPConnection connection) {

		try {			
			if (connection != null) {
				connection.connect();
				//connection.disconnect();
			}
			else
			{
				XmppConnectionManager.getInstance().init(xmpp_host,xmpp_port,xmpp_service_name);
				connection = XmppConnectionManager.getInstance().getConnection();
			}
			
			if(!connection.isAuthenticated()){
				connection.login(username, password, DcAndroidConsts.XMPP_SERVICE_RESOURCE); // 登录
			}
			return LOGIN_SECCESS;
			
		} catch (Exception xee) {
			if (xee instanceof XMPPException) {
				XMPPException xe = (XMPPException) xee;
				final XMPPError error = xe.getXMPPError();
				int errorCode = 0;
				if (error != null) {
					errorCode = error.getCode();
				}
				if (errorCode == 401) {
					return LOGIN_ERROR_ACCOUNT_PASS;
				} else if (errorCode == 403) {
					return LOGIN_ERROR_ACCOUNT_PASS;
				} else {
					return SERVER_UNAVAILABLE;
				}
			} else {
				return LOGIN_ERROR;
			}
		}
	}
}

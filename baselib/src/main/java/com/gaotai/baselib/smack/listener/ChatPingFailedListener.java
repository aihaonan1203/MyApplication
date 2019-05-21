package com.gaotai.baselib.smack.listener;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.keepalive.KeepAliveManager;
import org.jivesoftware.smack.ping.PingFailedListener;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.XmppConnectionManager;

import android.util.Log;

/**
 * 即时消息监听ping
 * 
 */
public class ChatPingFailedListener implements PingFailedListener{
	/**
	 * 全局ping失败计数器
	 */
	int rePingTimes = 0;
	
	KeepAliveManager km = null;
	
	public ChatPingFailedListener(KeepAliveManager km)
	{
		this.km = km;
	}
	
	@Override
	public void pingFailed() {
		// TODO Auto-generated method stub
		if (km == null) {
			km = KeepAliveManager.getInstanceFor(XmppConnectionManager
					.getInstance().getConnection());
		}
		rePingTimes++;
		// ping 失败
		Log.e("zhxy smack ping", "失败");
		if (rePingTimes > 3) {
			// 重置
			rePingTimes = 0;
			km.stopPinging();
			// 重连
			Log.e("zhxy smack ping", "开始重连");
			MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_RECONNECT,null);
		} else {
			if (rePingTimes <= 3) {
				km.stopPinging();
				km.setPingInterval(SmackConfiguration
						.getKeepAliveInterval() - rePingTimes % 3 * 5000);// 递减5秒，重ping
			}
		}
	}

}

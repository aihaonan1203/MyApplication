package com.gaotai.baselib.smack.listener;

import org.jivesoftware.smack.ConnectionListener;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.util.AndroidUtil;

import android.content.Context;
import android.util.Log;

/**
 * 即时消息链接监听 
 * 1.监听到链接异常 通知重新链接 
 * 2.多用户登录超出限制 通知
 */
public class ChatConnectionListener implements ConnectionListener
{

	private Context context;

	public ChatConnectionListener(Context context)
	{
		this.context = context;
	}

	@Override
	public void connectionClosed()
	{
		// TODO Auto-generated method stub
		Log.e("zhxy samck ERROR", "XMPP connectionClosed!");
	}

	@Override
	public void connectionClosedOnError(Exception e)
	{
		// TODO Auto-generated method stub
		// 验证是否联网
		if(AndroidUtil.isHasNetWork(context))
		{
			Log.e("ERROR", "XMPP连接异常，失败!", e);
			// 判斷為帳號已被登錄
			boolean error = false;
			// e.getMessage().equals("stream:error (conflict)");
			if(e.getMessage().contains("stream:error") && e.getMessage().contains("conflict"))
			{
				error = true;
			}
			if(error)
			{
				MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_MAC_RECONNECT,
					null);
			}
			else
			{
				// 执行重连
				MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_RECONNECT,
					null);
			}
		}
	}

	@Override
	public void reconnectingIn(int seconds)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnectionSuccessful()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnectionFailed(Exception e)
	{
		// TODO Auto-generated method stub

	}

}

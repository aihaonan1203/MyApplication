package com.gaotai.baselib.smack.broadcast;

import org.jivesoftware.smack.XMPPConnection;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.XmppConnectionManager;
import com.gaotai.baselib.smack.listener.MessageListenerContext;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
/**
 * 监听网络开启关闭
 * 网络开启时，判断登录状态，未登陆通知监听进行登录
 */
public class NetWorkConnectionBroadcastReceiver extends BroadcastReceiver  {

	private ConnectivityManager connectivityManager;
	
	private NetworkInfo info;
	
	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		// TODO Auto-generated method stub
		try {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Log.d("mark", "网络状态已经改变");
				connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				XMPPConnection connection = XmppConnectionManager
						.getInstance().getConnection();
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					if (!connection.isConnected()
							|| !connection.isAuthenticated()) {
						MessageListenerContext.getInstance().callMsgAddListener(DcAndroidConsts.CHAT_MESSAGET_PACKET_RECONNECT, null);
					} else {
						sendInentAndPre(DcAndroidConsts.RECONNECT_STATE_SUCCESS);
						// Toast.makeText(context, "用户已上线!",
						// Toast.LENGTH_LONG).show();
					}
				} else {
					// connection.disconnect();
					sendInentAndPre(DcAndroidConsts.RECONNECT_STATE_FAIL);
					// Toast.makeText(context, "网络断开,用户已离线!",
					// Toast.LENGTH_LONG) .show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送是否在线信息
	 * @param isSuccess
	 */
	private void sendInentAndPre(boolean isSuccess) {
		Intent intent = new Intent();
		SharedPreferences preference = context.getSharedPreferences(DcAndroidConsts.LOGIN_SET, 0);
		// 保存在线连接信息
		preference.edit().putBoolean(DcAndroidConsts.IS_ONLINE, isSuccess).commit();
		intent.setAction(DcAndroidConsts.ACTION_RECONNECT_STATE);
		intent.putExtra(DcAndroidConsts.RECONNECT_STATE, isSuccess);
		context.sendBroadcast(intent);
	}

}

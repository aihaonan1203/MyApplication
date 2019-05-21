package com.gaotai.baselib.util;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeviceUtil
{
	
	/**
	 * 获取机器序列号
	 * @return
	 */
	public static String getDeviceCode(Context context) {
		try
		{
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);			
		    final String tmDevice, tmSerial, androidId;
		    tmDevice = "" + tm.getDeviceId();
		    tmSerial = "" + tm.getSimSerialNumber();
		    androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
	 
		    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		    String uniqueId = deviceUuid.toString();
		    Log.d("uniqueId",uniqueId);
		    //Log.d("tmDevice",tmDevice);
		    //Log.d("tmSerial",tmSerial);
		    //Log.d("androidId",androidId);
		    return uniqueId;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	    
		return  "";
	}
}

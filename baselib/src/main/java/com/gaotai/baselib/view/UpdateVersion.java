package com.gaotai.baselib.view;

import java.util.HashMap;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.http.SocketHttpRequester;

public class UpdateVersion extends Thread {
	private Handler mHandler;
	private String mLocalVersion;

	/**
	 *  版本获取地址
	 */
	private String versionServletAddress = "";


	public UpdateVersion(Handler handler, String localVersion,String versionServletAddress){
		this.mHandler = handler;
		this.mLocalVersion = localVersion;
		this.versionServletAddress = versionServletAddress;
	}

	@Override
	public void run() {
		try {
			getVersion();
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
		}
	}

	void getVersion() throws Exception {
		String lastestVersion = "";
		String apkPathServer = "";
		String phoneModel = android.os.Build.MODEL;
        String versionDesc = "";
		String versionResult = getLastestVersion(phoneModel);
		String isupdateapk = "1";
		 
		
		if (null != versionResult && !"no".equals(versionResult)
				&& versionResult.length() != 0) {
			JSONObject jsonObject = new JSONObject(versionResult);
			apkPathServer = jsonObject.getString("filePath");
			lastestVersion = jsonObject.getString("version");
			versionDesc = jsonObject.getString("versionDesc");
			
			if(!jsonObject.isNull("isupdateapk"))
			{
				isupdateapk  = jsonObject.getString("isupdateapk");
			}
			
			//没有找到 {"filePath":null,"versionDesc":null,"isupdateapk":null,"version":null}
			//如果为空 跳过更新
			if(lastestVersion.equals("null") || lastestVersion.equals(""))
			{
				mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
				return;
			}
			
			boolean isdown = true;
			if (lastestVersion.trim().equals(mLocalVersion.trim())) {
				 isdown = false;
			}
			else 
			{
				try
				{
					//验证高低版本
					String[] strlast = lastestVersion.split("\\.");
					String[] strmLocal = mLocalVersion.split("\\.");
					if(strlast.length == 3 && strlast.length == strmLocal.length)
					{
						int local0 = Integer.parseInt(strmLocal[0].toString());
						int local1 = Integer.parseInt(strmLocal[1].toString());
						int local2 = Integer.parseInt(strmLocal[2].toString());

						int strlast0 = Integer.parseInt(strlast[0].toString());
						int strlast1 = Integer.parseInt(strlast[1].toString());
						int strlast2 = Integer.parseInt(strlast[2].toString());
						if (local0 > strlast0) {
							isdown = false;
						} else if (local0 == strlast0 && local1 > strlast1) {
							isdown = false;
						} else if (local0 == strlast0 && local1 == strlast1
								&& local2 > strlast2) {
							isdown = false;
						}
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
			if(isdown)
			{
				Message msg = new Message();
				Bundle data = new Bundle();

				data.putString("apkUrl", apkPathServer);
				data.putString("lastestVersion", lastestVersion);
				data.putString("versionDesc", versionDesc);
				data.putString("isupdateapk", isupdateapk);				
				msg.setData(data);
				msg.what = DcAndroidConsts.down_file;
				mHandler.sendMessage(msg);
			}
			else
			{
				mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
			}
			
			
		} else {
			mHandler.sendEmptyMessage(DcAndroidConsts.down_cancle);
		}
	}

	/**
	 * 获取版本信息
	 * @param type
	 * @return
	 * @throws Exception
     */
	public String getLastestVersion(String type) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("phoneType", type);
		byte[] by  = SocketHttpRequester.post(versionServletAddress, params, "utf-8");
		if(by == null){
			return null;
		}
		String response = new String(by, "utf-8");			
		return response;
	}
}

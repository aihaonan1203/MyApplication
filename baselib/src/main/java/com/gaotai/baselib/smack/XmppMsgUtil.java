package com.gaotai.baselib.smack;

import java.io.File;




import org.json.JSONException;
import org.json.JSONObject;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.util.ImageUtil;

import android.graphics.Bitmap;
import android.util.Base64;

/**
 * 即时消息发送消息 消息内容获取
 * @author mengliang
 */
public class XmppMsgUtil {
	/**
	 * 获取图片的byte[]
	 * @param filepath
	 * @return
	 */
	public static byte[] getImgByte(String filepath)
	{
		Bitmap bitmap = ImageUtil
				.compressImageFromFile(filepath);

		// 三星转换角度
		int jiaodu = ImageUtil.getExifOrientation(filepath);
		if (jiaodu != 0) {
			bitmap = ImageUtil.rotateBitmap(bitmap,
					jiaodu);
		}
		//压缩图片
		byte[] imgbtye = ImageUtil
				.getStrByCompressImage(bitmap);
	
		// 销毁内存中图片
		bitmap.recycle();				
		return imgbtye;
	}
	
	/**
	 * 获取图片的byte[]
	 * @param filepath
	 * @return
	 */
	public static byte[] getFileByte(String filepath)
	{
		File file = new File(filepath);
		if (!file.exists()) {
			return null;
		}
		byte[] data = ImageUtil.GetDataByFile(filepath);
		return data;
	}
	
	/**
	 * 获取语音文件的内容字符
	 * @param filePath  路径
	 * @return
	 */
	public static String getVoiceContext(byte[] filebyte,long time)
	{
		// 语音 + 语音时间
		String content = DcAndroidConsts.VOICE_SIGN + getBase64String(filebyte) + DcAndroidConsts.VOICE_SIGN_FOOT
				+ DcAndroidConsts.VOICE_TIME + String.valueOf(time)
				+ DcAndroidConsts.VOICE_TIME_FOOT;	
		return content;
	}
	
	/**
	 * 获取图片的消息内容字符
	 * @param filebyte  路径
	 * @return
	 */
	public static String getImgContext(byte[] filebyte)
	{
		return  DcAndroidConsts.PIC_SIGN + getBase64String(filebyte) + DcAndroidConsts.PIC_SIGN_FOOT;
	}
	
	
	/**
	 * 获取视频的消息内容字符
	 * @param filePath  路径
	 * @return
	 */
	public static String getVideoContext(byte[] filebyte)
	{
		return DcAndroidConsts.VIDEO_SIGN + getBase64String(filebyte) + DcAndroidConsts.VIDEO_SIGN_FOOT;
	}
	
	

	/**
	 * 获取视频的消息内容字符
	 * @param filePath  路径
	 * @return
	 */
	public static String getLocationContext(String name,String address,double latitude,double longitude,byte[] filebyte)
	{
		String locationname = "";
		if (address != null && !address.equals("")) {
			locationname += name + "<>" + address;
		} else {
			locationname += name;
		}
		locationname += "<>" + latitude + "," + longitude;		
		
		String content = DcAndroidConsts.LOCATION_SIGN + DcAndroidConsts.LOCATIONADDRESS_SIGN
				+ locationname + DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT + getBase64String(filebyte) 
				+ DcAndroidConsts.LOCATION_SIGN_FOOT;
		
		return content;
	}
	
	/**
	 * 获取分享链接的内容字符
	 * @param title 分享的标题
	 * @param weburl 分享的链接地址
	 * @param filebyte 分享图片的byte
	 * @return
	 */
	public static String getWeblinkContext(String title,String weburl,byte[] filebyte)
	{
		String strImgBase64 = "";
		if(filebyte != null)
		{
			strImgBase64 = DcAndroidConsts.WEBLINK_PIC
					+  getBase64String(filebyte) 
					+ DcAndroidConsts.WEBLINK_PIC_FOOT;
		}	
		String content = DcAndroidConsts.WEBLINK_URL + weburl + DcAndroidConsts.WEBLINK_URL_FOOT
				+ DcAndroidConsts.WEBLINK_TITLE + title + DcAndroidConsts.WEBLINK_TITLE_FOOT
				+ strImgBase64;
				
		return content;
	}
		
	/**
	 * byte 转换成字符
	 * @param filebyte
	 */
	private static String getBase64String(byte[] filebyte)
	{
		String content = "";
		try {
			content = Base64.encodeToString(filebyte, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	
	/**
	 * 获取存储到数据的内容
	 * @param msgType 消息类别
	 * @param msginfo 消息内容
	 * @return
	 */
	public static String getContentForDB(String msgType,String msginfo)
	{
		String content = "";
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_IMG))
		{
			return content;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_VOICE))
		{
			// 录音时间
			content = msginfo.substring(
				msginfo.indexOf(DcAndroidConsts.VOICE_TIME) + DcAndroidConsts.VOICE_TIME.length(),
				msginfo.indexOf(DcAndroidConsts.VOICE_TIME_FOOT));
			return content;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_VIDEO))
		{
			return content;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_LOCATION))
		{
			content = msginfo.substring(msginfo.indexOf(DcAndroidConsts.LOCATIONADDRESS_SIGN)
				+ DcAndroidConsts.LOCATIONADDRESS_SIGN.length(),
				msginfo.indexOf(DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT));
			return content;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_WEBLINK))
		{
			content = msginfo.substring(msginfo.indexOf(DcAndroidConsts.WEBLINK_URL),
				msginfo.indexOf(DcAndroidConsts.WEBLINK_TITLE_FOOT) + DcAndroidConsts.WEBLINK_TITLE_FOOT.length());
			return content;
		}
		
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_IMG))
		{
			return content;
		}
		return msginfo;
	}
	
	/**
	 * 从数据库存储的内容获取发送的消息
	 * @param msgType 消息类别
	 * @param msginfo 消息内容
	 * @param filebyte 消息filebyte
	 * @return
	 */
	public static String getSendContentFromDB(String msgType,String msginfo,byte[] filebyte)
	{
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_IMG))
		{
			return getImgContext(filebyte);
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_IMG_URL))
		{
			return msginfo;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_VOICE))
		{
			// 录音
			return getVoiceContext(filebyte,Long.parseLong(msginfo));
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_VIDEO))
		{
			return getVideoContext(filebyte);
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_LOCATION))
		{
			return DcAndroidConsts.LOCATION_SIGN + DcAndroidConsts.LOCATIONADDRESS_SIGN
				+ msginfo + DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT + getBase64String(filebyte) 
				+ DcAndroidConsts.LOCATION_SIGN_FOOT;
		}
		if(msgType.equals(DcAndroidConsts.MSG_TYPE_WEBLINK))
		{
			String strImgBase64 = "";
			if(filebyte != null)
			{
				strImgBase64 = DcAndroidConsts.WEBLINK_PIC
						+  getBase64String(filebyte) 
						+ DcAndroidConsts.WEBLINK_PIC_FOOT;
			}
			return msginfo + strImgBase64;
		}
		return msginfo;
	}
	
	/**
	 * 获取消息链接中的第三方名称
	 * @param strMsglinkurl 消息链接点击数据
	 * @return
	 */
	public static String getMsgAppName(String strMsglinkurl)
	{
		String strMsgAppName = "";//第三方的名称
		if(strMsglinkurl == null || strMsglinkurl.equals(""))
		{
			return strMsglinkurl;
		}
		//转换JSON
		JSONObject object;
		try
		{
			object = new JSONObject(strMsglinkurl);
			//获取第三方名称
			//如果有 1 的 是第三方
			if(!object.isNull("1"))
			{
				String[] strAttParams = object.getString("1").split(";");
				if(strAttParams.length > 5)
				{					
					strMsgAppName = strAttParams[5];
				}
			}
		} catch (JSONException e) {
		}
		return strMsgAppName;
	}
}

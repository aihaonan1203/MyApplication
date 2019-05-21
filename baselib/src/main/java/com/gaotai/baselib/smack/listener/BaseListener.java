package com.gaotai.baselib.smack.listener;

import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.XmppMsgUtil;
import com.gaotai.baselib.smack.domain.MessagePacketDomain;
import com.gaotai.baselib.util.DcDateUtils;

import android.util.Base64;

/**
 * 底层处理的消息监听器
 */
public class BaseListener
{

	/***
	 * 封装基本通用的消息结构体
	 * 
	 * @param message
	 *            消息结构体
	 * @param messagePacketDomain
	 *            消息封装数据
	 */
	protected void dealMessage(Message message, MessagePacketDomain messagePacketDomain)
	{
		// 消息时间
		String createtime = (String) message.getProperty(DcAndroidConsts.MSG_KEY_TIME);
		if(createtime == null)
		{
			createtime = DcDateUtils.getCurrentTimeAsString();
		}

		messagePacketDomain.setChatid(message.getPacketID().toString());		
		//默认格式:yyyymmddhhmmssSSS
		//位数一致		
		if(createtime.length() <= 14)
		{
			createtime = createtime + "000";
		}
		try
		{
			messagePacketDomain.setCreatetime(DcDateUtils.toDate(createtime,DcDateUtils.FORMAT_DATE_TIME_9));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		// 消息类别
		String sendtype = getPacketExtensionValue((DefaultPacketExtension) message.getExtension(
			DcAndroidConsts.MSG_TYPE_KEY_ELEMENT_NAME, DcAndroidConsts.MSG_TYPE_KEY_NAME_SPACE),
			DcAndroidConsts.MSG_TYPE_KEY_ELEMENT_CHILD_NAME);

		if(sendtype == null || sendtype.equals("null"))
		{
			sendtype = "-1";
		}

		//消息链接地址
		String strMsgUrl = getPacketExtensionValue((DefaultPacketExtension) message.getExtension(
			DcAndroidConsts.MSG_URL_KEY_ELEMENT_NAME, DcAndroidConsts.MSG_URL_KEY_NAME_SPACE),
			DcAndroidConsts.MSG_URL_KEY_ELEMENT_CHILD_NAME);
		messagePacketDomain.setMsgurl(strMsgUrl);
		//消息参数信息
		String strAttrParams = getPacketExtensionValue((DefaultPacketExtension) message.getExtension(
			DcAndroidConsts.MSG_ATT_KEY_ELEMENT_NAME, DcAndroidConsts.MSG_ATT_KEY_NAME_SPACE),
			DcAndroidConsts.MSG_ATT_KEY_ELEMENT_CHILD_NAME);
		messagePacketDomain.setAttrparams(strAttrParams);
		//消息富文本信息
		String strDetails = getPacketExtensionValue((DefaultPacketExtension) message.getExtension(
			DcAndroidConsts.MSG_NEWS_KEY_ELEMENT_NAME, DcAndroidConsts.MSG_NEWS_KEY_NAME_SPACE),
			DcAndroidConsts.MSG_NEWS_KEY_ELEMENT_CHILD_NAME);
		messagePacketDomain.setDetails(strDetails);

		String msg = message.getBody();

		byte[] data = null;
		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_IMG))
		{
			if(msg.toString().contains(DcAndroidConsts.PIC_SIGN))
			{
				// 图片
				sendtype = DcAndroidConsts.MSG_TYPE_IMG;
				try
				{
					String newimgstr = msg.substring(DcAndroidConsts.PIC_SIGN.length(),
						msg.indexOf(DcAndroidConsts.PIC_SIGN_FOOT));
					data = Base64.decode(newimgstr, Base64.DEFAULT);
					msg = XmppMsgUtil.getContentForDB(DcAndroidConsts.MSG_TYPE_IMG, msg);
				}
				catch (Exception ex)
				{
					msg = "图片获取失败";
				}
			}
		}

		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_IMG_URL))//网络图片
		{
			sendtype = DcAndroidConsts.MSG_TYPE_IMG_URL;
			try {
				msg = msg.substring(DcAndroidConsts.PIC_SIGN.length(),
						msg.indexOf(DcAndroidConsts.PIC_SIGN_FOOT));
			} catch (Exception ex) {
				//msg = "图片获取失败";
			}
		}

		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_VOICE))
		{
			// 语音
			if(msg.toString().contains(DcAndroidConsts.VOICE_SIGN))
			{
				// 语音
				sendtype = DcAndroidConsts.MSG_TYPE_VOICE;
				try
				{
					String newimgstr = msg.substring(DcAndroidConsts.VOICE_SIGN.length(),
						msg.indexOf(DcAndroidConsts.VOICE_SIGN_FOOT));
					// 录音时间
//					String time = msg.substring(
//						msg.indexOf(DcAndroidConsts.VOICE_TIME) + DcAndroidConsts.VOICE_TIME.length(),
//						msg.indexOf(DcAndroidConsts.VOICE_TIME_FOOT));

					data = Base64.decode(newimgstr, Base64.DEFAULT);

					msg = XmppMsgUtil.getContentForDB(DcAndroidConsts.MSG_TYPE_VOICE, msg);
					
					//msg = createtime + ".amr" + DcAndroidConsts.VOICE_TIME_SPLIT + time;
					//msg = time;

				}
				catch (Exception ex)
				{
					msg = "语音获取失败";
				}
			}
		}

		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_VIDEO))
		{
			// 视频
			if(msg.toString().contains(DcAndroidConsts.VIDEO_SIGN))
			{
				// 视频
				sendtype = DcAndroidConsts.MSG_TYPE_VIDEO;
				try
				{
					String newvideostr = msg.substring(DcAndroidConsts.VIDEO_SIGN.length(),
						msg.indexOf(DcAndroidConsts.VIDEO_SIGN_FOOT));

					data = Base64.decode(newvideostr, Base64.DEFAULT);

					//msg = createtime + ".mp4";
					msg = XmppMsgUtil.getContentForDB(DcAndroidConsts.MSG_TYPE_VIDEO, msg);
				}
				catch (Exception ex)
				{
					msg = "视频获取失败";
				}
			}
		}

		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_LOCATION))
		{
			// 地址
			if(msg.toString().contains(DcAndroidConsts.LOCATION_SIGN))
			{
				// 地址
				sendtype = DcAndroidConsts.MSG_TYPE_LOCATION;
				try
				{
					String newlocationstr = msg.substring(msg.indexOf(DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT)
							+ DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT.length(),
						msg.indexOf(DcAndroidConsts.LOCATION_SIGN_FOOT));

					data = Base64.decode(newlocationstr, Base64.DEFAULT);
					
					msg = XmppMsgUtil.getContentForDB(DcAndroidConsts.MSG_TYPE_LOCATION, msg);
//					msg = msg.substring(msg.indexOf(DcAndroidConsts.LOCATIONADDRESS_SIGN)
//							+ DcAndroidConsts.LOCATIONADDRESS_SIGN.length(),
//						msg.indexOf(DcAndroidConsts.LOCATIONADDRESS_SIGN_FOOT));
				}
				catch (Exception ex)
				{
				}
			}
		}

		if(sendtype.equals("-1") || sendtype.equals(DcAndroidConsts.MSG_TYPE_WEBLINK))
		{
			// 链接形式
			if(msg.toString().contains(DcAndroidConsts.WEBLINK_URL))
			{
				sendtype = DcAndroidConsts.MSG_TYPE_WEBLINK;
				try
				{
					//链接的图片
					String strImgBase64 = msg.substring(msg.indexOf(DcAndroidConsts.WEBLINK_PIC)
							+ DcAndroidConsts.WEBLINK_PIC.length(), msg.indexOf(DcAndroidConsts.WEBLINK_PIC_FOOT));

					//内容
					msg = XmppMsgUtil.getContentForDB(DcAndroidConsts.MSG_TYPE_WEBLINK, msg);
//					msg = msg.substring(msg.indexOf(DcAndroidConsts.WEBLINK_URL),
//						msg.indexOf(DcAndroidConsts.WEBLINK_TITLE_FOOT) + DcAndroidConsts.WEBLINK_TITLE_FOOT.length());

					if(!strImgBase64.equals(""))
					{
						data = Base64.decode(strImgBase64, Base64.DEFAULT);
					}
				}
				catch (Exception ex)
				{
				}
			}
		}

		// 其他没获取到的类型默认文件
		if(sendtype.equals("-1"))
		{
			sendtype = DcAndroidConsts.MSG_TYPE_TEXT;
		}
		messagePacketDomain.setSendtype(sendtype);
		messagePacketDomain.setMsginfo(msg);
		messagePacketDomain.setSenddata(data);
	}

	/**
	 * 获取节点信息
	 * 
	 * @param defaultPacketExtension
	 *            节点
	 * @param childName
	 *            子节点名称
	 * @return
	 */
	public String getPacketExtensionValue(DefaultPacketExtension defaultPacketExtension, String childName)
	{
		String strValue = "";
		if(defaultPacketExtension != null)
		{
			strValue = defaultPacketExtension.getValue(childName);
			if(strValue == null || strValue.equals("null"))
			{
				strValue = "";
			}
		}
		return strValue;
	}

}

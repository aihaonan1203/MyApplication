package com.gaotai.baselib.smack.provider;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.domain.UserGroupDomain;
import com.gaotai.baselib.smack.listener.MessageIQProviderListenerContext;
import com.gaotai.baselib.util.DcDateUtils;
/**
 * 加入新的群组接收 通知
 */
public class GroupCreateIQProvider implements IQProvider {

	private static final String TAG = "smack";
	
	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
				
		int eventType = parser.getEventType();
				
		String groupid = parser.getAttributeValue(null, "name");
		String groupname = "";
		
		
		while (true) {
			if (eventType == XmlPullParser.START_DOCUMENT) {  
				
			}
			if (eventType == XmlPullParser.START_TAG) {
				if ("muc".equals(parser.getName())) {				
					groupname = parser.nextText();
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if ("x".equals(parser.getName())) {
					break;
				}
			}
			eventType = parser.next();
		}
		
		if(!groupname.equals(""))
		{
			UserGroupDomain gdomain = new UserGroupDomain();
			gdomain.setName(groupname);
			gdomain.setId(groupid);
			gdomain.setHeadurl("");
			gdomain.setCreatetime(DcDateUtils.now());
			gdomain.setOrderid(100);
			gdomain.setType(5);
			gdomain.setNote("");
			gdomain.setSoundflag("1");
			
			MessageIQProviderListenerContext.getInstance().callParseIQListener(DcAndroidConsts.CHAT_IQPROVIDER_TYPE_GROUPCREATE, gdomain);
		}		
		return null;
	}

}
package com.gaotai.baselib.smack.provider;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.domain.UserGroupDomain;
import com.gaotai.baselib.smack.listener.MessageIQProviderListenerContext;
import com.gaotai.baselib.util.DcDateUtils;

/**
 * 接收个人加入的群组数据
 */
public class MUCGroupListIQProvider implements IQProvider {

	private static final String TAG = "smack.MUCGroupListIQProvider";

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		
		int eventType = parser.getEventType();		
		List<UserGroupDomain> usergroups = new ArrayList<UserGroupDomain>();
		int orderid = 0;
		// MUCInfo info = null;
		while (true) {
			if (eventType == XmlPullParser.START_TAG) {
				if ("room".equals(parser.getName())) {
					try {
						String roominfo = parser.nextText();
						// Log.e("smack iq", roominfo);
						System.out.println("smack group iq :" + roominfo);
						// String account = parser.getAttributeValue("",
						// "account");
						String room = roominfo.split("#")[1];
						String groupid = roominfo.split("#")[0];
						// parser.getAttributeValue("", "id");
						UserGroupDomain gdomain = new UserGroupDomain();
						gdomain.setName(room);
						gdomain.setId(groupid);
						gdomain.setHeadurl("");
						gdomain.setCreatetime(DcDateUtils.now());
						gdomain.setOrderid(orderid);
						gdomain.setType(5);
						gdomain.setNote("");
						gdomain.setSoundflag("1");
						usergroups.add(gdomain);
						orderid++;
					} catch (Exception ex) {

					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if ("muc".equals(parser.getName())) {
					break;
				}
			}
			eventType = parser.next();
		}

		if (usergroups.size() > 0) {
			MessageIQProviderListenerContext.getInstance().callParseIQListener(DcAndroidConsts.CHAT_IQPROVIDER_TYPE_GROUPLIST, usergroups);
			// 添加到数据库中
			//usergroupbll.addDataList(usergroups, stradddate);
		}
		return null;
	}
}
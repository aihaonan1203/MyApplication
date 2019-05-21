package com.gaotai.baselib.smack.provider;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;
import com.gaotai.baselib.smack.domain.UserGroupMembersDomain;
import com.gaotai.baselib.smack.listener.MessageIQProviderListenerContext;


/**
 * 接收群组成员数据
 */
public class GroupMemberIQProvider implements IQProvider {

	private static final String TAG = "smack.GroupMemberIQProvider";
	
	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		
		int eventType = parser.getEventType();

		List<UserGroupMembersDomain> usergroupmembers = new ArrayList<UserGroupMembersDomain>();
		int orderid = 0;

		String groupid = parser.getAttributeValue(null, "mucid");
		while (true) {
			if (eventType == XmlPullParser.START_DOCUMENT) {

			}
			if (eventType == XmlPullParser.START_TAG) {
				if ("member".equals(parser.getName())) {
					try {
						// id#name#headimg#用户类型
						String[] info = parser.nextText().split("#");
						String id = info[0];
						String name = info[1];
						String headimg = info[2];

						UserGroupMembersDomain gdomain = new UserGroupMembersDomain();
						gdomain.setName(name);
						gdomain.setHeadurl(headimg);
						gdomain.setId(id);
						gdomain.setCreatetime("");
						gdomain.setOrderid(orderid);
						gdomain.setGroupid(groupid);
						if(info.length > 3)
						{
							gdomain.setUserType(info[3]);
						}
						usergroupmembers.add(gdomain);
						orderid++;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if ("mucquery".equals(parser.getName())) {
					break;
				}
			}
			eventType = parser.next();
		}

		if (usergroupmembers.size() > 0) {
			MessageIQProviderListenerContext.getInstance().callParseIQListener(DcAndroidConsts.CHAT_IQPROVIDER_TYPE_GROUPMEMBER, usergroupmembers);
		}
		return null;
	}

}
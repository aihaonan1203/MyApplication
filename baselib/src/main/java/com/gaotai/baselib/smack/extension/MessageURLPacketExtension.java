package com.gaotai.baselib.smack.extension;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;

/**
 * 消息链接 Extension
 *
 */
public class MessageURLPacketExtension implements PacketExtension {

	private String msgurl = null;
	
	public static final String ELEMENT_NAME= DcAndroidConsts.MSG_URL_KEY_ELEMENT_NAME;
	public static final String NAME_SPACE= DcAndroidConsts.MSG_URL_KEY_NAME_SPACE;
	public static final String ELEMENT_CHILD_NAME = DcAndroidConsts.MSG_URL_KEY_ELEMENT_CHILD_NAME;
	
	public String MsgUrl() {
		return msgurl;
	}
	
	public void setPacketID(String msgurl) {
		this.msgurl = msgurl;
	}
	
    public MessageURLPacketExtension(String msgurl) {
	  this.msgurl = msgurl;
    }
    	
	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return ELEMENT_NAME;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAME_SPACE;
	}

	@Override
	public String toXML() {
		return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\"><" + ELEMENT_CHILD_NAME+">" + msgurl +"</" + ELEMENT_CHILD_NAME+"></" + getElementName() +">";
	}
	
	public static class Provider implements PacketExtensionProvider {
	        public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
	            return new MessageURLPacketExtension(parser.getName());
	       }
	}

}

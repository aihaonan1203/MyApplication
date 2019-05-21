package com.gaotai.baselib.smack.extension;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;


/**
 * 即时聊天 消息类别节点
 */
public class MessageTypePacketExtension implements PacketExtension {

	private String msgtype = null;
	
	public static final String ELEMENT_NAME= DcAndroidConsts.MSG_TYPE_KEY_ELEMENT_NAME;
	public static final String NAME_SPACE= DcAndroidConsts.MSG_TYPE_KEY_NAME_SPACE;
	public static final String ELEMENT_CHILD_NAME = DcAndroidConsts.MSG_TYPE_KEY_ELEMENT_CHILD_NAME;
	
	public String MsgType() {
		return msgtype;
	}
	
	public void setPacketID(String msgtype) {
		this.msgtype = msgtype;
	}
	
    public MessageTypePacketExtension(String msgtype) {
	  this.msgtype = msgtype;
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
		//return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\" id=\"" + packetID.toString() +"\" />";
		
		return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\"><" + ELEMENT_CHILD_NAME+">" + msgtype +"</" + ELEMENT_CHILD_NAME+"></" + getElementName() +">";
		//return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\">" + packetID.toString() +"</" + getElementName() +">";
	}
	
	
	public static class Provider implements PacketExtensionProvider {
	        public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
	            return new MessageTypePacketExtension(parser.getName());
	       }
	}

}

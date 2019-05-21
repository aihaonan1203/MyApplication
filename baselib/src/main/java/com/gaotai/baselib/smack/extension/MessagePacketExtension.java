package com.gaotai.baselib.smack.extension;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

import com.gaotai.baselib.DcAndroidConsts;

/**
 * 即时聊天 回执的节点
 */
public class MessagePacketExtension implements PacketExtension {

	private String packetID = null;
	
	public static final String ELEMENT_NAME= DcAndroidConsts.MSG_KEY_ELEMENT_NAME;
	public static final String NAME_SPACE= DcAndroidConsts.MSG_KEY_NAME_SPACE;
	
	public String getPacketID() {
		return packetID;
	}
	
	public void setPacketID(String packetID) {
		this.packetID = packetID;
	}
	
    public MessagePacketExtension(String packetID) {
	  this.packetID = packetID;
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
		// TODO Auto-generated method stub
		//return packetContent.toString();
		return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\" id=\"" + packetID.toString() +"\" />";
		//return "<" + getElementName() + " xmlns=\"" + getNamespace() + "\" />";
	}
	
	
	public static class Provider implements PacketExtensionProvider {
	        public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
	            return new MessagePacketExtension(parser.getName());
	       }
	}

}

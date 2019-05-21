package com.gaotai.baselib.smack.domain;

import java.util.Date;

public class MessagePacketDomain {	

	//是否我发送的消息回执
	private boolean sendreceipt = false;
	
	//消息发送时间
	private Date createtime;
	//消息类别
	private String businesstype;
	//发送人名称
	private String sendername;
	//发送人头像
	private String senderHeadurl;
	//消息类型 1 文本 2图片 3语音
	private String sendtype;
	//消息内容
	private String msginfo;
	//图片 语音 视频的存储内容
	private byte[] senddata;
	//发送者ID
	private long sender;	
	//接收者ID
	private long touser;
	//消息数量  在当前聊天页为0
	private int msgcount =1;
	//即时消息ID
	private String chatid;
	//消息URL 链接地址
	private String msgurl;
	//消息附加的参数值
	private String attrparams;
	//富文本消息
	private String details;
	//群组的ID
	private String groupid;
	// 是否被移除群
	private boolean iskick = false;	
	/**
	 * 是否通知消息
	 */
	private boolean isnoticemsg = false;
	//发送者类型
	private String senderType;

	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getSenderHeadurl() {
		return senderHeadurl;
	}
	public void setSenderHeadurl(String senderHeadurl) {
		this.senderHeadurl = senderHeadurl;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	public String getMsginfo() {
		return msginfo;
	}
	public void setMsginfo(String msginfo) {
		this.msginfo = msginfo;
	}
	public byte[] getSenddata() {
		return senddata;
	}
	public void setSenddata(byte[] senddata) {
		this.senddata = senddata;
	}
	public long getSender() {
		return sender;
	}
	public void setSender(long sender) {
		this.sender = sender;
	}
	public int getMsgcount() {
		return msgcount;
	}
	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}
	public String getChatid() {
		return chatid;
	}
	public void setChatid(String chatid) {
		this.chatid = chatid;
	}
	public String getMsgurl() {
		return msgurl;
	}
	public void setMsgurl(String msgurl) {
		this.msgurl = msgurl;
	}
	public String getAttrparams() {
		return attrparams;
	}
	public void setAttrparams(String attrparams) {
		this.attrparams = attrparams;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public boolean isSendreceipt() {
		return sendreceipt;
	}
	public void setSendreceipt(boolean sendreceipt) {
		this.sendreceipt = sendreceipt;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public long getTouser() {
		return touser;
	}
	public void setTouser(long touser) {
		this.touser = touser;
	}
	public boolean iskick() {
		return iskick;
	}
	public void setIskick(boolean iskick) {
		this.iskick = iskick;
	}
	
	public boolean isNoticeMsg()
	{
		return isnoticemsg;
	}
	
	public void setIsNoticeMsg(boolean isnoticemsg)
	{
		this.isnoticemsg = isnoticemsg;
	}

	public String getSenderType() {
		return senderType;
	}

	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
}

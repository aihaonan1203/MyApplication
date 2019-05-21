package com.gaotai.baselib.smack.domain;

/**
 * 即时消息发送类
 */
public class ChatSendDomain {	
	//发送内容 
	private String content;
	//接收用户ID
	private String touser;
	//发送时间
	private String createtime;
	//发送消息类型
	private String msgtype;
	//分享信息
	private String shareinfo;
	//消息ID
	private String chatid;
	//消息的本地文件地址
	private String filepath;

	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}
	public String getMsgtype()
	{
		return msgtype;
	}
	public void setMsgtype(String msgtype)
	{
		this.msgtype = msgtype;
	}
	public String getShareinfo()
	{
		return shareinfo;
	}
	public void setShareinfo(String shareinfo)
	{
		this.shareinfo = shareinfo;
	}
	public String getTouser()
	{
		return touser;
	}
	public void setTouser(String touser)
	{
		this.touser = touser;
	}
	public String getChatid()
	{
		return chatid;
	}
	public void setChatid(String chatid)
	{
		this.chatid = chatid;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}

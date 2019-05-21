package com.gaotai.baselib.smack.domain;

import java.util.Date;

/**
 * 用户群组信息
 */
public class UserGroupDomain {
	private int receiver;
	private int type;
	private String id;
	private String name;
	private Date createtime;
	private String headurl;
	private int orderid;
	//5 群组时  0不开启声音提示  1开启声音
	private String soundflag;
	private String note;
	/**
	 * 人数
	 */
	private int persionNumber;

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSoundflag() {
		return soundflag;
	}

	public void setSoundflag(String soundflag) {
		this.soundflag = soundflag;
	}

	public int getPersionNumber() {
		return persionNumber;
	}

	public void setPersionNumber(int persionNumber) {
		this.persionNumber = persionNumber;
	}
}

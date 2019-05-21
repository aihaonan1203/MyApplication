package com.zcf.bananavideohannan.dbmodel;

import org.xutils.db.annotation.Column;

import java.util.Date;

/**
 * 基础类
 */
public class BaseDBModel {
    public String TAG = getClass().getName();
    @Column(name = "id", isId = true)
    private long id;

    @Column(name = "createtime")
    private Date createtime;//创建时间 格式必须为yyyyMMddHHmmss

    @Column(name = "updatetime")
    private Date updatetime;//更新时间 格式必须为yyyyMMddHHmmss

    @Column(name = "hashcode")
    private String hashcode;//哈希校验

    @Column(name = "userid")
    private String userid;//所属用户

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

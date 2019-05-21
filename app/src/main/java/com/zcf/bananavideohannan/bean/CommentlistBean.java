package com.zcf.bananavideohannan.bean;

public class CommentlistBean {


    /**
     * id : 6
     * comment : 评论罕见的萨克继父回家
     * uid : 5
     * vid : 1
     * create_time : 1554794692
     * nickname : 嘿嘿
     * avatar : http://172.16.1.105:12121/avatar/155436804841543573608b884d01225ce6c90b5ebc.jpeg
     * sex : 男
     */

    private int id;
    private String comment;
    private int uid;
    private String vid;
    private String create_time;
    private String nickname;
    private String avatar;
    private String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

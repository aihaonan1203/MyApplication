package com.zcf.bananavideohannan.bean.videodetail;

public class VideoContentBean {
    //   "comment":"第一条评论",
//            "create_time":"1544075258",
//            "comment_id":2,
//            "nickname":"相忘于江湖",
//            "avatar":"http://47.100.160.168:829avatars/20181215/870dca17e99e4559403a85650b9141f7.jpeg",
//            "sex":"女",
//            "user_id":25
    public static final String nan = "男";
    public static final String nv = "女";

    private String comment;
    private String create_time;
    private int comment_id;
    private String nickname;
    private String avatar;
    private String sex;
    private int user_id;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

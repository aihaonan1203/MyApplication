package com.zcf.bananavideohannan.dbmodel;

/**
 * 电影评论
 */
public class FilmPinglunDBModel {

    public static final String nan = "男";
    public static final String nv = "女";

    private String filmId;// 影片id
    private String userId; // 评论用户id
    private String headImgUrl;// 头像
    private String userName;// 姓名
    private String publishDate;// 评论时间
    private int zanCount;// 点赞数量
    private String sex;// 性别
    private String content;// 评论内容

    public FilmPinglunDBModel(){}

    public FilmPinglunDBModel(String name,String plDate,String content,int zanCount,String sex){
        this.userName =name;
        this.publishDate = plDate;
        this.content = content;
        this.zanCount = zanCount;
        this.sex = sex;
    }


    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

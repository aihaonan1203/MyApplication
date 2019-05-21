package com.zcf.bananavideohannan.bean;

public class LishiBean {


    /**
     * id : 14
     * name : 与快乐就好
     * image : http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg
     * videofile : http://172.16.1.105:12121/upload/385a3dc060084ba54ec2148dc62330ad.mp4
     * fanhao : 21
     * actor : 张三
     * category_id : 无码
     * keyword : 这里是关键字
     * special : 63
     * label : 13,14,149,153,171,174,177,181,183,186
     * views : 49
     * score : null
     * likes : 0
     * content : 这里是简介
     * goodnum : 1
     * badnum : 0
     * create_time : 1554980187
     * update_time : 1555052058
     * fpid : 1
     */

    private int id;
    private String name;
    private String image;
    private String videofile;
    private String fanhao;
    private String actor;
    private String category_id;
    private String keyword;
    private String special;
    private String label;
    private int views;
    private Object score;
    private int likes;
    private String content;
    private int goodnum;
    private int badnum;
    private int create_time;
    private int update_time;
    private int fpid;
    private boolean ischeck;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideofile() {
        return videofile;
    }

    public void setVideofile(String videofile) {
        this.videofile = videofile;
    }

    public String getFanhao() {
        return fanhao;
    }

    public void setFanhao(String fanhao) {
        this.fanhao = fanhao;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(int goodnum) {
        this.goodnum = goodnum;
    }

    public int getBadnum() {
        return badnum;
    }

    public void setBadnum(int badnum) {
        this.badnum = badnum;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getFpid() {
        return fpid;
    }

    public void setFpid(int fpid) {
        this.fpid = fpid;
    }
}

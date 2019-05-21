package com.zcf.bananavideohannan.bean.videodetail;

public class VideoDetailBean {
    //      "actor":"苍井空",
//              "sort":"无码",
//              "special":"主页专题一",
//              "label":"兽性啊",
//              "likes":0,
//              "intro":"这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介",
//              "score":"8.3",
//              "name":"测试视频1",
//              "video_id":7,
//              "image":"http://47.100.160.168:829/uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//              "keyword":"美少女",
//              "video":"http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//              "views":121,
//              "create_time":"1544517709"


    private String actor;
    private String sort;
    private String special;
    private String label;
    private int likes;
    private String intro;
    private String score;
    private String name;
    private int video_id;
    private String image;
    private String keyword;
    private String video;
    private int views;
    private String create_time;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}

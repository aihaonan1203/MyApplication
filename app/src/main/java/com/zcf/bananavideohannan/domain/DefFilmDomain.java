package com.zcf.bananavideohannan.domain;

public class DefFilmDomain {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int id;
    private String actor;
    private String sort;
    private String special;
    private String label;
    private int likes;
    private int like_status;
    private String intro;
    private double score;// 评分
    private String name;//视频名称
    private int video_id;// 视频id
    private String image;// 视频图片封面地址
    private String keyword;
    private String video;// 视频地址
    private int views;// 播放次数
    private String create_time;

//      "actor": "苍井空",
//              "sort": "无码",
//              "special": "主页专题一",
//              "label": "兽性啊",
//              "likes": 0,
//              "intro": "这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介",
//              "score": 8.3000000000000007,
//              "name": "测试视频1",
//              "video_id": 7,
//              "image": "/uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//              "keyword": "美少女",
//              "video": "/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//              "views": 108,
//              "create_time": "1544517709"


    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
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

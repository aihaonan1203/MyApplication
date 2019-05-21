package com.zcf.bananavideohannan.bean;

public class VideoBean {


    /**
     * id : 1
     * name : 劳斯莱斯
     * image : http://172.16.1.105:12121/uploads/20190408/8642f7462defb3e23684172dae26b121.jpg
     * videofile : http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4
     * fanhao : IPZ-855
     * actor : 张三
     * category_id : 分类一
     * keyword :
     * special : 专题一
     * label : 13,14,15
     * views : 20
     * score : 9.9
     * likes : 0
     * content : 水电费偶记是冬季风科技速度快发几个阿杜放狠话文件我温湿度表弄明白你接口和我是计划接口山东黄金卡会晤恶化接口斯伯纳克华盛顿和覅
     * goodnum : 10
     * badnum : 3
     * create_time : 1553937573
     * update_time : 1554715159
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
    private double score;
    private int likes;
    private String content;
    private int goodnum;
    private int badnum;
    private int create_time;
    private int update_time;
    private int likestatus;

    public int getLikestatus() {
        return likestatus;
    }

    public void setLikestatus(int likestatus) {
        this.likestatus = likestatus;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
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
}

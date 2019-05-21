package com.zcf.bananavideohannan.domain;

public class BaseFilmDomain {
    //      "score":"8.3",
//              "name":"测试视频11",
//              "video_id":19,
//              "image":"http://47.100.160.168:829/uploads/20181211/91b502db7ce0de90d58463167adb7ed2.jpg",
//              "video":"http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//              "create_time":"1544530239"
//
//              "score":"8.3",
//              "name":"测试视频1",
//              "video_id":7,
//              "image":"http://47.100.160.168:829/uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//              "video":"http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//              "views":109
//
//              "score":"8.3",
//              "name":"测试视频10",
//              "video_id":18,
//              "image":"http://47.100.160.168:829/uploads/20181211/2b927f222d56c76cf6b95f789c44393f.jpg",
//              "video":"http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4"
    private String score;
    private String name;
    private int video_id;
    private String image;
    private String video;

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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}

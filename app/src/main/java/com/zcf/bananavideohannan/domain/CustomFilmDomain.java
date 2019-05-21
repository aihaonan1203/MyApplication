package com.zcf.bananavideohannan.domain;

public class CustomFilmDomain {
//     "special": "主页专题一",
//                "name": "测试视频1",
//                "image": "D:/phpStudy/PHPTutorial/WWW/xiangjiao//uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//                "video_id": 9,
//                "video": "D:/phpStudy/PHPTutorial/WWW/xiangjiao//upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//                "views": 100,
//                "create_time": "1544517709"
    private String special;
    private String name;
    private String image;
    private int video_id;
    private String video;
    private int views;
    private String create_time;

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
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

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
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

package com.zcf.bananavideohannan.bean.zltj;

public class ZltjAvVideoBean {
//    "actor": "苍井空",
//            "special": "主页专题一",
//            "score": "8.3",
//            "name": "测试视频1",
//            "video_id": 7,
//            "image": "http://47.100.160.168:829/uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//            "video": "http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4"ttp://47.100.160.168:829/recommend/20181211/ec05bea5dfbf8a5e8e4f5ca43153ee4e.jpg"7.100.160.168:829/recommend/20181211/3b2ecee77f86b87ed5035a3ec2b53bb4.jpg"

    private int video_id;
    private String name;
    private String image;
    private String score;

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

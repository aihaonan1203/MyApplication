package com.zcf.bananavideohannan.bean.zltj;

public class ZltjTjBean {
//     "special_id": 1,
//             "image": "http://47.100.160.168:829/recommend/20181211/0621ee8a510e4dc922d397197501d110.jpg",
//             "status": "推荐",
//             "create_time": "1543225368",
//             "special": "推荐专题一",
//             "intro": "这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介",
//             "icon": "http://47.100.160.168:829/recommend/20181211/3b2ecee77f86b87ed5035a3ec2b53bb4.jpg"

    private int special_id;
    private String image;
    private String status;
    private String create_time;
    private String special;
    private String intro;
    private String icon;

    public int getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(int special_id) {
        this.special_id = special_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

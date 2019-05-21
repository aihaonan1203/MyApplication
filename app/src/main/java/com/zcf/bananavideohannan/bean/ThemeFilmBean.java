package com.zcf.bananavideohannan.bean;

import java.util.List;

public class ThemeFilmBean {
    //    "special":"主页专题一",
//            "special_id":3,
    private String special;
    private int special_id;
    private List<ThemeBean> video;

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public int getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(int special_id) {
        this.special_id = special_id;
    }

    public List<ThemeBean> getVideo() {
        return video;
    }

    public void setVideo(List<ThemeBean> video) {
        this.video = video;
    }
}

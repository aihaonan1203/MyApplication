package com.zcf.bananavideohannan.bean.zltj;

import com.zcf.bananavideohannan.domain.BaseFilmDomain;

import java.util.List;

public class ZltjAvBean {
    //             "actor": "苍井空",
    //            "intro": "宅男女神",
    //            "avatar": "http://47.100.160.168:829/avatars/20181029/b7533826cb5733937df8fef8c806e2ae.jpg",
    //            "pianliang": 11,
    //            "video": ["http://47.100.160.168:829/recommend/20181211/ec05bea5dfbf8a5e8e4f5ca43153ee4e.jpg"7.100.160.168:829/recommend/20181211/3b2ecee77f86b87ed5035a3ec2b53bb4.jpg"

    private String actor;
    private String intro;
    private String avatar;
    private int pianliang;
    private List<BaseFilmDomain> video;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPianliang() {
        return pianliang;
    }

    public void setPianliang(int pianliang) {
        this.pianliang = pianliang;
    }

    public List<BaseFilmDomain> getVideo() {
        return video;
    }

    public void setVideo(List<BaseFilmDomain> video) {
        this.video = video;
    }
}

package com.zcf.bananavideohannan.evenMsg;

import com.zcf.bananavideohannan.bean.LikeBean;

import java.util.List;

public class LikeEvent {

    private String type;
    private List<LikeBean> likeBeans;

    public LikeEvent(String type, List<LikeBean> likeBeans) {
        this.type = type;
        this.likeBeans = likeBeans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LikeBean> getLikeBeans() {
        return likeBeans;
    }

    public void setLikeBeans(List<LikeBean> likeBeans) {
        this.likeBeans = likeBeans;
    }
}

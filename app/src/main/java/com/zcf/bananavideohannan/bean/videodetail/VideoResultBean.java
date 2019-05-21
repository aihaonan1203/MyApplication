package com.zcf.bananavideohannan.bean.videodetail;

import com.zcf.bananavideohannan.domain.AdDomain;

import java.util.List;

public class VideoResultBean {
    private int code;
    private VideoDetailBean data;
    private List<VideoContentBean> comment;
    private List<AdDomain> adlink;

    private int comment_num;
    private int like_status;// 是否喜欢  0-否 1-是
    private int percent;// 是否喜欢  0-否 1-是
    private int good_status;// 0为踩，1为点赞，2为全无

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public VideoDetailBean getData() {
        return data;
    }

    public void setData(VideoDetailBean data) {
        this.data = data;
    }

    public List<VideoContentBean> getComment() {
        return comment;
    }

    public void setComment(List<VideoContentBean> comment) {
        this.comment = comment;
    }

    public List<AdDomain> getAdlink() {
        return adlink;
    }

    public void setAdlink(List<AdDomain> adlink) {
        this.adlink = adlink;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getGood_status() {
        return good_status;
    }

    public void setGood_status(int good_status) {
        this.good_status = good_status;
    }
}

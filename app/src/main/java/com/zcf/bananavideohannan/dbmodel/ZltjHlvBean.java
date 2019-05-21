package com.zcf.bananavideohannan.dbmodel;

public class ZltjHlvBean {
    String imgUrl;
    String title;
    String time;
    String msg;

    public ZltjHlvBean(String imgUrl, String title, String time, String msg) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.time = time;
        this.msg = msg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

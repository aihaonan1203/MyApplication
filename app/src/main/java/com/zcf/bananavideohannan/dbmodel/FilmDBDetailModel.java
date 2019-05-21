package com.zcf.bananavideohannan.dbmodel;

import java.util.List;

public class FilmDBDetailModel extends FilmDBModel {
    private int percentZan;// 点赞百分比
    private String uploadDate;// 上传日期
    private String content;// 简介
    private List<FilmPinglunDBModel> contents;// 评论

    public int getPercentZan() {
        return percentZan;
    }

    public void setPercentZan(int percentZan) {
        this.percentZan = percentZan;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FilmPinglunDBModel> getContents() {
        return contents;
    }

    public void setContents(List<FilmPinglunDBModel> contents) {
        this.contents = contents;
    }
}

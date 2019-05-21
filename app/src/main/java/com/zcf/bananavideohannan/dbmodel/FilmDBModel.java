package com.zcf.bananavideohannan.dbmodel;

import java.util.List;

/**
 * 电影model
 */
public class FilmDBModel {
    private String filmName;// 电影名字
    private List<String> labels;// 标签
    private String iconUrl;// 图标
    private String score;// 评分
    private int playNum;// 播放次数
    private String videoUrl;// 视频地址
    private String filmType;// 视频地址

    public FilmDBModel() {

    }

    public FilmDBModel(String filmName, List<String> labels, String iconUrl, String score, int playNum, String videoUrl) {
        this.filmName = filmName;
        this.labels = labels;
        this.iconUrl = iconUrl;
        this.score = score;
        this.playNum = playNum;
        this.videoUrl = videoUrl;
    }

    public FilmDBModel(String filmName, String iconUrl, String score, String filmType) {
        this.filmName = filmName;
        this.iconUrl = iconUrl;
        this.score = score;
        this.filmType = filmType;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

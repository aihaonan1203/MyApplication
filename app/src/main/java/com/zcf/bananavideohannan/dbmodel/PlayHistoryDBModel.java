package com.zcf.bananavideohannan.dbmodel;

/**
 * 历史播放
 */
public class PlayHistoryDBModel {
    private String filmId;
    private String iconUrl;// 图标
    private String filmName;// 影片名字
    private String longTime; // 时长

    public PlayHistoryDBModel(String filmId, String iconUrl, String filmName, String longTime) {
        this.filmId = filmId;
        this.iconUrl = iconUrl;
        this.filmName = filmName;
        this.longTime = longTime;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }
}

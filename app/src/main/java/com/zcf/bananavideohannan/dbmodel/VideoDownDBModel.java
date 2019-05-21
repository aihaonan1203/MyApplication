package com.zcf.bananavideohannan.dbmodel;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "DB_DOWNLOAD_VIDEO")
public class VideoDownDBModel extends BaseDBModel {
    @Column(name = "video_id")
    private int video_id;

    @Column(name = "image")
    private String image;

    @Column(name = "downurl")
    private String downurl;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "progress")
    private int progress;

    @Column(name = "status") // 0-下载中 1-下载完成 2-暂停
    private String status;

    public static final String DOWN_STATUS_DOWNING = "0";
    public static final String DOWN_STATUS_SUCCESS = "1";
    public static final String DOWN_STATUS_PAUSEED = "2";


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private boolean isDowning;

    public void setDowning(boolean downing) {
        isDowning = downing;
    }

    public boolean getIsDowning() {
        return isDowning;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

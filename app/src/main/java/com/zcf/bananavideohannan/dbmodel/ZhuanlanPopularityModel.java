package com.zcf.bananavideohannan.dbmodel;

public class ZhuanlanPopularityModel  {

    private String name;
    private String msg;
    private String VideoNumber;
    private String ImaUrl;
    private VideoBean videoBean;

    public ZhuanlanPopularityModel(String name, String msg, String videoNumber, String imaUrl, VideoBean videoBean) {
        this.name = name;
        this.msg = msg;
        VideoNumber = videoNumber;
        ImaUrl = imaUrl;
        this.videoBean = videoBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVideoNumber() {
        return VideoNumber;
    }

    public void setVideoNumber(String videoNumber) {
        VideoNumber = videoNumber;
    }

    public String getImaUrl() {
        return ImaUrl;
    }

    public void setImaUrl(String imaUrl) {
        ImaUrl = imaUrl;
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }

    class VideoBean{
        private String imgUrl;
        private String VideoName;

        public VideoBean(String imgUrl, String videoName) {
            this.imgUrl = imgUrl;
            VideoName = videoName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getVideoName() {
            return VideoName;
        }

        public void setVideoName(String videoName) {
            VideoName = videoName;
        }
    }
}

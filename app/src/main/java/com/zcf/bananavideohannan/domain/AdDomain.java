package com.zcf.bananavideohannan.domain;

public class AdDomain {
    //        "adlink_id":4,
//                "image":"D:/phpStudy/PHPTutorial/WWW/xiangjiao//adlinkimage/20181205/9b16b296b2b5528ab80a3d2df0ce8c00.png",
//                "adlink":"https://www.baidu.com",
//                "status":"首页"
    public static final int AD_TOP = 0;// 头部
    public static final int AD_CENTER = 1;// 中部
    public static final int AD_BOTTOM = 2; //底部

    private int adlink_id;
    private String image;
    private String adlink;
    private String status;
    private String create_time;
    private int ad_position;// 0-头部 1-中部 2-底部

    public AdDomain(int typeId,String image, String adlink) {
        this.ad_position = typeId;
        this.image = image;
        this.adlink = adlink;
    }

    public int getAd_position() {
        return ad_position;
    }

    public void setAd_position(int ad_position) {
        this.ad_position = ad_position;
    }

    public int getAdlink_id() {
        return adlink_id;
    }

    public void setAdlink_id(int adlink_id) {
        this.adlink_id = adlink_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdlink() {
        return adlink;
    }

    public void setAdlink(String adlink) {
        this.adlink = adlink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}

package com.zcf.bananavideohannan.domain;

/**
 * 用户登录信息
 */
public class UserInfoDomain {
    private int uid;
    private String mobile;//用户绑定手机号
    private String nickname;//昵称
    private String sex;//用户性别
    private String avatar;//用户头像地址
    private String code;//用户推广码
    private int level;//用户等级
    private int play_num;//用户每日已播放次数
    private int play_max;//用户每日最大播放次数
    private int download_num;//用户每日已下载次数
    private int download_max;//用户每日最大下载次数
    private int extension_num;//用户已推广人数
    private int num;//用户距离下一等级人数
    private String app_url;
    private String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPlay_num() {
        return play_num;
    }

    public void setPlay_num(int play_num) {
        this.play_num = play_num;
    }

    public int getPlay_max() {
        return play_max;
    }

    public void setPlay_max(int play_max) {
        this.play_max = play_max;
    }

    public int getDownload_num() {
        return download_num;
    }

    public void setDownload_num(int download_num) {
        this.download_num = download_num;
    }

    public int getDownload_max() {
        return download_max;
    }

    public void setDownload_max(int download_max) {
        this.download_max = download_max;
    }

    public int getExtension_num() {
        return extension_num;
    }

    public void setExtension_num(int extension_num) {
        this.extension_num = extension_num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

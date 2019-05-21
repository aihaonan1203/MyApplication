package com.zcf.bananavideohannan.bean;

import com.zcf.bananavideohannan.domain.UserInfoDomain;

// 用户信息
public class PersonInfoBean {
    private int code;
    private UserInfoDomain userData;

    public UserInfoDomain getUserData() {
        return userData;
    }

    public void setUserData(UserInfoDomain userData) {
        this.userData = userData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}

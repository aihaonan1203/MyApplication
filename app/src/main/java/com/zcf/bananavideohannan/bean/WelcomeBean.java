package com.zcf.bananavideohannan.bean;

import com.zcf.bananavideohannan.domain.AdDomain;

import java.util.List;

public class WelcomeBean {
    private int code;

    private List<AdDomain> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AdDomain> getData() {
        return data;
    }

    public void setData(List<AdDomain> data) {
        this.data = data;
    }
}

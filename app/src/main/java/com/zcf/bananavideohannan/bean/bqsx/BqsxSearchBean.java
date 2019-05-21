package com.zcf.bananavideohannan.bean.bqsx;

import com.zcf.bananavideohannan.domain.BqSearchDomain;

import java.util.List;

public class BqsxSearchBean {
    private int code;
    private List<BqSearchDomain> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BqSearchDomain> getData() {
        return data;
    }

    public void setData(List<BqSearchDomain> data) {
        this.data = data;
    }
}

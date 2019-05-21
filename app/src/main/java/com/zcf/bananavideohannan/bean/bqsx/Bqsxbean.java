package com.zcf.bananavideohannan.bean.bqsx;

import java.util.List;

public class Bqsxbean {
    private int code;
    private List<BqsxparentBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BqsxparentBean> getData() {
        return data;
    }

    public void setData(List<BqsxparentBean> data) {
        this.data = data;
    }
}

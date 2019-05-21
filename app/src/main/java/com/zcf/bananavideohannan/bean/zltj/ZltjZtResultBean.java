package com.zcf.bananavideohannan.bean.zltj;

import java.util.List;

public class ZltjZtResultBean {
    //    "code": 200,
//            "data": [
//    {
//        "special_id": 2,
//            "special": "热门专题一",
//            "icon": "http://47.100.160.168:829/recommend/20181211/ec05bea5dfbf8a5e8e4f5ca43153ee4e.jpg"
//    },
    private int code;
    private List<ZltjZtBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ZltjZtBean> getData() {
        return data;
    }

    public void setData(List<ZltjZtBean> data) {
        this.data = data;
    }
}

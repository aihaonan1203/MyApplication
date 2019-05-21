package com.zcf.bananavideohannan.bean.zltj;

import java.util.List;

public class ZltjAvResultBean {
    //     "special_id": 1,
//             "image": "http://47.100.160.168:829/recommend/20181211/0621ee8a510e4dc922d397197501d110.jpg",
//             "status": "推荐",
//             "create_time": "1543225368",
//             "special": "推荐专题一",
//             "intro": "这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介这是推荐专题一的简介",
//             "icon": "http://47.100.160.168:829/recommend/20181211/3b2ecee77f86b87ed5035a3ec2b53bb4.jpg"
    private int code;
    private List<ZltjAvBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ZltjAvBean> getData() {
        return data;
    }

    public void setData(List<ZltjAvBean> data) {
        this.data = data;
    }
}

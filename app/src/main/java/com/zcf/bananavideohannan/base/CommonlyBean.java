package com.zcf.bananavideohannan.base;

import java.util.List;

public class CommonlyBean<T> {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1554875774
     * data : [{"id":6,"comment":"评论罕见的萨克继父回家","uid":5,"vid":"1","create_time":"1554794692","nickname":"嘿嘿"},{"id":5,"comment":"评论","uid":5,"vid":"1","create_time":"1554781862","nickname":"嘿嘿"},{"id":4,"comment":"评论","uid":5,"vid":"1","create_time":"1554696431","nickname":"嘿嘿"},{"id":3,"comment":"评论","uid":5,"vid":"1","create_time":"1554695164","nickname":"嘿嘿"},{"id":2,"comment":"评论","uid":5,"vid":"1","create_time":"1554695152","nickname":"嘿嘿"},{"id":1,"comment":"评论","uid":5,"vid":"1","create_time":"1554686439","nickname":"嘿嘿"}]
     */

    private int code;
    private String msg;
    private String time;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}

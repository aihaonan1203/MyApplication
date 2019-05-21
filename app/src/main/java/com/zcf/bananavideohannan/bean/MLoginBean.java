package com.zcf.bananavideohannan.bean;

/**
 * Created by Hjb on 2019/5/21.
 */

public class MLoginBean {


    /**
     * code : 0
     * msg : 手机格式不正确
     * time : 1558431210
     * data : null
     */

    private int code;
    private String msg;
    private String time;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

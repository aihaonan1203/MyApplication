package com.zcf.bananavideohannan.bean;

import java.util.List;

/**
 * Created by Hjb on 2019/5/15.
 */

public class Ttt {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1557913695
     * data : [{"id":1,"uid":10,"user":10,"money":"8.94","create_time":1557723316,"update_time":null},{"id":3,"uid":10,"user":10,"money":"8.94","create_time":1557723316,"update_time":null}]
     */

    private int code;
    private String msg;
    private String time;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * uid : 10
         * user : 10
         * money : 8.94
         * create_time : 1557723316
         * update_time : null
         */

        private int id;
        private int uid;
        private int user;
        private String money;
        private int create_time;
        private Object update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }
    }
}

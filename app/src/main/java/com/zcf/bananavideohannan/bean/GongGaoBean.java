package com.zcf.bananavideohannan.bean;

import java.util.List;

public class GongGaoBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1554691300
     * data : [{"id":1,"notice_content":"111111111111111111111111111111","link_url":"http://www.baidu.com","other":"其他信息测试","title":"第一条","status":"1","create_time":"1549872793","update_time":"1554076800"}]
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
         * notice_content : 111111111111111111111111111111
         * link_url : http://www.baidu.com
         * other : 其他信息测试
         * title : 第一条
         * status : 1
         * create_time : 1549872793
         * update_time : 1554076800
         */

        private int id;
        private String notice_content;
        private String link_url;
        private String other;
        private String title;
        private String status;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNotice_content() {
            return notice_content;
        }

        public void setNotice_content(String notice_content) {
            this.notice_content = notice_content;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}

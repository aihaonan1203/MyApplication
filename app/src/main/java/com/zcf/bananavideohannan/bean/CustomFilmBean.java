package com.zcf.bananavideohannan.bean;

import java.util.List;

public class CustomFilmBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1554696753
     * data : [{"id":3,"image":"http://172.16.1.105:12121/uploads/20190404/fe3bc7d4e2c8a981fdea9708e233f05c.png","adlink":"www.baidu.com","flag":"channel,index","status":"normal","create_time":1554342375,"update_time":1554342375},{"id":4,"image":"http://172.16.1.105:12121/uploads/20190404/fe3bc7d4e2c8a981fdea9708e233f05c.png","adlink":"www.baidu.com","flag":"index,my","status":"normal","create_time":1554342389,"update_time":1554342389},{"id":5,"image":"http://172.16.1.105:12121/uploads/20190404/fe3bc7d4e2c8a981fdea9708e233f05c.png","adlink":"","flag":"channel,index,my","status":"normal","create_time":1554346675,"update_time":1554346675}]
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
         * id : 3
         * image : http://172.16.1.105:12121/uploads/20190404/fe3bc7d4e2c8a981fdea9708e233f05c.png
         * adlink : www.baidu.com
         * flag : channel,index
         * status : normal
         * create_time : 1554342375
         * update_time : 1554342375
         */

        private int id;
        private String image;
        private String adlink;
        private String flag;
        private String status;
        private int create_time;
        private int update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAdlink() {
            return adlink;
        }

        public void setAdlink(String adlink) {
            this.adlink = adlink;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}

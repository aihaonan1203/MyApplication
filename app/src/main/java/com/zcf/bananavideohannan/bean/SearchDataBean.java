package com.zcf.bananavideohannan.bean;

import java.util.List;

public class SearchDataBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1555639060
     * data : [{"id":209,"pid":67,"type":"label","name":"女主播","nickname":"","flag":"hot","image":"","keywords":"","description":"","diyname":"","createtime":1554726190,"updatetime":1555583193,"weigh":209,"status":"normal"},{"id":236,"pid":68,"type":"label","name":"丝袜/网袜","nickname":"","flag":"hot","image":"","keywords":"","description":"","diyname":"","createtime":1554726538,"updatetime":1555583108,"weigh":236,"status":"normal"},{"id":238,"pid":68,"type":"label","name":"空姐制服","nickname":"","flag":"hot","image":"","keywords":"","description":"","diyname":"","createtime":1554726558,"updatetime":1555583119,"weigh":238,"status":"normal"}]
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
         * id : 209
         * pid : 67
         * type : label
         * name : 女主播
         * nickname :
         * flag : hot
         * image :
         * keywords :
         * description :
         * diyname :
         * createtime : 1554726190
         * updatetime : 1555583193
         * weigh : 209
         * status : normal
         */

        private int id;
        private int pid;
        private String type;
        private String name;
        private String nickname;
        private String flag;
        private String image;
        private String keywords;
        private String description;
        private String diyname;
        private int createtime;
        private int updatetime;
        private int weigh;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDiyname() {
            return diyname;
        }

        public void setDiyname(String diyname) {
            this.diyname = diyname;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public int getWeigh() {
            return weigh;
        }

        public void setWeigh(int weigh) {
            this.weigh = weigh;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

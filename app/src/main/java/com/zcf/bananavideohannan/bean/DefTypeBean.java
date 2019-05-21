package com.zcf.bananavideohannan.bean;

import java.util.List;

/**
 * 首页分类
 */
public class DefTypeBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1554695252
     * data : [{"id":1,"pid":0,"type":"default","name":"分类一","nickname":"昵称衣服","flag":"hot,index,recommend","image":"","keywords":"衣服","description":"","diyname":"","createtime":1553683446,"updatetime":1553683446,"weigh":1,"status":"normal"},{"id":2,"pid":0,"type":"default","name":"分类二","nickname":"T恤","flag":"hot,index","image":"","keywords":"","description":"","diyname":"","createtime":1553683784,"updatetime":1553683784,"weigh":2,"status":"normal"},{"id":4,"pid":0,"type":"default","name":"分类四","nickname":"bbb","flag":"index","image":"/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg","keywords":"","description":"","diyname":"","createtime":1553683880,"updatetime":1554169388,"weigh":4,"status":"normal"},{"id":3,"pid":0,"type":"default","name":"分类三","nickname":"裤子","flag":"hot,index,recommend","image":"","keywords":"裤子","description":"","diyname":"","createtime":1553683842,"updatetime":1554184348,"weigh":5,"status":"normal"},{"id":16,"pid":0,"type":"default","name":"分类五","nickname":"","flag":"index","image":"/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg","keywords":"","description":"SDFSADF阿斯顿发送到发送到发送到范德萨发的说法三","diyname":"","createtime":1554183738,"updatetime":1554184263,"weigh":16,"status":"normal"},{"id":17,"pid":0,"type":"default","name":"hah","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554694310,"updatetime":1554694310,"weigh":17,"status":"normal"}]
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
         * pid : 0
         * type : default
         * name : 分类一
         * nickname : 昵称衣服
         * flag : hot,index,recommend
         * image :
         * keywords : 衣服
         * description :
         * diyname :
         * createtime : 1553683446
         * updatetime : 1553683446
         * weigh : 1
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

        public DataBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

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

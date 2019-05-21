package com.zcf.bananavideohannan.bean;

import java.util.List;

public class LabelBean {


    /**
     * id : 9
     * pid : 0
     * type : label
     * name : 进阶
     * nickname : 1
     * flag :
     * image :
     * keywords :
     * description :
     * diyname :
     * createtime : 1553828237
     * updatetime : 1554723813
     * weigh : 9
     * status : normal
     * children : [{"id":13,"pid":9,"type":"label","name":"黄瓜出品","nickname":"1","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1553828345,"updatetime":1554724022,"weigh":13,"status":"normal"},{"id":69,"pid":9,"type":"label","name":"动漫","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724048,"updatetime":1554724048,"weigh":69,"status":"normal"},{"id":70,"pid":9,"type":"label","name":"国产","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724057,"updatetime":1554724057,"weigh":70,"status":"normal"},{"id":71,"pid":9,"type":"label","name":"韩国","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724068,"updatetime":1554724068,"weigh":71,"status":"normal"},{"id":72,"pid":9,"type":"label","name":"中文字幕","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724082,"updatetime":1554724082,"weigh":72,"status":"normal"},{"id":73,"pid":9,"type":"label","name":"简体中文","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724096,"updatetime":1554724096,"weigh":73,"status":"normal"},{"id":74,"pid":9,"type":"label","name":"原汁原味","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724107,"updatetime":1554724107,"weigh":74,"status":"normal"},{"id":75,"pid":9,"type":"label","name":"热门排行","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724122,"updatetime":1554724122,"weigh":75,"status":"normal"},{"id":76,"pid":9,"type":"label","name":"最新上架","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724149,"updatetime":1554724149,"weigh":76,"status":"normal"},{"id":77,"pid":9,"type":"label","name":"特色影片","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724161,"updatetime":1554724161,"weigh":77,"status":"normal"},{"id":78,"pid":9,"type":"label","name":"写真影片","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724180,"updatetime":1554724180,"weigh":78,"status":"normal"},{"id":79,"pid":9,"type":"label","name":"深夜综艺","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724196,"updatetime":1554724196,"weigh":79,"status":"normal"},{"id":80,"pid":9,"type":"label","name":"女性专属","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724208,"updatetime":1554724208,"weigh":80,"status":"normal"},{"id":81,"pid":9,"type":"label","name":"合集/精华篇","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724223,"updatetime":1554724223,"weigh":81,"status":"normal"},{"id":82,"pid":9,"type":"label","name":"出道作","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724237,"updatetime":1554724237,"weigh":82,"status":"normal"},{"id":83,"pid":9,"type":"label","name":"无码","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724246,"updatetime":1554724246,"weigh":83,"status":"normal"},{"id":84,"pid":9,"type":"label","name":"时间停止","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724262,"updatetime":1554724262,"weigh":84,"status":"normal"},{"id":85,"pid":9,"type":"label","name":"三级剧情","nickname":"","flag":"","image":"","keywords":"","description":"","diyname":"","createtime":1554724277,"updatetime":1554724277,"weigh":85,"status":"normal"}]
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
    private List<ChildrenBean> children;

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

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 13
         * pid : 9
         * type : label
         * name : 黄瓜出品
         * nickname : 1
         * flag :
         * image :
         * keywords :
         * description :
         * diyname :
         * createtime : 1553828345
         * updatetime : 1554724022
         * weigh : 13
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

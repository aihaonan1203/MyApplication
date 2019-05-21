package com.zcf.bananavideohannan.bean;

import java.util.List;

public class CustomTagBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1554709456
     * data : [{"id":7,"pid":0,"type":"special","name":"专题三","nickname":"1","flag":"index","image":"http://172.16.1.105:12121/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg","keywords":"","description":"","diyname":"","createtime":1553827585,"updatetime":1554169412,"weigh":7,"status":"normal","value":[{"id":5,"name":"看见两次","image":"/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"\\upload\\644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"张三","category_id":"分类三","keyword":"12","special":"专题三","label":"13,14,15","views":34,"score":null,"likes":678,"content":"歌手巅峰会","goodnum":0,"badnum":0,"create_time":1554255033,"update_time":1554255153}]},{"id":8,"pid":0,"type":"special","name":"专题四","nickname":"1","flag":"hot,index,recommend","image":"http://172.16.1.105:12121/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg","keywords":"","description":"","diyname":"","createtime":1553827601,"updatetime":1554169407,"weigh":8,"status":"normal","value":[{"id":6,"name":"即可观看","image":"/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"\\upload\\644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"李四","category_id":"分类四","keyword":"123","special":"专题四","label":"15,13","views":678,"score":null,"likes":8097,"content":"地方很关键","goodnum":0,"badnum":0,"create_time":1554255157,"update_time":1554255157},{"id":7,"name":"即可观看","image":"/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"\\upload\\644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"李四","category_id":"分类四","keyword":"123","special":"专题四","label":"15,13","views":678,"score":null,"likes":8097,"content":"地方很关键","goodnum":0,"badnum":0,"create_time":1554255157,"update_time":1554255157}]}]
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
         * id : 7
         * pid : 0
         * type : special
         * name : 专题三
         * nickname : 1
         * flag : index
         * image : http://172.16.1.105:12121/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg
         * keywords :
         * description :
         * diyname :
         * createtime : 1553827585
         * updatetime : 1554169412
         * weigh : 7
         * status : normal
         * value : [{"id":5,"name":"看见两次","image":"/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"\\upload\\644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"张三","category_id":"分类三","keyword":"12","special":"专题三","label":"13,14,15","views":34,"score":null,"likes":678,"content":"歌手巅峰会","goodnum":0,"badnum":0,"create_time":1554255033,"update_time":1554255153}]
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
        private List<ValueBean> value;

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

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {

            private int id;
            private String name;
            private String image;
            private String videofile;
            private String fanhao;
            private String actor;
            private String category_id;
            private String keyword;
            private String special;
            private String label;
            private int views;
            private Object score;
            private int likes;
            private String content;
            private int goodnum;
            private int badnum;
            private int create_time;
            private int update_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getVideofile() {
                return videofile;
            }

            public void setVideofile(String videofile) {
                this.videofile = videofile;
            }

            public String getFanhao() {
                return fanhao;
            }

            public void setFanhao(String fanhao) {
                this.fanhao = fanhao;
            }

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public Object getScore() {
                return score;
            }

            public void setScore(Object score) {
                this.score = score;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getGoodnum() {
                return goodnum;
            }

            public void setGoodnum(int goodnum) {
                this.goodnum = goodnum;
            }

            public int getBadnum() {
                return badnum;
            }

            public void setBadnum(int badnum) {
                this.badnum = badnum;
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
}

package com.zcf.bananavideohannan.bean.zltj;

import java.util.List;

public class ZltjTjResultBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1555481187
     * data : [{"id":59,"pid":0,"type":"special","name":"中文字幕","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723607,"updatetime":1555472060,"weigh":59,"status":"normal","value":[{"id":16,"name":"sdf ","image":"http://172.16.1.105:12121/uploads/20190417/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"张三","category_id":"无码","keyword":"这里是关键字","special":"中文字幕","label":"74,71,81,76,84","views":0,"score":null,"likes":0,"content":"这里是简介","goodnum":0,"badnum":0,"create_time":1555474636,"update_time":1555474636}]},{"id":60,"pid":0,"type":"special","name":"嗨动漫","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723623,"updatetime":1555472071,"weigh":60,"status":"normal","value":[]},{"id":61,"pid":0,"type":"special","name":"精品最佳","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723638,"updatetime":1555472049,"weigh":61,"status":"normal","value":[]},{"id":62,"pid":0,"type":"special","name":"约约哥","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723660,"updatetime":1555472042,"weigh":62,"status":"normal","value":[]},{"id":63,"pid":0,"type":"special","name":"秦先生","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723676,"updatetime":1555472035,"weigh":63,"status":"normal","value":[]},{"id":64,"pid":0,"type":"special","name":"fcc无码","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723698,"updatetime":1555472023,"weigh":64,"status":"normal","value":[]},{"id":65,"pid":0,"type":"special","name":"萌眼妹","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png","keywords":"","description":"这里是专题简介","diyname":"","createtime":1554723728,"updatetime":1555472017,"weigh":65,"status":"normal","value":[]},{"id":66,"pid":0,"type":"special","name":"国产高清","nickname":"","flag":"recommend","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","keywords":"","description":"这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述这里是描述","diyname":"","createtime":1554723750,"updatetime":1555409260,"weigh":66,"status":"normal","value":[]}]
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
         * id : 59
         * pid : 0
         * type : special
         * name : 中文字幕
         * nickname :
         * flag : recommend
         * image : http://172.16.1.105:12121/uploads/20190417/f22aadb4f4e961f658b2baa23bb804c9.png
         * keywords :
         * description : 这里是专题简介
         * diyname :
         * createtime : 1554723607
         * updatetime : 1555472060
         * weigh : 59
         * status : normal
         * value : [{"id":16,"name":"sdf ","image":"http://172.16.1.105:12121/uploads/20190417/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"张三","category_id":"无码","keyword":"这里是关键字","special":"中文字幕","label":"74,71,81,76,84","views":0,"score":null,"likes":0,"content":"这里是简介","goodnum":0,"badnum":0,"create_time":1555474636,"update_time":1555474636}]
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
            /**
             * id : 16
             * name : sdf
             * image : http://172.16.1.105:12121/uploads/20190417/803a180247e1da537246aa4dc87e1fbd.jpg
             * videofile : http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4
             * fanhao : IPZ-855
             * actor : 张三
             * category_id : 无码
             * keyword : 这里是关键字
             * special : 中文字幕
             * label : 74,71,81,76,84
             * views : 0
             * score : null
             * likes : 0
             * content : 这里是简介
             * goodnum : 0
             * badnum : 0
             * create_time : 1555474636
             * update_time : 1555474636
             */

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

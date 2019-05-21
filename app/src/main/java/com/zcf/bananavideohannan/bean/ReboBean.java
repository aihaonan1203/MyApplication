package com.zcf.bananavideohannan.bean;

import java.util.List;

public class ReboBean {


    /**
     * code : 200
     * msg : 最热视频
     * time : 1554691844
     * data : [{"id":6,"name":"即可观看","image":"http://172.16.1.105:12121/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"李四","category_id":"分类四","keyword":"123","special":"专题四","label":"15,13","views":678,"score":null,"likes":8097,"content":"地方很关键","create_time":1554255157,"update_time":1554255157},{"id":5,"name":"看见两次","image":"http://172.16.1.105:12121/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"张三","category_id":"分类三","keyword":"12","special":"专题三","label":"13,14,15","views":34,"score":null,"likes":678,"content":"歌手巅峰会","create_time":1554255033,"update_time":1554255153},{"id":1,"name":"劳斯莱斯","image":"http://172.16.1.105:12121/uploads/20190330/d68c7fb3d24c85c35e357f8f83bf1b30.jpg","videofile":"http://172.16.1.105:12121","fanhao":"IPZ-855","actor":"桃","category_id":"分类一","keyword":"","special":"专题一","label":"14,15","views":8,"score":9.9,"likes":0,"content":"水电费偶记是冬季风科技速度快发几个阿杜放狠话文件我温湿度表弄明白你接口和我是计划接口山东黄金卡会晤恶化接口斯伯纳克华盛顿和覅","create_time":1553937573,"update_time":1554170047},{"id":4,"name":"方的观点","image":"http://172.16.1.105:12121/uploads/20190403/8642f7462defb3e23684172dae26b121.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"八块","category_id":"分类一","keyword":"12","special":"专题一","label":"13,14,15","views":1,"score":null,"likes":1,"content":"何时复更还","create_time":1554254661,"update_time":1554255030},{"id":2,"name":"think php","image":"http://172.16.1.105:12121/uploads/20190401/d68c7fb3d24c85c35e357f8f83bf1b30.jpg","videofile":"http://172.16.1.105:12121/upload/f48620609811e6ac064accb551943878.avi","fanhao":"IPZ-855","actor":"桃","category_id":"分类一","keyword":"士大夫","special":"专题二","label":"14","views":0,"score":null,"likes":0,"content":"","create_time":1554081585,"update_time":1554170026},{"id":3,"name":"接口是导航接口好几个","image":"http://172.16.1.105:12121/uploads/20190402/8642f7462defb3e23684172dae26b121.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"桃","category_id":"分类一","keyword":"12","special":"专题五","label":"15","views":0,"score":null,"likes":1,"content":"","create_time":1554168155,"update_time":1554169478}]
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
         * id : 6
         * name : 即可观看
         * image : http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg
         * videofile : http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4
         * fanhao : IPZ-855
         * actor : 赵六
         * category_id : 素人
         * keyword : 123
         * special : 黄瓜出品
         * label : 13,15
         * views : 694
         * score : null
         * likes : 8097
         * content : 这里是视频简介
         * goodnum : 0
         * badnum : 0
         * create_time : 1554255157
         * update_time : 1555032723
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

        public DataBean(int id, String name, String image, String videofile, String fanhao, String actor, String category_id, String keyword, String special, String label, int views, Object score, int likes, String content, int goodnum, int badnum, int create_time, int update_time) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.videofile = videofile;
            this.fanhao = fanhao;
            this.actor = actor;
            this.category_id = category_id;
            this.keyword = keyword;
            this.special = special;
            this.label = label;
            this.views = views;
            this.score = score;
            this.likes = likes;
            this.content = content;
            this.goodnum = goodnum;
            this.badnum = badnum;
            this.create_time = create_time;
            this.update_time = update_time;
        }

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

package com.zcf.bananavideohannan.bean;

import java.util.List;

public class AVBean {


    /**
     * id : 10
     * actor : 哈哈
     * intro : 哈哈哈哈哈哈
     * avatar_image : http://172.16.1.105:12121/uploads/20190415/28c66f19a2971c7803f8a2ea274328ce.png
     * renqi : 2
     * pianliang : 13
     * cup : e
     * stature : null
     * Measur : null
     * create_time : 1554254036
     * update_time : 1555314886
     * video : {"total":3,"per_page":30,"current_page":1,"last_page":1,"data":[{"id":10,"name":"开过会","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/37afb97c0b3dd0ecdd0081087ee98f98.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"帝王温暖","keyword":"这里是关键字","special":"黄瓜出品","label":"13,240,234,233,201,153,121,100,81","views":4,"score":null,"likes":0,"content":"这里是简介","goodnum":0,"badnum":0,"create_time":1554979808,"update_time":1555291271},{"id":7,"name":"即可观看","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"性感内衣","keyword":"123","special":"黄瓜出品","label":"13,73,74,78,83,87,100,102,119,128,132,138,237,228,236,222,213,205,191,184,174,163,152,141,133,122","views":689,"score":null,"likes":8097,"content":"这里是视频简介","goodnum":0,"badnum":0,"create_time":1554255157,"update_time":1555291379},{"id":8,"name":"角色","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"性感内衣","keyword":"小姐姐","special":"黄瓜出品","label":"73,15,79,102,97,111,120,116,131,140,160,164,171,167,181,183,189,193,201,211,214","views":8,"score":null,"likes":1,"content":"这里是视频简介","goodnum":0,"badnum":0,"create_time":1554772055,"update_time":1555291333}]}
     */

    private int id;
    private String actor;
    private String intro;
    private String avatar_image;
    private int renqi;
    private int pianliang;
    private String cup;
    private Object stature;
    private Object Measur;
    private int create_time;
    private int update_time;
    private VideoBean video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }

    public int getRenqi() {
        return renqi;
    }

    public void setRenqi(int renqi) {
        this.renqi = renqi;
    }

    public int getPianliang() {
        return pianliang;
    }

    public void setPianliang(int pianliang) {
        this.pianliang = pianliang;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public Object getStature() {
        return stature;
    }

    public void setStature(Object stature) {
        this.stature = stature;
    }

    public Object getMeasur() {
        return Measur;
    }

    public void setMeasur(Object Measur) {
        this.Measur = Measur;
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

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public static class VideoBean {
        /**
         * total : 3
         * per_page : 30
         * current_page : 1
         * last_page : 1
         * data : [{"id":10,"name":"开过会","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/37afb97c0b3dd0ecdd0081087ee98f98.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"帝王温暖","keyword":"这里是关键字","special":"黄瓜出品","label":"13,240,234,233,201,153,121,100,81","views":4,"score":null,"likes":0,"content":"这里是简介","goodnum":0,"badnum":0,"create_time":1554979808,"update_time":1555291271},{"id":7,"name":"即可观看","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"性感内衣","keyword":"123","special":"黄瓜出品","label":"13,73,74,78,83,87,100,102,119,128,132,138,237,228,236,222,213,205,191,184,174,163,152,141,133,122","views":689,"score":null,"likes":8097,"content":"这里是视频简介","goodnum":0,"badnum":0,"create_time":1554255157,"update_time":1555291379},{"id":8,"name":"角色","image":"http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg","videofile":"http://172.16.1.105:12121/upload/644b01626832164975e61a845e9a3285.mp4","fanhao":"IPZ-855","actor":"哈哈","category_id":"性感内衣","keyword":"小姐姐","special":"黄瓜出品","label":"73,15,79,102,97,111,120,116,131,140,160,164,171,167,181,183,189,193,201,211,214","views":8,"score":null,"likes":1,"content":"这里是视频简介","goodnum":0,"badnum":0,"create_time":1554772055,"update_time":1555291333}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 10
             * name : 开过会
             * image : http://172.16.1.105:12121/uploads/20190411/803a180247e1da537246aa4dc87e1fbd.jpg
             * videofile : http://172.16.1.105:12121/upload/37afb97c0b3dd0ecdd0081087ee98f98.mp4
             * fanhao : IPZ-855
             * actor : 哈哈
             * category_id : 帝王温暖
             * keyword : 这里是关键字
             * special : 黄瓜出品
             * label : 13,240,234,233,201,153,121,100,81
             * views : 4
             * score : null
             * likes : 0
             * content : 这里是简介
             * goodnum : 0
             * badnum : 0
             * create_time : 1554979808
             * update_time : 1555291271
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

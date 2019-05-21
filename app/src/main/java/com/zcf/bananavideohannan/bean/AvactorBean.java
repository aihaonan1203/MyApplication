package com.zcf.bananavideohannan.bean;

import java.util.List;

public class AvactorBean {
    //      "actor_id":6,
//        "actor":"苍井空",
//        "avatar":"http://47.100.160.168:829/avatars/20181029/b7533826cb5733937df8fef8c806e2ae.jpg",
//        "pianliang":15,
//        "intro":"宅男女神",
//        "cup":"A"
    private int code;
    private Data data;
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int actor_id;
        private String actor;
        private String avatar;
        private int pianliang;
        private String intro;
        private String cup;

        public int getActor_id() {
            return actor_id;
        }

        public void setActor_id(int actor_id) {
            this.actor_id = actor_id;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPianliang() {
            return pianliang;
        }

        public void setPianliang(int pianliang) {
            this.pianliang = pianliang;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCup() {
            return cup;
        }

        public void setCup(String cup) {
            this.cup = cup;
        }
    }

//    "name":"测试视频1",
//            "image":"http://47.100.160.168:829/uploads/20181211/7ce3409e37d13e567773b82ea67a091f.jpg",
//            "actor":"苍井空",
//            "sort":"无码",
//            "keyword":"美少女",
//            "special":"主页专题一",
//            "label":"兽性啊",
//            "views":137,
//            "likes":0,
//            "create_time":"1544517709",
//            "intro":"这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介这是测试视频1的简介",
//            "video":"http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//            "score":"8.3",
//            "good_num":5,
//            "bad_num":1,
//            "video_id":7

    public class Video {

        private List<Data> data;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        // "name": "测试视频12",
//         "image": "http://47.100.160.168:829/uploads/20181217/7d33dcb4ea50a0e700d3e9f2a431cf45.jpg",
//         "actor": "苍井空",
//         "views": 152,
//         "create_time": "1545015652",
//         "intro": "这是测试视频12的简介这是测试视频12的简介这是测试视频12的简介这是测试视频12的简介这是测试视频12的简介",
//         "video": "http://47.100.160.168:829/upload/33c06b8f9f4b11abc3c2943136d69458.mp4",
//         "score": "8.3",
//         "video_id": 22
        public class Data {
            private String name;
            private String image;
            private String actor;
            private int views;
            private String create_time;
            private String intro;
            private String video;
            private String score;
            private int video_id;

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

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }


            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public int getVideo_id() {
                return video_id;
            }

            public void setVideo_id(int video_id) {
                this.video_id = video_id;
            }
        }

    }
}

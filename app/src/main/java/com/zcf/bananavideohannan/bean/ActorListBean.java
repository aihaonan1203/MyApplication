package com.zcf.bananavideohannan.bean;

import java.util.List;

public class ActorListBean {
    private int code;
    private Data data;

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
        //         "actor_id": 6,
//                 "intro": "宅男女神",
//                 "avatar": "http://47.100.160.168:829/avatars/20181029/ce4a3e5dbb243477692a1773911c5d45.jpg",
//                 "renqi": 1300,
//                 "pianliang": 9,
//                 "cup": "A",
//                 "create_time": "15407855
        private List<AvData> data;

        public List<AvData> getData() {
            return data;
        }

        public void setData(List<AvData> data) {
            this.data = data;
        }

        public class AvData {
            private int actor_id;
            private String avatar;
            private String name;
            private int renqi;
            private String cup;

            public int getRenqi() {
                return renqi;
            }

            public void setRenqi(int renqi) {
                this.renqi = renqi;
            }

            public String getCup() {
                return cup;
            }

            public void setCup(String cup) {
                this.cup = cup;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getActor_id() {
                return actor_id;
            }

            public void setActor_id(int actor_id) {
                this.actor_id = actor_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }
}

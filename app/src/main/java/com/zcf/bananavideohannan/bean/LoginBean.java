package com.zcf.bananavideohannan.bean;

public class LoginBean {

    /**
     * code : 200
     * msg : 登录成功
     * time : 1557908819
     * data : {"userinfo":{"id":10,"mobile":"12345612352","money":"10.00","level":0,"VIP":1,"sex":"","invite":"PcMW7d","refer":"PcMW7c","times":"3","daytimes":"3","timesupper":"3","cache":"0","cacheupper":"0","recommend":0,"username":"","nickname":"小小小","avatar":"http://203.78.142.214/assets/img/avatar.png","score":0,"token":"3ddac7b0-a5dd-4e59-8cb7-bb3e944e62cb","user_id":10,"createtime":1557908819,"expiretime":1560500819,"expires_in":2592000,"blv":1,"url":"http://203.78.142.214/index.php/index/user/register.html?referrer=PcMW7d"}}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userinfo : {"id":10,"mobile":"12345612352","money":"10.00","level":0,"VIP":1,"sex":"","invite":"PcMW7d","refer":"PcMW7c","times":"3","daytimes":"3","timesupper":"3","cache":"0","cacheupper":"0","recommend":0,"username":"","nickname":"小小小","avatar":"http://203.78.142.214/assets/img/avatar.png","score":0,"token":"3ddac7b0-a5dd-4e59-8cb7-bb3e944e62cb","user_id":10,"createtime":1557908819,"expiretime":1560500819,"expires_in":2592000,"blv":1,"url":"http://203.78.142.214/index.php/index/user/register.html?referrer=PcMW7d"}
         */

        private UserinfoBean userinfo;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public static class UserinfoBean {
            /**
             * id : 10
             * mobile : 12345612352
             * money : 10.00
             * level : 0
             * VIP : 1
             * sex :
             * invite : PcMW7d
             * refer : PcMW7c
             * times : 3
             * daytimes : 3
             * timesupper : 3
             * cache : 0
             * cacheupper : 0
             * recommend : 0
             * username :
             * nickname : 小小小
             * avatar : http://203.78.142.214/assets/img/avatar.png
             * score : 0
             * token : 3ddac7b0-a5dd-4e59-8cb7-bb3e944e62cb
             * user_id : 10
             * createtime : 1557908819
             * expiretime : 1560500819
             * expires_in : 2592000
             * blv : 1
             * url : http://203.78.142.214/index.php/index/user/register.html?referrer=PcMW7d
             */

            private int id;
            private String mobile;
            private String money;
            private int level;
            private int VIP;
            private String sex;
            private String invite;
            private String refer;
            private String times;
            private String daytimes;
            private String timesupper;
            private String cache;
            private String cacheupper;
            private int recommend;
            private String username;
            private String nickname;
            private String avatar;
            private int score;
            private String token;
            private int user_id;
            private int createtime;
            private int expiretime;
            private int expires_in;
            private int blv;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getVIP() {
                return VIP;
            }

            public void setVIP(int VIP) {
                this.VIP = VIP;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getInvite() {
                return invite;
            }

            public void setInvite(String invite) {
                this.invite = invite;
            }

            public String getRefer() {
                return refer;
            }

            public void setRefer(String refer) {
                this.refer = refer;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getDaytimes() {
                return daytimes;
            }

            public void setDaytimes(String daytimes) {
                this.daytimes = daytimes;
            }

            public String getTimesupper() {
                return timesupper;
            }

            public void setTimesupper(String timesupper) {
                this.timesupper = timesupper;
            }

            public String getCache() {
                return cache;
            }

            public void setCache(String cache) {
                this.cache = cache;
            }

            public String getCacheupper() {
                return cacheupper;
            }

            public void setCacheupper(String cacheupper) {
                this.cacheupper = cacheupper;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getExpiretime() {
                return expiretime;
            }

            public void setExpiretime(int expiretime) {
                this.expiretime = expiretime;
            }

            public int getExpires_in() {
                return expires_in;
            }

            public void setExpires_in(int expires_in) {
                this.expires_in = expires_in;
            }

            public int getBlv() {
                return blv;
            }

            public void setBlv(int blv) {
                this.blv = blv;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}

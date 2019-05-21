package com.zcf.bananavideohannan.bean;

import java.util.List;

public class FeedbackResultBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1555826226
     * data : [{"id":3,"uid":9,"type":"无法播放","content":"就趁现在立刻举报来看","create_time":1555658833,"update_time":1555658833,"file":"http://172.16.1.105:12121"},{"id":4,"uid":9,"type":"无法播放","content":"就趁现在立刻举报来看","create_time":1555825510,"update_time":1555825510,"file":"http://172.16.1.105:12121/avatar/15558255344a301072dec6b6a49050e5b294cd7983.png"},{"id":5,"uid":9,"type":"无法播放","content":"就趁现在立刻举报来看","create_time":1555825534,"update_time":1555825534,"file":"http://172.16.1.105:12121/avatar/15558255344a301072dec6b6a49050e5b294cd7983.png"},{"id":6,"uid":9,"type":"无法播放","content":"就趁现在立刻举报来看","create_time":1555825801,"update_time":1555825801,"file":"http://172.16.1.105:12121"}]
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
         * id : 3
         * uid : 9
         * type : 无法播放
         * content : 就趁现在立刻举报来看
         * create_time : 1555658833
         * update_time : 1555658833
         * file : http://172.16.1.105:12121
         */

        private int id;
        private int uid;
        private String type;
        private String content;
        private long create_time;
        private long update_time;
        private String file;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}

package com.zcf.bananavideohannan.bean;

import java.util.List;

public class NoticeBean {
    private int code;
    private List<NoticeDomain> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<NoticeDomain> getData() {
        return data;
    }

    public void setData(List<NoticeDomain> data) {
        this.data = data;
    }

    public class NoticeDomain {
        private int notice_id;
        private String title;
        private String content;
        private String create_time;

        public int getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(int notice_id) {
            this.notice_id = notice_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}

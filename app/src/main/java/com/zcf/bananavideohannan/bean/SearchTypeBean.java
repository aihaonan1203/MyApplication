package com.zcf.bananavideohannan.bean;

import java.util.List;

public class SearchTypeBean {
    private int code;
    private List<SearchType> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SearchType> getData() {
        return data;
    }

    public void setData(List<SearchType> data) {
        this.data = data;
    }

    public class SearchType {
        private int id;
        private String keyword;
        private int views;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }
    }
}

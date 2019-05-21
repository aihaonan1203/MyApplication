package com.zcf.bananavideohannan.bean;

import com.zcf.bananavideohannan.domain.DefFilmDomain;

import java.util.List;

public class FindBean {
    private int code;
    private Data data;

    public Data getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class Data {
//        private int total;
//        private int per_page;
//        private int current_page;
//        private int last_page;
        private List<DefFilmDomain> data;

//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public int getPer_page() {
//            return per_page;
//        }
//
//        public void setPer_page(int per_page) {
//            this.per_page = per_page;
//        }
//
//        public int getCurrent_page() {
//            return current_page;
//        }
//
//        public void setCurrent_page(int current_page) {
//            this.current_page = current_page;
//        }
//
//        public int getLast_page() {
//            return last_page;
//        }
//
//        public void setLast_page(int last_page) {
//            this.last_page = last_page;
//        }

        public List<DefFilmDomain> getData() {
            return data;
        }

        public void setData(List<DefFilmDomain> data) {
            this.data = data;
        }
    }
}

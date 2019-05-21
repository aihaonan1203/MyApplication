package com.zcf.bananavideohannan.bean;

import com.zcf.bananavideohannan.domain.DefFilmDomain;

import java.util.List;

public class SearchBean {
    private int code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class Data {
        private List<DefFilmDomain> data;

        public List<DefFilmDomain> getData() {
            return data;
        }

        public void setData(List<DefFilmDomain> data) {
            this.data = data;
        }
    }
}

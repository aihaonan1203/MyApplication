package com.zcf.bananavideohannan.bean;

import com.zcf.bananavideohannan.domain.FilmDomain;

import java.util.List;

public class AllFilmDataBean {
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


    public class Data{
        private List<FilmDomain>  data;

        public List<FilmDomain> getData() {
            return data;
        }

        public void setData(List<FilmDomain> data) {
            this.data = data;
        }
    }
}

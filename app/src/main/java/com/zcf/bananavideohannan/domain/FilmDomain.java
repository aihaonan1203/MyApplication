package com.zcf.bananavideohannan.domain;

public class FilmDomain extends DefFilmDomain {
    private String sort;// 分类名称
    private String intro;// 视频简介
    private int type_id;// 分类id

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }


    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}

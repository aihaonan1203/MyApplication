package com.zcf.bananavideohannan.domain;

public class BqSearchDomain extends BaseFilmDomain{

    private String label;
    private int views;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}

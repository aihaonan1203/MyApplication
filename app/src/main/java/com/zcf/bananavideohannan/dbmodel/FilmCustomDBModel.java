package com.zcf.bananavideohannan.dbmodel;

import com.zcf.bananavideohannan.domain.DefFilmDomain;

import java.util.List;

public class FilmCustomDBModel {
    private String typeTitle;
    private List<DefFilmDomain> films;

    public FilmCustomDBModel() {
    }

    public FilmCustomDBModel(String typeTitle, List<DefFilmDomain> films) {
        this.typeTitle = typeTitle;
        this.films = films;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public List<DefFilmDomain> getFilms() {
        return films;
    }

    public void setFilms(List<DefFilmDomain> films) {
        this.films = films;
    }
}

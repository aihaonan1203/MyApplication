package com.zcf.bananavideohannan.dbmodel;

/**
 * 首页分类数据
 */
public class MainVideoTypeDBModel extends BaseDBModel {
    public MainVideoTypeDBModel() {

    }

    public MainVideoTypeDBModel(String type, String typeName, String imgUrl) {
        this.type = type;
        this.typeName = typeName;
        this.imgUrl = imgUrl;
    }

    public MainVideoTypeDBModel(String type, String typeNamel) {
        this.type = type;
        this.typeName = typeName;
    }

    private String type;
    private String typeName;
    private String imgUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}


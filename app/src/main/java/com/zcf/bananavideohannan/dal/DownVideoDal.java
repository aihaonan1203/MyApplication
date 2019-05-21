package com.zcf.bananavideohannan.dal;

import com.zcf.bananavideohannan.dbmodel.VideoDownDBModel;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class DownVideoDal extends GTBaseDBDal {
    public DownVideoDal() {

    }

    public VideoDownDBModel getById(String id) {
        VideoDownDBModel model = null;
        try {
            model = db.selector(VideoDownDBModel.class).where("video_id", "=", id).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return model;
    }

    public List<VideoDownDBModel> getList() {
        List<VideoDownDBModel> models = null;
        try {
            models = db.selector(VideoDownDBModel.class).orderBy("createtime", true).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return models;
    }

    public void saveOrUpdate(VideoDownDBModel entity) {
        try {
            db.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String video_id) {
        try {
            db.delete(VideoDownDBModel.class, WhereBuilder.b("video_id", "=", video_id));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

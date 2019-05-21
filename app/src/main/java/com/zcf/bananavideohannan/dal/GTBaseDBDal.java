package com.zcf.bananavideohannan.dal;

import com.gaotai.baselib.DcAndroidContext;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;


/**
 * 基础类
 */
public class GTBaseDBDal {
    public String TAG = getClass().getName();

    DbManager db = x.getDb(DcAndroidContext.getInstance().daoConfig);

    /**
     * 查询单条记录
     */
    public <T> T get(Class<T> entityType, long id) {
        try {
            return db.findById(entityType, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除单条记录
     */
    public void delete(Class<?> entityType, long id) {
        try {
            db.delete(entityType, WhereBuilder.b("id", "=", id));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 条件删除
     */
    public void delete(Class<?> entityType, String where) {
        try {
            db.delete(entityType, WhereBuilder.b("'" + where + "'", "<>", where));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除所有记录
     */
    public void deleteAll(Class<?> entityType) {
        try {
            db.delete(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新或保存（注意:依据ID）
     */
    public void saveOrUpdate(Object entity) {
        try {
            db.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存单个数据
     */
    public void save(Object entity) {
        try {
            db.save(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}

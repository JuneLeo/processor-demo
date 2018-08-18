package com.example.songpengfei.myapplication.abtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.songpengfei.myapplication.abtest.model.ABTestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库获取云端key
 */
public class AbTestDBService {

    private static final String TABLE = "abtest";
    private static AbTestDBService INSTENCE = new AbTestDBService();

    public static AbTestDBService getDefault() {
        return INSTENCE;
    }

    /**
     * 单个查询
     *
     * @param key
     * @param action
     * @param context
     * @return
     */
    public ABTestModel queryByKeyAndAction(String key, String action, Context context) {
        AbTestDBHelper abTestDBHelper = new AbTestDBHelper(context);
        SQLiteDatabase db = abTestDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = db.query(TABLE, null, "key=? and action=?", new String[]{key, action}, null, null, null);
            return querySingle(query);

        } finally {
            if (query != null) {
                query.close();
            }
            db.close();
        }
    }

    /**
     * page 查询
     *
     * @param page_
     * @param context
     * @return
     */
    public List<ABTestModel> queryAllByPage(String page_, Context context) {
        AbTestDBHelper abTestDBHelper = new AbTestDBHelper(context);
        SQLiteDatabase db = abTestDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = db.query(TABLE, null, "_page=?", new String[]{page_}, null, null, null);
            return query(query);
        } finally {
            if (query != null) {
                query.close();
            }
            db.close();
        }
    }

    /**
     * 批量查询
     *
     * @param context
     * @return
     */
    public List<ABTestModel> queryAll(Context context) {
        AbTestDBHelper abTestDBHelper = new AbTestDBHelper(context);
        SQLiteDatabase db = abTestDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = db.query(TABLE, null, null, null, null, null, null);
            return query(query);
        } finally {
            if (query != null) {
                query.close();
            }
            db.close();
        }
    }

    /**
     * 单个数据插入
     *
     * @param model
     * @param context
     * @return
     */
    public boolean insert(ABTestModel model, Context context) {
        AbTestDBHelper abTestDBHelper = new AbTestDBHelper(context);
        SQLiteDatabase db = abTestDBHelper.getWritableDatabase();
        try {
            insert(db, model);
        } finally {
            db.close();
        }
        return true;
    }

    /**
     * 批量插入，开启事务
     *
     * @param models
     * @param context
     * @return
     */
    public boolean insertAll(List<ABTestModel> models, Context context) {
        AbTestDBHelper abTestDBHelper = new AbTestDBHelper(context);
        SQLiteDatabase db = abTestDBHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ABTestModel model : models) {
                insert(db, model);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    /**
     * 更新数据
     * @param key
     * @param action
     * @param _value
     * @param context
     * @return
     */
    public boolean update(String key, String action, String _value, Context context) {
        AbTestDBHelper helper = new AbTestDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues value = new ContentValues();
            value.put("_value", _value);
            db.update(TABLE, value, "key=? and action=?", new String[]{key, action});
        } finally {
            db.close();
        }
        return true;
    }

    /**
     * 批量操作
     * @param query
     * @return
     */



    private List<ABTestModel> query(Cursor query) {

        List<ABTestModel> list = null;
        while (query.moveToNext()) {
            if (list == null) {
                list = new ArrayList<>();
            }
            String page = query.getString(query.getColumnIndex("_page"));
            String key = query.getString(query.getColumnIndex("_key"));
            String action = query.getString(query.getColumnIndex("_action"));
            String keyName = query.getString(query.getColumnIndex("_key_name"));
            String values = query.getString(query.getColumnIndex("_values"));
            String value = query.getString(query.getColumnIndex("_value"));
            ABTestModel model = new ABTestModel(page, action, key, keyName, values, value);
            list.add(model);

        }
        return list;
    }

    private ABTestModel querySingle(Cursor query) {
        ABTestModel model = null;
        while (query.moveToNext()) {
            String page = query.getString(query.getColumnIndex("_page"));
            String key = query.getString(query.getColumnIndex("_key"));
            String action = query.getString(query.getColumnIndex("_action"));
            String keyName = query.getString(query.getColumnIndex("_key_name"));
            String values = query.getString(query.getColumnIndex("_values"));
            String value = query.getString(query.getColumnIndex("_value"));
            model = new ABTestModel(page, action, key, keyName, values, value);
        }
        return model;
    }

    private void insert(SQLiteDatabase db, ABTestModel model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_key", model.key);
        contentValues.put("_action", model.action);
        contentValues.put("_page", model.page);
        contentValues.put("_key_name", model.keyName);
        contentValues.put("_values", model.values);
        contentValues.put("_value", model.value);
        db.insert(TABLE, null, contentValues);
    }
}

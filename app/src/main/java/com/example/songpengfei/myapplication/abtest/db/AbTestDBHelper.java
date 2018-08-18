package com.example.songpengfei.myapplication.abtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AbTestDBHelper extends SQLiteOpenHelper {

    private static final String DB_ABTEST_NAME = "abtest.db";
    private static final int DB_ABTEST_VERSION = 1;

    public static final String CREATE_BOOK = "create table abtest (" +
            "_id integer primary key autoincrement, " +
            "_key text, " +
            "_action text, " +
            "_page text, " +
            "_key_name text, " +
            "_values text, " +
            "_value text) ";


    public AbTestDBHelper(Context context) {
        super(context, DB_ABTEST_NAME, null, DB_ABTEST_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}

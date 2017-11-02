package com.bawei.sjx.jinritoutiao.Herper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * dell 孙劲雄
 * 2017/8/17
 * 10:31
 */

public class Myhelper extends SQLiteOpenHelper {

    private static final String DBNAME="news.db";
    private static final int VERSION=3; //设置版本号
    private static final String TBL_DETAILNEWS="news"; //创建表名为news的表
    public Myhelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists ne(id Integer primary key autoincrement,title varchar(20),url varchar(100),ima varchar(100))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.bawei.sjx.jinritoutiao.Dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.bawei.sjx.jinritoutiao.Herper.Myhelper;
import com.bawei.sjx.jinritoutiao.bean.Nwes;
import com.bawei.sjx.jinritoutiao.bean.Nwes1;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * dell 孙劲雄
 * 2017/8/17
 * 10:31
 */

public class Userdao {

    private Myhelper helper;
    SQLiteDatabase db;

    public Userdao(Context context) {

        helper = new Myhelper(context); //与数据库建立连接 }
        db= helper.getWritableDatabase();
    }
    //插入数据
    public void insertDetsilNews(String title,String url,String ima){

        String sql="insert into ne(title,url,ima) values('"+title+"','"+url+"','"+ima+"')";

        db.execSQL(sql);

    }
    //删除数据
    public void del(String docid){ //根据传入参数docid删除数据 SQLiteDatabase db=helper.getReadableDatabase();

       db.delete("ne","title=?",new String[]{docid+""});

    }
    //查询数据
    public List<Nwes> findSelected(){

        List<Nwes> list=new ArrayList<Nwes>();
        Cursor c=db.rawQuery("select * from ne", null); //只有对数据进行查询时，才用rawQuery()，增、删、改和建表，都用execSQl() List<News> list=new ArrayList<News>();
        while(c.moveToNext()){
            Nwes news=new Nwes();
            news.setTitle(c.getString(c.getColumnIndex("title")));
            news.setUrl(c.getString(c.getColumnIndex("url")));
            news.setList(c.getString(c.getColumnIndex("ima")));
            news.setId(c.getShort(c.getColumnIndex("id")));
            list.add(news);
        }
      return list;
    }


}

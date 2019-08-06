package com.codepath.apps.restclienttemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TweetData extends SQLiteOpenHelper {
    /**db version*/
    private static final int DATABASE_VERSION = 1;
    /**database name*/
    private static final String DATABASE_NAME = "database.db";

    /**database creation string*/
    private static final String DATABASE_CREATE = "CREATE TABLE home (id INTEGER NOT NULL , text TEXT NOT NULL, createdAt TEXT NOT NULL, name TEXT NOT NULL, screen_name TEXT NOT NULL, img_url TEXT NOT NULL, img_post TEXT NOT NULL, favorite_count TEXT NOT NULL, retweet_count TEXT NOT NULL)";

    public TweetData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db)
    { db.execSQL(DATABASE_CREATE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long BackupTweetData(long id,String text,
                                String createAt,String name,String Screen_name,String img_url,String img_post,
                                String favorite_count,String retweet_count)
    {
        SQLiteDatabase database=this.getWritableDatabase();
       // int favorite=like ? 1:0;
       // int retweeted=isretweet ? 1:0;
       // int replyed=isreply ? 1:0;

        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("text",text);
        values.put("createdAt",createAt);
        values.put("name",name);
        values.put("screen_name",Screen_name);
        values.put("img_url",img_url);
        values.put("img_post",img_post);
        values.put("favorite_count",favorite_count);
        values.put("retweet_count",retweet_count);
        long result=database.insert("home",null,values);
        return result;
    }

    public void CleanRecycler()
    {
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete("home",null,null);
    }

    public Cursor GetData(){
        SQLiteDatabase database=this.getReadableDatabase();
        String query ="Select *from home";
        Cursor cursor=database.rawQuery(query,null);
        return cursor;
    }
}

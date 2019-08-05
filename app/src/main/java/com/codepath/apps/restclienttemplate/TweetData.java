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
    private static final String DATABASE_CREATE = "CREATE TABLE home (" +
            "  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "  `text`        TEXT NOT NULL,\n" +
            "  `createdAt` TEXT)";

    public TweetData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db)
    { db.execSQL(DATABASE_CREATE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS home");
        onCreate(db);
    }

    public long BackupTweetData(long id,String text,
                                String createAt)
    {
        SQLiteDatabase database=this.getWritableDatabase();
       // int favorite=like ? 1:0;
       // int retweeted=isretweet ? 1:0;
       // int replyed=isreply ? 1:0;

        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("text",text);
        values.put("createdAt",createAt);
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
        String Query ="Select *from home";
        Cursor cursor=database.rawQuery(Query,null);
        return cursor;
    }
}

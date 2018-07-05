package com.example.administrator.hanswer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by K on 2018/6/26 0026.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String DB_NAME = "test.db";

    public DBOpenHelper(Context context) {

        super(context, DB_NAME, null, VERSION);

        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO Auto-generated method stub
        try {

            db.execSQL("create table tbl_userinfo(username varchar(20),password varchar(20))");// 创建用户表
            db.execSQL("create table tbl_question(type varchar(5),title varchar(255),optionA varchar(255)," +
                    "optionB varchar(255),optionC varchar(255),optionD varchar(255)," +
                    "answer varchar(255),write_answer varchar(255)");//创建题库表


        } catch (Exception e) {
            System.out.println("------》》建表失败！");
            // TODO: handle exception
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}

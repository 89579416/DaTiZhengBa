package com.example.administrator.hanswer.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 增删改查
 * Created by K on 2018/6/26 0026.
 */

public class DBManage {
    private DBOpenHelper helper;
    private static SQLiteDatabase db;

    public DBManage() {
        super();
    }

    public DBManage(Context context) {
        helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    //添加信息
    public static void add(tbl_question tbl_question) {
        String sql = "insert into tbl_question(id,type,title,optionA,optionB,optionC,optionD,answer,write_answer)values(?,?,?,?,?,?,?,?,?)";
        Object[] bindArgs = new Object[]{
                tbl_question.getId(),
                tbl_question.getType(),
                tbl_question.getTitle(),
                tbl_question.getOptionA(),
                tbl_question.getOptionB(),
                tbl_question.getOptionC(),
                tbl_question.getOptionD(),
                tbl_question.getAnswer(),
                tbl_question.getWrite_answer(),
        };
        try {
            db.execSQL(sql, bindArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询所有信息
    public List<tbl_question> getAllData(int start, int count) {
        List<tbl_question> tbl_Question = new ArrayList<tbl_question>();
        String sql = "select * from tbl_question";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            tbl_Question.add(new tbl_question(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("optionA")),
                            cursor.getString(cursor.getColumnIndex("optionB")),
                            cursor.getString(cursor.getColumnIndex("optionC")),
                            cursor.getString(cursor.getColumnIndex("optionD")),
                            cursor.getString(cursor.getColumnIndex("answer")),
                            cursor.getString(cursor.getColumnIndex("write_answer"))
                            )
            );
        }
        return tbl_Question;
    }

    //获取总的记录数
    public static long getCount() {
        String sql = "select count(id)from tbl_question";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);

        }
        return 0;
    }

    //根据ID查询数据库
    public static tbl_question find(long id) {
        String sql = "select id,type,title,optionA,optionB,optionC,optionD,answer,write_answer from tbl_question where id=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            return new tbl_question(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("optionA")),
                    cursor.getString(cursor.getColumnIndex("optionB")),
                    cursor.getString(cursor.getColumnIndex("optionC")),
                    cursor.getString(cursor.getColumnIndex("optionD")),
                    cursor.getString(cursor.getColumnIndex("answer")),
                    cursor.getString(cursor.getColumnIndex("write_answer"))
            );
        }
        return null;
    }

    //根据ID更新数据库
    public static void update(tbl_question tbl_Question) {
        String sql = "update tbl_question set type=?,title=?,optionA=?,optionB=?,optionC=?,optionD=?,answer=?,write_answer=? where id=?";
        Object[]bindArgs={tbl_Question.getType(), tbl_Question.getTitle(),
                tbl_Question.getOptionA(), tbl_Question.getOptionB(),
                tbl_Question.getOptionC(), tbl_Question.getOptionD(),
                tbl_Question.getAnswer(), tbl_Question.getWrite_answer(),
        };
        db.execSQL(sql, bindArgs);
    }

    //删除数据
    public void detele(tbl_question tbl_Question) {
        String sql = "update tbl_question set type=?,title=?,optionA=?,optionB=?,optionC=?,optionD=?,answer=?,write_answer=? where id=?";
        Object[] bindArgs = {"", "", "", "", "", "", "", "", tbl_Question.getId()};
        db.execSQL(sql, bindArgs);
    }

}

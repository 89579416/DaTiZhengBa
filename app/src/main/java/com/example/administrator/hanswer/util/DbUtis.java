package com.example.administrator.hanswer.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.hanswer.db.tbl_question;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-07-04.
 */

public class DbUtis {
    private NotesDB notesDB;
    private Context c;
    private SQLiteDatabase db;
    public DbUtis(Context context) {
        c = context;
        notesDB = new NotesDB(c);
        db = notesDB.getReadableDatabase();
    }

    //添加信息
    public boolean add(tbl_question tbl_question) {
        boolean result = false;
        String sql = "insert into tbl_question(_id,type,title,optionA,optionB,optionC,optionD,answer,write_answer)values(?,?,?,?,?,?,?,?,?)";
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
            result = true;
            L.i("保存成功:");
        } catch (Exception e) {
            e.printStackTrace();
            L.i("保存失败:"+e.toString());
            result = false;
        }
        return result;
    }

    //查询所有信息
    public ArrayList<tbl_question> getAllData() {
        ArrayList<tbl_question> tbl_Question = new ArrayList<tbl_question>();
        String sql = "select * from tbl_question";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            tbl_Question.add(new tbl_question(
                            cursor.getInt(cursor.getColumnIndex("_id")),
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
    public long getCount() {
        String sql = "select count(_id)from tbl_question";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);

        }
        return 0;
    }

    //根据ID查询数据库
    public tbl_question find(long id) {
        String sql = "select _id,type,title,optionA,optionB,optionC,optionD,answer,write_answer from tbl_question where _id=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            return new tbl_question(
                    cursor.getInt(cursor.getColumnIndex("_id")),
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
    public void update(tbl_question tbl_Question) {
        String sql = "update tbl_question set write_answer=? where _id=?";
        Object[]bindArgs={tbl_Question.getWrite_answer(),tbl_Question.getId()
        };
        db.execSQL(sql, bindArgs);
    }
    //清除上一次做的题
    public void updateWriteAnswer() {
        String sql = "update tbl_question set write_answer=''";
        db.execSQL(sql);
    }
    //删除数据
    public void detele(tbl_question tbl_Question) {
        String sql = "update tbl_question set type=?,title=?,optionA=?,optionB=?,optionC=?,optionD=?,answer=?,write_answer=? where _id=?";
        Object[] bindArgs = {"", "", "", "", "", "", "", "", tbl_Question.getId()};
        db.execSQL(sql, bindArgs);
    }




}

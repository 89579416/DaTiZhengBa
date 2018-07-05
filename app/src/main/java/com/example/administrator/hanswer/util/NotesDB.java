package com.example.administrator.hanswer.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME_NOTES = "tbl_question";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NOTE_CONTENT = "content";
    public static final String COLUMN_NAME_NOTE_DATE = "date";

    public NotesDB(Context context) {
        super(context, "note", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //INTEGER PRIMARY KEY AUTOINCREMENT
        String sql = "CREATE TABLE " + TABLE_NAME_NOTES + "(" + COLUMN_NAME_ID
                + " INTEGER PRIMARY KEY ,"
                + "type TEXT NOT NULL DEFAULT\"\","
                + "title TEXT NOT NULL DEFAULT\"\","
                + "optionA TEXT NOT NULL DEFAULT\"\","
                + "optionB TEXT NOT NULL DEFAULT\"\","
                + "optionC TEXT NOT NULL DEFAULT\"\","
                + "optionD TEXT NOT NULL DEFAULT\"\","
                + "answer TEXT NOT NULL DEFAULT\"\","
                + "write_answer TEXT NOT NULL DEFAULT\"\","
                + COLUMN_NAME_NOTE_DATE + " TEXT NOT NULL DEFAULT\"\"" + ")";
        Log.d("AAAA", "sql="+sql);
        db.execSQL(sql);
//        db.execSQL("create table tbl_question(type varchar(5),title varchar(255),optionA varchar(255)," +
//                "optionB varchar(255),optionC varchar(255),optionD varchar(255)," +
//                "answer varchar(255),write_answer varchar(255)");//创建题库表

//		String sql1="insert into "+TABLE_NAME_NOTES+"values("+"1,"+"'写作业',"+"'晚上要写作业的干活'"+")";
//		Log.d("SQL1", sql1);
//		db.execSQL(sql1);
//		ContentValues values=new ContentValues();
//		values.put("id",1);
//		values.put("content","写作业");
//		values.put("date", "2013-1-2");
//		db.insert("note", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

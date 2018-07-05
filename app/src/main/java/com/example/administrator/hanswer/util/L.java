package com.example.administrator.hanswer.util;

import android.util.Log;

/**
 * Created by Administrator on 2018-05-25.
 */

public class L {
    public static final String TAG = "AAAA";
    public static void i(String str){
        Log.i(TAG,str);
    }
    public static void i(String tag,String str){
        Log.i(tag,str);
    }

    public static void e(String str){
        Log.e(TAG,str);
    }
    public static void d(String str){
        Log.d(TAG,str);
    }
}

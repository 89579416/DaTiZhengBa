package com.example.administrator.hanswer.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

//import static android.content.Intent.getIntent;

/**
 * 经验进度条设置
 * Created by K on 2018/6/29 0029.
 */

public class ProgressActivity extends AsyncTask<Integer, Integer, String> {
    private ProgressBar progressBar = null;

    public ProgressActivity(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ProgressActivity() {
    }

    @Override
    protected String doInBackground(Integer... params) {
        publishProgress(params[0]);//实时更新任务完成的进度
        return params[0].toString();
    }

    //将异步任务执行之前的一些准备工作的代码封装到该方法中，如：实例化对象等
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //在任务执行过程中更新UI线程中的控件属性放在该方法中
    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress((Integer) values[0]);
        progressBar.setVisibility(View.VISIBLE);
        super.onProgressUpdate(values);
    }

}

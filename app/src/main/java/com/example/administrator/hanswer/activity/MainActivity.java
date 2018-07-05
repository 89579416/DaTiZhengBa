package com.example.administrator.hanswer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.util.L;

/**
 * 主界面
 * Created by K on 2018/6/27 0027.
 */

public class MainActivity extends BaseActivity {
    private TextView grade = null;
    private TextView userinfoTX = null;
    private ProgressBar progressBar = null;
    private Button answerQuestion = null;
    private Button setQuestion = null;
    private Button rankingList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到布局文件
        setContentView(getLayoutId());
        //初始化View
        initView();
        //初始化界面数据
        initData();
        //绑定监听器与适配器
        initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    public void initView() {
        grade = (TextView) findViewById(R.id.grade);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        answerQuestion = (Button) findViewById(R.id.answerquestion);
        setQuestion = (Button) findViewById(R.id.setquestion);
        userinfoTX = (TextView) findViewById(R.id.userInfo);
        // 设置用户名
        if(getIntent()!=null){
            try {
                String username = (String) getIntent().getExtras().get("username");
                if (!username.isEmpty()) {
                    userinfoTX.setText(username);
                }
            }catch (Exception e){}
        }

    }

    @Override
    public void initListener() {
        answerQuestion.setOnClickListener(this);
        setQuestion.setOnClickListener(this);
//        rankingList.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null && getIntent().getExtras().get("grade") != null){

        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.answerquestion:
                Intent intent1 = new Intent(MainActivity.this, GetActivity.class);
                startActivityForResult(intent1,1001);
                break;
            case R.id.setquestion:
                Intent intent2 = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //重写返回键
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode==2001){
            //获取成绩
            int grades = data.getIntExtra("grade",0);
            L.i("收到的成绩 ："+grades);
            //显示成绩
            grade.setText(""+grades);
            //将成绩传递到进度条中
            ProgressActivity progressActivity = new ProgressActivity(progressBar);
            progressActivity.execute(grades);
        }
    }
}

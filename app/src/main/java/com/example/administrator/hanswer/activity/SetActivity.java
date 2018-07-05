package com.example.administrator.hanswer.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.bean.JsonQuestionBean;
import com.example.administrator.hanswer.db.DBManage;
import com.example.administrator.hanswer.db.tbl_question;
import com.example.administrator.hanswer.util.DbUtis;
import com.example.administrator.hanswer.util.L;
import com.example.administrator.hanswer.util.SharedPreferencesUtils;
import com.example.administrator.hanswer.util.T;

import java.util.List;

/**
 * 我要出题
 * 未完成
 * Created by K on 2018/6/28 0028.
 */

public class SetActivity extends BaseActivity{

    private EditText myTitle_ET = null;
    private EditText myOptionA_ET = null;
    private EditText myOptionB_ET = null;
    private EditText myOptionC_ET = null;
    private EditText myOptionD_ET = null;
    private Button A_btn = null;
    private Button B_btn = null;
    private Button C_btn = null;
    private Button D_btn = null;
    private List<JsonQuestionBean.Result> message;
    private int option_id;
    private DbUtis db;
    @Override
    protected int getLayoutId() {
        return R.layout.set;
    }

    @Override
    public void initView() {
        new DBManage(this);
        option_id = (int) SharedPreferencesUtils.getValue(this,"option_id",1);
        L.i("取出的数据："+option_id);
        db = new DbUtis(this);
        myTitle_ET = (EditText) findViewById(R.id.myTitle_ET);
        myOptionA_ET = (EditText) findViewById(R.id.myOptionA_ET);
        myOptionB_ET = (EditText) findViewById(R.id.myOptionB_ET);
        myOptionC_ET = (EditText) findViewById(R.id.myOptionC_ET);
        myOptionD_ET = (EditText) findViewById(R.id.myOptionD_ET);
        A_btn = (Button) findViewById(R.id.A_btn);
        B_btn = (Button) findViewById(R.id.B_btn);
        C_btn = (Button) findViewById(R.id.C_btn);
        D_btn = (Button) findViewById(R.id.D_btn);
    }

    @Override
    public void initListener() {
        A_btn.setOnClickListener(this);
        B_btn.setOnClickListener(this);
        C_btn.setOnClickListener(this);
        D_btn.setOnClickListener(this);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.A_btn:
                if(!validate()) {
                    //输入非法
                    Toast.makeText(SetActivity.this,"输入不合法", Toast.LENGTH_LONG).show();
                } else {
                    set("A");
                }
                break;
            case R.id.B_btn:
                if(!validate()) {
                    //输入非法
                    Toast.makeText(SetActivity.this,"输入不合法", Toast.LENGTH_LONG).show();
                } else {
                    set("B");
                }
                break;
            case R.id.C_btn:
                if(!validate()) {
                    //输入非法
                    Toast.makeText(SetActivity.this,"输入不合法", Toast.LENGTH_LONG).show();
                } else {
                    set("C");
                }
                break;
            case R.id.D_btn:
                if(!validate()) {
                    //输入非法
                    Toast.makeText(SetActivity.this,"输入不合法", Toast.LENGTH_LONG).show();
                } else {
                    set("D");
                }
                break;
            default:
                break;
        }
    }

    private void set(final String answer) {
        //获取输入内容
        final String title = myTitle_ET.getText().toString().trim();
        final String OptionA = myOptionA_ET.getText().toString().trim();
        final String OptionB = myOptionB_ET.getText().toString().trim();
        final String OptionC = myOptionC_ET.getText().toString().trim();
        final String OptionD = myOptionD_ET.getText().toString().trim();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 保存在本地服务器
                         */
                        option_id =(int)SharedPreferencesUtils.getValue(SetActivity.this,"option_id",option_id)+1;
                        boolean result = db.add(new tbl_question(option_id,"1",title,OptionA,OptionB,OptionC,OptionD,answer,""));
                        if(result){
                            handler.sendEmptyMessage(1);
                        }
                        /**
                         * 保存在线上服务器
                         */
//                        Looper.prepare();
//                        OkHttpClient client = new OkHttpClient();
//                        Request request = new Request.Builder()
//                                .url(MyConfig.URL_SETQUESTION + "?type=1 &title=" + title
//                                                + "&OptionA=" + OptionA + "&OptionB=" + OptionB
//                                                + "&OptionC=" + OptionC + "&OptionD=" + OptionD
//                                                + "&answer=" + answer
//                                )
//                                .build();
//                        Response response = null;
//                        try {
//                            response = client.newCall(request).execute();
//                            String result = response.body().string();
//                            if (response.isSuccessful()) {
//                                if (result == null) {
//                                    Toast.makeText(SetActivity.this, "服务器故障", Toast.LENGTH_SHORT).show();
//                                } else if (result.equals("0") || result.equals("2")) {
//                                    Toast.makeText(SetActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(SetActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Looper.loop();
                    }
                }
        ).start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    L.i("case 1  保存成功");
                    T.s(SetActivity.this,"添加成功");
                    SharedPreferencesUtils.setValue(SetActivity.this,"option_id",option_id);
                    tbl_question t = db.find(option_id);
                    if(t!=null){
                        L.i("查询出来的数据="+t.toString());
                    }
                    break;
            }
        }
    };
    private boolean validate() {
        boolean valid = true;
        String myTitle = myTitle_ET.getText().toString().trim();
        String myOptionA = myOptionA_ET.getText().toString().trim();
        String myOptionB = myOptionB_ET.getText().toString().trim();
        String myOptionC = myOptionC_ET.getText().toString().trim();
        String myOptionD = myOptionD_ET.getText().toString().trim();

        if (myTitle.isEmpty()) {
            myTitle_ET.setError("请输入问题！");
            valid = false;
        } else {
            myTitle_ET.setError(null);
        }
        if (myOptionA.isEmpty()) {
            myOptionA_ET.setError("请输入A选项");
        } else {
            myOptionA_ET.setError(null);
        }
        if (myOptionB.isEmpty()) {
            myOptionB_ET.setError("请输入B选项");
        } else {
            myOptionB_ET.setError(null);
        }
        if (myOptionC.isEmpty()) {
            myOptionC_ET.setError("请输入C选项");
        } else {
            myOptionC_ET.setError(null);
        }
        if (myOptionD.isEmpty()) {
            myOptionD_ET.setError("请输入D选项");
        } else {
            myOptionD_ET.setError(null);
        }
        return valid;
    }

    //返回会回到主界面
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SetActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

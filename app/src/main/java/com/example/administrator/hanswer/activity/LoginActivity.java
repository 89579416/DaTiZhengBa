package com.example.administrator.hanswer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.util.MyConfig;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 登录
 * Created by K on 2018/6/26 0026.
 */

public class LoginActivity extends BaseActivity{
    private static final String TAG = "LoginActivity";

    private EditText name = null;
    private EditText psd = null;
    private Button login = null;
    private Button register = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void initView() {
        name = (EditText) findViewById(R.id.name);
        psd = (EditText) findViewById(R.id.psd);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
    }

    @Override
    public void initListener() {
        //登录按钮 注册按钮设置点击监听事件
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            //点击登录按钮，执行登录操作
            case R.id.login:
//                login();
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                intent1.putExtra("username", "范冰冰");
                intent1.putExtra("flag", "0");
                startActivity(intent1);
                break;
            //如果点击了注册链接，则跳转到注册页面
            case R.id.register:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, 0);//请求码
                break;
            default:
                break;
        }
    }

    //点击登录后调用login()方法
    private void login() {
        //获取输入内容
        final String username = name.getText().toString().trim();
        final String password = psd.getText().toString().trim();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(MyConfig.URL_LOGIN + "?username=" + username + "&password=" + password)
                                    .build();
                            Response response = null;
                            response = client.newCall(request).execute();
                            String result = response.body().string();
                            if (response.isSuccessful()) {
                                if (result == null) {
                                    Toast.makeText(LoginActivity.this, "服务器故障", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("0")) {
                                    Toast.makeText(LoginActivity.this, "登录失败，没有此用户", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("2")) {
                                    Toast.makeText(LoginActivity.this, "密码输入错误,请检查用户名/密码", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("1")) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("username", username);
                                    intent.putExtra("flag", "0");
                                    startActivity(intent);

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }
                }
        ).start();
    }

    //重写返回键
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

}

package com.example.administrator.hanswer.activity;

import android.content.Intent;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.util.MyConfig;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by K on 2018/6/27 0027.
 */

public class RegisterActivity extends BaseActivity{
    private static final String TAG = "RegisterActivity";

    private Button register = null;
    private EditText name = null;
    private EditText psd = null;
    private TextView welcome = null;

    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    public void initView() {
        register = (Button) findViewById(R.id.btn_register);
        name = (EditText) findViewById(R.id.ed_username);
        psd = (EditText) findViewById(R.id.ed_psd);
        welcome = (TextView) findViewById(R.id.welcome);
    }

    public void initListener() {
        register.setOnClickListener(this);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            default:
                break;

        }
    }

    private void register() {
        final String username = name.getText().toString().trim();
        final String password = psd.getText().toString().trim();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(MyConfig.URL_REGISTER + "?username=" + username + "&password=" + password)
                                .build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            String result = response.body().string();
                            if (response.isSuccessful()) {
                                if (result == null) {
                                    Toast.makeText(RegisterActivity.this, "服务器故障", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("0")) {
                                    Toast.makeText(RegisterActivity.this, "注册失败,未知错误", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("2")) {
                                    Toast.makeText(RegisterActivity.this, "注册失败,此用户名已被注册", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("username", username);
                                    intent.putExtra("flag", "0");
                                    startActivity(intent);
                                }
                            }
                        } catch(IOException e){
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

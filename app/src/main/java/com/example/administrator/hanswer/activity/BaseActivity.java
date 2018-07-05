package com.example.administrator.hanswer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by K on 2018/6/27 0027.
 */

//基类 便于管理
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

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

    public void initListener() {

    }

    public void initData() {
    }

    public void initView() {
    }

    protected abstract int getLayoutId();


    //对点击事件统一管理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                processClick(v);
                break;
        }
    }

    public void processClick(View v) {

    }


}

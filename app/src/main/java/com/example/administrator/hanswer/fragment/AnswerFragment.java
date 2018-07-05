package com.example.administrator.hanswer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.db.tbl_question;
import com.example.administrator.hanswer.util.DbUtis;
import com.example.administrator.hanswer.util.L;


/**
 * 答题
 * Created by K on 2018/6/29 0029.
 */

public class AnswerFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    private static final String TAG = "AnswerFragment";
    private DbUtis db;
    private tbl_question result = null;
    private TextView realTitle = null;
    private RadioGroup radioGroup = null;
    private RadioButton optionA =null;
    private RadioButton optionB =null;
    private RadioButton optionC =null;
    private RadioButton optionD =null;
    private String option = "";
    private Handler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.answer_fragment);
    }

    @Override
    protected View initView() {
        db = new DbUtis(getContext());
        View view = View.inflate(fragmentActivity, R.layout.answer_fragment, null);
        realTitle = (TextView) view.findViewById(R.id.realtitle);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        //类型正确 找id 设置监听事件
        if ("1".equals(result.getType()+"")) {
            optionA = (RadioButton) view.findViewById(R.id.optionA);
            optionB = (RadioButton) view.findViewById(R.id.optionB);
            optionC = (RadioButton) view.findViewById(R.id.optionC);
            optionD = (RadioButton) view.findViewById(R.id.optionD);
            initListener();
        }
        return view;
    }

    @Override
    protected void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        realTitle.setText("" + result.getTitle());
        //若获取不到题目 则退出
        if (result == null) {
            Log.i(TAG, "initData: questionBean == null");
            return;
        }
        //否则就获取选项内容
        if ("1".equals(result.getType()+"")) {
            optionA.setText("" + result.getOptionA());
            optionB.setText("" + result.getOptionB());
            optionC.setText("" + result.getOptionC());
            optionD.setText("" + result.getOptionD());
        }
    }

    public AnswerFragment(tbl_question result) {
        this.result = result;
    }

    public AnswerFragment() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == optionA.getId()) {
            option = "A";
        } else if (checkedId == optionB.getId()) {
            option = "B";
        } else if (checkedId == optionC.getId()) {
            option = "C";
        } else if (checkedId == optionD.getId()) {
            option = "D";
        }
        L.i("点击了option="+option);
        //设置答案
        result.setWrite_answer(option);
        L.i("result="+result.toString());
        //数据库更新数据
        db.update(result);
        handler.sendEmptyMessage(2);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}

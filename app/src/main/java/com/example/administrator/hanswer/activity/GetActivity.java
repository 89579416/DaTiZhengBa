package com.example.administrator.hanswer.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.hanswer.R;
import com.example.administrator.hanswer.bean.JsonQuestionBean;
import com.example.administrator.hanswer.db.tbl_question;
import com.example.administrator.hanswer.fragment.AnswerFragment;
import com.example.administrator.hanswer.util.DbUtis;
import com.example.administrator.hanswer.util.L;
import com.example.administrator.hanswer.util.T;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 答题争霸
 * Created by K on 2018/6/28 0028.
 */

public class GetActivity extends BaseActivity {
    private static final String TAG = "GetActivity";
    FrameLayout layout;
    int grade = 0;
    private TextView timerTV = null;
    private TextView gradeTV = null;
    private ViewPager viewPager = null;
    private ArrayList<Fragment> fragmentArrayList = null;
    private int nowPager = 0;//当前页面
    private ArrayList<String> stringArrayList = null;
    private ArrayList<tbl_question> tbl_question_list = null;
    private JsonQuestionBean.Result messages = null;
    private String type = "1";
    private DbUtis db;
    private Timer timer;
    private TimerTask task;
    private int startTime;
    public Runnable myRunnabme = new Runnable() {
        @Override
        public void run() {
            toNext();
        }
    };
    /**
     * Handler
     */
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    timerTV.setText("倒计时：" + startTime + "秒");
                    countGrade();
                    if (startTime == 0) {
                        nowPager++;
                        toNext();
                    }
                    break;
                case 2:
                    //收到点击的答案，500毫秒后显示下一题，直接显示看起来会不自然
                    countGrade();
                    nowPager++;
                    timerTV.setText("倒计时：" + startTime + "秒");
                    handler.postDelayed(myRunnabme, 500);
                    break;
            }
        }

    };

    @Override
    protected int getLayoutId() {
        return R.layout.answer;
    }

    public void initView() {
        db = new DbUtis(this);
        db.updateWriteAnswer();
        gradeTV = (TextView) findViewById(R.id.gradeTV);
        timerTV = (TextView) findViewById(R.id.timerTV);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        layout = (FrameLayout) findViewById(R.id.answer_layout);
        fragmentArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        answer();
    }

    public void initListener() {
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void answer() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        tbl_question_list = db.getAllData();
                        L.i("打印查询出来的数据");
                        for (int i = 0; i < tbl_question_list.size(); i++) {
                            L.i("当前第" + i + "条=" + tbl_question_list.get(i).toString());
                            AnswerFragment answerFragment = new AnswerFragment(tbl_question_list.get(i));
                            answerFragment.setHandler(handler);
                            fragmentArrayList.add(answerFragment);
                            stringArrayList.add(tbl_question_list.get(i).getId() + "");
                        }

                        viewPager.setAdapter(new ViewAdapter(getSupportFragmentManager()));
                        Timing();
//                        AnswerFragment f1 = (AnswerFragment) fragmentArrayList.get(nowPager);
//                        fragmentTransaction.add(R.id.answer_layout,f1);
//                        fragmentTransaction.commit();

//                        Looper.prepare();
//                        try {
//                            OkHttpClient client = new OkHttpClient();
//                            Request request = new Request.Builder()
//                                    .url(MyConfig.URL_GETQUESTION + "?type=" + type)
//                                    .build();
//                            Response response = null;
//                            response = client.newCall(request).execute();
//                            String result = response.body().string();
//                            if (response.isSuccessful()) {
//                                if (result == null) {
//                                    Toast.makeText(GetActivity.this, "服务器故障", Toast.LENGTH_SHORT).show();
//                                } else if (result.equals("0") || result.equals("2")) {
//                                    Toast.makeText(GetActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
//                                } else if (result.equals("1")) {
//
//
//                                    //Gson解析
//                                    Gson gson = new Gson();
//                                    java.lang.reflect.Type reflectType = new TypeToken<JsonQuestionBean>() {}.getType();
//                                    JsonQuestionBean jsonQuestionBean = gson.fromJson(response.body().string(), reflectType);
//                                    //获得message
//                                    messages = jsonQuestionBean.getResult();
//                                    fragmentArrayList.add(new AnswerFragment(messages));
//                                    DBManage.add(messages);
//                                    stringArrayList.add(messages.getId()+"");
//                                }
//                                //配置适配器
//                                viewPager.setAdapter(new ViewAdapter(getSupportFragmentManager()));
//                            }
//                        }catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Looper.loop();
                    }
                }
        ).start();
    }

    //只是用来实现计时跳转而已
    @Override
    public void processClick(View v) {
//        if(nowPager == 0) {
//            Toast.makeText(GetActivity.this, "已经到头啦!", Toast.LENGTH_SHORT).show();
//        } else {
//            //12s时间到 nowPager加一 并计算得分
//            timer.onTick(12000);
////            viewPager.setCurrentItem(nowPager ++);
//            countGrade();
//        }
    }

    //计算分数
    private void countGrade() {
        grade = 0;
        Long count = db.getCount();
        L.i("计算分数：总条数：" + count);
        for (int i = 0; i < count; i++) {
            //查询选项
            tbl_question tbl_Question = db.find(Integer.parseInt(stringArrayList.get(i)));
            //判断 若相等即正确 加5分
            if (tbl_Question.getAnswer().equals(tbl_Question.getWrite_answer())) {
                grade += 5;
            }
        }
        //显示即时分数
        gradeTV.setText("得分：" + grade);
    }

//    /**
//     * 倒计时12秒，一次1秒
//     * 第一个参数表示总时间，第二个参数表示间隔时间
//     */
//    CountDownTimer timer = new CountDownTimer(12*1000, 1000) {
//
//        //在倒计时的时候调用 每倒计时一次要进行的操作
//        @Override
//        public void onTick(long millisUntilFinished) {
//            // TODO Auto-generated method stub
//            timerTV.setText(millisUntilFinished / 1000 + "秒");
//        }
//
//        //倒计时完毕后调用 倒计时完毕须要进行的操作都能写在里面
//        @Override
//        public void onFinish() {
//            Toast.makeText(GetActivity.this, "时间到了，下一题咯~", Toast.LENGTH_SHORT).show();
//        }
//    }.start();

    /**
     * 计时器
     */
    public void Timing() {
        //从15开始
        startTime = 12;
        if (timer == null) {
            timer = new Timer();
        }

        if (task != null) {
            task.cancel();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                startTime--;//每秒-1
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * 显示下一题
     */
    public void toNext() {
        if (nowPager < fragmentArrayList.size()) {
            viewPager.setCurrentItem(nowPager);
            startTime = 12;
        } else {
            T.s(GetActivity.this, "做完了");
            L.i("做完了,得分：=" + grade);
            //传递分数
            Intent intent = new Intent();
            intent.putExtra("grade", grade);
            setResult(2001, intent);
            finish();
        }
    }

    //返回会回到主界面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("grade", grade);
        setResult(2001, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (task != null) {
            task.cancel();
        }
    }

    //viewPager的监听事件
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            nowPager = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    //viewPager的适配器
    private class ViewAdapter extends FragmentPagerAdapter {

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        //获取条目
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        //数目
        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}


package com.example.panzq.simpleandroid_11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HandlerActivity extends Activity implements View.OnClickListener {

    private Button btn_main_to_sub;
    private Button btn_sub_to_main;
    Handler mMainHandler;
    Handler mSubHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mMainHandler = new MainHandler();
        new MainToSubThread("Thread#####1").start();
        initViews();
    }

    private void initViews() {
        btn_main_to_sub = findViewById(R.id.main_to_sub);
        btn_sub_to_main = findViewById(R.id.sub_to_main);
        btn_sub_to_main.setOnClickListener(this);
        btn_main_to_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_to_sub:
                Log.d("panzqww", "onClick  ---  MainThread to SubThread");
                Message msg = mSubHandler.obtainMessage();
                msg.obj = "从主线发送给子线程的消息";
                mSubHandler.sendMessage(msg);
                break;

            case R.id.sub_to_main:
                Log.d("panzqww", "onClick  ---  SubThread to MainThread");
                new SubToMainThread("Thread####2").start();
                break;
        }
    }

    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String msg_obj = (String) msg.obj;
            //longRunningMethod();//主线程运行耗时操作会出现ANR异常
            Log.d("panzqww", Thread.currentThread().getName()+"接收到子线程的消息： " + msg_obj);
        }
    }

    class SubToMainThread extends Thread {
        public SubToMainThread(String threadName) {
            super.setName(threadName);
        }

        @Override
        public void run() {
            super.run();
            Message msg = mMainHandler.obtainMessage();
            msg.obj = "从子线发送给主线程的消息 666";
            mMainHandler.sendMessage(msg);
        }
    }

    class MainToSubThread extends Thread {

        public MainToSubThread(String threadName) {
            super.setName(threadName);
        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mSubHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String msg_obj = (String) msg.obj;
                    Log.d("panzqww", Thread.currentThread().getName()+"接收到主线程的消息：" + msg_obj);
                    //longRunningMethod();//子线程执行耗时操作不会出现ANR异常
                }
            };
            createDialog();
            Looper.loop();
        }

    }

    private void longRunningMethod() {
        long i = 0;
        long sum = 0;
        while (i < 10000) {
            sum += i;
            SystemClock.sleep(1);
            i++;
        }
        Log.d("panzqww", Thread.currentThread().getName()+" 耗时操作结果 sum = " + sum);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void createDialog() {
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle("请做出选择").setIcon(R.drawable.icon1)
                .setMessage("我美不美")
                .setPositiveButton("美", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(HandlerActivity.this, "恭喜你答对了", 0)
                                .show();
                    }
                }).setNegativeButton("不美", new DialogInterface.OnClickListener() {// 消极

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                // TODO Auto-generated method stub
                Toast.makeText(HandlerActivity.this, "一点也不老实", 0)
                        .show();
            }
        }).setNeutralButton("不知道", new DialogInterface.OnClickListener() {// 中间级

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                // TODO Auto-generated method stub
                Toast.makeText(HandlerActivity.this, "快睁开眼瞅瞅", 0)
                        .show();
            }
        });
        Log.d("panzqww","线程名字:"+Thread.currentThread().getName());
        builder.create().show();
    }
}

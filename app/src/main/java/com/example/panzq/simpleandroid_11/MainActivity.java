package com.example.panzq.simpleandroid_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn1;
    private TextView tv1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String msgObj = msg.obj.toString();
            switch (msg.what)
            {
                case 1001:
                    Log.d("panzqww",msgObj);
                    break;
                case 1002:
                    Log.d("panzqww",msgObj);
                    break;
                case 1003:
                    Log.d("panzqww",msgObj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initListener();
        //executeTheard();
    }

    private void findViews() {
        btn1 = findViewById(R.id.btn1);
        tv1 = findViewById(R.id.tv1);
    }
    private void initListener()
    {
        btn1.setOnClickListener(this);
    }

    private void executeTheard() {
        Log.d("panzqww", Thread.currentThread().getName() + " Thread --- executeTheard start");
        new Thread() {
            @Override
            public void run() {
                super.run();
                btn1.setText("更新了btn1");
                tv1.setText("更新了btn1");
            }
        }.start();
        Log.d("panzqww", Thread.currentThread().getName() + " Thread --- executeTheard end");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn1:
                //mHandler.post(mRunnable);
                //executeTheard();
                //tv1.invalidate();
                //sendMessage_1();
                //sendMessage_2();
                //sendMessage_3();
                //sendMessage_4();
                startActivity(new Intent(MainActivity.this,HandlerActivity.class));
                break;
        }
    }
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            btn1.setText("更新了btn1");
            SystemClock.sleep(5000);
            tv1.setText("更新了btn1");
        }
    };


    private void sendMessage_1()
    {
        mHandler.sendEmptyMessage(1001);
        mHandler.sendEmptyMessageAtTime(1002,SystemClock.uptimeMillis()+1000);
        mHandler.sendEmptyMessageDelayed(1003,3000);
    }

    private void sendMessage_2()
    {
        Message message1 = Message.obtain();
        message1.what = 1001;
        message1.arg1 = 1002;
        message1.arg2 = 1003;
        message1.obj = " mHandler.sendMessage(message1);";
        mHandler.sendMessage(message1);
        Message message2 = Message.obtain();
        message2.what = 1002;
        message2.arg1 = 1002;
        message2.arg2 = 1003;
        message2.obj = " mHandler.sendMessageAtTime(message2,SystemClock.uptimeMillis()+1000);";
        mHandler.sendMessageAtTime(message2,SystemClock.uptimeMillis()+1000);
        Message message3 = Message.obtain();
        message3.what = 1003;
        message3.arg1 = 1002;
        message3.arg2 = 1003;
        message3.obj = " mHandler.sendMessageDelayed(message3,3000);";
        mHandler.sendMessageDelayed(message3,3000);
        mHandler.removeMessages(1003);
    }

    private void sendMessage_3()
    {
        mHandler.post(runnable);
        mHandler.postDelayed(runnable,3000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("panzqww","remove runnable");
            mHandler.removeCallbacks(runnable);
        }
    };
    private void sendMessage_4()
    {
        Message message = mHandler.obtainMessage();
        message.what = 1001;
        message.obj="message.sendToTarget();";
        message.sendToTarget();
    }
}

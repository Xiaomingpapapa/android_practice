package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 傻明也有春天 on 2016/7/19.
 */
public class threadspeak extends Activity {
    Button btn_1;
    TexttoSpeak texttoSpeak;
    EditText edit_1;
    int i = 33;
    int time = 100;
    Timer timer;
    Timer timer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threadtalk);
        btn_1 = (Button) findViewById(R.id.btn_1);
        texttoSpeak = new TexttoSpeak(this);
        edit_1 = (EditText) findViewById(R.id.edit_1);
        final Thread thread = new Thread(new myRunnable());
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();

            }
        });
        timer = new Timer();
        timer2 = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String string = edit_1.getText().toString();
                String string1 = string.substring(0, 2);
                if (Integer.parseInt(string1) >= 30) {
                    Log.v("msg", "执行");
                    texttoSpeak.speak("welcome to this app");

                }
            }
        };
        TimerTask timerTask2 =new TimerTask() {
            @Override
            public void run() {
                texttoSpeak.speak("hahaha");
            }
        };
        timer.schedule(timerTask, 3000, 7000);
        timer2.schedule(timerTask2,3000,7000);
    }

    class myRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                Message message = Message.obtain();
                message.what = 0x111;
                message.obj = 33;
                handler.sendMessage(message);


            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x111) {
                int h = (int) msg.obj;
                edit_1.setText(h + "");

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (texttoSpeak!=null)
        texttoSpeak.stop();
        if (timer!=null){
            timer.cancel();
        }
    }
}

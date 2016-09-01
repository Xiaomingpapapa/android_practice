package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.security.MessageDigestSpi;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 傻明也有春天 on 2016/7/29.
 */
public class Move1 extends Activity {
    Button btn_start;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img_people2;
    SeekBar seekBar;
    ObjectAnimator objectAnimator;
    ObjectAnimator objectAnimator1;
    ObjectAnimator objectAnimator2;
    myBtnClick btnClick;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    Timer timer;
    int distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move1);
        init();
        setBtnClick();
        setAnimator();


    }

    private void init() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btnClick = new myBtnClick();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);
        img_people2 = (ImageView) findViewById(R.id.img_people2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        builder = new AlertDialog.Builder(Move1.this);
        builder.setTitle("确认");
        builder.setMessage("游戏失败");
        builder.setPositiveButton("重新游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objectAnimator.start();
                objectAnimator1.start();
                objectAnimator2.start();
                seekBar.setProgress(0);
                timer = new Timer();
                setTimer(timer);

            }
        });
        builder.setNegativeButton("退出游戏",null);
        builder2 = new AlertDialog.Builder(Move1.this);
        builder2.setTitle("确认");
        builder2.setMessage("游戏成功");
        builder2.setPositiveButton("进入下一关",null);
    }

    private void setBtnClick() {
        btn_start.setOnClickListener(btnClick);
    }

    private class myBtnClick implements View.OnClickListener {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start: {
                    objectAnimator.start();
                    objectAnimator1.start();
                    objectAnimator2.start();
                    seekBar.setProgress(0);
                    timer = new Timer();
                    setTimer(timer);
                    break;
                }

            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x112: {
                    timer.cancel();
                    stopAnimator();
                   // builder.show();
                    break;
                }
                case 0x113: {
                    timer.cancel();
                    stopAnimator();
                    //builder2.show();
                    break;
                }
                case 0x114:{
                    int speed = (int)msg.obj;
                    if (speed!=0){
                        distance = distance+speed/4; //此处到底除以几合适还得调试才知道
                        seekBar.setProgress(distance);
                    }
                    break;
                }


            }
        }
    };

    private void setAnimator() {
        objectAnimator = ObjectAnimator.ofFloat(img1, "y", 0f, 550f);
        objectAnimator1 = ObjectAnimator.ofFloat(img2, "y", 0f, 550f);
        objectAnimator2 = ObjectAnimator.ofFloat(img3,"y",0f,550f);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(3000);
        objectAnimator1.setDuration(3000);
        objectAnimator1.setStartDelay(1000);
        objectAnimator2.setDuration(3000);
        objectAnimator2.setStartDelay(1200);


    }

    private void setTimer(Timer timer) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.v("msg",""+seekBar.getProgress());
                if ((img1.getY() >= 547f && Math.abs(seekBar.getProgress() - 10) <= 3) ||(img2.getY()>=547f&&Math.abs(seekBar.getProgress()-37)<=3)||(img3.getY()>=547&&Math.abs(seekBar.getProgress() - 63)<=3)) {
                    Message message = Message.obtain();
                    message.what = 0x112;
                    handler.sendMessage(message);
                }
                if ((seekBar.getProgress() >= 90)) {
                    Message message = Message.obtain();
                    message.what = 0x113;
                    handler.sendMessage(message);
                }

            }
        };
        timer.schedule(timerTask, 0, 100);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void stopAnimator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            objectAnimator.pause();
        }
        objectAnimator1.pause();
        objectAnimator2.pause();

    }
    private void getSpeed(){
        //在此不断换取数据，每获取一次数据就将数据通过Message发送给Handler处理
        int speed = 4;
        Message message = Message.obtain();
        message.what = 0x114;
        message.obj = speed;
        handler.sendMessage(message);
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}

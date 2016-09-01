package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    ImageView img;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    Button btn_1;
    int[] location = new int[2];
    int[] location2 = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move2();

            }
        });


    }


    private void move2() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 500f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Message message = Message.obtain();
                message.what = 0x111;
                float deltaX = (float) animation.getAnimatedValue();
                message.obj = deltaX;
                handler.sendMessage(message);
                //img.setTranslationX(deltaX);

            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
//        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0f,1000f);
//        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float deltaY = (float) animation.getAnimatedValue();
//                img1.setTranslationY(deltaY);
//
//
//            }
//        });
//        valueAnimator1.setDuration(1000);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(valueAnimator).before(valueAnimator1);
//        animatorSet.start();


    }

    private void init() {
        img = (ImageView) findViewById(R.id.img);
        btn_1 = (Button) findViewById(R.id.btn_1);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
    }
    private  void getLocation(){
        img.getLocationOnScreen(location);
        img1.getLocationOnScreen(location2);
        Log.v("msg","img:"+location[1]+"img1"+location2[1]);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x111){
                float deltaX = (float)msg.obj;
                Log.v("msg",deltaX+"");
                img.setTranslationX(deltaX);
            }
        }
    };

}

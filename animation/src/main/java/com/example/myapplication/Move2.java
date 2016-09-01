package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by 傻明也有春天 on 2016/7/28.
 */
public class Move2 extends Activity implements View.OnClickListener {
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img_people;
    Button btnPause;
    Button btnStop;
    Button btnPlay;
    ObjectAnimator animator;
    ObjectAnimator animator1;
    ObjectAnimator animator2;
    ObjectAnimator animator3;
    AnimatorSet animatorSet;
    MyAnimatorUpdateListener updateListener = new MyAnimatorUpdateListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move2);
        init();
        setClick();
        setAnimator();
        //为了增加
    }

    private void init() {
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img_people = (ImageView)findViewById(R.id.img_people);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnPause = (Button) findViewById(R.id.btn_pause);
        btnStop = (Button) findViewById(R.id.btn_stop);
        animatorSet = new AnimatorSet();

    }

    private void setClick() {
        btnStop.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
    }
    private void setAnimator(){
        animator = ObjectAnimator.ofFloat(img1, "y", 0f, 700f);
        animator1 = ObjectAnimator.ofFloat(img2, "y", 0f, 700f);
        animator2 = ObjectAnimator.ofFloat(img3,"y",0f,700f);
        animator3 = ObjectAnimator.ofFloat(img_people,"x",0f,400f);
        //animator.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setDuration(20001);
        animatorSet.play(animator).with(animator1).with(animator2);
        animatorSet.setDuration(1000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                //如果已经暂停，是继续播放
                if (updateListener.isPause) updateListener.play();
                    //否则就是从头开始播放
                else {
                   animatorSet.start();


                }
                break;
            case R.id.btn_stop:
                //如果点击停止，那么我们还需要将暂停的动画重新设置一下
                updateListener.play();
                animator.end();
                animator1.end();
                break;
            case R.id.btn_pause:
                updateListener.pause();
                break;
        }

    }

    class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        /**
         * 暂停状态
         */
        private boolean isPause = false;
        /**
         * 是否已经暂停，如果一已经暂停，那么就不需要再次设置停止的一些事件和监听器了
         */
        private boolean isPaused = false;
        /**
         * 当前的动画的播放位置
         */
        private float fraction = 0.0f;
        /**
         * 当前动画的播放运行时间
         */
        private long mCurrentPlayTime = 0l;

        /**
         * 是否是暂停状态
         *
         * @return
         */
        public boolean isPause() {
            return isPause;
        }

        /**
         * 停止方法，只是设置标志位，剩余的工作会根据状态位置在onAnimationUpdate进行操作
         */
        public void pause() {
            isPause = true;
        }

        public void play() {
            isPause = false;
            isPaused = false;
        }

        @Override
        public void onAnimationUpdate(final ValueAnimator animation) {
            /**
             * 如果是暂停则将状态保持下来，并每个刷新动画的时间了；来设置当前时间，让动画
             * 在时间上处于暂停状态，同时要设置一个静止的时间加速器，来保证动画不会抖动
             */
            if (isPause) {
                if (!isPaused) {
                    mCurrentPlayTime = animation.getCurrentPlayTime();
                    fraction = animation.getAnimatedFraction();
                    animation.setInterpolator(new TimeInterpolator() {
                        @Override
                        public float getInterpolation(float input) {
                            return fraction;
                        }
                    });
                    isPaused = true;
                }
                //每隔动画播放的时间，我们都会将播放时间往回调整，以便重新播放的时候接着使用这个时间,同时也为了让整个动画不结束
                new CountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay()) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        animator.setCurrentPlayTime(mCurrentPlayTime);
                        animator1.setCurrentPlayTime(mCurrentPlayTime);
                    }
                }.start();
            } else {
                //将时间拦截器恢复成线性的，如果您有自己的，也可以在这里进行恢复
                animation.setInterpolator(null);
            }
        }
    }
}

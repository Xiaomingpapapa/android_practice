package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 傻明也有春天 on 2016/8/1.
 */
public class mySurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean flag;
    private Canvas canvas;
    private Paint paint;
    private int x = 300,y = 30,r = 50;
    public mySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        setFocusable(true);

    }

   public void Draw(){
       canvas = surfaceHolder.lockCanvas();
       canvas.drawRGB(0,0,0);
       canvas.drawCircle(x, y, r, paint);
       surfaceHolder.unlockCanvasAndPost(canvas);
   }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:{
            y--;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN:{
                y++;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT:{
                x--;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT:{
                x++;
                break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
        while(flag){
            Draw();

        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    y++;
                }

            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}

package com.example.myapplication;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;

/**
 * Created by 傻明也有春天 on 2016/8/1.
 */
public class myDraw extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new mySurfaceView(this));
    }
}

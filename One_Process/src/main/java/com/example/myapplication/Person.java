package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by 傻明也有春天 on 2016/4/20.
 */
public class Person extends Activity {
    Calendar calendar;
    TextView text11;
    ImageButton image_btn7;
    ImageButton image_btn4;
    int count = 1;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        init();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        text11.setText(month+"月"+day+"日");


    }

    private void init() {
        image_btn7 = (ImageButton)findViewById(R.id.image_btn7);
        calendar = Calendar.getInstance();
        text11 = (TextView)findViewById(R.id.text11);
        image_btn4 = (ImageButton)findViewById(R.id.image_btn4);
        image_btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Person.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    }


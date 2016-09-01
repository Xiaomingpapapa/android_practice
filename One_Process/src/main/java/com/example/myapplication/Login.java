package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 傻明也有春天 on 2016/4/13.
 */
public class Login extends Activity {
    private SQLiteDatabase db;
    EditText edit_3;
    EditText edit_4;
    EditText edit_5;
    Button btn_3;
    ImageButton image_btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        image_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
       btn_3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getTxt_toDataBase();
               Toast.makeText(Login.this, "注册成功，5秒后跳转到登录界面", Toast.LENGTH_SHORT).show();
               Timer timer = new Timer();
               TimerTask timerTask = new TimerTask() {
                   @Override
                   public void run() {
                    Intent intent = new Intent(Login.this,Register.class);
                       startActivity(intent);
                   }
               };
               timer.schedule(timerTask,500);

           }
       });
    }
    private void init() {
        edit_3 = (EditText) findViewById(R.id.edit_3);
        edit_4 = (EditText) findViewById(R.id.edit_4);
        edit_5 = (EditText) findViewById(R.id.edit_5);
        btn_3 = (Button) findViewById(R.id.btn_3);
        image_btn5 = (ImageButton)findViewById(R.id.image_btn5);
    }

    private void getTxt_toDataBase() {
        String str1 = edit_3.getText().toString();
        String str2 = edit_4.getText().toString();
        String str3 = edit_5.getText().toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            db = SQLiteDatabase.openOrCreateDatabase(file + File.separator + "login.db4", null);
        }
        db.execSQL("insert into User1 values('" + str1 + "','" + str2 + "','" + str3 + "')");
        Log.v("msg", "插入数据成功");
    }
}

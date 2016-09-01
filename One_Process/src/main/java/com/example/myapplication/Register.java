package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import static android.database.sqlite.SQLiteDatabase.*;

/**
 * Created by 傻明也有春天 on 2016/4/13.
 */
public class Register extends Activity {
    SQLiteDatabase db;
    Button btn_1;
    Button btn_2;
    EditText edit_1;
    EditText edit_2;
    TexttoSpeak speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getDB();
        init();
        CreateTable();
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

    }
    private void init(){
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        edit_1 = (EditText)findViewById(R.id.edit_1);
        edit_2 = (EditText)findViewById(R.id.edit_2);
        speak = new TexttoSpeak(this);
    }

    private void CreateTable() {
        db.execSQL("create table if not exists User1(mobileNum varchar(20),password varchar(20), name varchar(20))");

    }

    private void getDB() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            db = SQLiteDatabase.openOrCreateDatabase(file + File.separator + "login.db4", null);
        }
    }
    private void register(){
        String str1 = edit_1.getText().toString();
        String str2 = edit_2.getText().toString();
        boolean right = false;
        Cursor cursor = db.query("User1", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String mobile_id = cursor.getString(cursor.getColumnIndex("mobileNum")).trim();
            String password = cursor.getString(cursor.getColumnIndex("password")).trim();
            if (mobile_id.equals(str1) && password.equals(str2)) {
                right = true;
                break;
            }
        }
        if (right) {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            speak.speak("2016小梦为您服务，请先连接蓝牙");

        } else {
            Toast.makeText(Register.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

        }


    }

}

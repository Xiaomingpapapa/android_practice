package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;


/**
 * Created by 傻明也有春天 on 2016/8/1.
 */
public class bmobTest extends Activity {
    public static String APPID ="e522d322e7fe16842354b01841c5d424";
    EditText edit_username;
    EditText edit_password;
    Button btn_submit;
    Button btn_query;
    myBtnClick btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmobtest);
        init();
        setBtnClick();
        Bmob.initialize(bmobTest.this, APPID);

    }

    private void init() {
        edit_username = (EditText)findViewById(R.id.edit_username);
        edit_password = (EditText)findViewById(R.id.edit_password);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_query = (Button)findViewById(R.id.btn_query);
        btnClick = new myBtnClick();
    }
    private void setBtnClick(){
        btn_submit.setOnClickListener(btnClick);
        btn_query.setOnClickListener(btnClick);
    }
    private void sumbit(){
        String username = edit_username.getText().toString();
        String password = edit_password.getText().toString();
        myBmob bmober = new myBmob();
        bmober.setUsername(username);
        bmober.setPassword(password);
        bmober.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(bmobTest.this, s, Toast.LENGTH_SHORT).show();
                }
                if (e != null) {
                    Toast.makeText(bmobTest.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void connect(){
        BmobRealTimeData bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {

            }

            @Override
            public void onDataChange(JSONObject jsonObject) {

            }
        });
    }
    class myBtnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_submit:{
                    sumbit();
                    break;
                }
                case R.id.btn_query:{
                    break;
                }
            }
        }
    }
}

package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_1;
    List<CPerson> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //获取到文件流
                    InputStream inputStream  = getResources().getAssets().open("person.xml");
                    //创建解析器
                    XmlPullParser xmlPullParser = Xml.newPullParser();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

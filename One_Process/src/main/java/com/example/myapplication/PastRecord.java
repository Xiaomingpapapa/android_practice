package com.example.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 傻明也有春天 on 2016/4/16.
 */
public class PastRecord extends Activity {
   // BindService bindService;
   /// BindService.myBinder binder;
    ListView list;
    Canvas canvas = new Canvas();
    ImageButton image_btn8;
    List<Map<String,Object>> data;
    SimpleAdapter simpleAdapter;
    HistogramView histogramView;
    int imageId[] = {R.drawable.r,R.drawable.s,R.drawable.t};
    String txt1[] = {"平均骑行","平均心率","健康状态"};
    String txt2[] = {"400米","45跳/分","良好"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastrecord);
        init();
        list = (ListView)findViewById(R.id.list);
        data = new ArrayList<Map<String,Object>>();
        for (int i=0;i<imageId.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",imageId[i]);
            map.put("txt1",txt1[i]);
            map.put("txt2",txt2[i]);
            data.add(map);
        }
        simpleAdapter = new SimpleAdapter(PastRecord.this,data,R.layout.list, new String[]{"image","txt1","txt2"},new int[]{R.id.image4,R.id.text7,R.id.text8});
        list.setAdapter(simpleAdapter);



    }
    private void init(){
        image_btn8  = (ImageButton)findViewById(R.id.image_btn8);
        histogramView = new HistogramView(this);
        image_btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PastRecord.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MainActivity extends AppCompatActivity {
    Button btn_1;
    HttpUtils httpUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "zhang";
                String passward = "123";
                httpUtils = new HttpUtils();
                RequestParams params = new RequestParams("utf-8");
                params.addBodyParameter("name",username);
                params.addBodyParameter("pass",passward);
                httpUtils.send(HttpRequest.HttpMethod.POST, "http://192.168.1.109:8080/myproject/test.jsp", params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.v("msg","参数传递成功");
                        Log.v("msg",responseInfo.result);


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.v("msg","参数传递失败");

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }
                });

            }
        });

    }
}

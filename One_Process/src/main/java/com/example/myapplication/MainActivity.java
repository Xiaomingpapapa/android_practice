package com.example.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class MainActivity extends Activity {
    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private SQLiteDatabase db;
    private InputStream is;    //输入流，用来接收蓝牙数据
    private String smsg = "";//显示用数据缓存
    private String heart_rate = "";
    private String weight = "";
    private String temperature = "";
    private String speed = "60";
    private int distance2 = 0;
    private String voice1 = "主人，当前室温已超过30度，您要适当多休息一下哦";
    private String voice2 = "主人，小梦检测到您当前运动量超出运动量范围，请减小运动强度";

    BluetoothDevice _device = null;     //蓝牙设备
    BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean bRun = true;
    boolean bThread = false;
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备

    CircleProgress circleProgress;
    private int mCurrentProgress;
    private int mTotalProgress;
    SeekBar seekBar;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    ImageButton image_btn1;
    ImageButton image_btn3;
    ImageButton image_btn4;
    myBtnClick click = new myBtnClick();
    int imagecount1 = 0;
    int[] imageId1 = new int[]{R.drawable.o1, R.drawable.o2, R.drawable.o3, R.drawable.o4, R.drawable.o5, R.drawable.o6, R.drawable.o7, R.drawable.o8, R.drawable.o9, R.drawable.o10};
    TexttoSpeak speaker1;
    TexttoSpeak speaker2;
    TexttoSpeak speaker3;
    TextToSpeech tts;
    Timer timer;
    Timer timer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDB();
        createTable();
        initVariable();
        initView();
        seekBar.setMax(100);
        setBtnClick();
        if (_bluetooth == null) {
            Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentProgress = (int) progress / 5;
                circleProgress.setProgress(mCurrentProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String string = text6.getText().toString();
                String string1 =string.substring(0,2);
                if(Integer.parseInt(string1)>=30){
                    speaker3.speak(voice1);
                }
            }
        };
        timer2 = new Timer();
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                String string = text4.getText().toString();
                String string1 = string.substring(0,string.indexOf("跳"));
                if (Integer.parseInt(string1)>=140){
                    speaker3.speak(voice2);
                }
            }
        };
        timer.schedule(timerTask,3000,6000);
        timer2.schedule(timerTask2,3000,6000);


    }

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceList.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = _bluetooth.getRemoteDevice(address);

                    // 用服务号得到socket
                    try {
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    ImageButton btn = (ImageButton) findViewById(R.id.image_btn5);
                    try {
                        _socket.connect();
                        Toast.makeText(this, "连接" + _device.getName() + "成功！", Toast.LENGTH_SHORT).show();
                        speaker2.speak("主人，蓝牙连接成功");
                        //btn.setText("断开");
                    } catch (IOException e) {
                        try {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            _socket.close();
                            _socket = null;
                        } catch (IOException ee) {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try {
                        is = _socket.getInputStream();   //得到蓝牙数据输入流
                    } catch (IOException e) {
                        Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //得到蓝牙数据输入流
                    if (bThread == false) {
                        ReadThread.start();
                        bThread = true;
                    } else {
                        bRun = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    Thread ReadThread = new Thread() {

        public void run() {
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            int j = 0;
            bRun = true;
            //接收线程
            while (true) {
                try {
                    while (is.available() == 0) {
                        while (bRun == false) {
                        }
                    }
                    while (true) {
                        num = is.read(buffer);         //读入数据
                        n = 0;
                        String s0 = new String(buffer, 0, num);
                        for (i = 0; i < num; i++) {
                            if ((buffer[i] == 0x0d) && (buffer[i + 1] == 0x0a)) {
                                buffer_new[n] = 0x0a;
                                i++;
                            } else {
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        String s = new String(buffer_new, 0, n);
                        smsg = smsg + s;
                        if (smsg.startsWith("a") && smsg.endsWith("z")) {
                            Message message = Message.obtain();
                            message.what = 0x111;
                            message.obj = smsg;
                            handler.sendMessage(message);

                        }
                        if (is.available() == 0) break;


                        //短时间没有数据才跳出进行显示

                    }
                    //发送显示消息，进行显示刷新


                } catch (IOException e) {
                }
            }
        }
    };
    //消息处理队列
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111: {
                    String string1 = (String) msg.obj;
                    temperature = string1.substring(string1.indexOf("t") + 1, string1.indexOf("w"));
                    weight = string1.substring(string1.indexOf("w") + 1, string1.indexOf("p"));
                    heart_rate = string1.substring(string1.indexOf("p") + 1, string1.indexOf("s"));
                    speed = string1.substring(string1.indexOf("s") + 1, string1.indexOf("z"));
                    distance2 = Integer.parseInt(speed);
                    text3.setText("骑行圈数 " + distance2);
                    text4.setText(heart_rate + "跳/分");
                    text5.setText(weight + " 千克");
                    text6.setText(temperature + " 度");
                    seekBar.setProgress(distance2);
                    smsg = "";
                    break;

                }

                case 0x112: {
                    image1.setImageResource(imageId1[imagecount1 % imageId1.length]);
                    //  image2.setImageResource(imageId2[imagecount2%imageId2.length]);
                    //image3.setImageResource(imageId3[imagecount3%imageId3.length]);
                    break;

                }

            }

            //dis.setText(smsg);   //显示数据
            //sv.scrollTo(0,dis.getMeasuredHeight()); //跳至数据最后一页
        }
    };

    //关闭程序掉用处理部分
    public void onDestroy() {
        super.onDestroy();
        if (speaker1 != null) {
            speaker1.stop();
        }
        if (speaker3 != null) {
            speaker3.stop();
        }
        if (tts != null) {
            tts.shutdown();
        }
        if (timer!=null){
            timer.cancel();
        }
        if (timer2!=null){
            timer2.cancel();
        }
        if (_socket != null)  //关闭连接socket
            try {
                _socket.close();
            } catch (IOException e) {
            }
        _bluetooth.disable();  //关闭蓝牙服务
    }

    //连接按键响应函数
    public void onConnectButtonClicked(View v) {
        if (_bluetooth.isEnabled() == false) {  //如果蓝牙服务不可用则提示
            Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
            return;
        }


        //如未连接设备则打开DeviceListActivity进行设备搜索
        ImageButton btn = (ImageButton) findViewById(R.id.image_btn4);
        if (_socket == null) {
            Intent serverIntent = new Intent(this, DeviceList.class); //跳转程序设置
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
        } else {
            //关闭连接socket
            try {

                is.close();
                _socket.close();
                _socket = null;
                bRun = false;
                // btn.setText("连接");
            } catch (IOException e) {
            }
        }
        return;
    }


    private void initVariable() {
        mTotalProgress = 1000;
        mCurrentProgress = 0;
    }

    private void initView() {
        speaker1 = new TexttoSpeak(this);
        speaker2 = new TexttoSpeak(this);
        speaker3 = new TexttoSpeak(this);
        circleProgress = (CircleProgress) findViewById(R.id.task_view);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        //text13 = (TextView) findViewById(R.id.text13);
        image_btn1 = (ImageButton) findViewById(R.id.image_btn1);
        image_btn3 = (ImageButton) findViewById(R.id.image_btn3);
        image_btn4 = (ImageButton) findViewById(R.id.image_btn4);

    }


    public void setBtnClick() {
        image_btn1.setOnClickListener(click);
        image_btn3.setOnClickListener(click);
        image_btn4.setOnClickListener(click);

    }

    public class myBtnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_btn1: {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Person.class);
                    startActivity(intent);


                }

                case R.id.image_btn3: {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, number.class);
                    startActivity(intent);
                    break;

                }
                case R.id.image_btn4: {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PastRecord.class);
                    startActivity(intent);
                    break;
                }

            }
        }
    }

    private void createTable() {
        db.execSQL("create table if not exists Data_list(Distance varchar(20),heart_rate)");

    }


    private void getDB() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            db = SQLiteDatabase.openOrCreateDatabase(file + File.separator + "login.db4", null);


        }
    }
//    class myRunnable implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                Message message = Message.obtain();
//                message.what = 0x111;
//                message.obj = "at33.0w123.00p122s88z";
//                handler.sendMessage(message);
//
//
//            }
//
//        }
//    }





}



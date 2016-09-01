package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    EditText edit_1;
    Button btn_1;
    TextView txt;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    ImageView image1;
    DatagramSocket datagramSocket = null;
    DatagramPacket datagramPacket = null;
    String temp = null;
    String heart_rate = null;
    String weight = null;
    String speed = null;
    InputStream inputStream;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        edit_1 = (EditText) findViewById(R.id.edit_1);
        btn_1 = (Button) findViewById(R.id.btn_1);
        txt = (TextView) findViewById(R.id.txt);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 = (Button)findViewById(R.id.btn_4);
        image1 = (ImageView)findViewById(R.id.image1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName("192.168.1.103");
                    datagramSocket = new DatagramSocket(10025);
                    byte[] buffer = edit_1.getText().toString().getBytes();
                    datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 10025);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                datagramSocket.send(datagramPacket);
                                Log.v("msg", "发送数据");
                                datagramSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }


            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = edit_1.getText().toString();
                if (string.startsWith("a") && string.endsWith("z")) {
                    temp = string.substring(string.indexOf("t")+1, string.indexOf("p"));
                    heart_rate = string.substring(string.indexOf("p")+1, string.indexOf("w"));
                    weight = string.substring(string.indexOf("w")+1, string.indexOf("s"));
                    speed = string.substring(string.indexOf("s")+1, string.indexOf("z"));

                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(temp+"\n");
                stringBuilder.append(heart_rate+"\n");
                stringBuilder.append(weight+"\n");
                stringBuilder.append(speed);
                String string2 = stringBuilder.toString();
                txt.setText(string2);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        URL url = null;
                        try {
                            url = new URL("http://192.168.1.106:8080/test/a.jpg");
                            URLConnection urlConnection = url.openConnection();
                            inputStream = urlConnection.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("msg","获取图片");
                                    image1.setImageBitmap(bitmap);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }.start();
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    socket = new Socket("192.168.56.1",4567);
                    OutputStream outputStream = socket.getOutputStream();
                    byte[] bytes = edit_1.getText().toString().getBytes();
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

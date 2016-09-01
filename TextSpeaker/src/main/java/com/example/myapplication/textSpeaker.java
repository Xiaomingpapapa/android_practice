package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by 傻明也有春天 on 2016/7/17.
 */
public class textSpeaker extends Activity {
    TexttoSpeak speaker=null;
    EditText edit_1=null;
    Button btn_1=null;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textspeak);
        init();
        tts = new TextToSpeech(textSpeaker.this,new TTSListener());
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = edit_1.getText().toString();
                sayHello(string);
            }
        });


    }
    public  void init(){
        speaker = new TexttoSpeak(this);
        edit_1=(EditText)findViewById(R.id.edit_1);
        btn_1=(Button)findViewById(R.id.btn_1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(speaker!=null){
            speaker.stop();
        }
        if (tts!=null){
            tts.shutdown();
        }
    }
    public void sayHello(String string){
        speaker.speak(string);
    }
    private class TTSListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (status == TextToSpeech.SUCCESS) {
                //int result = mSpeech.setLanguage(Locale.ENGLISH);
                int result = tts.setLanguage(Locale.ENGLISH);
                //如果打印为-2，说明不支持这种语言
                Toast.makeText(textSpeaker.this, "-------------result = " + result, Toast.LENGTH_LONG).show();
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    System.out.println("-------------not use");
                } else {
                    sayHello("welcome to this app");
                    //tts.speak("i love you", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }

    }
}

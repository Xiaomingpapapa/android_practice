package com.example.myapplication;

/**
 * Created by 傻明也有春天 on 2016/7/17.
 */

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TexttoSpeak {
    private Context context;
    private TextToSpeech tts;

    public TexttoSpeak(final Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.CHINA);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, "Language is not available.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }
    public void stop(){
        if (tts!=null)
            tts.shutdown();
    }
}

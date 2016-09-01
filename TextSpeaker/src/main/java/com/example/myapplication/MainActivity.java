package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 根据选择的语言播报TTS
 *
 * @author Administrator
 *
 */
public class MainActivity extends Activity {

    private TextToSpeech mSpeech = null;
    private Spinner langSpinner = null;
    private EditText edit = null;
    private Button btn = null;
    private String[] langs;
    private String curLang;
    private List<String> langList = new ArrayList<String>();
    private ArrayAdapter<String> langAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        langs = getResources().getStringArray(R.array.languages); // 得到语言数组
        langSpinner = (Spinner) findViewById(R.id.spinner);
        edit = (EditText) findViewById(R.id.edit);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new BtnListener());

        for (int i = 0; i < langs.length; i++) {
            langList.add(langs[i]);
        }
        // 设置下拉框的适配器和样式
        langAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, langList);
        langAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(langAdapter);

        // 下拉框监听器
        langSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapter,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        curLang = (String) langSpinner.getAdapter().getItem(
                                (int) id);
                        if(mSpeech != null)
                        {
                            mSpeech.stop();
                            mSpeech.shutdown();
                            mSpeech = null;
                        }
                        // 创建TTS对象
                        mSpeech = new TextToSpeech(MainActivity.this, new TTSListener());
                        Toast.makeText(MainActivity.this, "select = " + curLang, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    private int SetLanguage(String lang) {
        int result = 0;
        if (lang.equals("CANADA")) {
            result = mSpeech.setLanguage(Locale.CANADA);
        } else if (lang.equals("CANADA_FRENCH")) {
            result = mSpeech.setLanguage(Locale.CANADA_FRENCH);
        } else if (lang.equals("CHINA")) {
            result = mSpeech.setLanguage(Locale.CHINA);
        } else if (lang.equals("CHINESE")) {
            result = mSpeech.setLanguage(Locale.CHINESE);
        } else if (lang.equals("ENGLISH")) {
            result = mSpeech.setLanguage(Locale.ENGLISH);
        } else if (lang.equals("FRANCE")) {
            result = mSpeech.setLanguage(Locale.FRANCE);
        } else if (lang.equals("FRENCH")) {
            result = mSpeech.setLanguage(Locale.FRENCH);
        } else if (lang.equals("GERMAN")) {
            result = mSpeech.setLanguage(Locale.GERMAN);
        } else if (lang.equals("GERMANY")) {
            result = mSpeech.setLanguage(Locale.GERMANY);
        } else if (lang.equals("ITALIAN")) {
            result = mSpeech.setLanguage(Locale.ITALIAN);
        } else if (lang.equals("ITALY")) {
            result = mSpeech.setLanguage(Locale.ITALY);
        } else if (lang.equals("JAPAN")) {
            result = mSpeech.setLanguage(Locale.JAPAN);
        } else if (lang.equals("JAPANESE")) {
            result = mSpeech.setLanguage(Locale.JAPANESE);
        } else if (lang.equals("KOREA")) {
            result = mSpeech.setLanguage(Locale.KOREA);
        } else if (lang.equals("KOREAN")) {
            result = mSpeech.setLanguage(Locale.KOREAN);
        } else if (lang.equals("PRC")) {
            result = mSpeech.setLanguage(Locale.PRC);
        } else if (lang.equals("ROOT")) {
            result = mSpeech.setLanguage(Locale.ROOT);
        } else if (lang.equals("SIMPLIFIED_CHINESE")) {
            result = mSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);
        } else if (lang.equals("TAIWAN")) {
            result = mSpeech.setLanguage(Locale.TAIWAN);
        } else if (lang.equals("TRADITIONAL_CHINESE")) {
            result = mSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
        } else if (lang.equals("UK")) {
            result = mSpeech.setLanguage(Locale.UK);
        } else if (lang.equals("US")) {
            result = mSpeech.setLanguage(Locale.US);
        }
        return result;
    }

    private class TTSListener implements OnInitListener {

        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (status == TextToSpeech.SUCCESS) {
                //int result = mSpeech.setLanguage(Locale.ENGLISH);
                int result = SetLanguage(curLang);
                //如果打印为-2，说明不支持这种语言
                Toast.makeText(MainActivity.this, "-------------result = " + result, Toast.LENGTH_LONG).show();
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    System.out.println("-------------not use");
                } else {
                    mSpeech.speak("i love you", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }

    }

    private class BtnListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mSpeech.speak(edit.getText().toString(), TextToSpeech.QUEUE_FLUSH,
                    null);
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
        super.onDestroy();
    }

}

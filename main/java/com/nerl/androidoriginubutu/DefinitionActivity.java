package com.nerl.androidoriginubutu;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class DefinitionActivity extends AppCompatActivity {

    TextView tvWord, tvDef;
    WebView wvDef;
    Button btnSpeak;
    TextToSpeech tts1;
    String word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        tvWord  = (TextView)  findViewById(R.id.tv_word);
        tvDef = (TextView) findViewById(R.id.tv_def);
        wvDef = (WebView) findViewById(R.id.wv_def);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        Intent intent = getIntent();
        word = intent.getStringExtra("word");
        tvWord.setText(word);
        DatabaseAccess dbAccess  = DatabaseAccess.getInstance(this);
        dbAccess.open();

        String definition = dbAccess.getDefinition(word);

        dbAccess.close();

        tvDef.setText(definition);
        String data  = "<html><head><style>*{max-width:100%}</style></head><body>"+definition+"</body></html>";
        wvDef.loadData(data, "text/html; charset=utf-8", "utf-8");

        tts1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts1.setLanguage(Locale.ENGLISH);

                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //chay ham tts1
                tts1.speak(word, TextToSpeech.QUEUE_FLUSH, null );
            }
        });



    }
}

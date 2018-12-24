package com.nerl.androidoriginubutu;

import android.content.Intent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LearningActivity extends AppCompatActivity {
    TextView tvWord, tvPhatAm, tvDiem;
    Button btnNhanDang;
    SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        tvDiem = (TextView) findViewById(R.id.tvDiem);
        tvPhatAm =(TextView) findViewById(R.id.tvPhatAm);
        tvWord =(TextView) findViewById(R.id.tvWord);
        btnNhanDang =(Button) findViewById(R.id.btnNhanDang);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String word = ranDomWord();
                String [] words =word.split(",");
                String word1 = words[0];
                String word2 = words[1];
                Toast.makeText(LearningActivity.this, ""+word1, Toast.LENGTH_SHORT).show();
                Toast.makeText(LearningActivity.this, ""+word2, Toast.LENGTH_SHORT).show();
                tvWord.setText(word1);
                tvPhatAm.setText(word2);
            }
        });

//        speechRecognizer = new SpeechRecognizer(getApplicationContext(), new );

        btnNhanDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, SpeakActivity.class);
                startActivity(intent);
            }
        });



    }

    public String ranDomWord(){
        DatabaseAccess dbAccess  = DatabaseAccess.getInstance(this);
        dbAccess.open();

        String wordRandom = dbAccess.ranDomWord();

        dbAccess.close();
        return wordRandom;
    }
}

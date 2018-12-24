package com.nerl.androidoriginubutu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ListView lvWords;
    DatabaseAccess dbAccess;
    Button btnSearch, btnSpeak;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lvWords =(ListView) findViewById(R.id.lv_words);
        btnSearch = (Button) findViewById(R.id.btn_search);

        edtSearch = (EditText) findViewById(R.id.edt_search);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtSearch.getText())){
                    Toast.makeText(Main2Activity.this, "Nhap du lieu!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String txtSearch = edtSearch.getText().toString().trim();

                    try{
                        dbAccess = new DatabaseAccess(Main2Activity.this);
                        dbAccess.open();
                    }catch (Exception e){
                        Log.e("kiemtra",e.getMessage());
                    }

                    ArrayList<String> words = dbAccess.getWords(txtSearch);

                    dbAccess.close();

                    //load du lieu len listView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, words);

                    lvWords.setAdapter(adapter);
                    lvWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedWord = lvWords.getItemAtPosition(position).toString();

                            Intent intent = new Intent(Main2Activity.this, DefinitionActivity.class);
                            intent.putExtra("word", selectedWord);
                            startActivity(intent);
                        }
                    });
                }
            }
        });





    }
}

package com.nerl.androidoriginubutu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtAge, edtAddress;
    Button btnAdd, btnChuyen;
    ListView lvStudents;
    List<String> listStudents = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName =(EditText) findViewById(R.id.edt_name);
        edtAge =(EditText) findViewById(R.id.edt_age);
        edtAddress =(EditText) findViewById(R.id.edt_address);
        btnAdd =(Button) findViewById(R.id.btn_add);
        btnChuyen =(Button) findViewById(R.id.btn_chuyen);

        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        lvStudents =(ListView) findViewById(R.id.lv_students);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listStudents);

        lvStudents.setAdapter(adapter);

        try{
            StudentDatabaseHelper studentDB = new StudentDatabaseHelper(getApplicationContext());
            SQLiteDatabase db = studentDB.getReadableDatabase();

            /*Duyet het databse va hien thi len man hinh database*/
            Cursor cursor = db.query("student", new String[]{"name", "age", "address"}, null, null, null, null, "_id DESC", null);

            listStudents.clear();
            while (cursor.moveToNext()){
                //Toast.makeText(MainActivity.this, "okk", Toast.LENGTH_SHORT).show();
                String name1 = cursor.getString(0);
                int age1 = cursor.getInt(1);
                String address1 = cursor.getString(2);

                listStudents.add("Ten: "+name1+"\n"+"Tuoi: "+String.valueOf(age1)+"\n"+"Dia chi: "+address1+"\n");

            }
            adapter.notifyDataSetChanged();
            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtName.getText()) == false && TextUtils.isEmpty(edtAge.getText()) == false && TextUtils.isEmpty(edtAddress.getText()) == false){
                    String name = edtName.getText().toString().trim();
                    String age = edtAge.getText().toString().trim();
                    String address = edtAddress.getText().toString().trim();
                    int ageInt = Integer.parseInt(age);
                    if(ageInt > 99 || ageInt < 1){
                        Toast.makeText(MainActivity.this, "Tuoi phai tu 1->99", Toast.LENGTH_SHORT).show();
                    }else {
                        //Student student = new Student(name, age, address);
                        //listStudents.add("Ten: "+name+"\n"+"Tuoi: "+age+"\n"+"Dia chi: "+address+"\n");
                        //adapter.notifyDataSetChanged();

                        try{
                            StudentDatabaseHelper studentDB = new StudentDatabaseHelper(getApplicationContext());
                            SQLiteDatabase db = studentDB.getReadableDatabase();
                            /*insert 1 ban ghi vao CSDL*/
                            studentDB.insert(db, name, Integer.parseInt(age), address);

                            /*Duyet het databse va hien thi len man hinh database*/
                            Cursor cursor = db.query("student", new String[]{"name", "age", "address"}, null, null, null, null, "_id DESC", null);

                            listStudents.clear();
                            while (cursor.moveToNext()){
                                //Toast.makeText(MainActivity.this, "okk", Toast.LENGTH_SHORT).show();
                                String name1 = cursor.getString(0);
                                int age1 = cursor.getInt(1);
                                String address1 = cursor.getString(2);

                                listStudents.add("Ten: "+name1+"\n"+"Tuoi: "+String.valueOf(age1)+"\n"+"Dia chi: "+address1+"\n");

                            }
                            adapter.notifyDataSetChanged();
                            cursor.close();
                            db.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }

                }else{
                    Toast.makeText(MainActivity.this, "Vui long nhap du lieu truoc khi them", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}

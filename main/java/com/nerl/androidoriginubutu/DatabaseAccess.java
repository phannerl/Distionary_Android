package com.nerl.androidoriginubutu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseAccess  {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();

    }

    public void close(){
        if(database != null){
            this.database.close();
        }
    }

    public ArrayList<String> getWords(){
        ArrayList<String> list =new  ArrayList<String>();
        Cursor cursor = database.rawQuery("select * from anh_viet limit 10", null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(1));
        }

        cursor.close();
        return list;
    }

    public ArrayList<String> getWords(String filter){
        ArrayList<String> list =new  ArrayList<String>();
        Cursor cursor = database.rawQuery("select * from anh_viet where word like '"+filter+"%'", null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(1));
        }

        cursor.close();
        return list;
    }
    public String getDefinition (String word){
        String definition = "";
        Cursor cursor = database.rawQuery("select * from anh_viet where word='"+word+"'", null);
        if(cursor.moveToFirst()){
            definition = cursor.getString(2);
        }

        cursor.close();
        return definition;
    }

    public String ranDomWord(){
        String words = "";
        Cursor cursor = database.rawQuery("select * from anh_viet limit 1000", null);
        Random rand = new Random();
        int i = rand.nextInt(1000);
        int dem=0;
        while(cursor.moveToNext()){
            if(dem == i){

                String word1 = cursor.getString(1);
                String word2 = cursor.getString(2);
                return word1+","+word2;
            }

            dem++;
        }
        return words;

    }


}

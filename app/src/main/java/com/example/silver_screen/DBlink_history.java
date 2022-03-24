package com.example.silver_screen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBlink_history extends SQLiteOpenHelper {
    public static final String DBNAME = "history.db";

    public DBlink_history(Context context) {

        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Records(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, movie TEXT,theater TEXT, time TEXT, no_ticket TEXT, type TEXT, Date Text, Booking_id TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Records");
    }

    public Boolean insert_value(String Movie_name,String Theater_name, String Time, String Tickets_no, String type, String booking_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put("movie",Movie_name);
        values.put("theater",Theater_name);
        values.put("time", Time);
        values.put("no_ticket", Tickets_no);
        values.put("type", type);
        values.put("Date", date);
        values.put("Booking_id", booking_id);
        long result = db.insert("Records",null,values);

        return result != -1;

    }
    public  Boolean check_movie(String Movie_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Records WHERE movie = ?",new String[]{Movie_name});

        return cursor.getCount() > 0;
    }

    public  String get_movie(String Movie_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Records WHERE movie = ?",new String[]{Movie_name});
        String out, a, b, c, d;
        a = cursor.getString(0);
        b = cursor.getString(1);
        c = cursor.getString(2);
        d = cursor.getString(3);

        out = a + "\n" + b+ "\n" + c+ "\n" + d;
        return out;
    }

    public  Cursor get_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Records",null);

        return cursor;
    }


}

package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class History_page extends AppCompatActivity{

    ListView list;
    DBlink_history db;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        list = findViewById(R.id.history_list);
        db = new DBlink_history(this);
        btn = findViewById(R.id.history_refresh);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> history= new ArrayList<>();

                Cursor cursor = db.get_all();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String Movie_name = cursor.getString(1);
                        String Theater_name = cursor.getString(2);
                        String Time = cursor.getString(3);
                        String Tickets_no = cursor.getString(4);
                        String type = cursor.getString(5);
                        history.add(Movie_name+"\n"+ Theater_name+"\n"+ Time+"\n"+Tickets_no+"\n"+type);

                        cursor.moveToNext();
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, history);
                list.setAdapter(adapter);

            }
        });



    }

}
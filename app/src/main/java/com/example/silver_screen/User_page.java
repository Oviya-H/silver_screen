package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_page extends AppCompatActivity {

    Button user_edit;
    TextView tv;
    DBlink DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        user_edit = findViewById(R.id.user_edit);
        tv = findViewById(R.id.user_text);
        DB = new DBlink(this);

        Cursor c = DB.get_details();
        c.moveToFirst();


        SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
        String name_sp = sf.getString("Name", c.getString(3));
        String email_sp = sf.getString("Email", c.getString(1));
        String phone_sp = sf.getString("Phone", c.getString(0));
        String city_sp = sf.getString("City", c.getString(4));

        String out = "Name :" +name_sp+ "\n" + "Email  :"+ email_sp + "\n" + "Phone No :" + phone_sp + "\n" + "City  :" + city_sp;
        tv.setText(out);

        user_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u_edit = new Intent(getApplicationContext(), User_edit_page.class);
                startActivity(u_edit);
            }
        });
    }
}
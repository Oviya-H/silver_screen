package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_page extends AppCompatActivity {

    Button pro_setting, pro_history, pro_payment, pro_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        pro_setting = findViewById(R.id.profile_settings);
        pro_history = findViewById(R.id.profile_history);
        pro_payment = findViewById(R.id.profile_payments);
        pro_user = findViewById(R.id.profile_user);

        pro_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(getApplicationContext(), setting_page.class);
                startActivity(setting);
            }
        });

        pro_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(getApplicationContext(), History_page.class);
                startActivity(history);
            }
        });



        pro_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payment = new Intent(getApplicationContext(), Payment_page.class);
                startActivity(payment);
            }
        });

        pro_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(getApplicationContext(), User_page.class);
                startActivity(user);
            }
        });
    }
}
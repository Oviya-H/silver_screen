package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class reset_password extends AppCompatActivity {

    Button r_reset, r_login;
    EditText r_password, r_confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        r_reset = findViewById(R.id.reset_bt);
        r_login = findViewById(R.id.reset_login);
        r_password = findViewById(R.id.reset_password);
        r_confirm_password = findViewById(R.id.reset_confirm_password);

        r_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetlogin = new Intent(getApplicationContext(), login_page.class);
                startActivity(resetlogin);
            }
        });

    }
}
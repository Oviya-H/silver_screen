package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Forgot_password extends AppCompatActivity {

    Button f_send, f_verify, f_next;
    EditText f_mail, f_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        f_send = findViewById(R.id.forgot_send_otp_bt);
        f_verify = findViewById(R.id.forgot_verify_otp_bt);
        f_next = findViewById(R.id.forgot_next_otp_bt);
        f_mail = findViewById(R.id.forgot_mail);
        f_otp = findViewById(R.id.forgot_otp);

        f_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(getApplicationContext(), reset_password.class);
                startActivity(reset);
            }
        });

    }
}
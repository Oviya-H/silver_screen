package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Phone_verification extends AppCompatActivity {

    Button pv_bt, pv_reset;
    EditText pv_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        pv_bt = findViewById(R.id.phoneVerify_bt);
        pv_reset = findViewById(R.id.phoneVerify_resent);
        pv_otp = findViewById(R.id.phoneVerify_opt);
    }
}
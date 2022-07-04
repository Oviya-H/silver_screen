package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forgot_password extends AppCompatActivity implements SMSlistener{

    Button f_send, f_verify;
    EditText f_mail, f_otp;
    MyReciever myReceiver = new MyReciever();
    String phoneNO;
    int min = 1000;
    int max = 9999;

    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
    String message = String.format("your otp is %d",random_int);
    public static final String OTP_REGEX = "[0-9]{1,6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        f_send = findViewById(R.id.forgot_send_otp_bt);
        f_verify = findViewById(R.id.forgot_verify_otp_bt);
        f_mail = findViewById(R.id.forgot_mail);
        f_otp = findViewById(R.id.forgot_otp);

        f_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNO = f_mail.getText().toString();
                send_sms(phoneNO);
            }
        });

        f_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = f_otp.getText().toString();
                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp = "XXXXX";
                while (matcher.find()) {
                    otp = matcher.group();
                }

                if (random_int == Integer.parseInt(otp)) {
                    Toast.makeText(getApplicationContext(), "otp verification Done", Toast.LENGTH_SHORT).show();
                    Intent reset = new Intent(getApplicationContext(), reset_password.class);
                    reset.putExtra("phone", phoneNO);
                    startActivity(reset);
                }

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter ("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myReceiver);
    }


    @Override
    public void messageReceived(String messageText, String senderno) {
        Pattern pattern = Pattern.compile(OTP_REGEX);
        Matcher matcher = pattern.matcher(messageText);
        String otp = "XXXXX";
        while (matcher.find()) {
            otp = matcher.group();
        }
        Toast.makeText(this,"OTP Received is: "+ otp ,Toast.LENGTH_LONG).show();

        f_otp.setText(otp);

        if (random_int == Integer.parseInt(otp)){
            Toast.makeText(this,"Number Verified!!!!" ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), Register_page.class);
            startActivity(intent);
        }
    }

    private void send_sms(String number) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        }


        String phoneNo = "+91"+number;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(),"Sms send to "+phoneNo,Toast.LENGTH_LONG).show();

    }
}
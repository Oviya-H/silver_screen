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

public class Phone_verification extends AppCompatActivity implements SMSlistener{

    Button pv_bt, pv_reset;
    EditText pv_otp;
    MyReciever myReceiver = new MyReciever();
    int min = 1000;
    int max = 9999;

    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
    String message = String.format("your otp is %d",random_int);
    public static final String OTP_REGEX = "[0-9]{1,6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        pv_bt = findViewById(R.id.phoneVerify_bt);
        pv_reset = findViewById(R.id.phoneVerify_resent);
        pv_otp = findViewById(R.id.phoneVerify_opt);


        String phoneNO = getIntent().getStringExtra("phoneno");
        send_sms(phoneNO);

        pv_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = pv_otp.getText().toString();
                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp = "XXXXX";
                while (matcher.find()) {
                    otp = matcher.group();
                }

                if (random_int == Integer.parseInt(otp)) {
                    Toast.makeText(getApplicationContext(), "verification Done", Toast.LENGTH_SHORT).show();
                    SharedPreferences sf = getSharedPreferences("otp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sf.edit();
                    edit.putBoolean("done", true);
                    edit.apply();
                    Intent intent = new Intent(getApplicationContext(), Register_page.class);
                    intent.putExtra("phone_no", phoneNO);
                    startActivity(intent);
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

        pv_otp.setText(otp);

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
package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_page extends AppCompatActivity {

    Button l_google, l_facebook, login, l_forgotpassword, l_signup;
    EditText l_phone, l_password;
    DBlink DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        l_google = findViewById(R.id.regsiter_google);
        l_facebook = findViewById(R.id.regsiter_facebook);
        login = findViewById(R.id.login_bt);
        l_forgotpassword = findViewById(R.id.login_forgetPassword);
        l_signup = findViewById(R.id.login_newAccount);
        l_phone = findViewById(R.id.login_mail);
        l_password = findViewById(R.id.register_mail);
        DB = new DBlink(this);

        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sf.edit();

        l_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(getApplicationContext(), Forgot_password.class);
                startActivity(forgot);
            }
        });

        l_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_page.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = l_phone.getText().toString();
                String pass = l_password.getText().toString();
                Integer intValue;

                if (mobile.trim().equals("") || pass.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"Enter values",Toast.LENGTH_SHORT).show();
                else{

                    Boolean correct = DB.check_mobile_pass(mobile, pass);
                    Boolean correct_email = DB.check_email_pass(mobile, pass);
                    if(correct || correct_email){
                        Toast.makeText(getApplicationContext(),"Logged In!!", Toast.LENGTH_SHORT).show();
                        edit.putBoolean("Register Bool",true);
                        edit.apply();
                        Intent intent = new Intent(getApplicationContext(), Home_page.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Username or password", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

}
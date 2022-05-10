package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login_page extends AppCompatActivity {

    Button l_google, l_facebook, login, l_forgotpassword, l_signup;
    EditText l_phone, l_password;
    DBlink DB;
    Boolean correct;

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

        String login_url = "http://localhost:5000/api/users";

        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);


        SharedPreferences sf = getSharedPreferences("Verify", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();

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
                    Toast.makeText(getApplicationContext(), "Enter values", Toast.LENGTH_SHORT).show();
                else {
                    dialog.show();

                    StringRequest request = new StringRequest(login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response != null) {
                                dialog.dismiss();

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    correct = checkUser(jsonArray, mobile, pass);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    if (correct) {
                        Toast.makeText(getApplicationContext(), "Logged In!!", Toast.LENGTH_SHORT).show();
                        edit.putBoolean("Register Bool", true);
                        edit.apply();
                        Intent intent = new Intent(getApplicationContext(), Home_page.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username or password", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

    private Boolean checkUser(JSONArray jsonArray, String details, String pass) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                String phone = object.getString("phone");
                String email = object.getString("email");


            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;

    }
}
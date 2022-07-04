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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reset_password extends AppCompatActivity {

    Button r_reset;
    EditText r_password, r_confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        r_reset = findViewById(R.id.reset_bt);
        r_password = findViewById(R.id.reset_password);
        r_confirm_password = findViewById(R.id.reset_confirm_password);

        String PHONE = getIntent().getStringExtra("phone");

        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);


        r_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                String pass = r_password.getText().toString();
                String repass = r_confirm_password.getText().toString();

                if(pass.equals("") || repass.equals("")){
                    Toast.makeText(getApplicationContext(), "Fill all values!", Toast.LENGTH_SHORT).show();

                }else {
                    if (pass.equals(repass)){
                        String url = getString(R.string.reset);

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("phone", PHONE);
                        params.put("password", pass);


                        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Toast.makeText(getApplicationContext(),"Password Changed Successfully!",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                        Intent home_intent = new Intent(getApplicationContext(),login_page.class);
                                        startActivity(home_intent);
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                        requestQueue.add(jsonObjectRequest);
                    }
                }

            }
        });


    }
}
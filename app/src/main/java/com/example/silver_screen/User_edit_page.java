package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class User_edit_page extends AppCompatActivity {

    AutoCompleteTextView edit_city;
    EditText e_name, e_email, e_phone;
    Button save;

    String[] city = {"Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram",
            "Kanyakumari", "Karur", "Madurai", "Nagapattinam", "Nilgiris", "Namakkal", "Perambalur", "Pudukkottai",
            "Ramanathapuram", "Salem", "Sivaganga", "Tirupur", "Tiruchirappalli", "Theni", "Tirunelveli", "Thanjavur",
            "Thoothukudi", "Tiruvallur", "Tiruvarur", "Tiruvannamalai", "Vellore", "Viluppuram", "Virudhunagar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_page);

        edit_city = findViewById(R.id.userEdit_city);
        e_name = findViewById(R.id.userEdit_name);
        e_email = findViewById(R.id.userEdit_email);
        e_phone = findViewById(R.id.userEdit_phone);
        save = findViewById(R.id.userEdit_save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check;
                check = isValid(e_email.getText().toString());

                if(!check){
                    Toast.makeText(getApplicationContext(),"Enter a Valid Email", Toast.LENGTH_SHORT).show();

                }
                else if(e_phone.getText().toString().length() != 10){
                    Toast.makeText(getApplicationContext(),"Enter a Valid Phone number", Toast.LENGTH_SHORT).show();
                }
                else if(e_name.getText().toString().trim().equals("") ||e_email.getText().toString().trim().equals("") ||e_phone.getText().toString().trim().equals("") ||edit_city.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all the values", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Account saved successfully !!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sf.edit();

                    edit.putString("Name", e_name.getText().toString());
                    edit.putString("Email", e_email.getText().toString());
                    edit.putString("Phone", e_phone.getText().toString());
                    edit.putString("City", edit_city.getText().toString());
                    edit.apply();
                    Intent intent = new Intent(getApplicationContext(), User_page.class);
                    startActivity(intent);

                }



            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, city);
        edit_city.setAdapter(adapter);
        edit_city.setThreshold(1);


        edit_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edit_city.showDropDown();
                return false;
            }
        });


    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
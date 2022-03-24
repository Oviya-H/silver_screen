package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_page extends AppCompatActivity {

    Button reg_bt, reg_phoneVerify;
    EditText reg_name, reg_phone, reg_mail, reg_password, reg_re_password;
    AutoCompleteTextView reg_city;
    DBlink DB;


    String[] cities = {"Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram",
            "Kanyakumari", "Karur", "Madurai", "Nagapattinam", "Nilgiris", "Namakkal", "Perambalur", "Pudukkottai",
            "Ramanathapuram", "Salem", "Sivaganga", "Tirupur", "Tiruchirappalli", "Theni", "Tirunelveli", "Thanjavur",
            "Thoothukudi", "Tiruvallur", "Tiruvarur", "Tiruvannamalai", "Vellore", "Viluppuram", "Virudhunagar"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        reg_bt = findViewById(R.id.register_bt);
        reg_phoneVerify = findViewById(R.id.register_phoneVerify);
        reg_name = findViewById(R.id.register_name);
        reg_phone = findViewById(R.id.register_phone);
        reg_mail = findViewById(R.id.register_mail);
        reg_city = findViewById(R.id.register_city);
        reg_password = findViewById(R.id.register_password);
        reg_re_password = findViewById(R.id.re_register_password);
        DB = new DBlink(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, cities);
        reg_city.setAdapter(adapter);
        reg_city.setThreshold(1);


        reg_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reg_city.showDropDown();
                return false;
            }
        });

        reg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = reg_name.getText().toString();
                String phone = reg_phone.getText().toString();
                String email = reg_mail.getText().toString();
                String city = reg_city.getText().toString();
                String password = reg_password.getText().toString();
                String repass = reg_re_password.getText().toString();

                if (username.trim().equals("") || phone.trim().equals("") || email.trim().equals("") || city.trim().equals("") || password.trim().equals("") || repass.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"fill all values",Toast.LENGTH_SHORT).show();

                else{

                    if (password.equals(repass)){
                        Boolean checkuser = DB.check_mobile(phone);
                        if(!checkuser){
                            Boolean insert = DB.insert_value(phone,email,password, username, city);
                            if(insert){
                                Toast.makeText(getApplicationContext(),"Registered Succesfully!",Toast.LENGTH_SHORT).show();

                                Intent home_intent = new Intent(getApplicationContext(),login_page.class);
                                startActivity(home_intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Registered Failed, Try again",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"User already Exist!!",Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Password Not match",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });



        reg_phoneVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneverify = new Intent(getApplicationContext(), Phone_verification.class);
                startActivity(phoneverify);
            }
        });



    }
}
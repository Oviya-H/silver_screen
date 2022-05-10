package com.example.silver_screen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

public class Booking_ticket extends AppCompatActivity {

    AutoCompleteTextView tickets, time, theater, type;
    Button submit;
    DBlink_history db;
    AlertDialog.Builder builder;

    String[] tickets_counts ={"1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    String[] timings = {"10:15 A.M", "1:30 P.M", "4:45 P.M", "9:00 P.M"};

    String[] theaters = {"INOX Prozone Mall", "The Cinema Brookefields ( SPI Cinemas )", "Fun Cinemas ( Cinépolis )",
            "KG Cinemas", "Karpagam Cinemas", "Archana Darsana Theatre (Baba Cinemas)"};

    String[] types = {"Budget - Rs.150", "Elite - Rs.210"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ticket);

        tickets = findViewById(R.id.booking_ticket_ed);
        time = findViewById(R.id.booking_time_ed);
        theater = findViewById(R.id.booking_theater_ed);
        type = findViewById(R.id.booking_type_ed);
        submit = findViewById(R.id.booking_submit);
        builder = new AlertDialog.Builder(this);

        ArrayAdapter<String> adapter_theater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, theaters);
        theater.setAdapter(adapter_theater);
        theater.setThreshold(1);


        theater.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                theater.showDropDown();
                return false;
            }
        });

        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, types);
        type.setAdapter(adapter_type);
        type.setThreshold(1);


        type.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                type.showDropDown();
                return false;
            }
        });

        ArrayAdapter<String> adapter_count = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, tickets_counts);
        tickets.setAdapter(adapter_count);
        tickets.setThreshold(1);


        tickets.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tickets.showDropDown();
                return false;
            }
        });

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, timings);
        time.setAdapter(adapter_time);
        time.setThreshold(1);


        time.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                time.showDropDown();
                return false;
            }
        });

        db = new DBlink_history(this);

        Bundle bundle = getIntent().getExtras();
        String movie_title = bundle.getString("Movie");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Ticket, Time, Theater, Type;

                Ticket = tickets.getText().toString();
                Time = time.getText().toString();
                Theater = theater.getText().toString();
                Type = type.getText().toString();

                if (Ticket.trim().equals("") || Time.trim().equals("") || Theater.trim().equals("") || movie_title.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"fill all values",Toast.LENGTH_SHORT).show();
                }
                else {
                    int amount;
                    if (Type.equals("Budget - Rs.150")){
                        amount = Integer.parseInt(Ticket)*150;
                    }else {
                        amount = Integer.parseInt(Ticket)* 210;
                    }


                    builder.setMessage("Do You want to proceed for payment?" + "\n" + "Total Amount : " + amount).setCancelable(false).setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String uniqueID = UUID.randomUUID().toString();

                            Boolean insert = db.insert_value(movie_title,Theater, Time, Ticket, Type, uniqueID);
                            if (insert){
                                Toast.makeText(getApplicationContext(),"Ticket Booked !! Enjoy the Movie !!",Toast.LENGTH_SHORT).show();
                                Intent home_intent = new Intent(getApplicationContext(),booking_receipt.class);
                                home_intent.putExtra("Title", movie_title);
                                home_intent.putExtra("Theater", Theater);
                                home_intent.putExtra("Time", Time);
                                home_intent.putExtra("Ticket", Ticket);
                                home_intent.putExtra("Type", Type);
                                home_intent.putExtra("Amount", amount);
                                home_intent.putExtra("ID", uniqueID);
                                startActivity(home_intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Booking Failed, Try again",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),"Booking Cancelled !!", Toast.LENGTH_SHORT).show();
                        }
                    });


                    AlertDialog alert = builder.create();
                    alert.setTitle("Amount Details !!");
                    alert.show();

                }



            }
        });



    }
}




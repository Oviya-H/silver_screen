package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class booking_receipt extends AppCompatActivity {


    TextView movie_title,Date,Time,theater_name,no_of_tickets,type_seats,booking_ID,amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_receipt);

        movie_title = findViewById(R.id.booking_receipt_movie);
        Date = findViewById(R.id.booking_receipt_date);
        Time = findViewById(R.id.booking_ticket_time);
        theater_name = findViewById(R.id.booking_receipt_theatre);
        no_of_tickets = findViewById(R.id.booking_receipt_no_of_tickets);
        type_seats = findViewById(R.id.booking_receipt_type_seats);
        booking_ID = findViewById(R.id.booking_receipt_id);
        amount = findViewById(R.id.booking_receipt_amount);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Bundle b = getIntent().getExtras();

        movie_title.setText(b.getString("Title"));
        Date.setText(date);
        Time.setText(b.getString("Time"));
        theater_name.setText(b.getString("Theater"));
        no_of_tickets.setText(b.getString("Ticket")+" Tickets");
        type_seats.setText(b.getString("Type"));
        booking_ID.setText(b.getString("ID"));

        String amt = Integer.toString(b.getInt("Amount"));

        amount.setText("Rs."+amt);





    }
}
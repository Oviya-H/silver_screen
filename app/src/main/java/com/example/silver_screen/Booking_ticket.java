package com.example.silver_screen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Booking_ticket extends AppCompatActivity implements PaymentResultListener {

    AutoCompleteTextView tickets, time, theater, type;
    Button submit, show;
    DBlink_history db;
    String Theater_ID, Show_ID, Seattype_ID, User_ID ;
    String movie_title;
    String uniqueID;
    int amount;
    String Ticket, Time, Theater, Type;
    AlertDialog.Builder builder;
    ArrayAdapter<String> adapter_theater, adapter_type,adapter_count, adapter_time;

    String[] tickets_counts ={"1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16","17", "18"};

    String[] timings = {"10:15 A.M", "1:30 P.M", "4:45 P.M", "9:00 P.M"};

    ArrayList<String> theaters_arr, theater_type_cost;

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
        show = findViewById(R.id.booking_show);
        builder = new AlertDialog.Builder(this);
        theaters_arr = new ArrayList<>();
        theater_type_cost = new ArrayList<>();

        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Getting Data ...");
        dialog.setCancelable(true);
        dialog.show();


        String url = getString(R.string.theater_route);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject responseObj = response.getJSONObject(i);
                        String Name = responseObj.getString("name");
                        theaters_arr.add(Name);
                    }
                    adapter_theater.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

        adapter_theater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, theaters_arr);
        theater.setAdapter(adapter_theater);
        theater.setThreshold(1);

        theater.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dialog.show();
                String url = getString(R.string.theater_route);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject responseObj = response.getJSONObject(i);
                                String Name = responseObj.getString("name");
                                if(Name.equals(theater.getText().toString())){
                                    Theater_ID = responseObj.getString("id");
                                    Toast.makeText(getApplicationContext(), Theater_ID, Toast.LENGTH_SHORT).show();
                                }
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonArrayRequest);
            }
        });


        theater.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                theater.showDropDown();
                return false;
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url2 = "https://dry-meadow-32049.herokuapp.com/api/shows/minimized";

                RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                dialog.show();

                final JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject responseObj = response.getJSONObject(i);
                                String Movie = responseObj.getString("movieName");

                                if(movie_title.equals(Movie)){
                                    JSONObject Name = responseObj.getJSONObject("seatType");
                                    String type = Name.getString("name");
                                    String type_cost = Name.getString("rate");
                                    theater_type_cost.add(type + "  -Rs . "+type_cost);
                                    Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                }

                            }
                            adapter_type.notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue2.add(jsonArrayRequest2);
            }
        });




        adapter_type = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, theater_type_cost);
        type.setAdapter(adapter_type);
        type.setThreshold(1);


        type.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                type.showDropDown();
                return false;
            }
        });

        adapter_count = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, tickets_counts);
        tickets.setAdapter(adapter_count);
        tickets.setThreshold(1);


        tickets.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tickets.showDropDown();
                return false;
            }
        });

        adapter_time = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, timings);
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
        movie_title = bundle.getString("Movie");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Ticket = tickets.getText().toString();
                Time = time.getText().toString();
                Theater = theater.getText().toString();
                Type = type.getText().toString();

                if (Ticket.trim().equals("") || Time.trim().equals("") || Theater.trim().equals("") || movie_title.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"fill all values",Toast.LENGTH_SHORT).show();
                }
                else {

                    if (Type.equals("Budget - Rs.150")){
                        amount = Integer.parseInt(Ticket)*150;
                    }else {
                        amount = Integer.parseInt(Ticket)* 210;
                    }


                    builder.setMessage("Do You want to proceed for payment?" + "\n" + "Total Amount : " + amount).setCancelable(false).setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            uniqueID = UUID.randomUUID().toString();

                            Boolean insert = db.insert_value(movie_title,Theater, Time, Ticket, Type, uniqueID);
                            if (insert){
                                Checkout checkout = new Checkout();
                                checkout.setKeyID("rzp_test_osmpOBeLDIrdM2");
                                checkout.setImage(R.drawable.rzp_logo);

                                JSONObject object = new JSONObject();

                                try {
                                    object.put("name", "Sample pay");
                                    object.put("description", "Test payment");
                                    object.put("theme.color", "#0093DD");
                                    object.put("currency", "INR");
                                    object.put("amount", amount*100);
                                    object.put("prefill.contact", "9994072206");
                                    object.put("prefill.email", "bala@gmail.com");

                                    checkout.open(Booking_ticket.this, object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

    @Override
    public void onPaymentSuccess(String s) {

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

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "payment error", Toast.LENGTH_SHORT).show();
    }
}




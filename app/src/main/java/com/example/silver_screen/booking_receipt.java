package com.example.silver_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class booking_receipt extends AppCompatActivity {


    TextView movie_title,Date,Time,theater_name,no_of_tickets,type_seats,booking_ID,amount;
    ImageView img;
    String imgint;
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
        img = findViewById(R.id.booking_receipt_iv);
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Getting Data ...");
        dialog.setCancelable(true);
        dialog.show();

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

        String url = getString(R.string.movie_route)+"/one";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", b.getString("Title"));

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("cast");
                            imgint = response.getString("poster");
                            Picasso.with(getApplicationContext()).load(imgint).into(img);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show(); 

                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
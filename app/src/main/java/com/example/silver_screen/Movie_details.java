package com.example.silver_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie_details extends AppCompatActivity implements movieItemListener{

    RecyclerView castList;
    Button btn;
    String description, rating;
    latest_list_adapter adapter2;
    List<Movie> cast_list;
    TextView descr, rate;
    String trailer, imgint;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        castList = findViewById(R.id.detailed_cast_list);
        TextView tv2 = findViewById(R.id.detailed_title);
        ImageView img = findViewById(R.id.detailed_img);
        descr = findViewById(R.id.detailed_description);
        youTubePlayerView = findViewById(R.id.video_player);
        btn = findViewById(R.id.detailed_booking_btn);
        rate = findViewById(R.id.detailed_rating);
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Getting Data ...");
        dialog.setCancelable(true);
        dialog.show();
        String url = getString(R.string.movie_route)+"/one";

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("Title");
        tv2.setText(title);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Booking_ticket.class);
                intent.putExtra("Movie", title);
                startActivity(intent);
            }
        });

        cast_list = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", title);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("cast");
                            description = response.getString("description");
                            rating = response.getString("rating");
                            trailer = response.getString("trailer");
                            imgint = response.getString("poster");
                            Picasso.with(getApplicationContext()).load(imgint).into(img);
                            rate.setText("Rating :" + rating);
                            descr.setText(description);
                            getLifecycle().addObserver(youTubePlayerView);

                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.loadVideo(trailer, 0);
                                }
                            });

                            for (int i=0; i<array.length(); i++){
                                JSONObject obj = array.getJSONObject(i);
                                String Name = obj.getString("actorName");
                                String photo = obj.getString("actorPhoto");
                                cast_list.add(new Movie(Name, photo));
                            }

                            adapter2.notifyDataSetChanged();
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

        //cast_list.add(new Movie("Ajith Kumar: as ACP Arjun Kumar", R.drawable.valimai));

        adapter2 = new latest_list_adapter(this, cast_list, this);
        castList.setAdapter(adapter2);

        castList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




    }


    @Override
    public void onMovieClick(Movie movie, ImageView movieImgview) {

    }
}
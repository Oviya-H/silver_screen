package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home_page extends AppCompatActivity implements  movieItemListener{

    private List<slide> lst_slide;
    private ViewPager slider_pager;
    private RecyclerView movieRV, picksRV;
    latest_list_adapter adapter1, adapter2;
    ArrayList<String> Movie_list;
    AutoCompleteTextView search;
    ArrayAdapter<String> adapter_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        slider_pager = findViewById(R.id.home_slider_page);
        movieRV = findViewById(R.id.home_Latest_list);
        picksRV = findViewById(R.id.home_picks_list);
        search = findViewById(R.id.home_search);
        Movie_list = new ArrayList<>();



        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Getting Data ...");
        dialog.setCancelable(true);
        dialog.show();
        lst_slide = new ArrayList<>();
        lst_slide.add(new slide(R.drawable.movie2, "SHANG-CHI"));
        lst_slide.add(new slide(R.drawable.movie_1, "TOM YUM GOONG 2"));

        sliderAdapter adapter = new sliderAdapter(this, lst_slide);

        slider_pager.setAdapter(adapter);

        String url = getString(R.string.movie_route);
        List<Movie> lstMovies = new ArrayList<>();
        List<Movie> picks_list = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                for (int i = 0; i < response.length(); i++) {

                        JSONObject responseObj = response.getJSONObject(i);

                        String Name = responseObj.getString("name");
                        String poster = responseObj.getString("poster");
                        String trailer = responseObj.getString("trailer");
                        String lang = responseObj.getString("language");
                        Movie_list.add(Name);
                        lstMovies.add(new Movie(Name, poster,trailer));
                            if (lang.equals("Tamil")){
                                picks_list.add(new Movie(Name, poster,trailer));
                            }

                        }
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

        adapter_movie = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, Movie_list);
        search.setAdapter(adapter_movie);
        search.setThreshold(1);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Movie_details.class);
                intent.putExtra("Title", search.getText().toString());
                startActivity(intent);
            }
        });


        adapter1 = new latest_list_adapter(this, lstMovies, this);
        movieRV.setAdapter(adapter1);

        movieRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        adapter2 = new latest_list_adapter(this, picks_list, this);
        picksRV.setAdapter(adapter2);

        picksRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onMovieClick(Movie movie, ImageView movie_img) {
        // sending movie info to movie details

        Intent intent = new Intent(this, Movie_details.class);

        intent.putExtra("Title", movie.getTitle());

        startActivity(intent);

    }

}
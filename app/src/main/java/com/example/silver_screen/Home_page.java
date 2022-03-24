package com.example.silver_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home_page extends AppCompatActivity implements  movieItemListener{

    private List<slide> lst_slide;
    private ViewPager slider_pager;
    private RecyclerView movieRV, picksRV, theatersRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        slider_pager = findViewById(R.id.home_slider_page);
        movieRV = findViewById(R.id.home_Latest_list);
        picksRV = findViewById(R.id.home_picks_list);
        theatersRV = findViewById(R.id.home_theaters_list);

        lst_slide = new ArrayList<>();
        lst_slide.add(new slide(R.drawable.movie2, "SHANG-CHI"));
        lst_slide.add(new slide(R.drawable.movie_1, "TOM YUM GOONG 2"));

        sliderAdapter adapter = new sliderAdapter(this, lst_slide);

        slider_pager.setAdapter(adapter);



        List<Movie> lstMovies = new ArrayList<>();
        lstMovies.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        lstMovies.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        lstMovies.add(new Movie("TOM YUM GOONG 2", R.drawable.movie2, "j3YTT5-_nHA"));
        lstMovies.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        lstMovies.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        lstMovies.add(new Movie("TOM YUM GOONG 2", R.drawable.movie2, "j3YTT5-_nHA"));

        latest_list_adapter adapter1 = new latest_list_adapter(this, lstMovies, this);
        movieRV.setAdapter(adapter1);

        movieRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        List<Movie> picks_list = new ArrayList<>();
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));
        picks_list.add(new Movie("Resident Evil", R.drawable.movie_1,"4q6UGCyHZCI"));

        latest_list_adapter adapter2 = new latest_list_adapter(this, picks_list, this);
        picksRV.setAdapter(adapter2);

        picksRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        List<Movie> therters_list = new ArrayList<>();
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));
        therters_list.add(new Movie("SHANG-CHI", R.drawable.movie2, "giWIr7U1deA"));

        latest_list_adapter adapter3 = new latest_list_adapter(this, therters_list, this);
        theatersRV.setAdapter(adapter3);

        theatersRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



    }

    @Override
    public void onMovieClick(Movie movie, ImageView movie_img) {
        // sending movie info to movie details

        Intent intent = new Intent(this, Movie_details.class);

        intent.putExtra("Title", movie.getTitle());
        intent.putExtra("Imgurl", movie.getTumbnail());
        intent.putExtra("Trailerurl", movie.getStreaming_link());
        startActivity(intent);

    }

}
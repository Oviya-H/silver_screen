package com.example.silver_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class Movie_details extends AppCompatActivity implements  movieItemListener{


    RecyclerView castList;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        castList = findViewById(R.id.detailed_cast_list);
        TextView tv2 = findViewById(R.id.detailed_title);
        ImageView img = findViewById(R.id.detailed_img);
        btn = findViewById(R.id.detailed_booking_btn);






        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("Title");
        int imgint = bundle.getInt("Imgurl");
        String trailer = bundle.getString("Trailerurl");
        tv2.setText(title);
        img.setImageResource(imgint);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.video_player);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
           public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(trailer, 0);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Booking_ticket.class);
                intent.putExtra("Movie", title);
                startActivity(intent);
            }
        });

        List<Movie> cast_list = new ArrayList<>();
        cast_list.add(new Movie("Ajith Kumar: as ACP Arjun Kumar", R.drawable.valimai));
        cast_list.add(new Movie("Ajith Kumar: as ACP Arjun Kumar", R.drawable.valimai));
        cast_list.add(new Movie("Ajith Kumar: as ACP Arjun Kumar", R.drawable.valimai));

        latest_list_adapter adapter2 = new latest_list_adapter(this, cast_list, this);
        castList.setAdapter(adapter2);

        castList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onMovieClick(Movie movie, ImageView movieImgview) {

    }
}
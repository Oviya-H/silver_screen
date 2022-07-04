package com.example.silver_screen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class latest_list_adapter extends RecyclerView.Adapter<latest_list_adapter.MyViewHolder> {

    Context context;
    List<Movie> mData;
    movieItemListener Itemlistner;

    public latest_list_adapter(Context context, List<Movie> mData, movieItemListener listner) {
        this.context = context;
        this.mData = mData;
        Itemlistner = listner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.latest_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull latest_list_adapter.MyViewHolder holder, int position) {
        holder.tv_title.setText(mData.get(position).getTitle());

        try  {
            Picasso.with(context).load(mData.get(position).getTumbnail()).into(holder.Imag_movie);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView tv_title;
        private ImageView Imag_movie;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.latest_title);
            Imag_movie = itemView.findViewById(R.id.latest_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Itemlistner.onMovieClick(mData.get(getAdapterPosition()), Imag_movie);

                }
            });

        }
    }
}

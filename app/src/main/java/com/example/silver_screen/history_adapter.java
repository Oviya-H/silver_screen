package com.example.silver_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class history_adapter extends RecyclerView.Adapter<history_adapter.MyViewHolder>{

    Context context;
    List<String> mData;
    historylistener Itemlistner;

    public history_adapter(Context context, List<String> mData, historylistener itemlistner) {
        this.context = context;
        this.mData = mData;
        Itemlistner = itemlistner;
    }


    @NonNull
    @NotNull
    @Override
    public history_adapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_view, parent, false);
        return new history_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull history_adapter.MyViewHolder holder, int position) {
        holder.history_title.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView history_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            history_title = itemView.findViewById(R.id.History_view_tv);


            history_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Itemlistner.onItemClick(mData.get(getAdapterPosition()));

                }
            });

        }
    }
}

package com.example.silver_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

class sliderAdapter extends PagerAdapter {

    Context context;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_items, null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_image);
        TextView slidetext = slideLayout.findViewById(R.id.slide_title);

        slideImg.setImageResource(mlist.get(position).getImage());
        slidetext.setText(mlist.get(position).getTitle());

        container.addView(slideLayout);

        return slideLayout;



    }

    List<slide> mlist;

    public sliderAdapter(Context context, List<slide> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

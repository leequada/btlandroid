package com.example.bigproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bigproject.Model.Banner;
import com.example.bigproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    ImageView imageView;
    Context context;
    ArrayList<Banner> qc;

    public BannerAdapter(Context context, ArrayList<Banner> qc) {
        this.context = context;
        this.qc = qc;
    }

    @Override
    public int getCount() {
        return qc.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.temp_banner_fragment,null);
        imageView =view.findViewById(R.id.imgviewbackgroundbanner);
        Picasso.with(context).load(qc.get(position).getImage()).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(qc.get(position).getLink()));
                        context.startActivity(viewIntent);

            }
        });

        container.addView(view);
        return  view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

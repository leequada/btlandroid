package com.example.bigproject.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.BannerAdapter;
import com.example.bigproject.Model.Banner;
import com.example.bigproject.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Banner_fragment extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator indicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.banner_fragment,container,false);
        anhxa();
        getData();
        return view;
    }
    public void anhxa(){
        viewPager = view.findViewById(R.id.ViewpagerBanner);
        indicator = view.findViewById(R.id.Indicator);

    }
    public void getData(){
        Service dataservice = API_inplement.getService();
        Call<List<Banner>> callback = dataservice.getBanner();
        callback.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                ArrayList<Banner> banners = (ArrayList<Banner>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter);
                indicator.setViewPager(viewPager);
                handler = new Handler(Looper.getMainLooper());
                runnable = new Runnable() {

                    @Override
                    public void run() {
                        current = viewPager.getCurrentItem();
                        current++;
                        if(current >= viewPager.getAdapter().getCount()){
                            current=0;
                        }
                        viewPager.setCurrentItem(current,true);
                        handler.postDelayed(runnable,2500);
                    }
                };
                handler.postDelayed(runnable,2500);
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {

            }
        });

    }
}

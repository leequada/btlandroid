package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterListVideo;
import com.example.bigproject.Adapter.AdapterViewPager;
import com.example.bigproject.Fragment.listfarvorite_fragment;
import com.example.bigproject.Fragment.listprivate_fragment;
import com.example.bigproject.Fragment.listvideo_fragment;
import com.example.bigproject.Model.Video;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_fragment extends AppCompatActivity {
    ImageView imageView;
    TextView name,following,follwer,likes;
    Button Editprofile;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterViewPager adapterViewPager;
    ArrayList<Video> arrayList;
    private char[] c = new char[]{'k', 'm', 'b', 't'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fragment);

        imageView = findViewById(R.id.imgBackgroundUser);
        name = findViewById(R.id.NameUserMain);
        following = findViewById(R.id.following);
        follwer = findViewById(R.id.followe);
        likes = findViewById(R.id.likes);
        Editprofile = findViewById(R.id.editprofile);
        tabLayout = findViewById(R.id.tablayouts);
        viewPager = findViewById(R.id.viewPG);

        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());

        adapterViewPager.addFragment(new listvideo_fragment());
        adapterViewPager.addFragment(new listfarvorite_fragment());
        adapterViewPager.addFragment(new listprivate_fragment());
        viewPager.setAdapter(adapterViewPager);


        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_lsitvideo);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_listfarvorite);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_password);
        setData();

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_fragment.this,EditProfileActivity.class));
            }
        });


    }
    public void setData(){
        if(MainActivity.Usermain.getImageUser() == null || MainActivity.Usermain.getImageUser().equals("")){
                imageView.setBackgroundResource(R.drawable.ic_action_anydpi);
        }
        else {
            Picasso.with(Profile_fragment.this).load(MainActivity.Usermain.getImageUser()).into(imageView);
        }
        name.setText("'@"+MainActivity.Usermain.getNameUser());


        Service dataService = API_inplement.getService();
        Call<List<Video>> callback = dataService.getListVideo(MainActivity.Usermain.getIdUser());
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                arrayList = (ArrayList<Video>) response.body();
                String total = "";
                double  sum = 0;
                for(Video v:arrayList){
                    sum += Double.parseDouble(v.getLikesVideo());
                }
                total = coolFormat(sum,0);
                likes.setText(total);
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });

    }
    private String coolFormat(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration+1));
    }
}
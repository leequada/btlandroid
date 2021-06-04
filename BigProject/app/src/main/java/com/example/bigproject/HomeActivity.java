package com.example.bigproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterScollVideo;
import com.example.bigproject.Adapter.AdapterViewPagerMain;
import com.example.bigproject.Fragment.Home_fragment;
import com.example.bigproject.Fragment.Notification_fragment;
import com.example.bigproject.Fragment.Profile_fragment;
import com.example.bigproject.Fragment.Search_fragment;
import com.example.bigproject.Model.User;
import com.example.bigproject.Model.Video;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    AdapterScollVideo adapterScollVideo;
    AdapterViewPagerMain adapterViewPagerMain;
    ArrayList<Video> arrayListVideo;
    ArrayList<User> arrayListUser;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        floatingActionButton = findViewById(R.id.flactionbtn);
        bottomAppBar = findViewById(R.id.bottombar);
        nav = findViewById(R.id.nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Home_fragment()).commit();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragment = new Home_fragment();
                        break;
                    case R.id.nav_search:
                        fragment = new Search_fragment();
                        break;
                    case R.id.nav_people:
                        fragment = new Profile_fragment();
                        break;
                    case R.id.nav_notification:
                        fragment = new Notification_fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
                return true;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddvideoActivity.class));
            }
        });

    }


    /*public void getData(){
        Service DataService = API_inplement.getService();
        Call<List<Video>> callback = DataService.getDataAllVideo();
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                arrayListVideo = (ArrayList<Video>) response.body();
                arrayListUser = (ArrayList<User>) MainActivity.user;
                adapterScollVideo = new AdapterScollVideo(HomeActivity.this,arrayListVideo,arrayListUser);
                viewPager2.setAdapter(adapterScollVideo);
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });

    }*/
}
package com.example.bigproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterViewPager;
import com.example.bigproject.EditProfileActivity;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_fragment extends Fragment {
    View view;
    ImageView imageView;
    TextView name,following,follwer,likes;
    Button Editprofile;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterViewPager adapterViewPager;
    ArrayList<Video> arrayList;
    private char[] c = new char[]{'k', 'm', 'b', 't'};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment,container,false);
        imageView = view.findViewById(R.id.imgBackgroundUser);
        name = view.findViewById(R.id.NameUserMain);
        following = view.findViewById(R.id.following);
        follwer = view.findViewById(R.id.followe);
        likes = view.findViewById(R.id.likes);
        Editprofile = view.findViewById(R.id.editprofile);
        tabLayout = view.findViewById(R.id.tablayouts);
        viewPager = view.findViewById(R.id.viewPG);

        adapterViewPager = new AdapterViewPager(getChildFragmentManager());

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
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        return view;
    }
    public void setData(){
        if(MainActivity.Usermain.getImageUser() == null || MainActivity.Usermain.getImageUser().equals("")){
            imageView.setBackgroundResource(R.drawable.black);
        }
        else {
            Picasso.with(getActivity()).load(MainActivity.Usermain.getImageUser()).into(imageView);
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

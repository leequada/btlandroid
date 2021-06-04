package com.example.bigproject.Fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterScollVideo;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.Notificationss;
import com.example.bigproject.Model.User;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.*;

public class Home_fragment extends Fragment {
    View view;
    AdapterScollVideo adapterScollVideo;
    ArrayList<Video> arrayListVideo;
    ArrayList<User> arrayListUser;
    ViewPager2 viewPager2;
    ArrayList<Notificationss> notificationssArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        init();
        getData();
        getNoti(MainActivity.Usermain.getIdUser());
        return view;
    }
    public void init(){
        viewPager2 = view.findViewById(R.id.viewpgHomeScreen);

    }
    public void getData(){
        Service DataService = API_inplement.getService();
        Call<List<Video>> callback = DataService.getDataAllVideo();
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                arrayListVideo = (ArrayList<Video>) response.body();
                arrayListUser = (ArrayList<User>) MainActivity.user;
                adapterScollVideo = new AdapterScollVideo(getContext(),arrayListVideo,arrayListUser);
                viewPager2.setAdapter(adapterScollVideo);
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });

    }
    public void getNoti(String id){
            Service service = API_inplement.getService();
            Call<List<Notificationss>> call = service.getNotification(id);
            call.enqueue(new Callback<List<Notificationss>>() {
                @Override
                public void onResponse(Call<List<Notificationss>> call, Response<List<Notificationss>> response) {
                    notificationssArrayList = (ArrayList<Notificationss>) response.body();
                    for(Notificationss n: notificationssArrayList){
                        if(n.getIsChecked().equals("0")){
                            sendNotification(n);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Notificationss>> call, Throwable t) {

                }
            });

    }
    public void sendNotification(Notificationss notificationss){
        @SuppressLint("WrongConstant")
        Notification notification = new Notification.Builder(getContext())
                .setContentTitle("Thông báo App")
                .setContentText(notificationss.getContent())
                .setSmallIcon(R.drawable.ic_action_unreadnoti)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(Integer.parseInt(notificationss.getId()), notification);
        }
    }
}

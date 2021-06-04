package com.example.bigproject.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.User;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayVideo_Activity extends AppCompatActivity {
    TextView title,tag,name,likes;
    ImageView imageView;
    ProgressBar progressBar;
    VideoView videoView;
    ArrayList<User> arrayListUser;
    private char[] c = new char[]{'k', 'm', 'b', 't'};
    Video videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_);
        title = findViewById(R.id.titleVideoPV);
        tag = findViewById(R.id.TagPV);
        name = findViewById(R.id.NameUserPV);
        likes = findViewById(R.id.numofLikePV);
        imageView = findViewById(R.id.imgUserPV);
        progressBar = findViewById(R.id.processbarPV);
        videoView = findViewById(R.id.screenvideoPV);
        intentData();
        if(videos != null){
            setData(videos);
        }
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
    public void setData(Video video){
        String titlevid = video.getTitleVideo();
        String Url = video.getLinkVideo();
        String like = coolFormat(Double.parseDouble(video.getLikesVideo()),0);
        String idUser = video.getIdUser();
        String tags = video.getTagVideo();
        String nameUser = "'@"+ getUserbyID(idUser).getNameUser();
        String img = getUserbyID(idUser).getImageUser();

        title.setText(titlevid);
        tag.setText(tags);
        name.setText(nameUser);
        likes.setText(like);

        if(img == null || img.equals("")){
            imageView.setBackgroundResource(R.drawable.black);
        }
        else {
            Picasso.with(PlayVideo_Activity.this).load(img).into(imageView);
        }
        progressBar.setVisibility(View.VISIBLE);
        String url = video.getLinkVideo();
        Uri uri =Uri.parse(url);
        final MediaController mediaController = new MediaController(PlayVideo_Activity.this);
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                        progressBar.setVisibility(View.GONE);
                        return true;}
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                        progressBar.setVisibility(View.VISIBLE);
                        return true;}
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                        progressBar.setVisibility(View.GONE);
                        return true;}

                }
                return false;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }
    public void intentData(){
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("search")){
                videos =(Video)intent.getSerializableExtra("search");
                Toast.makeText(this, videos.getTitleVideo(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public User getUserbyID(String id){
        for(User user: MainActivity.user){
            if(id.equalsIgnoreCase(user.getIdUser())){
                return user;
            }
        }
        return null;
    }
}
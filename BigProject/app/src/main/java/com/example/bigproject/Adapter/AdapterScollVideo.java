package com.example.bigproject.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.API.API_VideoIML;
import com.example.bigproject.API.Service;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.User;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterScollVideo extends RecyclerView.Adapter<AdapterScollVideo.ViewHolder> {

    Context context;
    private ArrayList<Video> arrayListVideo;
    private ArrayList<User> arrayListUser;
    private char[] c = new char[]{'k', 'm', 'b', 't'};
    public AdapterScollVideo(Context context, ArrayList<Video> arrayListVideo, ArrayList<User> arrayListUser) {
        this.context = context;
        this.arrayListVideo = arrayListVideo;
        this.arrayListUser = arrayListUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scrollvideo_fragment_custome,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = arrayListVideo.get(position);
        String titlevid = video.getTitleVideo();
        String Url = video.getLinkVideo();
        String likes = coolFormat(Double.parseDouble(video.getLikesVideo()),0);
        String idUser = video.getIdUser();
        String tag = video.getTagVideo();
        String nameUser = "'@"+ getUserbyID(idUser).getNameUser();
        String img = getUserbyID(idUser).getImageUser();

        holder.title.setText(titlevid);
        holder.tag.setText(tag);
        holder.name.setText(nameUser);
        holder.likes.setText(likes);
        if(img == null || img.equals("")){
            holder.imageView.setBackgroundResource(R.drawable.black);
        }
        else {
            Picasso.with(context).load(img).into(holder.imageView);
        }

        setVideoUrl(video,holder);
    }

    private void setVideoUrl(Video video, final ViewHolder holder) {
        holder.progressBar.setVisibility(View.VISIBLE);
        String url = video.getLinkVideo();
        Uri uri =Uri.parse(url);
        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setVideoURI(uri);
        holder.videoView.requestFocus();

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                        holder.progressBar.setVisibility(View.GONE);
                        return true;}
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return true;}
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                        holder.progressBar.setVisibility(View.GONE);
                        return true;}

                }
                return false;
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
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

    @Override
    public int getItemCount() {
        return arrayListVideo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,tag,name,likes;
        ImageView imageView;
        ProgressBar progressBar;
        VideoView videoView;
        ImageView unlike , love;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleVideo);
            tag = itemView.findViewById(R.id.Tag);
            name = itemView.findViewById(R.id.NameUser);
            likes = itemView.findViewById(R.id.numofLike);
            imageView = itemView.findViewById(R.id.imgUser);
            progressBar = itemView.findViewById(R.id.processbar);
            videoView = itemView.findViewById(R.id.screenvideo);
            unlike = itemView.findViewById(R.id.unfarvorite);
            love = itemView.findViewById(R.id.farvorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(love.getVisibility() == View.GONE) {
                        animationHeart(love);
                        love.setVisibility(View.VISIBLE);
                        unlike.setVisibility(View.GONE);
                        int sum = Integer.parseInt( arrayListVideo.get(getPosition()).getLikesVideo());
                        sum+=1000;
                        arrayListVideo.get(getPosition()).setLikesVideo(sum+"");
                        InsertData(sum+"",arrayListVideo.get(getPosition()));
                    }
                    else {
                        animationDisHeart(unlike);
                        love.setVisibility(View.GONE);
                        unlike.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


    }

    private void InsertData(String s, final Video v) {
        Service data = API_VideoIML.getService();
        String idVideo = v.getIdVideo();
        Call<String> call = data.UpDateLikes(idVideo,s);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null){
                    //Toast.makeText(context,response.body(),Toast.LENGTH_SHORT).show();
                    String content = MainActivity.Usermain.getNameUser() + " Đã thích video của bạn";
                    Service dataservice = API_VideoIML.getService();
                    Call<String> callback = dataservice.uploadNotification(content,MainActivity.Usermain.getIdUser(),v.getIdUser());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null){
                                Toast.makeText(context,response.body(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private static Animation prepareAnimation(Animation animation){
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return  animation;
    }
    public  void animationHeart(ImageView imageView){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,
                0.5f);
        prepareAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animationSet= new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(1000);
        imageView.startAnimation(animationSet);

    }
    public  void animationDisHeart(ImageView imageView){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,
                0.5f);
        prepareAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animationSet= new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(1000);
        imageView.startAnimation(animationSet);

    }
    public User getUserbyID(String id){
        for(User user: arrayListUser){
            if(id.equalsIgnoreCase(user.getIdUser())){
                return user;
            }
        }
        return null;
    }
}

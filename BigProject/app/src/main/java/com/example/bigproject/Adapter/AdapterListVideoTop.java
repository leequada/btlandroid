package com.example.bigproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bigproject.Fragment.PlayVideo_Activity;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;

import java.util.ArrayList;

public class AdapterListVideoTop extends RecyclerView.Adapter<AdapterListVideoTop.Viewholder>{
    public Context context;
    public ArrayList<Video> top;
    private char[] c = new char[]{'k', 'm', 'b', 't'};

    public AdapterListVideoTop(Context context, ArrayList<Video> arrayList) {
        this.context = context;
        this.top = arrayList;
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

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_topvideo,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Video video = top.get(position);
        if(video.getTitleVideo().length() > 20) {
            holder.content.setText(video.getTitleVideo().substring(0, 10) + "...");
        }
        Double likenum = Double.parseDouble(video.getLikesVideo());

        holder.likes.setText(""+coolFormat(likenum,0));
        Uri url = Uri.parse(video.getLinkVideo());
        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        holder.videoView.setVideoURI(url);
        holder.videoView.requestFocus();

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(5);
                mp.setVolume(0,0);
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.pause();
                mp.setVolume(0,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return top.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        VideoView videoView;
        TextView content;
        TextView likes;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoTOPView);
            content = itemView.findViewById(R.id.ContentVideoTOP);
            likes = itemView.findViewById(R.id.likesnumberTOP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayVideo_Activity.class);
                    intent.putExtra("search" ,top.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }


}

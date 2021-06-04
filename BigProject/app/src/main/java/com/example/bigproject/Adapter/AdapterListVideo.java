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

import com.example.bigproject.Fragment.PlayVideo_Activity;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterListVideo extends RecyclerView.Adapter<AdapterListVideo.ViewHolder>{
    Context context;
    public ArrayList<Video> arrayList;
    private char[] c = new char[]{'k', 'm', 'b', 't'};

    public AdapterListVideo(Context context, ArrayList<Video> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_unit_video_fragment,parent,false);
        return new ViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = arrayList.get(position);
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
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        TextView likes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            likes = itemView.findViewById(R.id.likesnumber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayVideo_Activity.class);
                    intent.putExtra("search" ,arrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
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
}

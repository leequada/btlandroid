package com.example.bigproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.Model.Notificationss;
import com.example.bigproject.Model.User;
import com.example.bigproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder>{
    ArrayList<Notificationss> notificationsArrayList;
    ArrayList<User> userArrayList;
    Context context;

    public AdapterNotification(ArrayList<Notificationss> notificationsArrayList, ArrayList<User> userArrayList, Context context) {
        this.notificationsArrayList = notificationsArrayList;
        this.userArrayList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_fragment_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notificationss notificationss = notificationsArrayList.get(position);
        User u = getUserbyID(notificationss.getIdUser());
        String url = u.getImageUser();
        if(url == null || url.equals("")){
            holder.imageView.setBackgroundResource(R.drawable.ic_baseline_notifications_24);
        }
        else {
            Picasso.with(context).load(url).into(holder.imageView);
        }
        if(notificationss.getIsChecked().equals("0")){
            holder.noti.setBackgroundResource(R.drawable.ic_action_unreadnoti);
        }
        else{
            holder.noti.setBackgroundResource(R.drawable.ic_action_readed);
        }
        holder.textView.setText(notificationss.getContent());
    }

    @Override
    public int getItemCount() {
        return notificationsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,noti;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgNotification);
            textView = itemView.findViewById(R.id.contentNotification);
            noti = itemView.findViewById(R.id.noti);

        }
    }
    public User getUserbyID(String id){
        for(User user: userArrayList){
            if(id.equalsIgnoreCase(user.getIdUser())){
                return user;
            }
        }
        return null;
    }
}

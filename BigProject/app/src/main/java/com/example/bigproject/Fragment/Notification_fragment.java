package com.example.bigproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterNotification;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.Notificationss;
import com.example.bigproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_fragment extends Fragment {
    View view;
    AdapterNotification adapterNotification;
    RecyclerView recyclerView;
    Button button;
    ArrayList<Notificationss> notificationssArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewNotification);
        button = view.findViewById(R.id.viewall);
        String id = MainActivity.Usermain.getIdUser();
        getData(id);
        return view;
    }

    private void getData(String id) {
        Service service = API_inplement.getService();
        Call<List<Notificationss>> call = service.getNotification(id);
        call.enqueue(new Callback<List<Notificationss>>() {
            @Override
            public void onResponse(Call<List<Notificationss>> call, Response<List<Notificationss>> response) {
                notificationssArrayList = (ArrayList<Notificationss>) response.body();

                adapterNotification = new AdapterNotification(notificationssArrayList,MainActivity.user,getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterNotification);
            }

            @Override
            public void onFailure(Call<List<Notificationss>> call, Throwable t) {

            }
        });
    }
}

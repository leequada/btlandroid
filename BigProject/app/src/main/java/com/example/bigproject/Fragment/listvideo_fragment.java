package com.example.bigproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterListVideo;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listvideo_fragment extends Fragment {
    private View view;
    AdapterListVideo adapterListVideo;
    ArrayList<Video> arrayList;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.listvideo_fragment,container,false);
        String id = MainActivity.Usermain.getIdUser();
        recyclerView = view.findViewById(R.id.recyclerViewlistVideo);
        getData(id);
        return  view;
    }

    private void getData(String id) {
        Service dataService = API_inplement.getService();
        Call<List<Video>> callback = dataService.getListVideo(id);
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                arrayList = (ArrayList<Video>) response.body();
                adapterListVideo = new AdapterListVideo(getActivity(),arrayList);
                GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapterListVideo);
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }
}

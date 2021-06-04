package com.example.bigproject.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterListVideo;
import com.example.bigproject.Adapter.AdapterListVideoTop;
import com.example.bigproject.Adapter.BannerAdapter;
import com.example.bigproject.MainActivity;
import com.example.bigproject.Model.Video;
import com.example.bigproject.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topvideo_fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    AdapterListVideoTop adapterListVideoTop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.topvideo_fragment,container,false);
        recyclerView = view.findViewById(R.id.RecyclerViewTopVideo);
        getData();
        return view;
    }
    public void getData(){
        Service dataService = API_inplement.getService();
        Call<List<Video>> call = dataService.getVideoTOP();
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                ArrayList<Video> arrayList = (ArrayList<Video>) response.body();
                for(Video v: arrayList){
                    Log.d("Title"+v.getIdVideo()+"",v.getTitleVideo());
                    Log.d("Link"+v.getIdVideo()+"",v.getLinkVideo());
                }

                adapterListVideoTop = new AdapterListVideoTop(getActivity(),arrayList);

                GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapterListVideoTop);

            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }
}

package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Adapter.AdapterSearchView;
import com.example.bigproject.Model.Video;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListVideoFromSearch extends AppCompatActivity {
    Toolbar toolbar;
    EditText editText;
    RecyclerView recyclerView;
    Button btnSearch;
    AdapterSearchView adapterSearchView;
    ArrayList<Video> list;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video_from_search);
        toolbar = findViewById(R.id.toolbars);
        editText = findViewById(R.id.SearchContent);
        recyclerView = findViewById(R.id.recycSearch);
        btnSearch = findViewById(R.id.btnSearch);
        init();
        Intent intent = getIntent();
        if(intent.hasExtra("Contentsearch")){
            content = (String )intent.getSerializableExtra("Contentsearch");
            getData(content);
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = editText.getText().toString();
                Service dataservice = API_inplement.getService();
                Call<List<Video>> callback = dataservice.getVideobySearch(txt);
                callback.enqueue(new Callback<List<Video>>() {
                    @Override
                    public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                        list = (ArrayList<Video>) response.body();
                        adapterSearchView.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Video>> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }

    private void getData(String content) {
        Service dataservice = API_inplement.getService();
        Call<List<Video>> callback = dataservice.getVideobySearch(content);
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                list = (ArrayList<Video>) response.body();
                Toast.makeText(ListVideoFromSearch.this,list.size()+"",Toast.LENGTH_LONG).show();
                adapterSearchView = new AdapterSearchView(list,ListVideoFromSearch.this);
                GridLayoutManager manager = new GridLayoutManager(ListVideoFromSearch.this,2);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapterSearchView);
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Log.d("DDD",t.getMessage());
            }
        });
    }
}
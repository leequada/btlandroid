package com.example.bigproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.bigproject.API.API_VideoIML;
import com.example.bigproject.API.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddvideoActivity extends AppCompatActivity {
    Button btnadd;
    public static final int REQUEST_PICK_VIDEO = 3;
    EditText title,tag;
    VideoView videoView;
    String path;
    private Uri video;
    private String videoPath;
    FloatingActionButton floatingActionButton;
    String videolink="https://leequadapro.000webhostapp.com/Severtiktok/video/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvideo);
        floatingActionButton = findViewById(R.id.chooseVideo);
        title = findViewById(R.id.titleVideos);
        tag = findViewById(R.id.tagvideo);
        btnadd = findViewById(R.id.btnAdd);
        videoView = findViewById(R.id.videoV);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, REQUEST_PICK_VIDEO);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickVideoIntent= new Intent();
                pickVideoIntent.setAction(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (video != null){
                    try {
                        uploadFile();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(AddvideoActivity.this, "Please select a video", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {

                    video = data.getData();
                    Context context = getApplicationContext();
                    videoPath = RealPathUtil.getRealPath(context,video);
                    // Toast.makeText(this,videoPath,Toast.LENGTH_SHORT).show();
                    initializePlayer(video);

                }
            }
        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }
    private void initializePlayer(Uri urlVideo) {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.setVideoURI(urlVideo);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.pause();
            }
        });
    }
    public void uploadFile() throws  InterruptedException{
        if(video == null || video.equals("")){
            Toast.makeText(AddvideoActivity.this, "please select an image ", Toast.LENGTH_LONG).show();
        }else
        {
            File file = new File(videoPath);
            String file_path = file.getAbsolutePath();
            String[] arrpath = file_path.split("\\.");
            //Map<String, RequestBody> map = new HashMap<>();
            file_path = arrpath[0] + System.currentTimeMillis()+"." + arrpath[1];
            final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file",file_path,requestBody);

            Service dataservice = API_VideoIML.getService();
            Call<String> call = dataservice.uploadVideo(body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response != null){
                        path = response.body();
                        videolink = videolink + response.body();
                        Log.d("DDD",videolink);
                        String titles = title.getText().toString();
                        String tags = tag.getText().toString();
                        String id = MainActivity.Usermain.getIdUser();
                        Service newservice = API_VideoIML.getService();
                        Call<String> callback = newservice.uploadVideoS(titles,videolink,tags,id);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response != null){
                                    Toast.makeText(AddvideoActivity.this,response.body(),Toast.LENGTH_SHORT).show();
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
    }
}
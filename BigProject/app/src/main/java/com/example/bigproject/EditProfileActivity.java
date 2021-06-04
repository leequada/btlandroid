package com.example.bigproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bigproject.API.API_VideoIML;
import com.example.bigproject.API.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    EditText name;
    Button edit;
    public static int REQUEST_PICK_IMAGE =3;
    String file_path;
    String imagelink="https://leequadapro.000webhostapp.com/Severtiktok/profile/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = findViewById(R.id.Toolbar_editprofile);
        imageView = findViewById(R.id.imgUser_Editprofile);
        name = findViewById(R.id.editName);
        edit = findViewById(R.id.btnedituser);
        name.setText(MainActivity.Usermain.getNameUser());
        Init();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, REQUEST_PICK_IMAGE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickVideoIntent= new Intent();
                pickVideoIntent.setAction(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("image/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_IMAGE);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file_path == null || file_path.equals("")){
                    Toast.makeText(EditProfileActivity.this, "please select an image ", Toast.LENGTH_LONG).show();
                }
                else {
                    File file = new File(file_path);
                    String file_path = file.getAbsolutePath();
                    String[] arrpath = file_path.split("\\.");
                    //Map<String, RequestBody> map = new HashMap<>();
                    file_path = arrpath[0] + System.currentTimeMillis()+"." + arrpath[1];
                    final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload_files",file_path,requestBody);

                    Service service = API_VideoIML.getService();
                    Call<String> call = service.uploadImage(body);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response != null){
                                final String names = name.getText().toString();
                                imagelink = imagelink + response.body();
                                Log.d("VIDEOLINK", imagelink);
                                String id = MainActivity.Usermain.getIdUser();
                                Service data = API_VideoIML.getService();
                                Call<String> callback = data.uploadProfile(imagelink,names,id);
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(EditProfileActivity.this,response.body(),Toast.LENGTH_LONG).show();
                                        MainActivity.Usermain.setNameUser(names);
                                        MainActivity.Usermain.setImageUser(imagelink);
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
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_PICK_IMAGE) {
                if (data != null) {

                    Uri uri = data.getData();
                    file_path = RealPathUtil.getRealPath(EditProfileActivity.this,uri);
                    Log.d("file_path",file_path);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }
}
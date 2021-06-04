package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.Fragment.registerActivity;
import com.example.bigproject.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText account , password;
    Button loginbtn;
    TextView registerpage;
    String passwordtxt,accountxt;
    public static ArrayList<User> user;
    public static User Usermain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = findViewById(R.id.Account);
        loginbtn = findViewById(R.id.loginbtn);
        registerpage = findViewById(R.id.register);
        password = findViewById(R.id.Password);
        getAllUser();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordtxt = password.getText().toString().trim();
                accountxt = account.getText().toString().trim();
                if(checklogin(passwordtxt,accountxt)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        });

        registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registerActivity.class));
            }
        });
    }

    private void getAllUser() {
        Service dataService = API_inplement.getService();
        Call<List<User>> callback =dataService.getDataAllUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                user =(ArrayList<User>) response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private boolean checklogin(String passwordtxt, String accountxt) {
        for(User U: user){
            if(accountxt.equals(U.getAccountUser()) && passwordtxt.equals(U.getPasswordUser())){
                Usermain = U;
                return true;
            }
        }
        return false;
    }
}
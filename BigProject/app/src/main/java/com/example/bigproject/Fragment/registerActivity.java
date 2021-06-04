package com.example.bigproject.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigproject.API.API_inplement;
import com.example.bigproject.API.Service;
import com.example.bigproject.MainActivity;
import com.example.bigproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerActivity extends AppCompatActivity {
    TextView nickname,account,password,repas,warning,back;
    Button register;
    String errorNoti1 = "Mật khẩu nhập lại không khớp";
    String errorNoti2 = "Bạn chưa nhập mật khẩu";
    String errorNoti3 = "Bạn chưa nhập nick name";
    String errorNoti4 = "Bạn chưa nhập tài khoản";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname = findViewById(R.id.nickname);
        account = findViewById(R.id.account);
        password = findViewById(R.id.passwords);
        repas = findViewById(R.id.repassword);
        warning = findViewById(R.id.warning);
        register = findViewById(R.id.btnregister);
        back = findViewById(R.id.backLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nickname.getText().toString().trim();
                String accounts = account.getText().toString().trim();
                String pass = password.getText().toString();
                String repass = repas.getText().toString().trim();
                if(!pass.equals(repass)){
                    warning.setText(errorNoti1);
                }else if(name == ""){
                    warning.setText(errorNoti3);
                }else if(pass == ""){
                    warning.setText(errorNoti2);
                }else if(accounts == ""){
                    warning.setText(errorNoti4);
                }else {
                    setData(name,accounts,pass);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this, MainActivity.class));
            }
        });



    }

    private void setData(String name, String accounts, String pass) {
        Service dataService = API_inplement.getService();
        Call<String> callback = dataService.setValueUser(accounts,pass,name);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().toString().equalsIgnoreCase("Succes")){
                    Toast.makeText(registerActivity.this,"Register Successful!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
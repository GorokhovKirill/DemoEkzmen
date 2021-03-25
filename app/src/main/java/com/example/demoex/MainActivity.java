package com.example.demoex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText edEmail, edPass;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||TextUtils.isEmpty(edPass.getText().toString())){
                    String message = "Заполните все поля";
                    Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
                }else {
                    loginUser();
                }
            }
        });

    }
    public void  loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edEmail.getText().toString());
        loginRequest.setPassword(edPass.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                    String message = "Вы успешно вошли!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }else {
                    String message = "Что-то пошло не так!";
                    Toast.makeText(MainActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
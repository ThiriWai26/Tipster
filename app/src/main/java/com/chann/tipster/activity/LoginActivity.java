package com.chann.tipster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chann.tipster.R;
import com.chann.tipster.data.Login;
import com.chann.tipster.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etph, etpwd;
    private String ph = "", pwd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {

        etph = findViewById(R.id.ph);
        etpwd = findViewById(R.id.pwd);

    }

    public void onLogin(View view) {
        ph = etph.getText().toString();
        pwd = etpwd.getText().toString();
        Log.e("Da har :" , "onLogin ");
        Log.e("ph", ph);
        Log.e("pwd",pwd);

        if(ph == ""){
            etph.setError("Enter phone number");
        }

        if(pwd == ""){
            etpwd.setError("Enter password");
        }

        RetrofitService.getApiEnd().userLogin( ph , pwd ).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                Log.e("getApiEnd:","yoat tal");
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){

                        startActivity(MainActivity.getInstance(getApplicationContext()));
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),response.body().getErrorMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

                Log.e("loginFailure:", t.toString());
            }
        });
    }

    public void onRegister(View view) {
       startActivity(RegisterActivity.startIntent(this));
       finish();
    }
}

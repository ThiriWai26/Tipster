package com.chann.tipster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chann.tipster.R;
import com.chann.tipster.api.ApiEnd;
import com.chann.tipster.data.Register;
import com.chann.tipster.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etPh, etPwd, etConfirmPwd;
    private String ph, pwd, confirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    public static Intent startIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    private void init() {

        etPh = findViewById(R.id.ph);
        etPwd = findViewById(R.id.pwd);
        etConfirmPwd = findViewById(R.id.confirmPwd);

    }

    public void onRegister(View view) {

        ph = String.valueOf(etPh.getText());
        pwd = String.valueOf(etPwd.getText());
        confirmPwd = String.valueOf(etConfirmPwd.getText());

        if (ph == null) {
            etPh.setError("Enter Phone Number");
        }

        if (pwd == null) {
            etPwd.setError("Enter Password");
        }
        if (confirmPwd == null) {
            etConfirmPwd.setError("Enter Password");
        }

        RetrofitService.getApiEnd().userRegister(ph, pwd).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {

                    } else {
                        Toast.makeText(getApplicationContext(), "Phone number has already taken", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });


    }
}

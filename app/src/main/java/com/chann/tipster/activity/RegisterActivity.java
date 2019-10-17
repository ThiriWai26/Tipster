package com.chann.tipster.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.chann.tipster.R;
import com.chann.tipster.data.Register;
import com.chann.tipster.databinding.ActivityRegisterBinding;
import com.chann.tipster.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {


    private String ph, pwd, confirmPwd;
    private CompositeDisposable disposable;
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        disposable = new CompositeDisposable();
    }

    public static Intent startIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }


    public void onRegister(View view) {

        ph = binding.ph.getText().toString();
        pwd = binding.pwd.getText().toString();
        confirmPwd = binding.confirmPwd.getText().toString();

        if (ph.isEmpty()) {

            binding.ph.setError("Enter phone number");
        }
        if (pwd.isEmpty()) {
            binding.pwd.setError("Enter password");
        }
        if (confirmPwd.isEmpty()) {
            binding.confirmPwd.setError("Enter password again");
        }

        if (!(pwd.equals(confirmPwd))) {
            binding.pwd.setError("Password doesn't match.");
            binding.confirmPwd.setError("Password doesn't match.");

        }

        Disposable register = RetrofitService.getApiEnd().userRegister(ph, pwd)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);

        disposable.add(register);


    }

    private void handleError(Throwable throwable) {
    }

    private void handleResult(Register register) {

        binding.progressBar.setVisibility(View.VISIBLE);

        if(register.isSuccess()){

            binding.progressBar.setVisibility(View.GONE);
            finish();
        }
        else {

            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Phone number has already taken", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

package com.chann.tipster.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.chann.tipster.R;
import com.chann.tipster.data.Login;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.ActivityLoginBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private AccessToken mAccessToken;
    private CompositeDisposable disposable;
    private ActivityLoginBinding binding;
    private String ph = "", pwd = "";
    private SharedPreferences pref;// 0 - for private mode
    private SharedPreferences.Editor editor;
    private String token = "hello";

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        token = pref.getString("Token", null);
        disposable = new CompositeDisposable();
        checkLoginStatus();
    }

    private void init() {

//        callbackManager = CallbackManager.Factory.create();
//        binding.loginButton.setReadPermissions("email", "public_profile");
//
//        // Callback registration
//        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Log.e("accessToken", loginResult.getAccessToken().getToken());
////                loadUserProfile(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.e("onError_1", exception.toString());
//            }
//        });

    }


    @SuppressLint("CheckResult")
    public void onLogin(View view) {

        binding.progressBar.setVisibility(View.VISIBLE);

        ph = binding.ph.getText().toString();
        pwd = binding.pwd.getText().toString();
        if (ph.isEmpty()) {
            binding.ph.setError("Enter phone number");
        }

        if (pwd.isEmpty()) {
            binding.pwd.setError("Enter password");
        }


        Disposable subscribe = RetrofitService.getApiEnd().userLogin(ph, pwd)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);

        disposable.add(subscribe);
    }

    private void handleResult(Login login) {
        if (login.isSuccess()) {
            binding.progressBar.setVisibility(View.GONE);
            Token.token = login.getToken();
            Log.e("user token ", login.getToken());

            editor.putString("Token", login.getToken());
            editor.apply();
            editor.commit();
            startActivity(MainActivity.getInstance(getApplicationContext()));
            finish();

        }
        else {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),login.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Log.e("loginFailure:", t.toString());
    }


    public void onRegister(View view) {
        startActivity(RegisterActivity.startIntent(this));
    }


    private void checkLoginStatus() {
        if (token != null) {
            Token.token = token;
            startActivity(MainActivity.getInstance(getApplicationContext()));
            finish();
        }

        else {
            init();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public void facebookLogin(View view) {
        editor = pref.edit();
        String fbAccessToken = pref.getString("fb_token", null);
        if(fbAccessToken != null){
            Disposable subscribe = RetrofitService.getApiEnd().facebookLogin(fbAccessToken)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResult, this::handleError);
            disposable.add(subscribe);
        }
        else {
            Toast.makeText(this, "Not register yet.",Toast.LENGTH_LONG).show();

        }
    }
}

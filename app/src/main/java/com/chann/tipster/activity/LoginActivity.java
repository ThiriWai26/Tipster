package com.chann.tipster.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private AccessToken mAccessToken;
    private CompositeDisposable disposable;
    private ActivityLoginBinding binding;
    private String ph = "", pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        init();
    }

    private void init() {


        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        checkLoginStatus();

        disposable = new CompositeDisposable();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("onError_1", exception.toString());
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        AccessTokenTracker tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {

                    Toast.makeText(getApplicationContext(), "User Logged out", Toast.LENGTH_LONG).show();
                } else
                    loadUserProfile(currentAccessToken);
            }
        };
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    Log.e("account_id", id);
                    Log.e("image_url", image_url);
                    Log.e("first_name", first_name);
                    Log.e("last_name", last_name);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void reload(int miliseconds) {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {


                //some code for repeat
            }
        };

        handler.postDelayed(runnable, miliseconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

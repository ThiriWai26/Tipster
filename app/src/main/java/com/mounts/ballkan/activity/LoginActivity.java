package com.mounts.ballkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mounts.ballkan.R;

import com.mounts.ballkan.data.Login;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.mounts.ballkan.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private CompositeDisposable disposable;
    private ActivityLoginBinding binding;
    private String ph = null, pwd = null , fbToken=null , username=null , imageUrl =null;
    private SharedPreferences pref;// 0 - for private mode
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        disposable = new CompositeDisposable();
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

            editor.putString("ph", ph);
            editor.putString("pwd",pwd);
            editor.apply();
            editor.commit();
            startActivity(MainActivity.getInstance(getApplicationContext()));
            finish();

        }
        else {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Invalid User", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Log.e("loginFailure:", t.toString());
    }


    public void onRegister(View view) {
        startActivity(RegisterActivity.startIntent(this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

//    public void facebookLogin(View view) {
//        editor = pref.edit();
//        String fbAccessToken = pref.getString("fb_token", null);
//        if(fbAccessToken != null){
//            Disposable subscribe = RetrofitService.getApiEnd().facebookLogin(fbAccessToken)
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(this::handleResult, this::handleError);
//            disposable.add(subscribe);
//        }
//        else {
//            Toast.makeText(this, "Not register yet.",Toast.LENGTH_LONG).show();
//
//        }
//    }


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
                    Log.e("user name", first_name + " " + last_name);

                    editor.putString("fb_token", newAccessToken.getToken());
                    editor.putString("username",first_name + " " + last_name);
                    editor.putString("image_url",image_url);
                    editor.apply();
                    editor.commit();

                    Disposable subscribe = RetrofitService.getApiEnd().facebookLogin(newAccessToken.getToken(), first_name + " " + last_name , image_url)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(LoginActivity.this::handleResult , LoginActivity.this::handleError);
                    disposable.add(subscribe);


                } catch (JSONException e) {
                    Log.e("error", e.getMessage());
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        AccessTokenTracker tokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null) {
//
//                    Toast.makeText(getApplicationContext(), "User Logged out", Toast.LENGTH_LONG).show();
//                } else
//                    loadUserProfile(currentAccessToken);
//            }
//        };
//        if (AccessToken.getCurrentAccessToken() != null) {
//            loadUserProfile(AccessToken.getCurrentAccessToken());
//        }
    }


    public void facebookLogin(View view) {

        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions("email", "public_profile");

        // Callback registration

        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e("accessToken", loginResult.getAccessToken().getToken());
                loadUserProfile(loginResult.getAccessToken());
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
}

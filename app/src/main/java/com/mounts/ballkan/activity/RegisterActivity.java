package com.mounts.ballkan.activity;

import android.content.Context;
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
import com.mounts.ballkan.data.Register;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.databinding.ActivityRegisterBinding;
import com.mounts.ballkan.retrofit.RetrofitService;
import com.facebook.AccessToken;
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

public class RegisterActivity extends AppCompatActivity {


    private String ph, pwd, confirmPwd;
    private CompositeDisposable disposable;
    private ActivityRegisterBinding binding;
    private SharedPreferences pref;// 0 - for private mode
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        disposable = new CompositeDisposable();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
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
        Log.e("register error",throwable.toString());
    }

    private void handleResult(Register register) {

        binding.progressBar.setVisibility(View.VISIBLE);

        if(register.isSuccess()){

            binding.progressBar.setVisibility(View.GONE);
            Token.token = register.getToken();
            Toast.makeText(this,"Register Success",Toast.LENGTH_LONG).show();
            finish();
        }
        else {

            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), register.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void loadUserProfile(AccessToken newAccessToken) {

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String middle_name = object.getString("middle_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    Log.e("account_id", id);
                    Log.e("image_url", image_url);
                    Log.e("user name", first_name + " " + last_name);

                    editor.putString("fb_token", newAccessToken.getToken());
                    editor.apply();
                    editor.commit();

                    Disposable subscribe = RetrofitService.getApiEnd().facebookRegister(newAccessToken.getToken(), first_name + " "+middle_name+" " + last_name , image_url)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(RegisterActivity.this::handleResult, RegisterActivity.this::handleError);
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


    public void facebookRegister(View view) {

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

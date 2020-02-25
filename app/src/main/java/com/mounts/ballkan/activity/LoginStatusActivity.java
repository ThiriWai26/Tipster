package com.mounts.ballkan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import com.mounts.ballkan.R;
import com.mounts.ballkan.data.Login;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginStatusActivity extends AppCompatActivity {

    private String ph = null, pwd = null , fbToken=null , username=null , imageUrl =null;
    private SharedPreferences pref;// 0 - for private mode
    private SharedPreferences.Editor editor;
    private CompositeDisposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_status);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        disposable = new CompositeDisposable();

        ph = pref.getString("ph", null);
        pwd = pref.getString("pwd",null);
        fbToken= pref.getString("fb_token",null);
        username = pref.getString("username",null);
        imageUrl = pref.getString("image_url",imageUrl);


        if (ph != null) {
            Disposable subscribe = RetrofitService.getApiEnd().userLogin(ph, pwd)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResult, this::handleError);

            disposable.add(subscribe);
        }

        else if(fbToken!=null){
            Disposable subscribe = RetrofitService.getApiEnd().facebookLogin(fbToken, username , imageUrl)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(LoginStatusActivity.this::handleResult , LoginStatusActivity.this::handleError);
            disposable.add(subscribe);
        }

       else {
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void handleResult(Login login) {
        if (login.isSuccess()) {
            Token.token = login.getToken();
            Log.e("user token ", login.getToken());
            startActivity(MainActivity.getInstance(getApplicationContext()));
            finish();

        }
        else {

            editor.clear();
            editor.apply();
            editor.commit();
            
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void handleError(Throwable t) {
        Log.e("loginFailure:", t.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}

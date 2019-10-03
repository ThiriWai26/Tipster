package com.chann.tipster.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chann.tipster.R;
import com.chann.tipster.data.Login;
import com.chann.tipster.fragment.BetHistoryFragment;
import com.chann.tipster.fragment.MatchListFragment;
import com.chann.tipster.fragment.ProfileFragment;
import com.chann.tipster.fragment.RankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    public static Intent getInstance(Context context) {
        return new Intent(context,MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        loadFragment(new MatchListFragment());



        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.menu_item_matchlist:
                        loadFragment(new MatchListFragment());
                        return true;

                    case R.id.menu_item_profile:
                        loadFragment(new ProfileFragment());
                        return true;

                    case R.id.menu_item_ranking:
                        loadFragment(new RankFragment());
                        return true;


                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
    }

}

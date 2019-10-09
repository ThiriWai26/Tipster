package com.chann.tipster.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chann.tipster.R;
import com.chann.tipster.fragment.BetHistoryFragment;
import com.chann.tipster.fragment.MatchListFragment;
import com.chann.tipster.fragment.ProfileFragment;
import com.chann.tipster.fragment.RankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static Intent getInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        toolbar.setTitle(R.string.title_match_list);
        loadFragment(new MatchListFragment());


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.menu_item_matchlist:
                        toolbar.setTitle(R.string.title_match_list);
                        loadFragment(new MatchListFragment());
                        return true;

                    case R.id.menu_item_profile:
                        toolbar.setTitle(R.string.title_profile);
                        loadFragment(new ProfileFragment());
                        return true;

                    case R.id.menu_item_ranking:
                        toolbar.setTitle(R.string.title_ranking);
                        loadFragment(new RankFragment());
                        return true;

                    case R.id.menu_item_history:
                        toolbar.setTitle(R.string.title_bet_history);
                        loadFragment(new BetHistoryFragment());
                        return true;
                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

}

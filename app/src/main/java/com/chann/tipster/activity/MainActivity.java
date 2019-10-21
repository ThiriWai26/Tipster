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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public static Intent getInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        toolbar.setTitle(R.string.title_match_list);
        loadFragment(new MatchListFragment());


        navView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.menu_item_matchlist:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
                    toolbar.setTitle(R.string.title_match_list);
                    loadFragment(new MatchListFragment());
                    return true;

                case R.id.menu_item_profile:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
                    toolbar.setTitle(R.string.title_profile);
                    loadFragment(new ProfileFragment());
                    return true;

                case R.id.menu_item_ranking:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
                    toolbar.setTitle(R.string.title_ranking);
                    loadFragment(new RankFragment());
                    return true;

                case R.id.menu_item_history:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
                    toolbar.setTitle(R.string.title_bet_history);
                    loadFragment(new BetHistoryFragment());
                    return true;
            }
            return false;
        });

    }

    private void loadFragment(Fragment fragment) {
        transaction.replace(R.id.frame_container, fragment).addToBackStack("Tag").commit();
    }

}

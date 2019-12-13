package com.chann.tipster.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chann.tipster.R;
import com.chann.tipster.fragment.BetHistoryFragment;
import com.chann.tipster.fragment.HomeFragment;
import com.chann.tipster.fragment.MatchListFragment;
import com.chann.tipster.fragment.ProfileFragment;
import com.chann.tipster.fragment.RankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView title;

    public static Intent getInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        toolbar.setTitle(R.string.title_match_list);
        manager.addOnBackStackChangedListener(() -> {
            if (manager.getBackStackEntryCount() < 1)
                navView.getMenu().getItem(0).setChecked(true);
        });
        transaction.replace(R.id.frame_container, new MatchListFragment()).commit();


        navView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.action_tipstar:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
//                    toolbar.setTitle(R.string.title_match_list);
                    title.setText(R.string.title_match_list);
                    loadFragment(new MatchListFragment());
                    return true;

                case R.id.menu_item_profile:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
//                    toolbar.setTitle(R.string.title_profile);
                    title.setText(R.string.title_profile);
                    loadFragment(new ProfileFragment());
                    return true;

                case R.id.action_rank:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
//                    toolbar.setTitle(R.string.title_ranking);
                    title.setText(R.string.title_ranking);
                    loadFragment(new RankFragment());
                    return true;

                case R.id.action_history:
                    if (manager.getBackStackEntryCount() >= 1)
                        manager.popBackStack();
//                    toolbar.setTitle(R.string.title_bet_history);
                    title.setText(R.string.title_bet_history);
                    loadFragment(new BetHistoryFragment());
                    return true;

//                case R.id.action_home:
//                    if (manager.getBackStackEntryCount() >= 1)
//                        manager.popBackStack();
//                    toolbar.setTitle(R.string.title_bet_history);
//                    title.setText(R.string.title_home);
//                    loadFragment(new HomeFragment());
//                    return true;

                case R.id.action_custom_league:
                    return true;
            }
            return false;
        });

    }

    private void loadFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment).addToBackStack("tag");
        transaction.commit();
    }

}

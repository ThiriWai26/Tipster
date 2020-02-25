package com.mounts.ballkan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;


import com.mounts.ballkan.R;
import com.mounts.ballkan.adapter.LeaguePagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class LeagueDetailActivity extends AppCompatActivity {

    private LeaguePagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String leagueName;
    private TextView tvLeagueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);

        leagueName = getIntent().getExtras().getString("league_name");

        tabLayout = findViewById(R.id.tabLayout);
        adapter = new LeaguePagerAdapter(getSupportFragmentManager(),1);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tvLeagueName = findViewById(R.id.tvLeagueName);
        tvLeagueName.setText(leagueName);
    }
}

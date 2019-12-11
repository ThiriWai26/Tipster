package com.chann.tipster.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chann.tipster.fragment.LeagueGroupFragment;
import com.chann.tipster.fragment.LeagueHistoryFragment;
import com.chann.tipster.fragment.LeagueRankFragment;

public class LeaguePagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles = new String[]{"Groups","History","Rank"};

    public LeaguePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new LeagueGroupFragment();

            case 1:
                return new LeagueHistoryFragment();

            case 2:
                return new LeagueRankFragment();
        }
    return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

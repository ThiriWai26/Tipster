package com.chann.tipster.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chann.tipster.fragment.AfterTomorrowMatchFragment;
import com.chann.tipster.fragment.TodayMatchFragment;
import com.chann.tipster.fragment.TomorrowMatchFragment;

public class MatchPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles = {"Today", "Tomorrow", "20-11-2019"};

    public MatchPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayMatchFragment();
            case 1:
                return new TomorrowMatchFragment();

            case 2:
                return new AfterTomorrowMatchFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];

    }
}

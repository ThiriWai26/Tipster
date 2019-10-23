package com.chann.tipster.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chann.tipster.fragment.OnfinishFragment;
import com.chann.tipster.fragment.OngoingFragment;

public class BetHistoryPagerAdapter extends FragmentStatePagerAdapter {


    String[] tab = {"On going ", "On finish"};

    public BetHistoryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new OngoingFragment();
        } else {
            return new OnfinishFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}

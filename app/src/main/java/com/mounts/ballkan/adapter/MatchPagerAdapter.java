package com.mounts.ballkan.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mounts.ballkan.data.MatchDates;
import com.mounts.ballkan.fragment.AfterTomorrowMatchFragment;
import com.mounts.ballkan.fragment.TodayMatchFragment;
import com.mounts.ballkan.fragment.TomorrowMatchFragment;


public class MatchPagerAdapter extends FragmentStatePagerAdapter {

    private MatchDates matchDates;
    public MatchPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        matchDates = new MatchDates();
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
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0 )
            return matchDates.today;

        if (position == 1)
            return matchDates.tomorrow;

        if(position == 2)
            return matchDates.afterTomorrow;

        return null;

    }

    public void addDates(MatchDates matchDates){
        this.matchDates = matchDates;
        notifyDataSetChanged();
    }
}

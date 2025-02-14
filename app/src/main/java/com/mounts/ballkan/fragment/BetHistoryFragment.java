package com.mounts.ballkan.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mounts.ballkan.R;
import com.mounts.ballkan.adapter.BetHistoryPagerAdapter;
import com.mounts.ballkan.databinding.FragmentBetHistoryBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetHistoryFragment extends Fragment {

    private BetHistoryPagerAdapter pagerAdapter;
    private FragmentBetHistoryBinding betHistoryBinding;

    public BetHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        betHistoryBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_bet_history , container ,false);

        pagerAdapter = new BetHistoryPagerAdapter(getActivity().getSupportFragmentManager());
        betHistoryBinding.tbLayout.setupWithViewPager(betHistoryBinding.viewPager);
        betHistoryBinding.viewPager.setAdapter(pagerAdapter);
        return betHistoryBinding.getRoot();
    }


}

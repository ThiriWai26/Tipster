package com.mounts.ballkan.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mounts.ballkan.R;
import com.mounts.ballkan.viewmodel.LeagueHistoryViewModel;

public class LeagueHistoryFragment extends Fragment {

    private LeagueHistoryViewModel mViewModel;

    public static LeagueHistoryFragment newInstance() {
        return new LeagueHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.league_history_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LeagueHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}

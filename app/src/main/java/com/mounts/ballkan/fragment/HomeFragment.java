package com.mounts.ballkan.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mounts.ballkan.R;
import com.mounts.ballkan.activity.LeagueDetailActivity;
import com.mounts.ballkan.adapter.SliderImageAdapter;
import com.mounts.ballkan.databinding.FragmentHomeBinding;
import com.mounts.ballkan.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment implements HomeViewModel.BtnLeagueClickListener {

    private HomeViewModel mViewModel;
    private com.smarteist.autoimageslider.SliderView sliderView;
    SliderImageAdapter adapter;
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        adapter = new SliderImageAdapter(getContext());
        adapter.setCount(5);
        binding.imageSlider.setSliderAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.listener = this;
        binding.setViewmodel(mViewModel);

    }

    @Override
    public void onLeagueClick(String league) {

        Log.e("league_name",league);
        Intent intent = new Intent(getContext() , LeagueDetailActivity.class);
        intent.putExtra("league_name",league);
        startActivity(intent);
    }
}

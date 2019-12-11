package com.chann.tipster.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chann.tipster.R;
import com.chann.tipster.activity.LeagueDetailActivity;
import com.chann.tipster.adapter.LeaguePagerAdapter;
import com.chann.tipster.adapter.SliderImageAdapter;
import com.chann.tipster.databinding.FragmentHomeBinding;
import com.chann.tipster.viewmodel.HomeViewModel;
import com.google.android.material.tabs.TabLayout;

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

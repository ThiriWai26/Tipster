package com.chann.tipster.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chann.tipster.R;
import com.chann.tipster.adapter.BetHistoryAdapter;
import com.chann.tipster.adapter.BetHistoryPagerAdapter;
import com.chann.tipster.data.BetHistoryResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.FragmentBetHistoryBinding;
import com.chann.tipster.databinding.FragmentOngoingBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.chann.tipster.viewmodel.OnGoingViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingFragment extends Fragment {

    private BetHistoryAdapter adapter;
    private FragmentOngoingBinding binding;
    private OnGoingViewModel model;

    public OngoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_ongoing , container ,false);
        model = ViewModelProviders.of(this).get(OnGoingViewModel.class);

        adapter = new BetHistoryAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        model.getData().observe(this , betHistoryResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if(betHistoryResponse.betHistoryData.size() == 0){
                binding.tvNoHistory.setVisibility(View.VISIBLE);
            }
            Log.e("betHistory",String.valueOf(betHistoryResponse.betHistoryData.size()));
            adapter.addData(betHistoryResponse.betHistoryData);
            adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.disposable.clear();
    }



}

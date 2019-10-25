package com.chann.tipster.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chann.tipster.R;
import com.chann.tipster.adapter.BetHistoryAdapter;
import com.chann.tipster.databinding.FragmentOnfinishBinding;
import com.chann.tipster.viewmodel.OnFinishViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnfinishFragment extends Fragment {

    private BetHistoryAdapter adapter;
    private FragmentOnfinishBinding binding;
    private OnFinishViewModel model;

    public OnfinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_onfinish , container ,false);

        adapter = new BetHistoryAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        model = ViewModelProviders.of(this).get(OnFinishViewModel.class);
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

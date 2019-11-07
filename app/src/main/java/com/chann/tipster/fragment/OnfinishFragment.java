package com.chann.tipster.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private LinearLayoutManager layoutManager;
    private boolean loading = false;
    private String nextPage;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;


    public OnfinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_onfinish , container ,false);

        adapter = new BetHistoryAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        model = ViewModelProviders.of(this).get(OnFinishViewModel.class);
        getOnFinishHistory(pageNumber);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition();
                if (nextPage != null && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    getOnFinishHistory(pageNumber);

                }

            }
        });

        return binding.getRoot();
    }

    private void getOnFinishHistory(int pageNumber) {

        Log.e("pageNumber",String.valueOf(pageNumber));
        model.getData(pageNumber).observe(this , betHistoryResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if(betHistoryResponse.betHistoryData.size() == 0){
                binding.tvNoHistory.setVisibility(View.VISIBLE);
            }
            Log.e("betHistory",String.valueOf(betHistoryResponse.betHistoryData.size()));

            nextPage = betHistoryResponse.nextPage;
            adapter.addData(betHistoryResponse.betHistoryData);
            adapter.notifyDataSetChanged();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.disposable.clear();
    }

}

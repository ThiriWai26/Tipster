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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.chann.tipster.R;
import com.chann.tipster.adapter.BetHistoryAdapter;
import com.chann.tipster.databinding.FragmentOngoingBinding;
import com.chann.tipster.viewmodel.OnGoingViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingFragment extends Fragment {

    private BetHistoryAdapter adapter;
    private FragmentOngoingBinding binding;
    private OnGoingViewModel model;
    private LinearLayoutManager layoutManager;

    private String nextPage;
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;

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
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
//        binding.recyclerView.setAdapter(adapter);

        SkeletonScreen skeletonScreen = Skeleton.bind(binding.recyclerView)
                .adapter(adapter)
                .shimmer(true)
                .count(10)
                .angle(20)
                .load(R.layout.layout_skeleton_bet_history)
                .show();

        binding.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        } , 3000);


        getOnGoingHistory(pageNumber);


        binding.recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition();
                if ( nextPage != null
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    getOnGoingHistory(pageNumber);
                    loading = true;
                }

            }
        });


        return binding.getRoot();
    }

    private void getOnGoingHistory(int pageNumber) {
        Log.e("pageNumber",String.valueOf(pageNumber));
        model.getData(pageNumber).observe(this, betHistoryResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if (betHistoryResponse.betHistoryData.size() == 0) {
                binding.tvNoHistory.setVisibility(View.VISIBLE);
            }
            Log.e("betHistory", String.valueOf(betHistoryResponse.betHistoryData.size()));

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

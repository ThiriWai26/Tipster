package com.chann.tipster.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chann.tipster.R;
import com.chann.tipster.activity.OddsActivity;
import com.chann.tipster.adapter.MatchDataAdapter;
import com.chann.tipster.adapter.MatchPagerAdapter;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.databinding.FragmentMatchListBinding;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.chann.tipster.viewmodel.MatchListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayMatchFragment extends Fragment implements OnHolderItemClickListener {

    private MatchDataAdapter adapter;
    private Integer roomId;
//    private FragmentMatchListBinding binding;
    private MatchListViewModel model;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public TodayMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_match, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {


        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new MatchDataAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        model = ViewModelProviders.of(this).get(MatchListViewModel.class);

        model.getMatchList().observe(this, matchListResponse -> {
            progressBar.setVisibility(View.GONE);
            if (matchListResponse.isSuccess) {

                roomId = matchListResponse.room.roomId;

                if(matchListResponse.room.roomId != null ){
                    if(matchListResponse.room.isActive){
                        adapter.addData(matchListResponse.matchListData);
                    }
                    else {
//                        binding.tvStartDate.setText(String.format("Tipster will be started at %s %s .",matchListResponse.room.startDate , matchListResponse.room.startTime));
                    }
                }
                else
//                    binding.tvStartDate.setText("No room created");

                Log.e("matchlistsize", String.valueOf(matchListResponse.matchListData.size()));
            } else {
                Log.e("response", "is not successful");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.compositeDisposable.clear();
        model.disposable.dispose();
    }

    @Override
    public void onHolderitemClick(MatchData matchData) {

        Log.e("handicap",String.valueOf(matchData.handiCap.handicap));
        startActivity(OddsActivity.getInstance(getContext(), matchData , roomId));
    }


    @Override
    public void onResume() {
        super.onResume();

        if (model.disposable.isDisposed()) {

            model.getMatchList();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        model.disposable.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
        model.disposable.dispose();
    }
}

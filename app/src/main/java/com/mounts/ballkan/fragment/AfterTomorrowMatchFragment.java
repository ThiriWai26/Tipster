package com.mounts.ballkan.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.mounts.ballkan.R;
import com.mounts.ballkan.adapter.MatchDataAdapter;
import com.mounts.ballkan.data.MatchData;
import com.mounts.ballkan.holderInterface.OnHolderItemClickListener;
import com.mounts.ballkan.viewmodel.MatchListViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class AfterTomorrowMatchFragment extends Fragment implements OnHolderItemClickListener {

    private MatchDataAdapter adapter;
    private Integer roomId;
    private MatchListViewModel model;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public AfterTomorrowMatchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_after_tomorrow_match, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new MatchDataAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .count(10)
                .shimmer(true)
                .angle(0)
                .load(R.layout.skeleton_screen_matchlist)
                .show();

        recyclerView.postDelayed(() -> skeletonScreen.hide(), 2000);
        model = ViewModelProviders.of(this).get(MatchListViewModel.class);

        model.getMatchList("after_tomorrow").observe(this, matchListResponse -> {
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

//        Log.e("handicap",String.valueOf(matchData.handiCap.handicap));
//        startActivity(OddsActivity.getInstance(getContext(), matchData , roomId));
        Toast.makeText(getContext(), "Can't be bet now", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
//        model.getMatchList();
        if (model.disposable.isDisposed()) {

            model.getMatchList("after_tomorrow");

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

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
import com.chann.tipster.activity.OddsActivity;
import com.chann.tipster.adapter.MatchDataAdapter;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.databinding.FragmentMatchListBinding;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.chann.tipster.viewmodel.MatchListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment implements OnHolderItemClickListener , View.OnClickListener{

    private MatchDataAdapter adapter;
    private int roomId;
    private FragmentMatchListBinding binding;
    private MatchListViewModel model;
    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_match_list,container,false);
        View view = binding.getRoot();
        initFragment();
        return view;
    }

    private void initFragment() {
        adapter = new MatchDataAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        model = ViewModelProviders.of(this).get(MatchListViewModel.class);

        model.getMatchList().observe(this, matchListResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if (matchListResponse.isSuccess) {

                roomId = matchListResponse.roomId;
                adapter.addData(matchListResponse.matchListData);
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
//        model.disposable.dispose();
    }

    @Override
    public void onHolderitemClick(MatchData matchData) {
        startActivity(OddsActivity.getInstance(getContext(), matchData , roomId));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();

//        if (model.disposable.isDisposed()) {
//
//            model.getMatchList();
//
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        model.disposable.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
//        model.disposable.dispose();
    }
}


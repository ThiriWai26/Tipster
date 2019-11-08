package com.chann.tipster.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

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
public class MatchListFragment extends Fragment implements OnHolderItemClickListener {

    private MatchDataAdapter adapter;
    private Integer roomId;
    private FragmentMatchListBinding binding;
    private MatchListViewModel model;
    private MatchPagerAdapter pagerAdapter;
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

        pagerAdapter = new MatchPagerAdapter(getActivity().getSupportFragmentManager() , 0);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

//        adapter = new MatchDataAdapter(this);
//        binding.recyclerView.setAdapter(adapter);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

//        String[] data = {"7.11.2019","8.11.2019","9.11.2019"};
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_item_selected,data);
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        binding.spinner.setAdapter(arrayAdapter);
//        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(),data[i],Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        model = ViewModelProviders.of(this).get(MatchListViewModel.class);

//        model.getMatchList().observe(this, matchListResponse -> {
//            binding.progressBar.setVisibility(View.GONE);
//            if (matchListResponse.isSuccess) {
//
//                roomId = matchListResponse.room.roomId;
//
//                if(matchListResponse.room.roomId != null ){
//                    if(matchListResponse.room.isActive){
//                        adapter.addData(matchListResponse.matchListData);
//                    }
//                    else {
//                        binding.tvStartDate.setText(String.format("Tipster will be started at %s %s .",matchListResponse.room.startDate , matchListResponse.room.startTime));
//                    }
//                }
//                else
//                    binding.tvStartDate.setText("No room created");
//
//                Log.e("matchlistsize", String.valueOf(matchListResponse.matchListData.size()));
//            } else {
//                Log.e("response", "is not successful");
//            }
//        });


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


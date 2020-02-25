package com.mounts.ballkan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.mounts.ballkan.R;
import com.mounts.ballkan.adapter.MatchPagerAdapter;
import com.mounts.ballkan.data.MatchListResponse;
import com.mounts.ballkan.data.Token;
import com.mounts.ballkan.databinding.FragmentMatchListBinding;
import com.mounts.ballkan.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment  {

    private FragmentMatchListBinding binding;
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

        pagerAdapter = new MatchPagerAdapter(getActivity().getSupportFragmentManager() , 1);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        Disposable subscribe = RetrofitService.getApiEnd().getMatchList(Token.token , "Today")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);


    }

    private void handleError(Throwable throwable) {
        Toast.makeText(getContext() , "Something went wrong!!",Toast.LENGTH_LONG).show();
    }

    private void handleResult(MatchListResponse matchListResponse) {

        pagerAdapter.addDates(matchListResponse.matchDates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


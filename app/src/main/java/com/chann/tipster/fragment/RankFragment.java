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
import com.chann.tipster.adapter.UserStandingAdapter;
import com.chann.tipster.api.OnClickItemListener;
import com.chann.tipster.data.StandingResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.FragmentRankBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.chann.tipster.viewmodel.RankViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment implements OnClickItemListener {
    private UserStandingAdapter adapter;
    private FragmentRankBinding binding;
    private RankViewModel model;

    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank, container, false);
        model = ViewModelProviders.of(this).get(RankViewModel.class);
        adapter = new UserStandingAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        model.getData().observe(this , standingResponse -> {
            if(standingResponse.isSuccess) {
                binding.progressBar.setVisibility(View.GONE);
                adapter.addData(standingResponse.userStandings);
                adapter.notifyDataSetChanged();
                Picasso.get().load(RetrofitService.BASE_URL + "/api/get_image/" + standingResponse.image).resize(50, 50).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);
            }
            else {
                Log.e("response","fail");
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onHolderitemClick(int id) {

        Log.e("userId",String.valueOf(id));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.disposable.clear();
    }
}

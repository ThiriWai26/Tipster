package com.chann.tipster.fragment;


import android.annotation.SuppressLint;
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
import com.chann.tipster.adapter.UserStandingAdapter;
import com.chann.tipster.api.OnClickItemListener;
import com.chann.tipster.databinding.FragmentRankBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.chann.tipster.viewmodel.RankViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment implements OnClickItemListener {
    private UserStandingAdapter adapter;
    private FragmentRankBinding binding;
    private RankViewModel model;
    private LinearLayoutManager layoutManager;
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    private String nextPage = null;
    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank, container, false);
        model = ViewModelProviders.of(this).get(RankViewModel.class);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new UserStandingAdapter(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        SkeletonScreen  skeletonScreen = Skeleton.bind(binding.rootView)
                .load(R.layout.layout_skeleton_ranking)
                .angle(0)
                .shimmer(true)
                .show();

        binding.getRoot().postDelayed(() -> skeletonScreen.hide(), 3000);
        getRankingList(pageNumber);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition();

                if ( nextPage != null
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;

                    Log.e("pageNumber",String.valueOf(pageNumber));
                    getRankingList(pageNumber);
                }

            }
        });

        return binding.getRoot();
    }

    private void getRankingList(int pageNumber) {
//        Log.e("pageNumber",String.valueOf(pageNumber));
        model.getData(pageNumber).observe(this , standingResponse -> {
            if(standingResponse.isSuccess) {

                binding.setData(standingResponse);
                binding.progressBar.setVisibility(View.GONE);
                adapter.addData(standingResponse.userStandings);
                adapter.notifyDataSetChanged();
                nextPage = standingResponse.nextPage;
                if (standingResponse.user.image != null)
                    Picasso.get().load(RetrofitService.BASE_URL + "/api/get_image/" + standingResponse.user.image).resize(400, 400).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);

                else
                    Picasso.get().load( standingResponse.user.fbProfile).resize(400, 400).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);

            }
            else {
                Log.e("response","fail");
            }
        });
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

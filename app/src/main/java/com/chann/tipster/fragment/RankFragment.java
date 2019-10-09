package com.chann.tipster.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.chann.tipster.R;
import com.chann.tipster.adapter.UserStandingAdapter;
import com.chann.tipster.api.OnClickItemListener;
import com.chann.tipster.data.BetHistoryResponse;
import com.chann.tipster.data.StandingResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment implements OnClickItemListener {

    private TextView tvCoin, tvRank;
    private ImageView imgProfile;
    private RecyclerView recyclerView;
    private UserStandingAdapter adapter;
    private CompositeDisposable disposable;
    private ProgressBar progressBar;

    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        init(view);
        return view;
    }

    @SuppressLint("CheckResult")
    private void init(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        tvCoin = view.findViewById(R.id.tv_coin);
        tvRank = view.findViewById((R.id.tv_rank));
        imgProfile = view.findViewById(R.id.iv_profile);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new UserStandingAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        RetrofitService.getApiEnd().getUserStanding(Token.token , 1 , 1)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);
    }

    private void handleResult(StandingResponse standingResponse) {

        if(standingResponse.isSuccess) {
            progressBar.setVisibility(View.GONE);
            adapter.addData(standingResponse.userStandings);
            adapter.notifyDataSetChanged();

            tvRank.setText(String.valueOf(standingResponse.rank));
            tvCoin.setText(String.valueOf(standingResponse.coins));

            Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+standingResponse.image).resize(50, 50).into(imgProfile);
        }
        else {
            Log.e("response","fail");
        }
    }

    private void handleError(Throwable throwable) {
        Log.e("onfailure",throwable.toString());
    }



    @Override
    public void onHolderitemClick(int id) {

    }
}

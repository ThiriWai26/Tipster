package com.chann.tipster.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chann.tipster.R;
import com.chann.tipster.adapter.BetHistoryAdapter;
import com.chann.tipster.data.BetHistoryResponse;
import com.chann.tipster.data.Token;
import com.chann.tipster.holder.BetHistoryHolder;
import com.chann.tipster.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BetHistoryAdapter adapter;
    private CompositeDisposable disposable;
    private ProgressBar progressBar;
    private TextView tvNoHistory;

    public BetHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bet_history, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        tvNoHistory = view.findViewById(R.id.tv_no_history);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new BetHistoryAdapter();
        disposable = new CompositeDisposable();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

       Disposable subscribe = RetrofitService.getApiEnd().getBetHistory(Token.token , 1)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);

       disposable.add(subscribe);

    }

    private void handleError(Throwable throwable) {
        Log.e("history_throwable",throwable.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

    private void handleResult(BetHistoryResponse betHistoryResponse) {

        progressBar.setVisibility(View.GONE);
        if(betHistoryResponse.betHistoryData.size() == 0){
            tvNoHistory.setVisibility(View.VISIBLE);
        }
        Log.e("betHistory",String.valueOf(betHistoryResponse.betHistoryData.size()));
        adapter.addData(betHistoryResponse.betHistoryData);
        adapter.notifyDataSetChanged();
    }

}

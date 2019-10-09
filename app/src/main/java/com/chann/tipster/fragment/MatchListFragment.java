package com.chann.tipster.fragment;

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

import com.chann.tipster.R;
import com.chann.tipster.activity.OddsActivity;
import com.chann.tipster.adapter.MatchDataAdapter;
import com.chann.tipster.adapter.MatchItemAdapter;
import com.chann.tipster.data.LeagueData;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.data.MatchListData;
import com.chann.tipster.data.MatchListResponse;
import com.chann.tipster.data.RoomListOfLeague;
import com.chann.tipster.data.Token;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.chann.tipster.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment implements OnHolderItemClickListener , View.OnClickListener{

    private RecyclerView recyclerView;
    private MatchDataAdapter adapter;
    private int roomId;
    private ImageView ivRating;
    private CompositeDisposable disposable;
    private ProgressBar progressBar;

    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        disposable = new CompositeDisposable();

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new MatchDataAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        getMatchList();
    }

    private void getMatchList() {

        Disposable subscribe = RetrofitService.getApiEnd().getMatchList(Token.token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);

        disposable.add(subscribe);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

    private void handleError(Throwable throwable) {
        Log.e("failure", throwable.toString());
    }

    private void handleResult(MatchListResponse matchListResponse) {

        progressBar.setVisibility(View.GONE);
        if(matchListResponse.isSuccess){

            roomId = matchListResponse.roomId;
            adapter.addData(matchListResponse.matchListData);
            Log.e("matchlistsize", String.valueOf(matchListResponse.matchListData.size()));
        }
        else {
            Log.e("response","is not successful");
        }
    }


    @Override
    public void onHolderitemClick(MatchData matchData) {
        startActivity(OddsActivity.getInstance(getContext(), matchData , roomId));
    }

    @Override
    public void onClick(View view) {



    }
}


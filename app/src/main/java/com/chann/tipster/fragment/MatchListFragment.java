package com.chann.tipster.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chann.tipster.R;
import com.chann.tipster.activity.OddsActivity;
import com.chann.tipster.adapter.MatchAdapter;
import com.chann.tipster.data.League;
import com.chann.tipster.data.LeagueData;
import com.chann.tipster.data.MatchList;
import com.chann.tipster.data.Token;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.chann.tipster.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment implements OnHolderItemClickListener {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
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

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new MatchAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       RetrofitService.getApiEnd().getMatchList(Token.token).enqueue(new Callback<MatchList>() {
           @Override
           public void onResponse(Call<MatchList> call, Response<MatchList> response) {
               if(response.isSuccessful()){
                   if(response.body().isSuccess){
                       adapter.addData(response.body().leagueData);
                       adapter.notifyDataSetChanged();

                       Log.e("list", String.valueOf(response.body().leagueData.size()));
                   }
               }
           }

           @Override
           public void onFailure(Call<MatchList> call, Throwable t) {

           }
       });

    }


    @Override
    public void onHolderitemClick(LeagueData leagueData) {
        startActivity(OddsActivity.getInstance(getContext() , leagueData));
    }
}

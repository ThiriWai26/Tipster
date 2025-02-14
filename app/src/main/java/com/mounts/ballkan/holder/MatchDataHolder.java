package com.mounts.ballkan.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.ballkan.R;
import com.mounts.ballkan.adapter.MatchItemAdapter;
import com.mounts.ballkan.data.MatchListData;
import com.mounts.ballkan.holderInterface.OnHolderItemClickListener;

public class MatchDataHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;
    private OnHolderItemClickListener listener;
    private TextView tvLeageName;
    private MatchItemAdapter adapter;

    
    public MatchDataHolder(@NonNull View itemView , OnHolderItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        init(itemView);
    }

    private void init(View itemView) {


        tvLeageName = itemView.findViewById(R.id.tv_league_name);
        recyclerView = itemView.findViewById(R.id.recyclerView);
        adapter = new MatchItemAdapter(listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    public void bindData (final MatchListData matchListData ){


            tvLeageName.setText(matchListData.leagueName);
            Log.e("leagueTitle", matchListData.leagueName);
            adapter.addData(matchListData.matchData);


    }

    public static MatchDataHolder create (LayoutInflater inflater , ViewGroup parent , OnHolderItemClickListener listener){

        View view = inflater.inflate(R.layout.league_data , parent , false);
        return new MatchDataHolder( view , listener);
    }
}

package com.chann.tipster.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.data.MatchData;
import com.chann.tipster.holder.MatchItemHolder;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MatchItemAdapter extends RecyclerView.Adapter<MatchItemHolder> {

    private List<MatchData> matchData = new ArrayList<>();
    private OnHolderItemClickListener listener;


    public MatchItemAdapter(OnHolderItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return MatchItemHolder.create(inflater, parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchItemHolder holder, final int position) {

        if (position < matchData.size()-1)
            holder.bindData(matchData.get(position), false );

        if(position == matchData.size()-1)
            holder.bindData(matchData.get(position) , true);

        Log.e("match name",matchData.get(position).localTeamName);
    }

    @Override
    public int getItemCount() {
        return matchData.size();
    }

    public void addData(final List<MatchData> matchData ) {

        Log.e("laeagueSize",String.valueOf(matchData.size()));
        this.matchData = matchData;
        notifyDataSetChanged();
    }


}

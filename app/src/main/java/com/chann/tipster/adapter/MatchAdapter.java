package com.chann.tipster.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.data.LeagueData;
import com.chann.tipster.holder.MatchHolder;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {

    private List<LeagueData> leagueData = new ArrayList<>();
    private OnHolderItemClickListener listener;
    public MatchAdapter(OnHolderItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return MatchHolder.create(inflater,parent,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {

        holder.bindData(leagueData.get(position));
    }

    @Override
    public int getItemCount() {
        return leagueData.size();
    }

    public void addData(List<LeagueData> leagueData){
        this.leagueData = leagueData;
        notifyDataSetChanged();
    }



}

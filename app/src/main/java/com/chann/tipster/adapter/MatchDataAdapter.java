package com.chann.tipster.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.data.MatchListData;
import com.chann.tipster.holder.MatchDataHolder;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MatchDataAdapter extends RecyclerView.Adapter<MatchDataHolder> {

    private OnHolderItemClickListener listener;
    private List<MatchListData> matchListDataList;

    public MatchDataAdapter(OnHolderItemClickListener listener){
        this.listener = listener;
        matchListDataList = new ArrayList<>();
    }
    @NonNull
    @Override
    public MatchDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return MatchDataHolder.create(inflater , parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchDataHolder holder, int position) {

        holder.bindData(matchListDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return matchListDataList.size();
    }

    public void addData (List<MatchListData> matchListDataList){

        this.matchListDataList = matchListDataList;
        notifyDataSetChanged();

    }

}

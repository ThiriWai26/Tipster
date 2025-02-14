package com.mounts.ballkan.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.ballkan.data.MatchListData;
import com.mounts.ballkan.holder.MatchDataHolder;
import com.mounts.ballkan.holderInterface.OnHolderItemClickListener;

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

    public void addData (final List<MatchListData> matchListDataList){
        this.matchListDataList = matchListDataList;
        notifyDataSetChanged();
    }

}

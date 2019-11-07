package com.chann.tipster.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.data.BetHistoryData;
import com.chann.tipster.holder.BetHistoryHolder;

import java.util.ArrayList;
import java.util.List;

public class BetHistoryAdapter extends RecyclerView.Adapter<BetHistoryHolder> {
    private List<BetHistoryData> betHistoryDataList;

    public BetHistoryAdapter(){

        betHistoryDataList = new ArrayList<>();
    }
    @NonNull
    @Override
    public BetHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return BetHistoryHolder.create(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BetHistoryHolder holder, int position) {

        holder.bindData(betHistoryDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return betHistoryDataList.size();
    }

    public void addData(List<BetHistoryData> betHistoryDataList){

        if (this.betHistoryDataList == null)
            this.betHistoryDataList = betHistoryDataList;
        else
            this.betHistoryDataList.addAll(betHistoryDataList);
    }
}

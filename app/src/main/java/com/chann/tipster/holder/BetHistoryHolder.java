package com.chann.tipster.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.data.BetHistoryData;
import com.chann.tipster.data.ExpandAndCollapseViewUtil;
import com.chann.tipster.databinding.BetHistoryItemBinding;
import com.squareup.picasso.Picasso;


public class BetHistoryHolder extends RecyclerView.ViewHolder {

    private BetHistoryItemBinding binding;


    public BetHistoryHolder(@NonNull BetHistoryItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindData(BetHistoryData data) {

        binding.setData(data);
        binding.executePendingBindings();

        Picasso.get().load(data.localLogo).resize(50, 50).into(binding.ivLocalLogo);
        Picasso.get().load(data.visitorLogo).resize(50, 50).into(binding.ivVisitorLogo);


        if (data.betType == 1 && data.label == 1) {
            binding.tvBetTeamName.setText(data.localName);
        }
        else if (data.betType == 1 && data.label == 2) {
            binding.tvBetTeamName.setText(data.visitorName);
        }
        else if (data.betType == 2 && data.label == 1) {
            binding.tvBetTeamName.setText("Over");
        }
        else if (data.betType == 2 && data.label == 2) {
            binding.tvBetTeamName.setText("Under");
        }
        binding.layout1.setOnClickListener(view -> {
            if(binding.layout.getVisibility() == View.GONE){
                ExpandAndCollapseViewUtil.expand(binding.layout , 250);
            }
            else {
                ExpandAndCollapseViewUtil.collapse(binding.layout,250);
            }
        });

    }

    public static BetHistoryHolder create(LayoutInflater inflater, ViewGroup parent) {

        BetHistoryItemBinding binding = DataBindingUtil.inflate(inflater , R.layout.bet_history_item,parent , false);
        return new BetHistoryHolder(binding);
    }
}

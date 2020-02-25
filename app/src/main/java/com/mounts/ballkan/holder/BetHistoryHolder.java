package com.mounts.ballkan.holder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.mounts.ballkan.R;
import com.mounts.ballkan.databinding.BetHistoryItemBinding;
import com.mounts.ballkan.data.BetHistoryData;
import com.mounts.ballkan.data.ExpandAndCollapseViewUtil;



public class BetHistoryHolder extends RecyclerView.ViewHolder {

    private BetHistoryItemBinding binding;


    public BetHistoryHolder(@NonNull BetHistoryItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindData(BetHistoryData data) {

        binding.setData(data);
        binding.executePendingBindings();

        if (data.handiBetInfo!=null){
            @SuppressLint("DefaultLocale")
            String handiValue = data.handicapOdds.value < 0 ? String.format("%d(%d)", data.handicapOdds.handicap,data.handicapOdds.value) : String.format("%d(+%d)", data.handicapOdds.handicap,data.handicapOdds.value);
            if(data.handicapOdds.label.equals("Home")){
                binding.ivLocalLogo.setText(handiValue);
                binding.ivVisitorLogo.setText("");
            }

            if(data.handicapOdds.label.equals("Away")){
                binding.ivVisitorLogo.setText(handiValue);
                binding.ivLocalLogo.setText("");
            }
        }

        if (data.overUnderBetInfo!=null){
            @SuppressLint("DefaultLocale")
            String overUnder = data.overUnderOdds.value < 0 ? String.format("%d(%d)",data.overUnderOdds.totalScore,data.overUnderOdds.value): String.format("%d(+%d)",data.overUnderOdds.totalScore,data.overUnderOdds.value);
            binding.tvOverUnder.setText(overUnder);
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

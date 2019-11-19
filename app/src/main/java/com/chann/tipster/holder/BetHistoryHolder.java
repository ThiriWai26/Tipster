package com.chann.tipster.holder;

import android.util.Log;
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


public class BetHistoryHolder extends RecyclerView.ViewHolder {

    private BetHistoryItemBinding binding;


    public BetHistoryHolder(@NonNull BetHistoryItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindData(BetHistoryData data) {

        binding.setData(data);
        binding.executePendingBindings();

        String handiValue = data.odds.value < 0 ? String.format("%d(%d)", data.odds.handicap+data.odds.totalScore, data.odds.value) : String.format("%d(+%d)", data.odds.handicap+data.odds.totalScore, data.odds.value);
        if(data.odds.label.equals("Home")){

            binding.ivLocalLogo.setText(handiValue);
            binding.ivVisitorLogo.setText("");
            binding.tvOverUnder.setText("");
        }

        if(data.odds.label.equals("Away")){
            binding.ivVisitorLogo.setText(handiValue);
            binding.ivLocalLogo.setText("");
            binding.tvOverUnder.setText("");
        }
        if(data.odds.label.equals("over_under")) {
            Log.e("handiValue", handiValue);
            binding.tvOverUnder.setText(handiValue);
            binding.ivLocalLogo.setText("");
            binding.ivVisitorLogo.setText("");
        }

        if (data.betType == 1 && data.label == 1) {
            binding.tvBetTeamName.setText(data.match.localName);
        }
        else if (data.betType == 1 && data.label == 2) {
            binding.tvBetTeamName.setText(data.match.visitorName);
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

package com.chann.tipster.holder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.data.PrematchOdds;
import com.chann.tipster.databinding.ItemLeagueBinding;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.squareup.picasso.Picasso;


public class MatchItemHolder extends RecyclerView.ViewHolder{

    private OnHolderItemClickListener listener;
    private ItemLeagueBinding binding;

    public MatchItemHolder(@NonNull ItemLeagueBinding binding , OnHolderItemClickListener listener) {
        super(binding.getRoot());
        this.listener = listener;
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void bindData(final MatchData data , boolean isLastItem){

        binding.setData(data);
        binding.executePendingBindings();
        binding.setItemClickListener(listener);


        if(isLastItem){

            binding.divider.setVisibility(View.GONE);
        }

        if(data.handiCap.label.equals("Home")){

            if(data.handiCap.value>0)
            binding.localHandiCap.setText(data.handiCap.handicap+"(+"+ data.handiCap.value+")");
            else
                binding.localHandiCap.setText(data.handiCap.handicap+"("+ data.handiCap.value+")");

        }else {

            if(data.handiCap.value>0)
            binding.visitorHandiCap.setText(data.handiCap.handicap+"(+"+ data.handiCap.value+")");
            else {
                binding.visitorHandiCap.setText(data.handiCap.handicap+"("+ data.handiCap.value+")");
            }
        }

    }

    public static MatchItemHolder create(LayoutInflater inflater , ViewGroup parent , OnHolderItemClickListener listener){
        ItemLeagueBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_league,parent,false);
        return new MatchItemHolder(binding,listener);
    }

}

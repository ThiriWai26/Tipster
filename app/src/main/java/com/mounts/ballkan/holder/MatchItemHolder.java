package com.mounts.ballkan.holder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mounts.ballkan.R;
import com.mounts.ballkan.data.MatchData;
import com.mounts.ballkan.databinding.ItemLeagueBinding;
import com.mounts.ballkan.holderInterface.OnHolderItemClickListener;


public class MatchItemHolder extends RecyclerView.ViewHolder{

    private OnHolderItemClickListener listener;
    private ItemLeagueBinding binding;
    private String handiValue;

    public MatchItemHolder(@NonNull ItemLeagueBinding binding , OnHolderItemClickListener listener) {
        super(binding.getRoot());
        this.listener = listener;
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void bindData(final MatchData data , final boolean isLastItem){

            binding.setData(data);
            binding.executePendingBindings();
            binding.setItemClickListener(listener);


            if (isLastItem) {
                binding.divider.setVisibility(View.GONE);
            }
            else {
                binding.divider.setVisibility(View.VISIBLE);
            }


        if (data.handiCap.handicap == 0) {
            if (data.handiCap.value < 0)
                handiValue = "(L" + data.handiCap.value + ")";

            else
                handiValue = "(L" + "+" + data.handiCap.value + ")";
        } else {
            if (data.handiCap.value <0)
                handiValue = "(" + data.handiCap.handicap + data.handiCap.value + ")";

            else
                handiValue = "(" + data.handiCap.handicap + "+" + data.handiCap.value + ")";

        }

            if (data.handiCap.label.equals("Home")) {

                binding.localHandiCap.setText(handiValue);
                binding.visitorHandiCap.setText("");//30 sec reload မှာ မထည့်ရင်  ပေါက်ကရဖြစ်လို့

            } else {

                binding.visitorHandiCap.setText(handiValue);
                binding.localHandiCap.setText("");//30 sec reload မှာ မထည့်ရင်  ပေါက်ကရဖြစ်လို့
            }

            if(data.overUnder.value < 0){
                binding.overUnder.setText(data.overUnder.totalScore+" "+data.overUnder.value);
            }
            else {
                binding.overUnder.setText(data.overUnder.totalScore+" +"+data.overUnder.value);
            }

    }

    public static MatchItemHolder create(LayoutInflater inflater , ViewGroup parent , OnHolderItemClickListener listener){
        ItemLeagueBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_league,parent,false);
        return new MatchItemHolder(binding,listener);
    }

}

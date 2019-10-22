package com.chann.tipster.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.api.OnClickItemListener;
import com.chann.tipster.data.UserStanding;
import com.chann.tipster.databinding.HistoryItemBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

public class UserStandingHolder extends RecyclerView.ViewHolder {

    private OnClickItemListener listener;
    private HistoryItemBinding binding;

    public UserStandingHolder(@NonNull HistoryItemBinding binding, OnClickItemListener listener) {
        super(binding.getRoot());
        this.listener = listener;
        this.binding = binding;
    }


    public void bindData (UserStanding data , int positon){
        binding.setData(data);
        binding.executePendingBindings();
        binding.setItemListener(listener);
        Picasso.get().load(RetrofitService.BASE_URL + "/api/get_image/" + data.image).resize(50, 50).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);
        binding.tvNo.setText(String.valueOf(positon + 1));
    }
    public static UserStandingHolder create (LayoutInflater inflater , ViewGroup parent , OnClickItemListener listener){

        HistoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false);
        return new UserStandingHolder(binding, listener);
    }
}

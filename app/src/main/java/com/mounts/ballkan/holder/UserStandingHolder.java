package com.mounts.ballkan.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.mounts.ballkan.R;
import com.mounts.ballkan.api.OnClickItemListener;
import com.mounts.ballkan.data.UserStanding;
import com.mounts.ballkan.databinding.HistoryItemBinding;
import com.mounts.ballkan.retrofit.RetrofitService;
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
        if(data.image != null){
            Picasso.get().load(RetrofitService.BASE_URL + "/api/get_image/" + data.image).resize(50, 50).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);
        }
        else {
            Picasso.get().load(data.fbProfile).resize(50, 50).placeholder(R.drawable.logo_tipstar).into(binding.ivProfile);

        }

        binding.tvNo.setText(String.valueOf(positon + 1));
    }
    public static UserStandingHolder create (LayoutInflater inflater , ViewGroup parent , OnClickItemListener listener){

        HistoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false);
        return new UserStandingHolder(binding, listener);
    }
}

package com.mounts.ballkan.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.ballkan.api.OnClickItemListener;
import com.mounts.ballkan.data.UserStanding;
import com.mounts.ballkan.holder.UserStandingHolder;

import java.util.ArrayList;
import java.util.List;

public class UserStandingAdapter extends RecyclerView.Adapter<UserStandingHolder> {

    private List<UserStanding> userStandings;
    private OnClickItemListener listener;

    public UserStandingAdapter(OnClickItemListener listener){

        this.listener = listener;
        userStandings = new ArrayList<>();
    }
    @NonNull
    @Override
    public UserStandingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return UserStandingHolder.create(inflater , parent , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStandingHolder holder, int position) {

        holder.bindData(userStandings.get(position) , position);
    }

    @Override
    public int getItemCount() {
        return userStandings.size();
    }

    public void addData(List<UserStanding> userStandings){

        if (this.userStandings == null)
            this.userStandings = userStandings;
        else
            this.userStandings.addAll(userStandings);
    }
}

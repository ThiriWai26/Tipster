package com.chann.tipster.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.api.OnClickItemListener;
import com.chann.tipster.data.UserStanding;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

public class UserStandingHolder extends RecyclerView.ViewHolder {

    private OnClickItemListener listener;
    private TextView tvNo, tvName , tvPoint;
    private ImageView imgProfile;
    private LinearLayout layout;
    public UserStandingHolder(@NonNull View itemView , OnClickItemListener listener) {
        super(itemView);
        this.listener = listener;
        init(itemView);
    }

    private void init(View itemView) {

        layout = itemView.findViewById(R.id.layout);
        tvNo = itemView.findViewById(R.id.tv_no);
        tvName = itemView.findViewById(R.id.tv_name);
        tvPoint = itemView.findViewById(R.id.tv_point);

        imgProfile = itemView.findViewById(R.id.iv_profile);

    }

    public void bindData (UserStanding data , int positon){
        Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+data.image).resize(50, 50).into(imgProfile);
        tvNo.setText(String.valueOf(positon+1));
        tvName.setText(data.name);
        tvPoint.setText(String.valueOf(data.coin));

        layout.setOnClickListener(view -> listener.onHolderitemClick(data.userId));



    }
    public static UserStandingHolder create (LayoutInflater inflater , ViewGroup parent , OnClickItemListener listener){

        View view = inflater.inflate(R.layout.history_item , parent , false);
        return new UserStandingHolder(view , listener);
    }
}

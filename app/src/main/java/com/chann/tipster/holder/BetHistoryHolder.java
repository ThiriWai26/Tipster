package com.chann.tipster.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.data.BetHistoryData;
import com.chann.tipster.data.ExpandAndCollapseViewUtil;
import com.squareup.picasso.Picasso;


public class BetHistoryHolder extends RecyclerView.ViewHolder {

    private RelativeLayout layout;
    private LinearLayout layout1;
    private TextView tvLocalName, tvVisitorName, tvLocalScore, tvVisitorScore, tvBetTeamName, tvBetAmount, tvDate;
    private ImageView ivLocalLogo, ivVisitorLogo;

    public BetHistoryHolder(@NonNull View itemView) {
        super(itemView);

        init(itemView);
    }

    private void init(View itemView) {

        layout = itemView.findViewById(R.id.layout);
        layout1 = itemView.findViewById(R.id.layout1);
        tvLocalName = itemView.findViewById(R.id.tv_local_name);
        tvVisitorName = itemView.findViewById(R.id.tv_visitor_name);
        tvLocalScore = itemView.findViewById(R.id.tv_local_score);
        tvVisitorScore = itemView.findViewById(R.id.tv_visitor_score);
        tvBetAmount = itemView.findViewById(R.id.tv_bet_amount);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvBetTeamName = itemView.findViewById(R.id.tv_bet_team_name);

        ivLocalLogo = itemView.findViewById(R.id.iv_local_logo);
        ivVisitorLogo = itemView.findViewById(R.id.iv_visitor_logo);
        layout1.setOnClickListener(view -> {
            if(layout.getVisibility() == View.GONE){
                ExpandAndCollapseViewUtil.expand(layout , 250);
            }
            else {
                ExpandAndCollapseViewUtil.collapse(layout,250);
            }
        });

    }

    public void bindData(BetHistoryData data) {

        Picasso.get().load(data.localLogo).resize(50, 50).into(ivLocalLogo);
        Picasso.get().load(data.visitorLogo).resize(50, 50).into(ivVisitorLogo);

        tvLocalName.setText(data.localName);
        tvVisitorName.setText(data.visitorName);

        tvLocalScore.setText(String.valueOf(data.localScore));
        tvVisitorScore.setText(String.valueOf(data.visitorScore));

        tvBetAmount.setText(String.valueOf(data.coin));
        tvDate.setText(data.date);

        if (data.betType == 1 && data.label == 1) {
            tvBetTeamName.setText(data.localName);
        }
        else if (data.betType == 1 && data.label == 2) {
            tvBetTeamName.setText(data.visitorName);
        }
        else if (data.betType == 2 && data.label == 1) {
            tvBetTeamName.setText("Over");
        }
        else if (data.betType == 2 && data.label == 2) {
            tvBetTeamName.setText("Under");
        }


    }

    public static BetHistoryHolder create(LayoutInflater inflater, ViewGroup parent) {

        View view = inflater.inflate(R.layout.bet_history_item, parent, false);
        return new BetHistoryHolder(view);
    }
}

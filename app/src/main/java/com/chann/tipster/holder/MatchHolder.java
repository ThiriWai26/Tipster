package com.chann.tipster.holder;

import android.annotation.SuppressLint;
import android.print.PrinterCapabilitiesInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.data.LeagueData;
import com.chann.tipster.data.PrematchOdds;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.squareup.picasso.Picasso;


public class MatchHolder extends RecyclerView.ViewHolder{

    private OnHolderItemClickListener listener;

    private TextView tvTime , tvLocalScore, tvVisitorScore, tvLocalHandi ,tvVisitorHandi , tvLocalName , tvVistiorName;
    private ImageView imgVisitor, imgLocal;
    private ConstraintLayout viewLeague;

    public MatchHolder(@NonNull View itemView , OnHolderItemClickListener listener) {
        super(itemView);
        this.listener = listener;

        initHolder(itemView);
    }

    private void initHolder(View itemView) {
        viewLeague = itemView.findViewById(R.id.viewLeague);

        tvTime = itemView.findViewById( R.id.tvTime);
        tvLocalScore = itemView.findViewById(R.id.tvLocalTeamScore);
        tvVisitorScore = itemView.findViewById(R.id.tvVisitorTeamScore);
        tvLocalHandi = itemView.findViewById(R.id.localHandiCap);
        tvVisitorHandi = itemView.findViewById(R.id.visitorHandiCap);
        tvLocalName = itemView.findViewById(R.id.tvLocalTeamName);
        tvVistiorName = itemView.findViewById(R.id.tvVisitorTeamName);

        imgVisitor = itemView.findViewById(R.id.visitorProfile);
        imgLocal = itemView.findViewById(R.id.localProfile);


    }

    @SuppressLint("SetTextI18n")
    public void bindData(final LeagueData data){

        tvTime.setText(data.time);
        tvLocalScore.setText(String.valueOf(data.score.localScore));
        tvVisitorScore.setText(String.valueOf(data.score.visitorScore));
        tvLocalName.setText(data.localTeam.name);
        tvVistiorName.setText(data.visitorTeam.name);

        Picasso.get().load(data.localTeam.logo).resize(50,50).into(imgLocal);
        Picasso.get().load(data.visitorTeam.logo).resize(50,50).into(imgVisitor);

        PrematchOdds localOdds = data.prematchOddsList.get(0);
        PrematchOdds visitorOdds = data.prematchOddsList.get(1);
        if(localOdds.teamType>0){

            if(localOdds.value>0)
            tvLocalHandi.setText(localOdds.handicap+"(+"+ localOdds.value+")");
            else
                tvLocalHandi.setText(localOdds.handicap+"("+ localOdds.value+")");

        }else {

            if(visitorOdds.value>0)
            tvVisitorHandi.setText(visitorOdds.handicap+"(+"+ visitorOdds.value+")");
            else {
                tvVisitorHandi.setText(visitorOdds.handicap+"("+ visitorOdds.value+")");
            }
        }

        viewLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onHolderitemClick(data);
            }
        });
    }

    public static MatchHolder create(LayoutInflater inflater , ViewGroup parent , OnHolderItemClickListener listener){

        View  view = inflater.inflate(R.layout.item_league, parent , false);
        return new MatchHolder(view,listener);
    }

}

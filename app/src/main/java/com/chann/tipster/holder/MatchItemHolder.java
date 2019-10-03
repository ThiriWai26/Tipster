package com.chann.tipster.holder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chann.tipster.R;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.data.PrematchOdds;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.squareup.picasso.Picasso;


public class MatchItemHolder extends RecyclerView.ViewHolder{

    private OnHolderItemClickListener listener;

    private TextView tvTime , tvLocalScore, tvVisitorScore, tvLocalHandi ,tvVisitorHandi , tvLocalName , tvVistiorName;
    private ImageView imgVisitor, imgLocal;
    private ConstraintLayout viewLeague;

    public MatchItemHolder(@NonNull View itemView , OnHolderItemClickListener listener) {
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
    public void bindData(final MatchData data){

        tvTime.setText(data.time);
        tvLocalScore.setText(String.valueOf(data.localTeamScore));
        tvVisitorScore.setText(String.valueOf(data.visitorTeamScore));
        tvLocalName.setText(data.localTeamName);
        tvVistiorName.setText(data.visitorTeamName);

        Picasso.get().load(data.localTeamLogo).resize(50,50).into(imgLocal);
        Picasso.get().load(data.visitorTeamLogo).resize(50,50).into(imgVisitor);

        if(data.handiCap.label.equals("Home")){

            if(data.handiCap.value>0)
            tvLocalHandi.setText(data.handiCap.handicap+"(+"+ data.handiCap.value+")");
            else
                tvLocalHandi.setText(data.handiCap.handicap+"("+ data.handiCap.value+")");

        }else {

            if(data.handiCap.value>0)
            tvVisitorHandi.setText(data.handiCap.handicap+"(+"+ data.handiCap.value+")");
            else {
                tvVisitorHandi.setText(data.handiCap.handicap+"("+ data.handiCap.value+")");
            }
        }

        viewLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onHolderitemClick(data);
            }
        });
    }

    public static MatchItemHolder create(LayoutInflater inflater , ViewGroup parent , OnHolderItemClickListener listener){

        View  view = inflater.inflate(R.layout.item_league, parent , false);
        return new MatchItemHolder(view,listener);
    }

}

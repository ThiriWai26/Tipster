package com.chann.tipster.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chann.tipster.R;
import com.chann.tipster.data.Data;
import com.chann.tipster.data.LeagueData;
import com.chann.tipster.data.Odds;
import com.chann.tipster.data.OddsData;
import com.chann.tipster.data.RoomListOfLeague;
import com.chann.tipster.data.Token;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OddsActivity extends AppCompatActivity {

    private static LeagueData league;
    private OddsData oddsData;
    private TextView tvTime, tvLocalScore, tvVisitorScore, tvLocalName, tvVistiorName, tvDate;
    private ImageView imgVisitor, imgLocal;

    private ImageView imgLocalProfile, imgVisitorProfile;
    private TextView tvLocalValue, tvVisitorValue;

    private TextView tvOver, tvUnder;
    private static int roomId;

    private int matchId = 1, typeId = 1;

    public static Intent getInstance(Context context, LeagueData leagueData, int room_Id) {
        league = leagueData;
        roomId = room_Id;
        Intent intent = new Intent(context, OddsActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odds);
        init();
        Log.e("roomId",String.valueOf(roomId));

    }

    private void getOddsData() {

        Log.e("league_id",String.valueOf(league.id));
        Log.e("fixture_id",String.valueOf(league.fixtureId));

        RetrofitService.getApiEnd().getOddsData(Token.token, league.id, typeId, roomId).enqueue(new Callback<OddsData>() {
            @Override
            public void onResponse(Call<OddsData> call, Response<OddsData> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        Log.e("This is ", "successful");

                        oddsData = response.body();
                        bindData(oddsData.odds);


                    }
                }
                Log.e("This is ", "unsuccessful");
            }

            @Override
            public void onFailure(Call<OddsData> call, Throwable t) {
                Log.e("This is ", t.toString());
            }
        });
    }


    private void init() {
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        tvLocalScore = findViewById(R.id.tvLocalTeamScore);
        tvVisitorScore = findViewById(R.id.tvVisitorTeamScore);

        tvLocalName = findViewById(R.id.tvLocalTeamName);
        tvVistiorName = findViewById(R.id.tvVisitorTeamName);

        imgVisitor = findViewById(R.id.visitorProfile);
        imgLocal = findViewById(R.id.localProfile);

        imgLocalProfile = findViewById(R.id.imgLocalProfile);
        imgVisitorProfile = findViewById(R.id.imgVisitorProfile);

        tvLocalValue = findViewById(R.id.tvLocalValue);
        tvVisitorValue = findViewById(R.id.tvVisitorValue);

        tvOver = findViewById(R.id.tvOver);
        tvUnder = findViewById(R.id.tvUnder);

        getOddsData();
    }


    private void bindData(Odds odds) {

        Log.e("LastUpdate", odds.handicap.lastUpdate.date);
        tvDate.setText(league.date);
        tvTime.setText(league.time);
        tvLocalScore.setText(String.valueOf(league.score.localScore));
        tvVisitorScore.setText(String.valueOf(league.score.visitorScore));
        tvLocalName.setText(league.localTeam.name);
        tvVistiorName.setText(league.visitorTeam.name);

        Picasso.get().load(league.localTeam.logo).resize(50, 50).into(imgLocal);
        Picasso.get().load(league.visitorTeam.logo).resize(50, 50).into(imgVisitor);

        Picasso.get().load(league.localTeam.logo).resize(50, 50).into(imgLocalProfile);
        Picasso.get().load(league.visitorTeam.logo).resize(50, 50).into(imgVisitorProfile);

        Data localData = odds.handicap.data.get(0);
        Data visitorData = odds.handicap.data.get(1);

        Data over = odds.overUnder.data.get(0);
        Data under = odds.overUnder.data.get(1);

        if (localData.teamType > 0) {
            if (localData.value > 0) {

                Log.e("yout", "tal");
                @SuppressLint("DefaultLocale") String localvalue = String.format("%d(+%d)", localData.handicap, localData.value);
                tvLocalValue.setText(localvalue);
            } else {

                Log.e("yout", "tal");
                @SuppressLint("DefaultLocale") String localvalue = String.format("%d(%d)", localData.handicap, localData.value);
                tvLocalValue.setText(localvalue);
            }

        } else {
            if (visitorData.value > 0) {

                Log.e("yout", "tal");
                String visitorValue = String.format("%d(+%d)", visitorData.handicap, visitorData.value);
                tvLocalValue.setText(visitorValue);
            } else {

                Log.e("yout", "tal");
                String visitorValue = String.format("%d(%d)", visitorData.handicap, visitorData.value);
                tvLocalValue.setText(visitorValue);
            }
        }

        if (over.value > 0) {

            String overValue = String.format("%d(+%d)", over.handicap, over.value);
            tvOver.setText(overValue);

        } else {

            String overValue = String.format("%d(%d)", over.handicap, over.value);
            tvOver.setText(overValue);
        }

        if (under.value > 0) {
            String underValue = String.format("%d(+%d)", under.handicap, under.value);
            tvUnder.setText(underValue);
        } else {
            String underValue = String.format("%d(%d)", under.handicap, under.value);
            tvUnder.setText(underValue);
        }

    }

    public void onLocalTeamBetClick(View view) {

        final Dialog dialog = new Dialog(this);
        RadioButton minBtn , maxBtn;
        ImageView imgTeamLogo;
        TextView tvCancel ,tvBet ,tvHandicapValue , labelOverUnder , tvOverUnderValue;

        dialog.setContentView(R.layout.dialog_bet);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        labelOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);

        labelOverUnder.setVisibility(View.GONE);
        tvOverUnderValue.setVisibility(View.GONE);

        Picasso.get().load(league.localTeam.logo).resize(50, 50).into(imgTeamLogo);
        tvHandicapValue.setText(tvLocalValue.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void onVisitorTeamBetClick(View view) {
        final Dialog dialog = new Dialog(this);
        RadioButton minBtn, maxBtn;
        ImageView imgTeamLogo;
        TextView tvCancel ,tvBet ,tvHandicapValue ,labelOverUnder , tvOverUnderValue;

        dialog.setContentView(R.layout.dialog_bet);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);

        Picasso.get().load(league.visitorTeam.logo).resize(50, 50).into(imgTeamLogo);
        tvHandicapValue.setText(tvVisitorValue.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        labelOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);

        labelOverUnder.setVisibility(View.GONE);
        tvOverUnderValue.setVisibility(View.GONE);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onOverBetClick(View view) {
        final Dialog dialog = new Dialog(this);
        RadioButton minBtn, maxBtn;
        TextView tvOverUnder,tvCancel ,tvBet ,tvOverUnderValue , tvHanddicapValue;
        ImageView imgTeamLogo;

        dialog.setContentView(R.layout.dialog_bet);
        tvOverUnder = dialog.findViewById(R.id.labelOverUnder);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        tvHanddicapValue = dialog.findViewById(R.id.tvHandicapValue);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

        tvHanddicapValue.setVisibility(View.GONE);
        imgTeamLogo.setVisibility(View.GONE);

        tvOverUnder.setText("Over");
        tvOverUnderValue.setText(tvOver.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void onUnderBetClick(View view) {
        final Dialog dialog = new Dialog(this);
        RadioButton minBtn, maxBtn;
        TextView tvOverUnder,tvCancel ,tvBet , tvOverUnderValue ,tvHandicapValue;
        ImageView  imgTeamLogo;

        dialog.setContentView(R.layout.dialog_bet);
        tvCancel = dialog.findViewById(R.id.tvCancel);
        tvBet = dialog.findViewById(R.id.tvBet);

        tvOverUnder= dialog.findViewById(R.id.labelOverUnder);
        tvOverUnderValue = dialog.findViewById(R.id.tvOverUnderValue);
        minBtn = dialog.findViewById(R.id.minAmount);
        maxBtn = dialog.findViewById(R.id.maxAmount);
        tvHandicapValue = dialog.findViewById(R.id.tvHandicapValue);
        imgTeamLogo = dialog.findViewById(R.id.imgTeamLogo);

        tvHandicapValue.setVisibility(View.GONE);
        imgTeamLogo.setVisibility(View.GONE);

        tvOverUnder.setText("Under");
        tvOverUnderValue.setText(tvUnder.getText().toString());
        minBtn.setText(String.valueOf(oddsData.point.min));
        maxBtn.setText(String.valueOf(oddsData.point.max));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
